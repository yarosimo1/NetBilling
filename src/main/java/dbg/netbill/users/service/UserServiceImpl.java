package dbg.netbill.users.service;

import dbg.netbill.interactionapi.dto.users.NewUserDto;
import dbg.netbill.interactionapi.dto.users.UserDto;
import dbg.netbill.interactionapi.exception.ConflictException;
import dbg.netbill.interactionapi.exception.NotFoundException;
import dbg.netbill.time.TimeProvider;
import dbg.netbill.users.mapper.UserMapper;
import dbg.netbill.users.model.User;
import dbg.netbill.users.model.UserRole;
import dbg.netbill.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TimeProvider timeProvider;

    @Override
    @Transactional
    public UserDto registerUser(NewUserDto userDto) {
        log.info("Регитсрация пользователя userDto{}:", userDto);

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ConflictException("Пользователь с таким email:%s уже существует".formatted(userDto.getEmail()));
        }

        User newUser = userMapper.newUserDtoToEntity(userDto);

        LocalDateTime now = timeProvider.now();

        newUser.setRole(UserRole.USER);
        newUser.setCreatedAt(now);
        newUser.setUpdatedAt(now);

        return userMapper.toDto(userRepository.save(newUser));
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        log.info("Удаление пользователя userId{}:", userId);
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id:%d не найден".formatted(userId));
        }
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public UserDto update(Long userId, NewUserDto newUserDto) {
        User updatedUser = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь с id:%d не найден".formatted(userId)));

        userMapper.update(newUserDto, updatedUser);
        updatedUser.setUpdatedAt(timeProvider.now());

        return userMapper.toDto(updatedUser);
    }
}
