package dbg.netbill.contracts.controller;

import dbg.netbill.contracts.service.ContractService;
import dbg.netbill.interactionapi.dto.contact.ContractDto;
import dbg.netbill.interactionapi.dto.contact.NewContractDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController("api/admin/contracts")
@RequiredArgsConstructor
public class AdminContractController {
    private final ContractService contractService;

    @GetMapping("/{contactNumber}")
    public ContractDto getContract(@PathVariable @NotNull @Positive Long contactNumber) {
        return contractService.getContactByContractNumber(contactNumber);
    }

    @GetMapping
    public List<ContractDto> getContracts() {
        return contractService.getContracts();
    }

    @PatchMapping("/{contractNumber}/user/{userId}")
    public ContractDto updateContractByContractNumber(@PathVariable @NotNull @Positive Long userId,
                                                      @PathVariable @NotNull @Positive Long contractNumber,
                                                      @Valid NewContractDto newContractDto
    ) {
        return contractService.updateContract(userId, contractNumber, newContractDto);
    }

    @PostMapping
    public ContractDto createContract(@Valid NewContractDto newContractDto) {
        return contractService.createContract(newContractDto);
    }
}
