package dbg.netbill.interactionapi.dto.contact;

import dbg.netbill.contracts.model.ContractStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContractDto {
    private Long id;
    private Long userId;
    private Long number;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime closedAt;
    private ContractStatus status;
}
