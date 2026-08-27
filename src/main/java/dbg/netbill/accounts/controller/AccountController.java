package dbg.netbill.accounts.controller;

import dbg.netbill.accounts.service.AccountService;
import dbg.netbill.interactionapi.dto.accounts.AccountDto;
import dbg.netbill.interactionapi.dto.accounts.NewAccountDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController("api/user/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PatchMapping("/{accountId}")
    public AccountDto updateAccount(NewAccountDto dto) {
        return accountService.updateAccount(dto);
    }

    @GetMapping("/{accountId}")
    public AccountDto getAccount(@PathVariable @NotNull @Positive Long accountId) {
        return accountService.getAccount(accountId);
    }
}
