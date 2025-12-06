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

		//Check if username is empty and return null if it is
		if (userName.isEmpty()) {
			return null;
		}

		User user = userName.get();	//If username is present, returns the value, otherwise throws NoSuchElementException.


		//If raw password is correct, it will convert entity to DTO and return the value
		if (user.checkPassword(password)) {
			return UserMapper.toDTO(user);
		}

		return null;	//Return null if password is invalid
	}

	@Override
	public UserDTO createNewUser(UserDTO userDTO) {
		//Check if username already exists in the DB
		if (userRepository.existsByUsername(userDTO.getUsername())) {
			throw new UsernameAlreadyExistsException(userDTO.getUsername());
		}

		//Check if email already exists in the DB
		if (userRepository.existsByEmail(userDTO.getEmail())) {
			throw new EmailAlreadyExistsException(userDTO.getEmail());
		}

		//Check if mobile number already exists in the DB
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
			//Check if requested user ID is not logged in user ID
			if (!requestedUserId.equals(loggedInUserId)) {
				//Throw an unauthorized access exception if user ID is not equals to login user ID or role is not admin
				throw new UnauthorizedAccessException("You can only view your own user account!");
			}
		}

		Optional<User> userOpt = userRepository.findById(requestedUserId);	//Fetch user from DB

		if (userOpt.isPresent()) {
			return UserMapper.toDTO(userOpt.get());
		}

		return null; //Return null if user not found
	}


	@Override
	public ArrayList <UserDTO> getAllUsers(String role) {
		if (!"Admin".equalsIgnoreCase(role)) {
			//Throw an unauthorized access exception if user ID is not equals to login user ID or role is not admin
			throw new UnauthorizedAccessException("Only admin users can view all user accounts!");
		}

		ArrayList <User> users = new ArrayList <>(userRepository.findAll());	//Returns all instances of the type users
		ArrayList <UserDTO> userDtos = new ArrayList <>();						//Construct empty array list

		for (int i = 0; i <= users.size(); i++) {
			UserDTO userDto = UserMapper.toDTO(users.get(i));	//Convert entity to DTO
			userDtos.add(userDto);								//Add specific element to end of the list
		}

		return userDtos;
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Long userId, Long loggedInUserId, String role) {
		Optional <User> userID = userRepository.findById(userId);

		//Verify if user ID is present in the DB
		if (userID.isEmpty()) {
			throw new UserIdNotFoundException(userId);
		}

		User user = userID.get();	//Returns the value of user ID if it exists in the DB, otherwise throws NoSuchElementException

		//User can only update their own profile
		if (!userId.equals(loggedInUserId) && !"Admin".equalsIgnoreCase(role)) {
			//Throw an unauthorized access exception if user ID is not equals to login user ID or role is not admin
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

		userRepository.save(user);
		return UserMapper.toDTO(user);
	}

	@Override
	public void deleteUser(Long userId, Long loggedInUserId, String role) {
		Optional <User> userID = userRepository.findById(userId);	//Retrieve user entity by user ID

		if (userID.isEmpty()) {
			throw new UserIdNotFoundException(userId);				//Throw an user ID not found exception if user ID is not present in DB
		}

		if (!"Admin".equalsIgnoreCase(role) && !userId.equals(loggedInUserId)) {
			//Throw an unauthorized access exception if user ID is not equals to login user ID or role is not admin
			throw new UnauthorizedAccessException("You can only delete your own user account!");
		}

		userRepository.deleteById(userId);					//Delete user entity by user ID
		System.out.println("User deleted successfully!");	//Print delete successfully message in console
	}
}