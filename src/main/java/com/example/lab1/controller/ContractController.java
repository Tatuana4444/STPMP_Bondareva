package com.example.lab1.controller;

        import com.example.lab1.dto.ContractDTO;
        import com.example.lab1.service.ContractService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.net.URI;
        import java.net.URISyntaxException;
        import java.util.List;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService){
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity<ContractDTO> createContract(@RequestBody ContractDTO contractDTO) throws URISyntaxException {

        ContractDTO createdContract = contractService.createContract(contractDTO);
        if(createdContract != null) {

            return ResponseEntity.created(new URI("/clients/" + createdContract.getId())).body(createdContract);
        }
        else
        {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ContractDTO>> getContracts(){
        return ResponseEntity.ok(contractService.getContracts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDTO> getContract(@PathVariable(value = "id" ) Long id){
        return ResponseEntity.ok(contractService.getContract(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractDTO> updateContract(@RequestBody ContractDTO newContract, @PathVariable(value = "id") Long id){
        ContractDTO updatedContract = contractService.updateContract(newContract, id);
        if(updatedContract != null) {
            return ResponseEntity.ok().body(updatedContract);
        }
        else
        {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeContract(@PathVariable(value = "id") Long id){
        contractService.removeContract(id);
        return ResponseEntity.ok().build();
    }
}