package com.example.user.Controller;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.user.DTO.UserDTO;
import com.example.user.SecurityUtil.SecurityUtil;
import com.example.user.Service.UserService;

@RestController             //Marks the class as a REST controller
@RequestMapping("/users")   //Base URL mapping for this controller's endpoints
public class UserController {
	@Autowired
	private UserService userService;	//Inject user service for user operations

	//Declare class level variables for logged in user ID and role
	private Long loggedInUserId;
	private String role;

	@PostMapping("/login") 
	public ResponseEntity <UserDTO> userLogin(@RequestParam String username, @RequestParam String password) {
		UserDTO userDTO = userService.userLogin(username, password);

		//If login successful, return user data with HTTP 200
		if (userDTO != null) {
			return ResponseEntity.ok(userDTO);
		} 
		
		//Otherwise return HTTP 401 Unauthorized
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@PostMapping("/create")
	public ResponseEntity <UserDTO> createNewUser(@RequestBody UserDTO userDTO) {
		UserDTO createdUser = userService.createNewUser(userDTO);
		return ResponseEntity.ok(createdUser);
	}

	@GetMapping("/userId")
	public ResponseEntity <UserDTO> getUser(@PathVariable Long userId, Authentication authentication) {
		//Extract logged-in user's ID and role from Spring Security
		loggedInUserId = SecurityUtil.getLoggedInUserId(authentication);
		role = SecurityUtil.getLoggedInUserRole(authentication);
		
		//Call service method with authorization logic
		UserDTO userDTO = userService.getUser(userId, loggedInUserId, role);
		return ResponseEntity.ok(userDTO);
	}

	@GetMapping
	public ResponseEntity <ArrayList<UserDTO>> getAllUsers(Authentication authentication) {
		role = SecurityUtil.getLoggedInUserRole(authentication);	//Retrieve logged-in user's role
		ArrayList <UserDTO> users = userService.getAllUsers(role);	//Admin-only access enforced inside UserService
		return ResponseEntity.ok(users);
	}

	@PatchMapping("/userId")
	public ResponseEntity <UserDTO> updateUser (@RequestBody UserDTO userDTO, @PathVariable Long userId, Authentication authentication) {
		//Get logged-in user's ID and role
		loggedInUserId = SecurityUtil.getLoggedInUserId(authentication);
		role = SecurityUtil.getLoggedInUserRole(authentication);
		
		//Perform update logic
		UserDTO updatedUser = userService.updateUser(userDTO, userId, loggedInUserId, role);

		if (updatedUser != null) {
			return ResponseEntity.ok(updatedUser);
		} 
		
		//If userId not found
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/userId")
	public ResponseEntity <String> deleteUser (@PathVariable Long userId, Authentication authentication) {
		//Get logged-in user's information
		loggedInUserId = SecurityUtil.getLoggedInUserId(authentication);
		role = SecurityUtil.getLoggedInUserRole(authentication);
		
		//Perform delete operation with security checks
		userService.deleteUser(userId, loggedInUserId, role);
		return ResponseEntity.ok("User account deleted successfully");
	}
}