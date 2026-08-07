package dbg.netbill.contracts.service;

import dbg.netbill.interactionapi.dto.contact.ContractDto;
import dbg.netbill.interactionapi.dto.contact.NewContractDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface ContractService {
    List<ContractDto> getContracts(Long userId);

    List<ContractDto> getContracts();

    ContractDto getContactByContractNumber(Long contactNumber);

    ContractDto updateContractByContractNumber(Long contractNumber, NewContractDto newContractDto);

    ContractDto updateContractByUserId(Long userId, NewContractDto newContractDto);
}
