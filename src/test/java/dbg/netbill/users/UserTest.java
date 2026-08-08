package dbg.netbill.users;

import dbg.netbill.interactionapi.dto.users.NewUserDto;
import dbg.netbill.interactionapi.dto.users.UserDto;
import dbg.netbill.interactionapi.exception.ConflictException;
import dbg.netbill.interactionapi.exception.NotFoundException;
import dbg.netbill.time.TimeProvider;
import dbg.netbill.users.mapper.UserMapper;
import dbg.netbill.users.model.User;
import dbg.netbill.users.model.UserRole;
import dbg.netbill.users.repository.UserRepository;
import dbg.netbill.users.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTest {
    private final LocalDateTime now =
            LocalDateTime.of(2026, 8, 7, 15, 30);
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private TimeProvider timeProvider;
    @InjectMocks
    private UserServiceImpl userService;

    private NewUserDto newUserDto;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {

        newUserDto = new NewUserDto(
                "test@mail.ru",
                "tester",
                "password"
        );

        user = new User();
        user.setId(1L);
        user.setEmail("test@mail.ru");
        user.setUsername("tester");
        user.setPassword("password");

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
    }

    @Test
    void registerUser_shouldRegisterSuccessfully() {

        when(timeProvider.now()).thenReturn(now);

        when(userRepository.existsByEmail(newUserDto.getEmail()))
                .thenReturn(false);

        when(userMapper.newUserDtoToEntity(newUserDto))
                .thenReturn(user);

        when(userRepository.save(user))
                .thenReturn(user);

        when(userMapper.toDto(user))
                .thenReturn(userDto);

        UserDto result = userService.registerUser(newUserDto);

        assertEquals(userDto, result);

        ArgumentCaptor<User> captor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(captor.capture());

        User saved = captor.getValue();

        assertEquals(UserRole.USER, saved.getRole());
        assertEquals(now, saved.getCreatedAt());
        assertEquals(now, saved.getUpdatedAt());

        verify(timeProvider).now();
    }

    @Test
    void registerUser_shouldThrowConflictException() {

        when(userRepository.existsByEmail(newUserDto.getEmail()))
                .thenReturn(true);

        assertThrows(
                ConflictException.class,
                () -> userService.registerUser(newUserDto)
        );

        verify(userRepository, never()).save(any());
        verifyNoInteractions(userMapper);
    }

    @Test
    void update_shouldUpdateUser() {

        when(timeProvider.now()).thenReturn(now);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userMapper.toDto(user))
                .thenReturn(userDto);

        UserDto result = userService.update(1L, newUserDto);

        verify(userMapper).update(newUserDto, user);

        assertEquals(now, user.getUpdatedAt());

        assertEquals(userDto, result);

        verify(timeProvider).now();
    }

    @Test
    void update_shouldThrowNotFoundException() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> userService.update(1L, newUserDto)
        );

        verify(userMapper, never()).update(any(), any());
        verifyNoInteractions(timeProvider);
    }

    @Test
    void delete_shouldDeleteUser() {

        when(userRepository.existsById(1L))
                .thenReturn(true);

        userService.delete(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void delete_shouldThrowNotFoundException() {

        when(userRepository.existsById(1L))
                .thenReturn(false);

        assertThrows(
                NotFoundException.class,
                () -> userService.delete(1L)
        );

        verify(userRepository, never()).deleteById(anyLong());
    }
}
