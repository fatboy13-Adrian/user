package com.example.user.Interface;
import java.util.*;
import com.example.user.UserDTO;

public interface UserInterface {
	//CRUD methods to crate, read update and delete users in the DB
	UserDTO createNewUser(UserDTO userDTO);
	UserDTO getUser(Long userId);
	ArrayList <UserDTO> getAllUsers();
	UserDTO updateUser();
	void deleteUser();
}