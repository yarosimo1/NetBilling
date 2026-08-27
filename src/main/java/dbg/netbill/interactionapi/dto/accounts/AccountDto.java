package dbg.netbill.interactionapi.dto.accounts;

import java.time.LocalDateTime;
import java.util.Currency;

public class AccountDto {
    private Long id;

    private Long contractId;

    private Long balance;

    private Currency currency;

    private LocalDateTime updatedAt;
    private LocalDateTime closedAt;
}
