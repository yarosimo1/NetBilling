package dbg.netbill.contracts.mapper;

import dbg.netbill.contracts.model.Contract;
import dbg.netbill.interactionapi.dto.contact.ContractDto;
import dbg.netbill.interactionapi.dto.contact.NewContractDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ContractMapper {
    @Mapping(source = "user.id", target = "userId")
    ContractDto toContractDto(Contract contract);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "closedAt", ignore = true)
    Contract toContract(ContractDto contractDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "closedAt", ignore = true)
    Contract newContractDtotoContract(NewContractDto newContractDto);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void update(NewContractDto dto,
                @MappingTarget Contract contract);
}
