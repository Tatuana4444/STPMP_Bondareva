package com.example.lab1;
import com.example.lab1.dto.UserDTO;
import com.example.lab1.model.*;
import com.example.lab1.repositories.*;
import com.example.lab1.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class UserTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private FamilyStatusRepository familyStatusRepository;
    @Autowired
    private InvalidityRepository invalidityRepository;
    @Autowired
    private NationalityRepository nationalityRepository;


    public void initOtherTables(){
        City city = new City("Минск");
        cityRepository.save(city);
        FamilyStatus familyStatus = new FamilyStatus("Не состою в браке");
        familyStatusRepository.save(familyStatus);
        Invalidity invalidity = new Invalidity("Не имею");
        invalidityRepository.save(invalidity);
        Nationality nationality = new Nationality("Республика Беларусь");
        nationalityRepository.save(nationality);
    }

    @Test
    public void test1(){
        UserService userService = new UserService(userRepository, cityRepository, familyStatusRepository, invalidityRepository, nationalityRepository);
        initOtherTables();

        UserDTO user1 = new UserDTO(
                0L,
                "Бондарева",
                "Татьяна",
                "Олеговна",
                "2000-01-01",
                "HB",
                "1234567",
                "РУВД г. Гомеля",
                "2022-01-01",
                "1111111A111AA1",
                "г. Гомель",
                "Минск",
                "ул. Совецкая 1 д. 1",
                "11-11-11",
                "+375(11)-111-11-11",
                "tati@mail.ru",
                "ООО 'АйСиЭс Бел'",
                "инженер-программист",
                "Минск",
                "Не состою в браке",
                "Республика Беларусь",
                "Не имею",
                false,
                "1000");
        userService.createUser(user1);

        int countOfRecordsBefore = userService.getUsers().size();

        UserDTO user2 = new UserDTO(
                0L,
                "Бондарева",
                "Татьяна",
                "Олеговна",
                "2000-01-01",
                "HB",
                "1234569",
                "РУВД г. Гомеля",
                "2022-01-01",
                "1111111A111AA2",
                "г. Гомель",
                "Минск",
                "ул. Совецкая 1 д. 1",
                "11-11-11",
                "+375(11)-111-11-11",
                "tati@mail.ru",
                "ООО 'АйСиЭс Бел'",
                "инженер-программист",
                "Минск",
                "Не состою в браке",
                "Республика Беларусь",
                "Не имею",
                false,
                "1000");
        userService.createUser(user2);

        int countOfRecordsAfter = userService.getUsers().size();

        Assertions.assertEquals(countOfRecordsBefore, countOfRecordsAfter);
    }

    @Test
    public void test2(){
        UserService userService = new UserService(userRepository, cityRepository, familyStatusRepository, invalidityRepository, nationalityRepository);
        initOtherTables();

        UserDTO user1 = new UserDTO(
                0L,
                "Бондарева",
                "Татьяна",
                "Олеговна",
                "2000-01-01",
                "HB",
                "1234567",
                "РУВД г. Гомеля",
                "2022-01-01",
                "1111111A111AA1",
                "г. Гомель",
                "Минск",
                "ул. Совецкая 1 д. 1",
                "11-11-11",
                "+375(11)-111-11-11",
                "tati@mail.ru",
                "ООО 'АйСиЭс Бел'",
                "инженер-программист",
                "Минск",
                "Не состою в браке",
                "Республика Беларусь",
                "Не имею",
                false,
                "1000");
        userService.createUser(user1);

        int countOfRecordsBefore = userService.getUsers().size();

        UserDTO user2 = new UserDTO(
                0L,
                "Бондарева",
                "Екатерина",
                "Олеговна",
                "2000-01-01",
                "HB",
                "1234567",
                "РУВД г. Гомеля",
                "2022-01-01",
                "1111111A111AA2",
                "г. Гомель",
                "Минск",
                "ул. Совецкая 1 д. 1",
                "11-11-11",
                "+375(11)-111-11-11",
                "tati@mail.ru",
                "ООО 'АйСиЭс Бел'",
                "инженер-программист",
                "Минск",
                "Не состою в браке",
                "Республика Беларусь",
                "Не имею",
                false,
                "1000");
        userService.createUser(user2);

        int countOfRecordsAfter = userService.getUsers().size();

        Assertions.assertEquals(countOfRecordsBefore, countOfRecordsAfter);
    }

    @Test
    public void test3(){
        UserService userService = new UserService(userRepository, cityRepository, familyStatusRepository, invalidityRepository, nationalityRepository);
        initOtherTables();

        UserDTO user1 = new UserDTO(
                0L,
                "Бондарева",
                "Татьяна",
                "Олеговна",
                "2000-01-01",
                "HB",
                "1234567",
                "РУВД г. Гомеля",
                "2022-01-01",
                "1111111A111AA1",
                "г. Гомель",
                "Минск",
                "ул. Совецкая 1 д. 1",
                "11-11-11",
                "+375(11)-111-11-11",
                "tati@mail.ru",
                "ООО 'АйСиЭс Бел'",
                "инженер-программист",
                "Минск",
                "Не состою в браке",
                "Республика Беларусь",
                "Не имею",
                false,
                "1000");
        userService.createUser(user1);

        int countOfRecordsBefore = userService.getUsers().size();

        UserDTO user2 = new UserDTO(
                0L,
                "Бондарева",
                "Екатерина",
                "Олеговна",
                "2000-01-01",
                "HB",
                "1234569",
                "РУВД г. Гомеля",
                "2022-01-01",
                "1111111A111AA1",
                "г. Гомель",
                "Минск",
                "ул. Совецкая 1 д. 1",
                "11-11-11",
                "+375(11)-111-11-11",
                "tati@mail.ru",
                "ООО 'АйСиЭс Бел'",
                "инженер-программист",
                "Минск",
                "Не состою в браке",
                "Республика Беларусь",
                "Не имею",
                false,
                "1000");
        userService.createUser(user2);

        int countOfRecordsAfter = userService.getUsers().size();

        Assertions.assertEquals(countOfRecordsBefore, countOfRecordsAfter);
    }

    @Test
    public void test4(){
        UserService userService = new UserService(userRepository, cityRepository, familyStatusRepository, invalidityRepository, nationalityRepository);
        initOtherTables();

        int countOfRecordsBefore = userService.getUsers().size();

        UserDTO user1 = new UserDTO(
                0L,
                "1234",
                "Татьяна",
                "Олеговна",
                "2000-01-01",
                "HB",
                "1234567",
                "РУВД г. Гомеля",
                "2022-01-01",
                "1111111A111AA1",
                "г. Гомель",
                "Минск",
                "ул. Совецкая 1 д. 1",
                "11-11-11",
                "+375(11)-111-11-11",
                "tati@mail.ru",
                "ООО 'АйСиЭс Бел'",
                "инженер-программист",
                "Минск",
                "Не состою в браке",
                "Республика Беларусь",
                "Не имею",
                false,
                "1000");
        userService.createUser(user1);

        int countOfRecordsAfter = userService.getUsers().size();

        Assertions.assertEquals(countOfRecordsBefore, countOfRecordsAfter);
    }

    @Test
    public void test5(){
        UserService userService = new UserService(userRepository, cityRepository, familyStatusRepository, invalidityRepository, nationalityRepository);
        initOtherTables();

        int countOfRecordsBefore = userService.getUsers().size();

        UserDTO user1 = new UserDTO(
                0L,
                "Бондарева",
                " ",
                "Олеговна",
                "2000-01-01",
                "HB",
                "1234567",
                "РУВД г. Гомеля",
                "2022-01-01",
                "1111111A111AA1",
                "г. Гомель",
                "Минск",
                "ул. Совецкая 1 д. 1",
                "11-11-11",
                "+375(11)-111-11-11",
                "tati@mail.ru",
                "ООО 'АйСиЭс Бел'",
                "инженер-программист",
                "Минск",
                "Не состою в браке",
                "Республика Беларусь",
                "Не имею",
                false,
                "1000");
        userService.createUser(user1);

        int countOfRecordsAfter = userService.getUsers().size();

        Assertions.assertEquals(countOfRecordsBefore, countOfRecordsAfter);
    }

    @Test
    public void test6(){
        UserService userService = new UserService(userRepository, cityRepository, familyStatusRepository, invalidityRepository, nationalityRepository);
        initOtherTables();

        int countOfRecordsBefore = userService.getUsers().size();

        UserDTO user1 = new UserDTO(
                0L,
                "Бондарева",
                "Татьяна",
                "Олеговна",
                "31 февраля 2015 года",
                "HB",
                "1234567",
                "РУВД г. Гомеля",
                "2022-01-01",
                "1111111A111AA1",
                "г. Гомель",
                "Минск",
                "ул. Совецкая 1 д. 1",
                "11-11-11",
                "+375(11)-111-11-11",
                "tati@mail.ru",
                "ООО 'АйСиЭс Бел'",
                "инженер-программист",
                "Минск",
                "Не состою в браке",
                "Республика Беларусь",
                "Не имею",
                false,
                "1000");
        userService.createUser(user1);

        int countOfRecordsAfter = userService.getUsers().size();

        Assertions.assertEquals(countOfRecordsBefore, countOfRecordsAfter);
    }

    @Test
    public void test6_IdentifiedNum(){
        UserService userService = new UserService(userRepository, cityRepository, familyStatusRepository, invalidityRepository, nationalityRepository);
        initOtherTables();

        int countOfRecordsBefore = userService.getUsers().size();

        UserDTO user1 = new UserDTO(
                0L,
                "Бондарева",
                "Татьяна",
                "Олеговна",
                "2000-01-01",
                "HB",
                "1234567",
                "РУВД г. Гомеля",
                "2022-01-01",
                "ABC",
                "г. Гомель",
                "Минск",
                "ул. Совецкая 1 д. 1",
                "11-11-11",
                "+375(11)-111-11-11",
                "tati@mail.ru",
                "ООО 'АйСиЭс Бел'",
                "инженер-программист",
                "Минск",
                "Не состою в браке",
                "Республика Беларусь",
                "Не имею",
                false,
                "1000");
        userService.createUser(user1);

        int countOfRecordsAfter = userService.getUsers().size();

        Assertions.assertEquals(countOfRecordsBefore, countOfRecordsAfter);
    }

    @Test
    public void test6_PassportNum(){
        UserService userService = new UserService(userRepository, cityRepository, familyStatusRepository, invalidityRepository, nationalityRepository);
        initOtherTables();

        int countOfRecordsBefore = userService.getUsers().size();

        UserDTO user1 = new UserDTO(
                0L,
                "Бондарева",
                "Татьяна",
                "Олеговна",
                "2000-01-01",
                "HB",
                "ABC",
                "РУВД г. Гомеля",
                "2022-01-01",
                "1111111A111AA1",
                "г. Гомель",
                "Минск",
                "ул. Совецкая 1 д. 1",
                "11-11-11",
                "+375(11)-111-11-11",
                "tati@mail.ru",
                "ООО 'АйСиЭс Бел'",
                "инженер-программист",
                "Минск",
                "Не состою в браке",
                "Республика Беларусь",
                "Не имею",
                false,
                "1000");
        userService.createUser(user1);

        int countOfRecordsAfter = userService.getUsers().size();

        Assertions.assertEquals(countOfRecordsBefore, countOfRecordsAfter);
    }

    @Test
    public void test6_MobilePhone(){
        UserService userService = new UserService(userRepository, cityRepository, familyStatusRepository, invalidityRepository, nationalityRepository);
        initOtherTables();

        int countOfRecordsBefore = userService.getUsers().size();

        UserDTO user1 = new UserDTO(
                0L,
                "Бондарева",
                "Татьяна",
                "Олеговна",
                "2000-01-01",
                "HB",
                "1234567",
                "РУВД г. Гомеля",
                "2022-01-01",
                "1111111A111AA1",
                "г. Гомель",
                "Минск",
                "ул. Совецкая 1 д. 1",
                "11-11-11",
                "+375(11)-AAA-11-11",
                "tati@mail.ru",
                "ООО 'АйСиЭс Бел'",
                "инженер-программист",
                "Минск",
                "Не состою в браке",
                "Республика Беларусь",
                "Не имею",
                false,
                "1000");
        userService.createUser(user1);

        int countOfRecordsAfter = userService.getUsers().size();

        Assertions.assertEquals(countOfRecordsBefore, countOfRecordsAfter);
    }

    @Test
    public void test6_HomePhone(){
        UserService userService = new UserService(userRepository, cityRepository, familyStatusRepository, invalidityRepository, nationalityRepository);
        initOtherTables();

        int countOfRecordsBefore = userService.getUsers().size();

        UserDTO user1 = new UserDTO(
                0L,
                "Бондарева",
                "Татьяна",
                "Олеговна",
                "2000-01-01",
                "HB",
                "1234567",
                "РУВД г. Гомеля",
                "2022-01-01",
                "1111111A111AA1",
                "г. Гомель",
                "Минск",
                "ул. Совецкая 1 д. 1",
                "11-AA-11",
                "+375(11)-111-11-11",
                "tati@mail.ru",
                "ООО 'АйСиЭс Бел'",
                "инженер-программист",
                "Минск",
                "Не состою в браке",
                "Республика Беларусь",
                "Не имею",
                false,
                "1000");
        userService.createUser(user1);

        int countOfRecordsAfter = userService.getUsers().size();

        Assertions.assertEquals(countOfRecordsBefore, countOfRecordsAfter);
    }

    @Test
    public void test7(){
        UserService userService = new UserService(userRepository, cityRepository, familyStatusRepository, invalidityRepository, nationalityRepository);
        initOtherTables();

        int countOfRecordsBefore = userService.getUsers().size();

        UserDTO user1 = new UserDTO(
                0L,
                "Бондарева",
                "Татьяна",
                "Олеговна",
                "2000-01-01",
                "HB",
                "1234567",
                "",
                "2022-01-01",
                "1111111A111AA1",
                "г. Гомель",
                "Минск",
                "ул. Совецкая 1 д. 1",
                "11-11-11",
                "+375(11)-111-11-11",
                "tati@mail.ru",
                "ООО 'АйСиЭс Бел'",
                "инженер-программист",
                "Минск",
                "Не состою в браке",
                "Республика Беларусь",
                "Не имею",
                false,
                "1000");
        userService.createUser(user1);

        int countOfRecordsAfter = userService.getUsers().size();

        Assertions.assertEquals(countOfRecordsBefore, countOfRecordsAfter);
    }

    @Test
    public void test8(){
        UserService userService = new UserService(userRepository, cityRepository, familyStatusRepository, invalidityRepository, nationalityRepository);
        initOtherTables();

        int countOfRecordsBefore = userService.getUsers().size();

        UserDTO user1 = new UserDTO(
                0L,
                "Бондарева",
                "Татьяна",
                "Олеговна",
                "1999-11-19",
                "HB",
                "1234567",
                "РУВД г. Гомеля",
                "2022-01-01",
                "1111111A111AA1",
                "г. Гомель",
                "Минск",
                "ул. Совецкая 1 д. 1",
                "",
                "",
                "",
                "",
                "",
                "Минск",
                "Не состою в браке",
                "Республика Беларусь",
                "Не имею",
                false,
                "1000");
        userService.createUser(user1);

        int countOfRecordsAfter = userService.getUsers().size();

        Assertions.assertEquals(countOfRecordsBefore + 1, countOfRecordsAfter);
    }
