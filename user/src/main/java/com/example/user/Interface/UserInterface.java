package com.example.user.Interface;
import java.util.*;
import com.example.user.DTO.UserDTO;

public interface UserInterface {
	//CRUD methods to crate, read update and delete users in the DB
	UserDTO createNewUser(UserDTO userDTO);
	UserDTO getUser(Long userId);
	ArrayList <UserDTO> getAllUsers();
	UserDTO updateUser(UserDTO userDTO, Long userId);
	void deleteUser(Long userId);
}