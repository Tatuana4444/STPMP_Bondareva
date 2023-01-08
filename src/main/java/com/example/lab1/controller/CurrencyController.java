package com.example.lab1.controller;

        import com.example.lab1.exceptions.ResourceNotFoundException;
        import com.example.lab1.model.Currency;
        import com.example.lab1.repositories.CurrencyRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;


@RestController
@RequestMapping("/currency")
public class CurrencyController {
    private CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyController(CurrencyRepository currencyRepository){
        this.currencyRepository = currencyRepository;
    }

    @PostMapping()
    public Currency saveCurrency(@RequestBody Currency currency){
        return this.currencyRepository.save(currency);
    }

    @GetMapping()
    public ResponseEntity<List<Currency>> getCities(){
        return ResponseEntity.ok(
                this.currencyRepository.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Currency> getCurrency(@PathVariable(value = "id" ) Long id){
        Currency currency = this.currencyRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Contract type not found")
        );

        return  ResponseEntity.ok().body(currency);
    }

    @PutMapping("/{id}")
    public Currency updateCurrency(@RequestBody Currency newCurrency, @PathVariable(value = "id") Long id){
        return this.currencyRepository.findById(id)
                .map(currency -> {
                    currency.setName(newCurrency.getName());
                    return this.currencyRepository.save(currency);
                })
                .orElseGet(()->{
                    newCurrency.setId(id);
                    return this.currencyRepository.save(newCurrency);
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCurrency(@PathVariable(value = "id") Long id){
        Currency currency =this.currencyRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Contract type not found"+id)
        );

        this.currencyRepository.delete(currency);
        return ResponseEntity.ok().build();
    }
}




