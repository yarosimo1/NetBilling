package dbg.netbill.contracts.controller;

import dbg.netbill.contracts.service.ContractService;
import dbg.netbill.interactionapi.dto.contact.ContractDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController("api/users/{userId}/contracts/")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;

    @GetMapping
    public List<ContractDto> getContracts(@PathVariable @NotNull  @Positive Long userId) {
        return contractService.getContracts(userId);
    }
}
