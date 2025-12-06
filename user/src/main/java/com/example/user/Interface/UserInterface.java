package com.example.user.Interface;
import java.util.*;
import com.example.user.DTO.UserDTO;

public interface UserInterface {
	//CRUD methods to crate, read update and delete users in the DB
	UserDTO createNewUser(UserDTO userDTO);
	UserDTO getUser(Long requestedUserId, Long loggedInUserId, String role);
	ArrayList <UserDTO> getAllUsers(String role);
	UserDTO updateUser(UserDTO userDTO, Long userId, Long loggedInUserId, String role);
	void deleteUser(Long userId, Long loggedInUserId, String role);
}