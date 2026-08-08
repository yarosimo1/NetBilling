package dbg.netbill.contracts;

import dbg.netbill.contracts.mapper.ContractMapper;
import dbg.netbill.contracts.model.Contract;
import dbg.netbill.contracts.model.ContractStatus;
import dbg.netbill.contracts.repository.ContractRepository;
import dbg.netbill.contracts.service.ContractServiceImpl;
import dbg.netbill.interactionapi.dto.contact.ContractDto;
import dbg.netbill.interactionapi.dto.contact.NewContractDto;
import dbg.netbill.interactionapi.exception.NotFoundException;
import dbg.netbill.time.TimeProvider;
import dbg.netbill.users.model.User;
import dbg.netbill.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContractTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ContractMapper contractMapper;

    @Mock
    private TimeProvider timeProvider;

    @InjectMocks
    private ContractServiceImpl contractService;

    private User user;
    private Contract contract;
    private ContractDto contractDto;
    private NewContractDto newContractDto;

    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.of(2026, 8, 8, 12, 0);

        user = new User();
        user.setId(1L);
        user.setEmail("test@mail.ru");
        user.setUsername("tester");

        contract = new Contract();
        contract.setId(10L);
        contract.setUser(user);
        contract.setNumber(12345L);
        contract.setCreatedAt(now);
        contract.setUpdatedAt(now);
        contract.setStatus(ContractStatus.ACTIVE);

        contractDto = new ContractDto();
        contractDto.setId(10L);
        contractDto.setUserId(1L);
        contractDto.setNumber(12345L);
        contractDto.setCreatedAt(now);
        contractDto.setUpdatedAt(now);
        contractDto.setStatus(ContractStatus.ACTIVE);

        newContractDto = new NewContractDto();
        newContractDto.setUserId(1L);
        newContractDto.setNumber(12345L);
        newContractDto.setStatus(ContractStatus.ACTIVE);
    }

    @Test
    void getContractsByUserId_shouldReturnContracts() {
        // Arrange
        when(userRepository.existsById(1L))
                .thenReturn(true);

        when(contractRepository.findByUserId(1L))
                .thenReturn(List.of(contract));

        when(contractMapper.toContractDto(contract))
                .thenReturn(contractDto);

        // Act
        List<ContractDto> result =
                contractService.getContractsByUserId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(contractDto, result.get(0));

        verify(userRepository).existsById(1L);
        verify(contractRepository).findByUserId(1L);
        verify(contractMapper).toContractDto(contract);
    }

    @Test
    void getContractsByUserId_shouldThrowNotFoundException_whenUserNotFound() {
        // Arrange
        when(userRepository.existsById(1L))
                .thenReturn(false);

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> contractService.getContractsByUserId(1L)
        );

        assertEquals(
                "Пользователь с таким id:1 не найден",
                exception.getMessage()
        );

        verify(userRepository).existsById(1L);

        verifyNoInteractions(contractRepository);
        verifyNoInteractions(contractMapper);
    }

    @Test
    void createContract_shouldCreateContract() {
        // Arrange
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(contractMapper.newContractDtotoContract(newContractDto))
                .thenReturn(contract);

        when(timeProvider.now())
                .thenReturn(now);

        when(contractRepository.save(contract))
                .thenReturn(contract);

        when(contractMapper.toContractDto(contract))
                .thenReturn(contractDto);

        // Act
        ContractDto result =
                contractService.createContract(newContractDto);

        // Assert
        assertNotNull(result);
        assertEquals(contractDto, result);

        assertEquals(user, contract.getUser());
        assertEquals(now, contract.getCreatedAt());
        assertEquals(now, contract.getUpdatedAt());

        verify(userRepository).findById(1L);

        verify(contractMapper)
                .newContractDtotoContract(newContractDto);

        verify(timeProvider).now();

        verify(contractRepository).save(contract);

        verify(contractMapper)
                .toContractDto(contract);
    }

    @Test
    void createContract_shouldThrowNotFoundException_whenUserNotFound() {
        // Arrange
        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> contractService.createContract(newContractDto)
        );

        assertEquals(
                "Пользователь с id:1 не найден",
                exception.getMessage()
        );

        verify(userRepository).findById(1L);

        verifyNoInteractions(contractMapper);
        verifyNoInteractions(contractRepository);
        verifyNoInteractions(timeProvider);
    }

    @Test
    void getContracts_shouldReturnAllContracts() {
        // Arrange
        Contract secondContract = new Contract();
        secondContract.setId(20L);
        secondContract.setNumber(54321L);

        ContractDto secondContractDto = new ContractDto();
        secondContractDto.setId(20L);
        secondContractDto.setNumber(54321L);

        when(contractRepository.findAll())
                .thenReturn(List.of(contract, secondContract));

        when(contractMapper.toContractDto(contract))
                .thenReturn(contractDto);

        when(contractMapper.toContractDto(secondContract))
                .thenReturn(secondContractDto);

        // Act
        List<ContractDto> result =
                contractService.getContracts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(contractDto, result.get(0));
        assertEquals(secondContractDto, result.get(1));

        verify(contractRepository).findAll();

        verify(contractMapper)
                .toContractDto(contract);

        verify(contractMapper)
                .toContractDto(secondContract);
    }

    @Test
    void getContactByContractNumber_shouldReturnContract() {
        // Arrange
        when(contractRepository.findByNumber(12345L))
                .thenReturn(Optional.of(contract));

        when(contractMapper.toContractDto(contract))
                .thenReturn(contractDto);

        // Act
        ContractDto result =
                contractService.getContactByContractNumber(12345L);

        // Assert
        assertNotNull(result);
        assertEquals(contractDto, result);

        verify(contractRepository)
                .findByNumber(12345L);

        verify(contractMapper)
                .toContractDto(contract);
    }

    @Test
    void getContactByContractNumber_shouldThrowNotFoundException_whenContractNotFound() {
        // Arrange
        when(contractRepository.findByNumber(12345L))
                .thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> contractService.getContactByContractNumber(12345L)
        );

        assertEquals(
                "Договор с таким номером:12345 не найден",
                exception.getMessage()
        );

        verify(contractRepository)
                .findByNumber(12345L);

        verifyNoInteractions(contractMapper);
    }

    @Test
    void updateContract_shouldUpdateContract() {
        // Arrange
        when(contractRepository.findByNumberAndUserId(12345L, 1L))
                .thenReturn(Optional.of(contract));

        when(timeProvider.now())
                .thenReturn(now);

        when(contractMapper.toContractDto(contract))
                .thenReturn(contractDto);

        // Act
        ContractDto result =
                contractService.updateContract(
                        1L,
                        12345L,
                        newContractDto
                );

        // Assert
        assertNotNull(result);
        assertEquals(contractDto, result);

        assertEquals(now, contract.getUpdatedAt());

        verify(contractRepository)
                .findByNumberAndUserId(12345L, 1L);

        verify(contractMapper)
                .update(newContractDto, contract);

        verify(timeProvider).now();

        verify(contractMapper)
                .toContractDto(contract);
    }

    @Test
    void updateContract_shouldThrowNotFoundException_whenContractNotFound() {
        // Arrange
        when(contractRepository.findByNumberAndUserId(12345L, 1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> contractService.updateContract(
                        1L,
                        12345L,
                        newContractDto
                )
        );

        assertEquals(
                "Договор с номером:12345 у пользователя с id:1 не найден",
                exception.getMessage()
        );

        verify(contractRepository)
                .findByNumberAndUserId(12345L, 1L);

        verifyNoInteractions(contractMapper);
        verifyNoInteractions(timeProvider);
    }
}
