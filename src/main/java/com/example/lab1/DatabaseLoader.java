package com.example.lab1;

import com.example.lab1.dto.ContractDTO;
import com.example.lab1.model.*;
import com.example.lab1.repositories.*;
import com.example.lab1.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final CityRepository cityRepository;
    private final FamilyStatusRepository familyStatusRepository;
    private final InvalidityRepository invalidityRepository;
    private final NationalityRepository nationalityRepository;
    private final UserRepository userRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final CurrencyRepository currencyRepository;
    private final AccountRepository accountRepository;
    private final ContractService contractService;

    @Autowired
    public DatabaseLoader(CityRepository cityRepository, FamilyStatusRepository familyStatusRepository, InvalidityRepository invalidityRepository, NationalityRepository nationalityRepository, UserRepository userRepository, AccountTypeRepository accountTypeRepository, ContractTypeRepository contractTypeRepository, CurrencyRepository currencyRepository, AccountRepository accountRepository, ContractService contractService) {
        this.cityRepository = cityRepository;
        this.familyStatusRepository = familyStatusRepository;
        this.invalidityRepository = invalidityRepository;
        this.nationalityRepository = nationalityRepository;
        this.userRepository = userRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.contractTypeRepository = contractTypeRepository;
        this.currencyRepository = currencyRepository;
        this.accountRepository = accountRepository;
        this.contractService = contractService;
    }

    public void init()
    {
        initCities();
        initFamilyStatuses();
        initInvalidities();
        initNationalities();
        initUsers();
        initContractTypes();
        initAccountTypes();
        initCurrencies();
        initAccount();
        initDeposits();
    }

    private void initDeposits() {
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, 7);
        ContractDTO contract = new ContractDTO(
                0L,
                "2023-01-08",
                "2023-01-15",
                "Кредит аннуитетный",
                "Бондарева Татьяна Олеговна",
                "Br",
                10000,
                10,
                "",
                "");
        contractService.createContract(contract);
        /*contract = new ContractDTO(
                0L,
                "2023-01-08",
                "2023-01-15",
                "Депозит отзывный",
                "Бондарева Татьяна Олеговна",
                "Br",
                10000,
                10,
                "",
                "");
        contractService.createContract(contract);
       /* ContractDTO contract = new ContractDTO(
                0L,
                "2023-01-08",
                "2023-02-08",
                "Депозит отзывный",
                "Бондарева Татьяна Олеговна",
                "Br",
                10000,
                10,
                "",
                "");
        contractService.createContract(contract);

        contract = new ContractDTO(
                0L,
                "2023-01-08",
                "2023-02-08",
                "Кредит аннуитетный",
                "Бондарева Татьяна Олеговна",
                "Br",
                10000,
                10,
                "",
                "");
        contractService.createContract(contract);*/
    }

    private void initAccount() {
        Account bankAccount = new Account(
                accountTypeRepository.findByName("Активный").getId(),
                "7327000000000",
                "1724",
                0,
                0,
                0);
        accountRepository.save(bankAccount);
        Account cashAccount = new Account(
                accountTypeRepository.findByName("Пассивный").getId(),
                "1010000000000",
                "1724",
                0,
                0,
                100000000000.0);
        accountRepository.save(cashAccount);
    }

    @Override
    public void run(String... strings) throws Exception {
        init();
    }

    private void initCurrencies() {
        Currency currency = new Currency("Br", 1);
        currencyRepository.save(currency);
        currency = new Currency("USD", 2.5);
        currencyRepository.save(currency);
        currency = new Currency("EUR", 2.8);
        currencyRepository.save(currency);
    }

    private void initContractTypes() {
        ContractType contractType = new ContractType("Кредит аннуитетный");
        contractTypeRepository.save(contractType);
        contractType = new ContractType("Депозит отзывный");
        contractTypeRepository.save(contractType);
    }

    private void initAccountTypes() {
        AccountType accountType = new AccountType("Активный");
        accountTypeRepository.save(accountType);
        accountType = new AccountType("Пассивный");
        accountTypeRepository.save(accountType);
    }

    private void initUsers() {
        User user = new User(   "Бондарева",
                                "Татьяна",
                                "Олеговна",
                                new GregorianCalendar(2000, Calendar.JANUARY, 1),
                                "HB",
                                "1234567",
                                "РУВД г. Гомеля",
                                new GregorianCalendar(2017, Calendar.JANUARY , 1),
                                "1111111A111AA1",
                                "г. Гомель",
                                "ул. Совецкая 1 д. 1",
                                "11-11-11",
                                "+375(11)-111-11-11",
                                "tati@mail.ru",
                                "ООО 'АйСиЭс Бел'",
                                "инженер-программист",
                                false,
                                1000,
                                this.cityRepository.findByName("Гомель").getId(),
                                this.cityRepository.findByName("Минск").getId(),
                                this.familyStatusRepository.findByName("Не состою в браке").getId(),
                                this.nationalityRepository.findByName("Республика Беларусь").getId(),
                                this.invalidityRepository.findByName("Не имею").getId());
        userRepository.save(user);

        user = new User("Воранко",
                        "Ксения",
                        "Дмитриевна",
                        new GregorianCalendar(2000, Calendar.JANUARY, 1),
                        "HB",
                        "7654321",
                        "РУВД г. Гомеля",
                        new GregorianCalendar(2020, Calendar.NOVEMBER , 1),
                        "1111112A111AA1",
                        "г. Гомель",
                        "ул. Ленина 1 д. 1",
                        "22-22-22",
                        "+375(44)-131-77-92",
                        "vorankp_ks@mail.ru",
                        "БГУИР",
                        "ассистент кафедры ПОИТ",
                        false,
                        400,
                        this.cityRepository.findByName("Минск").getId(),
                        this.cityRepository.findByName("Минск").getId(),
                        this.familyStatusRepository.findByName("Не состою в браке").getId(),
                        this.nationalityRepository.findByName("Республика Беларусь").getId(),
                        this.invalidityRepository.findByName("Не имею").getId());
        userRepository.save(user);

        user = new User("Саидов",
                "Евгений",
                "Сергеевич",
                new GregorianCalendar(1983, Calendar.JANUARY, 1),
                "HB",
                "5236478",
                "РУВД г. Москва",
                new GregorianCalendar(2022, Calendar.NOVEMBER , 13),
                "3331112A111AA1",
                "г. Брест",
                "ул. Ленина 7 д. 54",
                "32-99-41",
                "+375(44)-777-10-32",
                "saild83@mail.ru",
                "EPAM",
                "программист",
                false,
                1300,
                this.cityRepository.findByName("Брест").getId(),
                this.cityRepository.findByName("Брест").getId(),
                this.familyStatusRepository.findByName("Разведен/Разведена").getId(),
                this.nationalityRepository.findByName("Российская федерация").getId(),
                this.invalidityRepository.findByName("Группа М1").getId());
        userRepository.save(user);

        user = new User("Кирюк",
                "Вадим",
                "Максимович",
                new GregorianCalendar(1950,Calendar.MAY, 18),
                "HB",
                "7329514",
                "РУВД г. Гродно",
                new GregorianCalendar(2021, Calendar.JULY , 22),
                "7654321А111AA1",
                "г. Гродно",
                "ул. Ленина 7 д. 54",
                "10-77-31",
                "+375(44)-546-10-31",
                "",
                "",
                "",
                true,
                600,
                this.cityRepository.findByName("Гродно").getId(),
                this.cityRepository.findByName("Гродно").getId(),
                this.familyStatusRepository.findByName("Вдовец/Вдова").getId(),
                this.nationalityRepository.findByName("Республика Беларусь").getId(),
                this.invalidityRepository.findByName("Группа М3").getId());
        userRepository.save(user);


        user = new User("Романов",
                "Александр",
                "Викторович",
                new GregorianCalendar(1994,Calendar.DECEMBER, 31),
                "HB",
                "7329514",
                "РУВД г. Гродно",
                new GregorianCalendar(2022, Calendar.DECEMBER , 11),
                "5674321A123AA1",
                "г. Могилев",
                "ул. Малайчука 11 д. 88",
                "99-74-31",
                "+375(29)-886-44-74",
                "roman_alex2013@gmail.com",
                "БГУИР",
                "старший преподаватель",
                false,
                700,
                this.cityRepository.findByName("Могилев").getId(),
                this.cityRepository.findByName("Могилев").getId(),
                this.familyStatusRepository.findByName("Cостою в браке").getId(),
                this.nationalityRepository.findByName("Республика Беларусь").getId(),
                this.invalidityRepository.findByName("Не имею").getId());
        userRepository.save(user);
    }

    private void initNationalities() {
        nationalityRepository.save(new Nationality("Республика Беларусь"));
        nationalityRepository.save(new Nationality("Российская федерация"));
        nationalityRepository.save(new Nationality("Гражданство стран СНГ"));
        nationalityRepository.save(new Nationality("Гражданство стран ЕС"));
    }

    private void initInvalidities() {
        invalidityRepository.save(new Invalidity("Не имею"));
        invalidityRepository.save(new Invalidity("Группа М1"));
        invalidityRepository.save(new Invalidity("Группа М2"));
        invalidityRepository.save(new Invalidity("Группа М3"));
        invalidityRepository.save(new Invalidity("Группа М4"));
        invalidityRepository.save(new Invalidity("Группа HM"));
        invalidityRepository.save(new Invalidity("Группа HT"));
        invalidityRepository.save(new Invalidity("Группа HO"));
    }

    private void initCities() {
        cityRepository.save(new City("Минск"));
        cityRepository.save(new City("Гомель"));
        cityRepository.save(new City("Витебск"));
        cityRepository.save(new City("Гродно"));
        cityRepository.save(new City("Могилев"));
        cityRepository.save(new City("Брест"));
    }

    private void initFamilyStatuses() {
        familyStatusRepository.save(new FamilyStatus("Не состою в браке"));
        familyStatusRepository.save(new FamilyStatus("Cостою в браке"));
        familyStatusRepository.save(new FamilyStatus("Разведен/Разведена"));
        familyStatusRepository.save(new FamilyStatus("Вдовец/Вдова"));
        familyStatusRepository.save(new FamilyStatus("Помолвлен/Помолвлена"));
        familyStatusRepository.save(new FamilyStatus("В отношениях"));
        familyStatusRepository.save(new FamilyStatus("Проживаю с сожителем/сожительницей"));
    }
}
