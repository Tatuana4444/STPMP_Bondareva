package com.example.lab1.controller;

        import com.example.lab1.exceptions.ResourceNotFoundException;
        import com.example.lab1.model.ContractType;
        import com.example.lab1.repositories.ContractTypeRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;


@RestController
@RequestMapping("/contractType")
public class ContractTypeController {
    private ContractTypeRepository contractTypeRepository;

    @Autowired
    public ContractTypeController(ContractTypeRepository contractTypeRepository){
        this.contractTypeRepository = contractTypeRepository;
    }

    @PostMapping()
    public ContractType saveContractType(@RequestBody ContractType contractType){
        return this.contractTypeRepository.save(contractType);
    }

    @GetMapping()
    public ResponseEntity<List<ContractType>> getCities(){
        return ResponseEntity.ok(
                this.contractTypeRepository.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractType> getContractType(@PathVariable(value = "id" ) Long id){
        ContractType contractType = this.contractTypeRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Contract type not found")
        );

        return  ResponseEntity.ok().body(contractType);
    }

    @PutMapping("/{id}")
    public ContractType updateContractType(@RequestBody ContractType newContractType, @PathVariable(value = "id") Long id){
        return this.contractTypeRepository.findById(id)
                .map(contractType -> {
                    contractType.setName(newContractType.getName());
                    return this.contractTypeRepository.save(contractType);
                })
                .orElseGet(()->{
                    newContractType.setId(id);
                    return this.contractTypeRepository.save(newContractType);
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeContractType(@PathVariable(value = "id") Long id){
        ContractType contractType =this.contractTypeRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Contract type not found"+id)
        );

        this.contractTypeRepository.delete(contractType);
        return ResponseEntity.ok().build();
    }
}



