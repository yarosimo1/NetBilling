package dbg.netbill.interactionapi.dto.contact;

import dbg.netbill.contracts.model.ContractStatus;
import dbg.netbill.users.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContractDto {
    private Long id;
    private User user;
    private Long number;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime closeAt;
    private ContractStatus status;
}
