package dbg.netbill.interactionapi.dto.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {
    @Size(min = 7, max = 100)
    @NotBlank
    private String email;

    @Size(min = 3, max = 32)
    @NotBlank
    private String username;

    @Size(min = 3, max = 100)
    @NotBlank
    private String password;

}
