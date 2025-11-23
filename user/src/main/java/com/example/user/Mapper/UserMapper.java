package com.example.user.Mapper;
import com.example.user.DTO.UserDTO;
import com.example.user.Entity.User;

public class UserMapper {
    /** Convert Entity to DTO */
    public static UserDTO toDTO(User user) {
        if (user == null) return null;	//Return null if user entity is null
        
        //Create and return a new user DTO populated with entity fields
        return new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(),
        user.getMobileNo(), user.getAddress(), user.getPostalCode(), user.getPassword(), user.getUserStatus(), user.getCreatedDate(),
        user.getSuspendDate(), user.getUnsuspendDate(), user.getTerminateDate(), user.getUnterminateDate(), user.getInactiveDate());
    }

    /** Convert DTO to Entity */
    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) return null;	//Return null if user DTO is null
        
        //Populate entity fields from user DTO
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setMobileNo(userDTO.getMobileNo());
        user.setAddress(userDTO.getAddress());
        user.setPostalCode(userDTO.getPostalCode());
        user.setPassword(userDTO.getPassword());
        user.setUserStatus(userDTO.getUserStatus());
        user.setCreatedDate(userDTO.getCreatedDate());
        user.setSuspendDate(userDTO.getSuspendDate());
        user.setUnsuspendDate(userDTO.getUnsuspendDate());
        user.setTerminateDate(userDTO.getTerminateDate());
        user.setUnterminateDate(userDTO.getUnterminateDate());
        user.setInactiveDate(userDTO.getInactiveDate());
        return user;	//Return populated user entity
    }
}