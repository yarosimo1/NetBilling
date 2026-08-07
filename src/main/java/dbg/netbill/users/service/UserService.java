package dbg.netbill.users.service;

import dbg.netbill.interactionapi.dto.users.NewUserDto;
import dbg.netbill.interactionapi.dto.users.UserDto;

public interface UserService {
    UserDto registerUser(NewUserDto userDto);

    void delete(Long userId);

    UserDto update(Long userId, NewUserDto userDto);
}
