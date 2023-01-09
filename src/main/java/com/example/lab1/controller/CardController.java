package com.example.lab1.controller;

import com.example.lab1.exceptions.ResourceNotFoundException;
import com.example.lab1.model.Card;
import com.example.lab1.repositories.CardRepository;
import com.example.lab1.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    private CardRepository cardRepository;
    private final CardService cardService;

    @Autowired
    public CardController(CardRepository cardRepository, CardService cardService){
        this.cardRepository = cardRepository;
        this.cardService = cardService;
    }

    @PostMapping()
    public Card saveCard(@RequestBody Card card){
        return this.cardRepository.save(card);
    }

    @PutMapping("/check")
    public boolean checkCard(@RequestBody Card card){
        return this.cardService.checkCard(card);
    }

    @PutMapping("/remainder")
    public double getRemainder(@RequestBody Card card){
        return this.cardService.getRemainder(card);
    }

    private HttpHeaders headers(String name) {

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + name);
        header.add("Cache-Control",
                "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return header;

    }

    @PutMapping(path = "/remainder/download")
    public ResponseEntity<Resource> downloadRemainderCheck(@RequestBody Card card) throws IOException {
        this.cardService.createRemainderCheck(card, "check.txt");
        File file = new File("check.txt");
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource =
                new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok().headers(this.headers("check.txt"))
                .contentLength(file.length())
                .contentType(MediaType
                        .parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @PutMapping(path = "/withdraw/download/{money}")
    public ResponseEntity<Resource> downloadWithdrawCheck(@PathVariable(value = "money" ) double money, @RequestBody Card card) throws IOException {
        this.cardService.createWithdrawCheck(card, money, "check.txt");
        File file = new File("check.txt");
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource =
                new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok().headers(this.headers("check.txt"))
                .contentLength(file.length())
                .contentType(MediaType
                        .parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @PutMapping("/withdraw/{money}")
    public boolean withdraw(@PathVariable(value = "money" ) double money, @RequestBody Card card){
        return this.cardService.withdraw(card, money);
    }

    @PutMapping("/mobile/{mobile}/{mobileNumber}/{money}")
    public boolean payMobile(@PathVariable(value = "mobile" ) String mobile, @PathVariable(value = "mobileNumber" ) String mobileNumber,@PathVariable(value = "money" ) double money, @RequestBody Card card){
        return this.cardService.payMobile(card, mobile, mobileNumber, money);
    }

    @PutMapping(path = "/mobile/{mobile}/{mobileNumber}/download/{money}")
    public ResponseEntity<Resource> downloadMobileCheck(
            @PathVariable(value = "mobile" ) String mobile,
            @PathVariable(value = "mobileNumber" ) String mobileNumber,
            @PathVariable(value = "money" ) double money,
            @RequestBody Card card) throws IOException {
        this.cardService.createMobilePaymentCheck(card, mobile, mobileNumber, money, "check.txt");
        File file = new File("check.txt");
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource =
                new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok().headers(this.headers("check.txt"))
                .contentLength(file.length())
                .contentType(MediaType
                        .parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @GetMapping()
    public ResponseEntity<List<Card>> getCurrencies(){
        return ResponseEntity.ok(
                this.cardRepository.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCard(@PathVariable(value = "id" ) Long id){
        Card card = this.cardRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Contract type not found")
        );

        return  ResponseEntity.ok().body(card);
    }



    @PutMapping("/{id}")
    public Card updateCard(@RequestBody Card newCard, @PathVariable(value = "id") Long id){
        return this.cardRepository.findById(id)
                .map(card -> {
                    card.setPIN(newCard.getPIN());
                    return this.cardRepository.save(card);
                })
                .orElseGet(()->{
                    newCard.setId(id);
                    return this.cardRepository.save(newCard);
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCard(@PathVariable(value = "id") Long id){
        Card card =this.cardRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Contract type not found"+id)
        );

        this.cardRepository.delete(card);
        return ResponseEntity.ok().build();
    }
}
