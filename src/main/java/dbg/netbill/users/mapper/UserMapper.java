package dbg.netbill.users.mapper;

import dbg.netbill.interactionapi.dto.NewUserDto;
import dbg.netbill.interactionapi.dto.UserDto;
import dbg.netbill.users.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User newUserDtoToEntity(NewUserDto newUserDto);

    User toEntity(UserDto userDto);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    void update(NewUserDto dto,
                @MappingTarget User user);
}
