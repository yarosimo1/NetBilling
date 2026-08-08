package dbg.netbill.interactionapi.dto.contact;

import dbg.netbill.contracts.model.ContractStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewContractDto {
    @NotBlank
    private Long userId;
    @Size(min = 5, max = 100)
    @NotBlank
    private Long number;
    private ContractStatus status;
}
