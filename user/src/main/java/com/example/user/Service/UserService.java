package com.example.user.Service;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.user.DTO.UserDTO;
import com.example.user.Entity.User;
import com.example.user.Exception.InvalidPasswordException;
import com.example.user.Exception.UserIdNotFoundException;
import com.example.user.Interface.UserInterface;
import com.example.user.Mapper.UserMapper;
import com.example.user.Repository.UserRepository;

public class UserService implements UserInterface {
	@Autowired
	private UserRepository userRepository;	//User repository to access user data

	public UserDTO userLogin(String username, String password) {
		Optional <User> userName = userRepository.findByUsername(username);

		//Check if username is empty and return null if it is
		if (userName.isEmpty()) {
			return null;
		}

		User user = userName.get();	//If a value is present, returns the value, otherwise throws NoSuchElementException.


		//If raw password is correct, it will convert entity to DTO and return the value
		if (user.checkPassword(password)) {
			return UserMapper.toDTO(user);
		}

		return null;	//Return null if password is invalid
	}

	@Override
	public UserDTO createNewUser(UserDTO userDTO) {
		User user = UserMapper.toEntity(userDTO);	//Convert DTO to entity
		userRepository.save(user);					//Save user entity into DB
		return UserMapper.toDTO(user);				//Convert entity to DTO
	}

	@Override
	public UserDTO getUser(Long userId) {
		Optional <User> userID = userRepository.findById(userId);	//Retrieve user by its user ID

		//Verify if user ID is present in DB
		if (userID.isPresent()) {
			User user = userID.get();		//Get user entity from optional
			return UserMapper.toDTO(user);	//Convert it into DTO and return user
		} else {
			return null;	//Return null if no user is found in DB
		}
	}

	@Override
	public ArrayList <UserDTO> getAllUsers() {
		ArrayList <User> users = new ArrayList <>(userRepository.findAll());	//Returns all instances of the type users
		ArrayList <UserDTO> userDtos = new ArrayList <>();						//Construct empty array list

		for (int i = 0; i <= users.size(); i++) {
			UserDTO userDto = UserMapper.toDTO(users.get(i));	//Convert entity to DTO
			userDtos.add(userDto);								//Add specific element to end of the list
		}

		return userDtos;
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Long userId) {
		Optional <User> userID = userRepository.findById(userId);

		//Verify if user ID is present in DB
		if (userID.isPresent()) {
			User user = userID.get();		//Get user entity from optional, otherwise throws NoSuchElementException
			
			//Update all user fields
			if (userDTO.getFirstName() != null) {
				user.setFirstName(userDTO.getFirstName());
			}
			
			if (userDTO.getLastName() != null) {
				user.setLastName(userDTO.getLastName());
			}
			
			if (userDTO.getUsername() != null) {
				user.setUsername(userDTO.getUsername());
			}
			
			if (userDTO.getEmail() != null) {
				user.setEmail(userDTO.getEmail());
			}
			
			if (userDTO.getMobileNo() != null) {
				user.setMobileNo(userDTO.getMobileNo());
			}
			
			if (userDTO.getAddress() != null) {
				user.setAddress(userDTO.getAddress());
			}
			
			if (userDTO.getPostalCode() != null) {
				user.setPostalCode(userDTO.getPostalCode());

			}
			
			if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
				String passwordError = userDTO.validatePassword(userDTO.getPassword());
				
				if (passwordError != null) {
					throw new InvalidPasswordException(passwordError);
				} 
				
				user.setPassword(userDTO.getPassword());
			}
			
			userRepository.save(user);		//Save user entity into DB
			
			return UserMapper.toDTO(user);	//Convert it into DTO and return user
		} else {
			return null;	//Return null if no user is found in DB
		}
	}
	
	@Override
	public void deleteUser(Long userId) {
		Optional <User> userID = userRepository.findById(userId);	//Retrieve user entity by user ID
		
		//Check if user ID is present in DB
		if (userID.isPresent()) {
			userRepository.deleteById(userId);					//Delete user entity by user ID
			System.out.println("User deleted successfully!");	//Print delete successfully message in console
		} else {
		throw new UserIdNotFoundException(userId);				//Throw an user ID not found exception if user ID is not present in DB
		}
	}
}