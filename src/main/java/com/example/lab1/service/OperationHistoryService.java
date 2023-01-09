package com.example.lab1.service;

import com.example.lab1.dto.OperationHistoryDTO;
import com.example.lab1.exceptions.ResourceNotFoundException;
import com.example.lab1.model.*;
import com.example.lab1.repositories.*;
import com.example.lab1.utils.OperationHistoryMappingUtils;
import com.example.lab1.utils.IMappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class OperationHistoryService {
    private final OperationHistoryRepository operationHistoryRepository;
private final AccountRepository accountRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final CurrencyRepository currencyRepository;
    private final IMappingUtils<OperationHistory, OperationHistoryDTO> operationHistoryMappingUtils;
    private Calendar currentDate = Calendar.getInstance();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final ContractRepository contractRepository;
    private final ConfigRepository configRepository;

    @Autowired
    public OperationHistoryService(OperationHistoryRepository operationHistoryRepository,
                                   ContractTypeRepository contractTypeRepository,
                                   AccountRepository accountRepository,
                                   CurrencyRepository currencyRepository,
                                   ContractRepository contractRepository, ConfigRepository configRepository) {
        this.operationHistoryRepository = operationHistoryRepository;
        this.accountRepository = accountRepository;
        this.contractTypeRepository = contractTypeRepository;
        this.currencyRepository = currencyRepository;
        this.contractRepository = contractRepository;
        this.configRepository = configRepository;
        this.operationHistoryMappingUtils = new OperationHistoryMappingUtils(accountRepository, currencyRepository);
        currentDate.set(Calendar.HOUR, 0);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);
    }


    public OperationHistoryDTO createOperationHistory(OperationHistoryDTO operationHistoryDTO) {

        OperationHistory operationHistory = operationHistoryMappingUtils.mapToEntity(operationHistoryDTO);
        OperationHistory savedOperationHistory = this.operationHistoryRepository.save(operationHistory);
        return operationHistoryMappingUtils.mapToDto(savedOperationHistory);
    }

    public List<OperationHistoryDTO> getOperationHistories(){
        return this.operationHistoryRepository.findAll().stream()
                .map(operationHistoryMappingUtils::mapToDto)
                .collect(Collectors.toList());
    }

    public OperationHistoryDTO getOperationHistory(Long id){
        OperationHistory operationHistory = this.operationHistoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Operation history not found")
        );

        return  operationHistoryMappingUtils.mapToDto(operationHistory);
    }

    public OperationHistoryDTO updateOperationHistory(OperationHistoryDTO newOperationHistory, Long id){
        return this.operationHistoryRepository.findById(id)
                .map(operationHistory -> {
                    operationHistory = operationHistoryMappingUtils.mapToEntity(newOperationHistory);
                    return operationHistoryMappingUtils.mapToDto(this.operationHistoryRepository.save(operationHistory));
                })
                .orElseGet(()->{
                    newOperationHistory.setId(id);
                    OperationHistory operationHistory = operationHistoryMappingUtils.mapToEntity(newOperationHistory);
                    return operationHistoryMappingUtils.mapToDto(this.operationHistoryRepository.save(operationHistory));
                });
    }

    public void removeOperationHistory(Long id){
        this.operationHistoryRepository.deleteById(id);
    }

    public String closeDay() {
        CheckDeposits();
        EndFinanceDay();
        currentDate.add(Calendar.DATE, 1);
        return dateFormat.format(currentDate.getTime());
    }

    private void EndFinanceDay() {
        for (Account account:accountRepository.findAll()) {
            account.setSaldo(account.getSaldo() + account.getCredit()+account.getDebit());
            account.setDebit(0);
            account.setCredit(0);
            accountRepository.save(account);
        }
    }

    private void CheckDeposits() {
        for(Contract contract: contractRepository.findAll())
        {
            switch (contractTypeRepository.findById(contract.getContractTypeId()).get().getName()){
                case "Депозит отзывный":
                    ProcessRevocableDeposit(contract);
                    break;
                case "Кредит аннуитетный":
                    ProcessAnnuityCredit(contract);
                    break;
            }
        }
    }

    private void ProcessAnnuityCredit(Contract contract) {
        OperationHistory operation;
        Account bankAccount = accountRepository.findByAccountNumber("1010000000000");
        Account cashAccount = accountRepository.findByAccountNumber("7327000000000");
        Account persentAccount = accountRepository.findById(contract.getPersentAccountId()).get();
        Account currentAccount = accountRepository.findById(contract.getCurrentAccountId()).get();
        Currency currency = currencyRepository.findById(contract.getCurrencyId()).get();

        if(contract.getStartDate().compareTo(currentDate) <= 0
                && contract.getEndDate().compareTo(currentDate) >= 0){
            Optional<OperationHistory> lastOperation = operationHistoryRepository.findFirstByFromAccountAndToAccountOrderByDateDesc(cashAccount.getId(), contract.getPersentAccountId());
            Calendar lastOperationDate = (lastOperation.isPresent()) ? lastOperation.get().getDate() : contract.getStartDate();

            Calendar nextPaymentMonth = (Calendar) lastOperationDate.clone();
            nextPaymentMonth.add(Calendar.MONTH, 1);
            Calendar nextPayment = (Calendar) nextPaymentMonth.clone();
            if(nextPayment.compareTo(contract.getEndDate()) > 0){
                nextPayment = (Calendar) contract.getEndDate().clone();
            }
            long monthPeriod = TimeUnit.MILLISECONDS.toDays(nextPaymentMonth.getTimeInMillis() - lastOperationDate.getTimeInMillis());
            long period = TimeUnit.MILLISECONDS.toDays(nextPayment.getTimeInMillis() - lastOperationDate.getTimeInMillis());
            long depositPeriod = TimeUnit.MILLISECONDS.toDays(currentDate.getTimeInMillis()- lastOperationDate.getTimeInMillis());

            int diffYear = contract.getEndDate().get(Calendar.YEAR) - contract.getStartDate().get(Calendar.YEAR);
            int monthCount = diffYear * 12 + contract.getEndDate().get(Calendar.MONTH) - contract.getStartDate().get(Calendar.MONTH);

            if(contract.getEndDate().get(Calendar.DATE) - contract.getStartDate().get(Calendar.DATE) != 0){
                monthCount++;
            }
            double kof = contract.getPercent()/100 * Math.pow(1 + contract.getPercent()/100, monthCount) / (Math.pow(1 + contract.getPercent()/100, monthCount) - 1);
            double payment = contract.getSum() * kof;
            double charge = (payment - contract.getSum() / monthCount) / monthPeriod;

            if (depositPeriod != period) {
                // Начисление процентов банком
                bankAccount.setCredit(bankAccount.getCredit() + charge * currency.getExchangeRate());
                accountRepository.save(bankAccount);

                persentAccount.setCredit(persentAccount.getCredit() - charge);
                accountRepository.save(persentAccount);

                operation = new OperationHistory(
                        contract.getPersentAccountId(),
                        bankAccount.getId(),
                        charge,
                        "Начисление процентов банком",
                        currentDate,
                        contract.getCurrencyId());
                operationHistoryRepository.save(operation);
            }


            if (depositPeriod == period || contract.getEndDate().equals(currentDate))
            {
                //Внесение % в кассу
                cashAccount.setDebit(cashAccount.getDebit() + charge * currency.getExchangeRate() * period);
                accountRepository.save(cashAccount);

                operation = new OperationHistory(
                        0,
                        cashAccount.getId(),
                        charge * period,
                        "Внесение % в кассу",
                        currentDate,
                        contract.getCurrencyId());
                operationHistoryRepository.save(operation);

                //Перевод % из кассы

                cashAccount.setCredit(cashAccount.getCredit() - charge * currency.getExchangeRate() * period);
                accountRepository.save(cashAccount);

                persentAccount.setDebit(persentAccount.getDebit() + charge * period);
                accountRepository.save(persentAccount);

                operation = new OperationHistory(
                        cashAccount.getId(),
                        contract.getPersentAccountId(),
                        charge * period,
                        "Перевод % из кассы",
                        currentDate,
                        contract.getCurrencyId());
                operationHistoryRepository.save(operation);

                //Внесение денег в кассу
                cashAccount.setDebit(cashAccount.getDebit() + ( contract.getSum() / monthCount) * currency.getExchangeRate());
                accountRepository.save(cashAccount);

                operation = new OperationHistory(
                        0,
                        cashAccount.getId(),
                        contract.getSum() / monthCount,
                        "Внесение денег в кассу",
                        currentDate,
                        contract.getCurrencyId());
                operationHistoryRepository.save(operation);

                //Погашение долга за кредит из кассы

                cashAccount.setCredit(cashAccount.getCredit() - ( contract.getSum() / monthCount) * currency.getExchangeRate());
                accountRepository.save(cashAccount);

                currentAccount.setDebit(currentAccount.getDebit() + ( contract.getSum() / monthCount));
                accountRepository.save(currentAccount);

                operation = new OperationHistory(
                        cashAccount.getId(),
                        contract.getCurrentAccountId(),
                        (contract.getSum() / monthCount),
                        "Погашение долга за кредит из кассы",
                        currentDate,
                        contract.getCurrencyId());
                operationHistoryRepository.save(operation);

            }
        }

        if (contract.getEndDate().equals(currentDate))
        {
            CloseCredit(contract);
        }
    }

    private void CloseCredit(Contract contract) {
        Account bankAccount = accountRepository.findByAccountNumber("1010000000000");
        Currency currency = currencyRepository.findById(contract.getCurrencyId()).get();
        Account currentAccount = accountRepository.findById(contract.getCurrentAccountId()).get();

        bankAccount.setCredit(bankAccount.getCredit() + contract.getSum() * currency.getExchangeRate());
        accountRepository.save(bankAccount);
        currentAccount.setCredit(currentAccount.getCredit() - contract.getSum());
        accountRepository.save(currentAccount);
        OperationHistory operation = new OperationHistory(
                contract.getCurrentAccountId(),
                bankAccount.getId(),
                contract.getSum(),
                "Окончание кредита",
                currentDate,
                contract.getCurrencyId());
        operationHistoryRepository.save(operation);
    }

    private double CalculateCharge(double sum, double percent, long period) {
        return (sum * percent)/100/ period;
    }

    private void ProcessRevocableDeposit(Contract contract) {
        Account bankAccount = accountRepository.findByAccountNumber("1010000000000");
        Account cashAccount = accountRepository.findByAccountNumber("7327000000000");
        Account persentAccount = accountRepository.findById(contract.getPersentAccountId()).get();
        Currency currency = currencyRepository.findById(contract.getCurrencyId()).get();
        Config config = this.configRepository.findAll().get(0);
        OperationHistory operation;
        if(contract.getStartDate().compareTo(currentDate) <= 0
                && contract.getEndDate().compareTo(currentDate) >= 0){
            Optional<OperationHistory> lastOperation = operationHistoryRepository.findFirstByFromAccountAndToAccountOrderByDateDesc(contract.getPersentAccountId(), cashAccount.getId());
            Calendar lastOperationDate = (lastOperation.isPresent()) ? lastOperation.get().getDate() : contract.getStartDate();

            Calendar nextPaymentMonth = (Calendar) lastOperationDate.clone();
            nextPaymentMonth.add(Calendar.MONTH, 1);
            Calendar nextPayment = (Calendar) nextPaymentMonth.clone();
            if(nextPayment.compareTo(contract.getEndDate()) > 0){
                nextPayment = (Calendar) contract.getEndDate().clone();
            }
            long monthPeriod = TimeUnit.MILLISECONDS.toDays(nextPaymentMonth.getTimeInMillis() - lastOperationDate.getTimeInMillis());
            long period = TimeUnit.MILLISECONDS.toDays(nextPayment.getTimeInMillis() - lastOperationDate.getTimeInMillis());

            long depositPeriod = TimeUnit.MILLISECONDS.toDays(currentDate.getTimeInMillis()- lastOperationDate.getTimeInMillis());
            double charge = CalculateCharge(contract.getSum(), contract.getPercent(), monthPeriod);

            if (depositPeriod != period){
                //Начисление % по депозиту

                bankAccount.setDebit(bankAccount.getDebit() - charge * currency.getExchangeRate());
                accountRepository.save(bankAccount);

                persentAccount.setCredit(persentAccount.getCredit() + charge);
                accountRepository.save(persentAccount);

                operation = new OperationHistory(
                        bankAccount.getId(),
                        contract.getPersentAccountId(),
                        charge,
                        "Начисление % по депозиту",
                        currentDate,
                        contract.getCurrencyId());
                operationHistoryRepository.save(operation);
            }

            if (depositPeriod == period || contract.getEndDate().equals(currentDate))
            {
                if(config.isWithdrawalThroughCash()) {
                    //Перевод % в кассу
                    cashAccount.setDebit(cashAccount.getDebit() + charge * currency.getExchangeRate() * period);
                    accountRepository.save(cashAccount);

                    persentAccount.setDebit(persentAccount.getDebit() - charge * period);
                    accountRepository.save(persentAccount);

                    operation = new OperationHistory(
                            contract.getPersentAccountId(),
                            cashAccount.getId(),
                            charge * period,
                            "Перевод % в кассу",
                            currentDate,
                            contract.getCurrencyId());
                    operationHistoryRepository.save(operation);

                    //Вывод % из кассы
                    cashAccount.setCredit(cashAccount.getCredit() - charge * currency.getExchangeRate() * period);
                    accountRepository.save(cashAccount);

                    operation = new OperationHistory(
                            cashAccount.getId(),
                            0,
                            charge * period,
                            "Вывод % из кассы",
                            currentDate,
                            contract.getCurrencyId());
                    operationHistoryRepository.save(operation);
                }
            }
        }

        if (contract.getEndDate().equals(currentDate))
        {
            CloseDeposit(contract);
        }
    }

    private void CloseDeposit(Contract contract) {
        Account bankAccount = accountRepository.findByAccountNumber("1010000000000");
        Currency currency = currencyRepository.findById(contract.getCurrencyId()).get();
        Account currentAccount = accountRepository.findById(contract.getCurrentAccountId()).get();
        Account cashAccount = accountRepository.findByAccountNumber("7327000000000");
        Config config = this.configRepository.findAll().get(0);

        //Окончание депозита
        bankAccount.setDebit(bankAccount.getDebit() - contract.getSum() * currency.getExchangeRate());
        accountRepository.save(bankAccount);
        currentAccount.setCredit(currentAccount.getCredit() + contract.getSum());
        accountRepository.save(currentAccount);
        OperationHistory operation = new OperationHistory(
                bankAccount.getId(),
                contract.getCurrentAccountId(),
                contract.getSum(),
                "Окончание депозита",
                currentDate,
                contract.getCurrencyId());
        operationHistoryRepository.save(operation);
        if(config.isWithdrawalThroughCash()) {

            //Перевод депозита в кассу

            cashAccount.setDebit(cashAccount.getDebit() + contract.getSum() * currency.getExchangeRate());
            accountRepository.save(cashAccount);

            currentAccount.setDebit(currentAccount.getDebit() - contract.getSum());
            accountRepository.save(currentAccount);

            operation = new OperationHistory(
                    contract.getCurrentAccountId(),
                    cashAccount.getId(),
                    contract.getSum(),
                    "Перевод депозита в кассу",
                    currentDate,
                    contract.getCurrencyId());
            operationHistoryRepository.save(operation);

            //Вывод денег из кассы
            cashAccount.setCredit(cashAccount.getCredit() - contract.getSum() * currency.getExchangeRate());
            accountRepository.save(cashAccount);

            operation = new OperationHistory(
                    cashAccount.getId(),
                    0,
                    contract.getSum(),
                    "Вывод денег из кассы",
                    currentDate,
                    contract.getCurrencyId());
            operationHistoryRepository.save(operation);
        }
    }

    public String getToday() {
        return dateFormat.format(currentDate.getTime());
    }

    public void openDebit(Contract contract) {
        Account bankAccount = accountRepository.findByAccountNumber("1010000000000");
        Currency currency = currencyRepository.findById(contract.getCurrencyId()).get();
        Account currentAccount = accountRepository.findById(contract.getCurrentAccountId()).get();

        Account cashAccount = accountRepository.findByAccountNumber("7327000000000");

        //Внесение денег в кассу
        cashAccount.setDebit(cashAccount.getDebit() + contract.getSum() * currency.getExchangeRate());
        accountRepository.save(cashAccount);

        OperationHistory operation = new OperationHistory(
                0,
                cashAccount.getId(),
                contract.getSum(),
                "Внесение денег в кассу",
                currentDate,
                contract.getCurrencyId());
        operationHistoryRepository.save(operation);

        //Перевод денег с кассы на текущий счет

        cashAccount.setCredit(cashAccount.getCredit() - contract.getSum() * currency.getExchangeRate());
        accountRepository.save(cashAccount);

        currentAccount.setCredit(currentAccount.getCredit() + contract.getSum());
        accountRepository.save(currentAccount);

        operation = new OperationHistory(
                cashAccount.getId(),
                contract.getCurrentAccountId(),
                contract.getSum(),
                "Перевод денег с кассы на текущий счет",
                currentDate,
                contract.getCurrencyId());
        operationHistoryRepository.save(operation);

        bankAccount.setCredit(bankAccount.getCredit() + contract.getSum() * currency.getExchangeRate());
        accountRepository.save(bankAccount);

        currentAccount.setDebit(currentAccount.getDebit() - contract.getSum());
        accountRepository.save(currentAccount);

        operation = new OperationHistory(
                contract.getCurrentAccountId(),
                bankAccount.getId(),
                contract.getSum(),
                "Использование денег банком",
                currentDate,
                contract.getCurrencyId());
        operationHistoryRepository.save(operation);
    }

    public void openCredit(Contract contract) {
        Account bankAccount = accountRepository.findByAccountNumber("1010000000000");
        Currency currency = currencyRepository.findById(contract.getCurrencyId()).get();
        Account currentAccount = accountRepository.findById(contract.getCurrentAccountId()).get();
        Account cashAccount = accountRepository.findByAccountNumber("7327000000000");

        //Выделение кредита банком
        bankAccount.setDebit(bankAccount.getDebit() - contract.getSum() * currency.getExchangeRate());
        accountRepository.save(bankAccount);

        currentAccount.setDebit(currentAccount.getCredit() + contract.getSum());
        accountRepository.save(currentAccount);

        OperationHistory operation = new OperationHistory(
                bankAccount.getId(),
                contract.getCurrentAccountId(),
                contract.getSum(),
                "Выделение кредита банком",
                currentDate,
                contract.getCurrencyId());
        operationHistoryRepository.save(operation);
/*
        //Перевод кредита в кассу
        cashAccount.setDebit(cashAccount.getDebit() + contract.getSum() * currency.getExchangeRate());
        accountRepository.save(cashAccount);

        currentAccount.setCredit(currentAccount.getCredit() - contract.getSum());
        accountRepository.save(currentAccount);

        operation = new OperationHistory(
                contract.getCurrentAccountId(),
                cashAccount.getId(),
                contract.getSum(),
                "Перевод кредита в кассу",
                currentDate,
                contract.getCurrencyId());
        operationHistoryRepository.save(operation);

        //Получение кредита через кассу
        cashAccount.setCredit(cashAccount.getCredit() - contract.getSum() * currency.getExchangeRate());
        accountRepository.save(cashAccount);

        operation = new OperationHistory(
                cashAccount.getId(),
                0,
                contract.getSum(),
                "Получение кредита через кассу",
                currentDate,
                contract.getCurrencyId());
        operationHistoryRepository.save(operation);*/
    }

    public boolean withdraw(Card card, double money) {
        Account account = accountRepository.findByAccountNumber(card.getAccountNumber());

        if(account.getSaldo() +account.getCredit() + account.getDebit() < money){
            return false;
        }
        Account cashAccount = accountRepository.findByAccountNumber("7327000000000");

        Optional<Contract> contract = contractRepository.findByCurrentAccountId(account.getId());
        if(!contract.isPresent()) {
            contract = contractRepository.findByPersentAccountId(account.getId());
        }

        Currency currency = currencyRepository.findById(contract.get().getCurrencyId()).get();
        //Перевод денег в кассу(банкомат)
        cashAccount.setDebit(cashAccount.getDebit() + money / currency.getExchangeRate());
        accountRepository.save(cashAccount);

        account.setCredit(account.getCredit() - money);
        accountRepository.save(account);

        OperationHistory operation = new OperationHistory(
                account.getId(),
                cashAccount.getId(),
                money,
                "Перевод денег в кассу(банкомат)",
                currentDate,
                contract.get().getCurrencyId());

        operationHistoryRepository.save(operation);

        //Получение кредита через кассу
        cashAccount.setCredit(cashAccount.getCredit() - money / currency.getExchangeRate());
        accountRepository.save(cashAccount);

        operation = new OperationHistory(
                cashAccount.getId(),
                0,
                money,
                "Получение кредита через кассу",
                currentDate,
                contract.get().getCurrencyId());
        operationHistoryRepository.save(operation);
        return true;
    }

    public List<OperationHistoryDTO> getOperationHistoryByContract(Long id) {
        List<OperationHistory> operationHistories;
        Contract contract = contractRepository.findById(id).get();
        operationHistories = operationHistoryRepository.findAllByFromAccount(contract.getCurrentAccountId());
        operationHistories.addAll(operationHistoryRepository.findAllByFromAccount(contract.getPersentAccountId()));
        operationHistories.addAll(operationHistoryRepository.findAllByToAccount(contract.getCurrentAccountId()));
        operationHistories.addAll(operationHistoryRepository.findAllByToAccount(contract.getPersentAccountId()));

        return  operationHistories.stream().map(operationHistoryMappingUtils::mapToDto).sorted((a, b) -> a.getDate().compareToIgnoreCase(b.getDate())).collect(Collectors.toList());
    }

    public boolean payMobile(Card card, String mobile, String mobileNumber, double money) {
        Account account = accountRepository.findByAccountNumber(card.getAccountNumber());

        if(account.getSaldo() +account.getCredit() + account.getDebit() < money){
            return false;
        }
        Account mobileAccount = this.getMobileAccount(mobile);

        Optional<Contract> contract = contractRepository.findByCurrentAccountId(account.getId());
        if(!contract.isPresent()) {
            contract = contractRepository.findByPersentAccountId(account.getId());
        }

        Currency currency = currencyRepository.findById(contract.get().getCurrencyId()).get();
        //Перевод денег мобильному оператору
        mobileAccount.setDebit(account.getDebit() + money / currency.getExchangeRate());
        accountRepository.save(mobileAccount);

        account.setCredit(account.getCredit() - money);
        accountRepository.save(account);

        OperationHistory operation = new OperationHistory(
                account.getId(),
                mobileAccount.getId(),
                money,
                "Перевод денег мобильному оператору на номер " + mobileNumber,
                currentDate,
                contract.get().getCurrencyId());

        operationHistoryRepository.save(operation);

        return true;
    }

    private Account getMobileAccount(String mobile) {
        Account account;
        switch (mobile){
            case "A1":
                account = accountRepository.findByAccountNumber("1010000000001");
                break;
            case "МТС":
                account = accountRepository.findByAccountNumber("1010000000002");
                break;
            default:
                account = accountRepository.findByAccountNumber("1010000000003");
                break;
        }

        return account;
    }
}
