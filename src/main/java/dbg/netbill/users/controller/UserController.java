package dbg.netbill.users.controller;

import dbg.netbill.interactionapi.dto.NewUserDto;
import dbg.netbill.interactionapi.dto.UserDto;
import dbg.netbill.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping("/{userId}}")
    public UserDto updateUser(@PathVariable Long userId,
                              @Valid @RequestBody NewUserDto userDto
    ) {
        return userService.update(userId, userDto);
    }
}
