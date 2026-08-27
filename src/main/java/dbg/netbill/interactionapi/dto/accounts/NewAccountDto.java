package dbg.netbill.interactionapi.dto.accounts;

import jakarta.validation.constraints.NotBlank;

import java.util.Currency;

public class NewAccountDto {
    @NotBlank
    private Long contractId;
    @NotBlank
    private Long balance;
    @NotBlank
    private Currency currency;
}
