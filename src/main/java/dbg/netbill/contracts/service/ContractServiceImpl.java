package dbg.netbill.contracts.service;

import dbg.netbill.contracts.mapper.ContractMapper;
import dbg.netbill.contracts.model.Contract;
import dbg.netbill.contracts.repository.ContractRepository;
import dbg.netbill.interactionapi.dto.contact.ContractDto;
import dbg.netbill.interactionapi.dto.contact.NewContractDto;
import dbg.netbill.interactionapi.exception.NotFoundException;
import dbg.netbill.time.TimeProvider;
import dbg.netbill.users.model.User;
import dbg.netbill.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractServiceImpl implements ContractService {
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;
    private final TimeProvider timeProvider;

    @Override
    public List<ContractDto> getContractsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с таким id:%s не найден".formatted(userId));
        }

        return contractRepository.findByUserId(userId).stream()
                .map(contractMapper::toContractDto)
                .toList();
    }

    @Override
    @Transactional
    public ContractDto createContract(NewContractDto newContractDto) {
        User user = userRepository.findById(newContractDto.getUserId()).orElseThrow(() ->
                        new NotFoundException("Пользователь с id:%d не найден".formatted(newContractDto.getUserId())));

        Contract contract = contractMapper.newContractDtotoContract(newContractDto);

        LocalDateTime now = timeProvider.now();

        contract.setUser(user);
        contract.setCreatedAt(now);
        contract.setUpdatedAt(now);

        Contract saved = contractRepository.save(contract);

        return contractMapper.toContractDto(saved);
    }

    @Override
    public List<ContractDto> getContracts() {
        return contractRepository.findAll().stream()
                .map(contractMapper::toContractDto)
                .toList();
    }

    @Override
    public ContractDto getContactByContractNumber(Long contactNumber) {
        Contract contract = contractRepository.findByNumber(contactNumber).orElseThrow(() ->
                new NotFoundException("Договор с таким номером:%s не найден".formatted(contactNumber)));

        return contractMapper.toContractDto(contract);
    }

    @Override
    @Transactional
    public ContractDto updateContract(Long userId, Long contractNumber, NewContractDto dto) {

        Contract contract = contractRepository.findByNumberAndUserId(contractNumber, userId).orElseThrow(() ->
                        new NotFoundException("Договор с номером:%d у пользователя с id:%d не найден"
                                .formatted(contractNumber, userId)
                        ));

        contractMapper.update(dto, contract);

        contract.setUpdatedAt(timeProvider.now());

        return contractMapper.toContractDto(contract);
    }
}
