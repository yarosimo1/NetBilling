package dbg.netbill.users.controller;

import dbg.netbill.interactionapi.dto.NewUserDto;
import dbg.netbill.interactionapi.dto.UserDto;
import dbg.netbill.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController("api/public/user")
@RequiredArgsConstructor
public class PublicUserController {
    private final UserService userService;

    @PostMapping("/register")
    public UserDto registerNewUser(@Valid @RequestBody NewUserDto userDto) {
        return userService.registerUser(userDto);
    }
}