/*
    @Test
    public void testUpdateUser(){
        initOtherTables();
        User user = new User(   "Bondareva",
                                "Tatyana",
                                "Olegovna",
                                new GregorianCalendar(2000, Calendar.JANUARY, 1),
                                "HB",
                                "1234567",
                                "Gomel",
                                new GregorianCalendar(2017, Calendar.JANUARY , 1),
                                "4123456А001РВ3",
                                "Gomel",
                                "address",
                                "123456",
                                "123456",
                                "tati@mail.ru",
                                "work",
                                "developer",
                                false,
                                1000,
                                1,
                                1,
                                1,
                                1,
                                1);
        user = userRepository.save(user);
        user.setFirstname("Tati");
        userRepository.save(user);
        userRepository.findById(1L)
                .map(newUser ->{
                    Assertions.assertEquals("Tati", newUser.getFirstname());
                    return true;
                });
    }

    @Test
    public void getUser(){
        initOtherTables();
        User user = new User(   "Bondareva",
                                "Tatyana",
                                "Olegovna",
                                new GregorianCalendar(2000, Calendar.JANUARY, 1),
                                "HB",
                                "1234567",
                                "Gomel",
                                new GregorianCalendar(2017, Calendar.JANUARY , 1),
                                "4123456А001РВ3",
                                "Gomel",
                                "address",
                                "123456",
                                "123456",
                                "tati@mail.ru",
                                "work",
                                "developer",
                                false,
                                1000,
                                1,
                                1,
                                1,
                                1,
                                1);
        User user2 = new User(  "Lion",
                                "Kate",
                                "Olegovna",
                                new GregorianCalendar(2000, Calendar.JANUARY, 1),
                                "HB",
                                "1234567",
                                "Minsk",
                                new GregorianCalendar(2017, Calendar.JANUARY , 1),
                                "4123456А001РВ3",
                                "Minsk",
                                "address",
                                "123456",
                                "123456",
                                "kate@mail.ru",
                                "work",
                                "developer",
                                false,
                                1000,
                                1,
                                1,
                                1,
                                1,
                                1);
        userRepository.save(user);

        userRepository.save(user2);

        userRepository.findById(1L)
                .map(newUser ->{
                    Assertions.assertEquals("Lion", newUser.getLastname());
                    return true;
                });

    }

    @Test
    public void getUsers(){
        initOtherTables();
        User user = new User(   "Bondareva",
                                "Tatyana",
                                "Olegovna",
                                new GregorianCalendar(2000, Calendar.JANUARY, 1),
                                "HB",
                                "1234567",
                                "Gomel",
                                new GregorianCalendar(2017, Calendar.JANUARY , 1),
                                "4123456А001РВ3",
                                "Gomel",
                                "address",
                                "123456",
                                "123456",
                                "tati@mail.ru",
                                "work",
                                "developer",
                                false,
                                1000,
                                1,
                                1,
                                1,
                                1,
                                1);
        User user2 = new User(  "Bondareva",
                                "Kate",
                                "Olegovna",
                                new GregorianCalendar(2000, Calendar.JANUARY, 1),
                                "HB",
                                "1234567",
                                "Minsk",
                                new GregorianCalendar(2017, Calendar.JANUARY , 1),
                                "4123456А001РВ3",
                                "Minsk",
                                "address",
                                "123456",
                                "123456",
                                "kate@mail.ru",
                                "work",
                                "developer",
                                false,
                                1000,
                                1,
                                1,
                                1,
                                1,
                                1);
        userRepository.save(user);
        userRepository.save(user2);

        Assertions.assertNotNull(userRepository.findAll());
    }

    @Test
    public void deleteUser(){
        initOtherTables();
        User user = new User(   "Bondareva",
                                "Tatyana",
                                "Olegovna",
                                new GregorianCalendar(2000, Calendar.JANUARY, 1),
                                "HB",
                                "1234567",
                                "Gomel",
                                new GregorianCalendar(2017, Calendar.JANUARY , 1),
                                "4123456А001РВ3",
                                "Gomel",
                                "address",
                                "123456",
                                "123456",
                                "tati@mail.ru",
                                "work",
                                "developer",
                                false,
                                1000,
                                1,
                                1,
                                1,
                                1,
                                1);
        userRepository.save(user);
        userRepository.delete(user);
        Assertions.assertTrue(userRepository.findAll().isEmpty());
    }*/
}