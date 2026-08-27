package dbg.netbill.accounts.service;

import dbg.netbill.accounts.repository.AccountRepository;
import dbg.netbill.interactionapi.dto.accounts.AccountDto;
import dbg.netbill.interactionapi.dto.accounts.NewAccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;


    @Override
    @Transactional
    public AccountDto updateAccount(NewAccountDto dto) {
        log.info("updateAccount {}:",dto);
        return null;
    }

    @Override
    public AccountDto getAccount(Long id) {
        log.info("getAccount");
        return null;
    }
}
