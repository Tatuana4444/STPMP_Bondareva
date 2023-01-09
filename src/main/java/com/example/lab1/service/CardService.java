package com.example.lab1.service;

import com.example.lab1.model.*;
import com.example.lab1.repositories.AccountRepository;
import com.example.lab1.repositories.CardRepository;
import com.example.lab1.repositories.ContractRepository;
import com.example.lab1.repositories.OperationHistoryRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final OperationHistoryService operationHistoryService;


    @Autowired
    public CardService(CardRepository cardRepository, AccountRepository accountRepository, OperationHistoryService operationHistoryService) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
        this.operationHistoryService = operationHistoryService;
    }

    public String generateCardNumber(){
        String cardNumber;
        do {
            cardNumber = RandomStringUtils.randomNumeric(16);
        }while (cardRepository.findByNumber(cardNumber).isPresent());

        return cardNumber;
    }

    public String generatePIN(){
        return  RandomStringUtils.randomNumeric(4);
    }

    public boolean checkCard(Card card) {
        Optional<Card> existingCard = cardRepository.findByNumber(card.getNumber());
        return existingCard.isPresent() && Objects.equals(existingCard.get().getPIN(), card.getPIN());
    }

    public double getRemainder(Card card) {
        if(this.checkCard(card)){
            Card existingCard = cardRepository.findByNumber(card.getNumber()).get();
            Account account = accountRepository.findByAccountNumber(existingCard.getAccountNumber());
            return  account.getSaldo() + account.getDebit() + account.getCredit();
        }
        else {
            return -1;
        }
    }


    public boolean withdraw(Card card, double money) {
        if(this.checkCard(card)) {
            Card existingCard = cardRepository.findByNumber(card.getNumber()).get();

            return operationHistoryService.withdraw(existingCard, money);
        }
        else {
            return false;
        }
    }

    public void createRemainderCheck(Card card, String filename) throws FileNotFoundException, UnsupportedEncodingException {
        if(this.checkCard(card)) {
            Card existingCard = cardRepository.findByNumber(card.getNumber()).get();
            Account account = accountRepository.findByAccountNumber(existingCard.getAccountNumber());
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println("Чек о состоянии счета");
            writer.println("Счет: " + existingCard.getAccountNumber());
            writer.println("Номер карты: " + existingCard.getNumber());
            writer.println("Остаток на счете " + (account.getSaldo() + account.getCredit() + account.getDebit()));
            writer.close();
        }
    }

    public void createWithdrawCheck(Card card, double money, String filename) throws FileNotFoundException, UnsupportedEncodingException {
        if(this.checkCard(card)) {
            Card existingCard = cardRepository.findByNumber(card.getNumber()).get();
            Account account = accountRepository.findByAccountNumber(existingCard.getAccountNumber());
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println("Чек о списании средств");
            writer.println("Счет: " + existingCard.getAccountNumber());
            writer.println("Номер карты: " + existingCard.getNumber());
            writer.println("Сумма " + money);
            writer.close();
        }
    }

    public void createMobilePaymentCheck(Card card, String mobile, String mobileNumber, double money, String filename) throws FileNotFoundException, UnsupportedEncodingException {
        if(this.checkCard(card)) {
            Card existingCard = cardRepository.findByNumber(card.getNumber()).get();
            Account account = accountRepository.findByAccountNumber(existingCard.getAccountNumber());
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println("Чек о платеже");
            writer.println("Счет: " + existingCard.getAccountNumber());
            writer.println("Номер карты: " + existingCard.getNumber());
            writer.println("Мобильный оператор: " + mobile);
            writer.println("Номер мобильного: " + mobileNumber);
            writer.println("Сумма " + money);
            writer.close();
        }
    }

    public boolean payMobile(Card card, String mobile, String mobileNumber, double money) {
        if(this.checkCard(card)) {
            Card existingCard = cardRepository.findByNumber(card.getNumber()).get();

            return operationHistoryService.payMobile(existingCard, mobile, mobileNumber, money);
        }
        else {
            return false;
        }
    }
}
