package ru.kata.spring.boot_security.demo.mappers;

import org.mapstruct.Mapper;
import ru.kata.spring.boot_security.demo.DTO.UserCreateDto;
import ru.kata.spring.boot_security.demo.DTO.UserDto;
import ru.kata.spring.boot_security.demo.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    User userCreateDtoToUser(UserCreateDto userCreateDto);
}
