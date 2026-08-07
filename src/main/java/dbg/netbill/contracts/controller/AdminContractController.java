package dbg.netbill.contracts.controller;

import dbg.netbill.contracts.service.ContractService;
import dbg.netbill.interactionapi.dto.contact.ContractDto;
import dbg.netbill.interactionapi.dto.contact.NewContractDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController("api/admin/contract")
@RequiredArgsConstructor
public class AdminContractController {
    private final ContractService contractService;

    @GetMapping("/{contactNumber}")
    public ContractDto getContract(@PathVariable @NotNull @Positive Long contactNumber) {
        return contractService.getContactByContractNumber(contactNumber);
    }

    @GetMapping("/user/{userId}")
    public List<ContractDto> getContractByUserId(@PathVariable @NotNull @Positive Long userId) {
        return contractService.getContracts(userId);
    }

    @GetMapping
    public List<ContractDto> getContracts() {
        return contractService.getContracts();
    }

    @PatchMapping("/{contractNumber}")
    public ContractDto updateContractByContractNumber(@PathVariable @NotNull @Positive Long contractNumber,
                                                      @Valid NewContractDto newContractDto
    ) {
        return contractService.updateContractByContractNumber(contractNumber, newContractDto);
    }

    @PatchMapping("/user/{userId}")
    public ContractDto updateContractByUserId(@PathVariable @NotNull @Positive Long userId,
                                                      @Valid NewContractDto newContractDto
    ) {
        return contractService.updateContractByUserId(userId, newContractDto);
    }
}
