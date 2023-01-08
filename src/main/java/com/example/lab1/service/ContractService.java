package com.example.lab1.service;

import com.example.lab1.dto.ContractDTO;
import com.example.lab1.exceptions.ResourceNotFoundException;
import com.example.lab1.model.Account;
import com.example.lab1.model.Contract;
import com.example.lab1.repositories.*;
import com.example.lab1.utils.ContractMappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class ContractService {
    private final ContractRepository contractRepository;
    private  final AccountRepository accountRepository;
    private final ContractMappingUtils contractMappingUtils;

    private final AccountTypeRepository accountTypeRepository;

    private final OperationHistoryService operationHistoryService;

    @Autowired
    public ContractService(ContractRepository contractRepository,
                           ContractTypeRepository contractTypeRepository,
                           UserRepository userRepository,
                           CurrencyRepository currencyRepository,
                           AccountRepository accountRepository,
                           AccountTypeRepository accountTypeRepository,
                           OperationHistoryService operationHistoryService) {
        this.contractRepository = contractRepository;
        this.accountRepository = accountRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.operationHistoryService = operationHistoryService;
        this.contractMappingUtils = new ContractMappingUtils(contractTypeRepository, userRepository, currencyRepository, accountRepository);
    }


    public ContractDTO createContract(ContractDTO contractDTO) {

        long currentAccountId = createAccount("Активный", "3014", contractDTO.getContractType());
        long percentAccountId = createAccount("Пассивный", "2400", contractDTO.getContractType());
        contractMappingUtils.setCurrentAccountId(currentAccountId);
        contractMappingUtils.setPercentAccountId(percentAccountId);
        Contract contract = contractMappingUtils.mapToEntity(contractDTO);
        Contract savedContract = this.contractRepository.save(contract);


        if(Objects.equals(contractDTO.getContractType(), "Кредит аннуитетный")){
            operationHistoryService.openCredit(contract);
        }
        else {
            operationHistoryService.openDebit(contract);
        }
        return contractMappingUtils.mapToDto(savedContract);
    }

    public long createAccount(String accountType, String balanceNumber,String contractType){
        Account account = new Account();
        account.setAccountTypeId(accountTypeRepository.findByName(accountType).getId());
        account.setAccountNumber(generateCheckKey(balanceNumber, generateIndividualNumber()));
        account.setPlanAccountNumber("1724");
        account.setDebit(0);
        account.setCredit(0);
        account.setSaldo(0);
        account = accountRepository.save(account);
        return account.getId();
    }

    public  String generateCheckKey(String balanceNumber, String individualNumber)
    {
        int sum = 0;
        String accountNumber = balanceNumber.concat(individualNumber);
        for(int i = 0; i < accountNumber.length(); i++)
        {
            sum += accountNumber.charAt(i)- '0';
        }
        return accountNumber.concat(Integer.toString(sum % 10));
    }

    public String generateIndividualNumber()
    {
        String individualNumber;

        {
            individualNumber =Integer.toString((int)(Math.random()*99999999));
        } while (accountRepository.findByAccountNumber(individualNumber) != null);

        return individualNumber;
    }

    public List<ContractDTO> getContracts(){
        return this.contractRepository.findAll().stream()
                .map(contractMappingUtils::mapToDto)
                .collect(Collectors.toList());
    }

    public ContractDTO getContract(Long id){
        Contract contract = this.contractRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Contract not found")
        );

        return  contractMappingUtils.mapToDto(contract);
    }

    public ContractDTO updateContract(ContractDTO newContract, Long id){
        return this.contractRepository.findById(id)
                .map(contract -> {
                    contractMappingUtils.setCurrentAccountId(0);
                    contractMappingUtils.setPercentAccountId(0);
                    contract = contractMappingUtils.mapToEntity(newContract);
                    return contractMappingUtils.mapToDto(this.contractRepository.save(contract));
                })
                .orElseGet(()->{
                    newContract.setId(id);
                    contractMappingUtils.setCurrentAccountId(0);
                    contractMappingUtils.setPercentAccountId(0);
                    Contract contract = contractMappingUtils.mapToEntity(newContract);
                    return contractMappingUtils.mapToDto(this.contractRepository.save(contract));
                });
    }

    public void removeContract(Long id){
        this.contractRepository.deleteById(id);
    }
}

