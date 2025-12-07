package com.example.user.Service;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.user.DTO.UserDTO;
import com.example.user.Entity.User;
import com.example.user.Exception.EmailAlreadyExistsException;
import com.example.user.Exception.InvalidPasswordException;
import com.example.user.Exception.MobileNumberAlreadyExistsException;
import com.example.user.Exception.UnauthorizedAccessException;
import com.example.user.Exception.UserIdNotFoundException;
import com.example.user.Exception.UsernameAlreadyExistsException;
import com.example.user.Interface.UserInterface;
import com.example.user.Mapper.UserMapper;
import com.example.user.Repository.UserRepository;

public class UserService implements UserInterface {
	@Autowired
	private UserRepository userRepository;	//User repository to access user data

	public UserDTO userLogin(String username, String password) {
		Optional <User> userName = userRepository.findByUsername(username);	//Fetch username from DB

		//Return null if username field is empty
		if (userName.isEmpty()) {
			return null;
		}

		User user = userName.get();	//If username is present, returns the value, otherwise throws NoSuchElementException.


		//Convert entity to DTO and returns the value if password is correct
		if (user.checkPassword(password)) {
			return UserMapper.toDTO(user);
		}

		return null;	//Return null if password is invalid
	}

	@Override
	public UserDTO createNewUser(UserDTO userDTO) {
		//Throw an exception if username already exists in the DB
		if (userRepository.existsByUsername(userDTO.getUsername())) {
			throw new UsernameAlreadyExistsException(userDTO.getUsername());
		}

		//Throw an exception if email already exists in DB
		if (userRepository.existsByEmail(userDTO.getEmail())) {
			throw new EmailAlreadyExistsException(userDTO.getEmail());
		}

		//Throw an exception if mobile number already exists in the DB
		if (userRepository.existsByMobileNo(userDTO.getMobileNo())) {
			throw new MobileNumberAlreadyExistsException(userDTO.getMobileNo());
		}

		User user = UserMapper.toEntity(userDTO);	//Convert DTO to entity
		userRepository.save(user);					//Save user entity into DB
		return UserMapper.toDTO(user);				//Convert entity to DTO
	}

	@Override
	public UserDTO getUser(Long requestedUserId, Long loggedInUserId, String role) {
		//Only admin role can access to any user accounts
		if (!role.equals("Admin")) {
			//Throw an unauthorized access exception if user's role is not admin or user ID is not the logged in user ID
			if (!requestedUserId.equals(loggedInUserId)) {
				throw new UnauthorizedAccessException("You can only view your own user account!");
			}
		}

		Optional<User> userID = userRepository.findById(requestedUserId);	//Fetch user from DB

		//Convert entity to DTO if user ID is present
		if (userID.isPresent()) {
			return UserMapper.toDTO(userID.get());
		}

		return null; //Return null if user not found
	}


	@Override
	public ArrayList <UserDTO> getAllUsers(String role) {
		//Throw an unauthorized access exception if user ID is not equals to login user ID or role is not admin
		if (!"Admin".equalsIgnoreCase(role)) {
			throw new UnauthorizedAccessException("Only admin users can view all user accounts!");
		}

		//Array lists to return all instances of user type and construct an empty array list for user dtos
		ArrayList <User> users = new ArrayList <>(userRepository.findAll());	
		ArrayList <UserDTO> userDtos = new ArrayList <>();

		//Loop through the user list entity 1 by 1
		for (int i = 0; i < users.size(); i++) {
			UserDTO userDto = UserMapper.toDTO(users.get(i));	//Convert entity to DTO
			userDtos.add(userDto);								//Add specific element to end of the list
		}

		return userDtos;
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Long userId, Long loggedInUserId, String role) {
		Optional <User> userID = userRepository.findById(userId);	//Retrieve user entity by user ID

		//Verify if user ID is present in the DB
		if (userID.isEmpty()) {
			throw new UserIdNotFoundException(userId);
		}

		User user = userID.get();	//Returns the value of user ID if it exists in the DB, otherwise throws NoSuchElementException

		//Throw an unauthorized access exception if user ID is not equals to login user ID or role is not admin
		if (!userId.equals(loggedInUserId) && !"Admin".equalsIgnoreCase(role)) {
			throw new UnauthorizedAccessException("You can only update your own user account!");
		}

		//Fields that can be updated by all users regardless of role
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


		//Only admin can update these fields
		if ("Admin".equalsIgnoreCase(role)) {
			if (userDTO.getSuspensionDate() != null) {
				user.setSuspensionDate(userDTO.getSuspensionDate());
			}

			if (userDTO.getUnsuspensionDate() != null) {
				user.setUnsuspensionDate(userDTO.getUnsuspensionDate());
			}

			if (userDTO.getTerminationDate() != null) {
				user.setTerminationDate(userDTO.getTerminationDate());
			}

			if (userDTO.getUnterminationDate() != null) {
				user.setUnterminationDate(userDTO.getUnterminationDate());
			}

			if (userDTO.getInactiveDate() != null) {
				user.setInactiveDate(user.getInactiveDate());
			}
		}

		userRepository.save(user);		//Save user entity into DB
		return UserMapper.toDTO(user);	//Convert entity to DTO
	}

	@Override
	public void deleteUser(Long userId, Long loggedInUserId, String role) {
		Optional <User> userID = userRepository.findById(userId);	//Retrieve user entity by user ID

		//Throw an user ID not found exception if user ID is not present in DB
		if (userID.isEmpty()) {
			throw new UserIdNotFoundException(userId);				
		}

		//Throw an unauthorized access exception if user ID is not equals to login user ID or role is not admin
		if (!"Admin".equalsIgnoreCase(role) && !userId.equals(loggedInUserId)) {
			throw new UnauthorizedAccessException("You can only delete your own user account!");
		}

		userRepository.deleteById(userId);					//Delete user entity by user ID
		System.out.println("User deleted successfully!");	//Print delete successfully message in console
	}
}