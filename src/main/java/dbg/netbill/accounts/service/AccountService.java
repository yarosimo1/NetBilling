package dbg.netbill.accounts.service;

import dbg.netbill.interactionapi.dto.accounts.AccountDto;
import dbg.netbill.interactionapi.dto.accounts.NewAccountDto;

public interface AccountService {
    AccountDto updateAccount(NewAccountDto dto);

    AccountDto getAccount(Long id);
}
