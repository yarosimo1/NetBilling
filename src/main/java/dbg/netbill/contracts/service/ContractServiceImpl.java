package dbg.netbill.contracts.service;

import dbg.netbill.interactionapi.dto.contact.ContractDto;
import dbg.netbill.interactionapi.dto.contact.NewContractDto;

import java.util.List;

public class ContractServiceImpl implements ContractService {
    @Override
    public List<ContractDto> getContracts(Long userId) {
        return List.of();
    }

    @Override
    public List<ContractDto> getContracts() {
        return List.of();
    }

    @Override
    public ContractDto getContactByContractNumber(Long contactNumber) {
        return null;
    }

    @Override
    public ContractDto updateContractByContractNumber(Long contractNumber, NewContractDto newContractDto) {
        return null;
    }

    @Override
    public ContractDto updateContractByUserId(Long userId, NewContractDto newContractDto) {
        return null;
    }
}
