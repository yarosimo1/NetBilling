package dbg.netbill.contracts.service;

import dbg.netbill.interactionapi.dto.contact.ContractDto;
import dbg.netbill.interactionapi.dto.contact.NewContractDto;

import java.util.List;

public interface ContractService {

    List<ContractDto> getContracts();

    ContractDto getContactByContractNumber(Long contactNumber);

    List<ContractDto> getContractsByUserId(Long userId);

    ContractDto createContract(NewContractDto newContractDto);

    ContractDto updateContract(Long userId, Long contractNumber, NewContractDto dto);
}
