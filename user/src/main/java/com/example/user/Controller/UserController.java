package com.example.user.Controller;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.user.Service.UserService;

@RestController             //Marks the class as a REST controller
@RequestMapping("/users")   //Base URL mapping for this controller's endpoints
public class UserController {
	@Autowired
	private UserService userService;	//Inject user service for user operations
	
	@PostMapping("/login") 
	public ResponseEntity <UserDTO> userLogin(@RequestParam String username, @RequestParam String password) {
		UserDTO userDTO = userService.userLogin(username, password);
		
		if (userDTO != null) {
			return ResponseEntity.ok(userDTO);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity <UserDTO> createNewUser(@RequestBody UserDTO userDTO) {
		UserDTO createdUser = userService.createNewUser(userDTO);
		return ResponseEntity.ok(createdUser);
	}
	
	@GetMapping("/userId")
	public ResponseEntity <UserDTO> getUser(@PathVariable Long userId) {
		UserDTO userID = userService.getUser(userId);
		return ResponseEntity.ok(userID);
	}
	
	@GetMapping
	public ResponseEntity <ArrayList<UserDTO>> getAllUsers() {
		ArrayList <UserDTO> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	@PatchMapping("/userId")
	public ResponseEntity <UserDTO> updateUser (@RequestBody UserDTO userDTO, @PathVariable Long userId) {
		UserDTO updatedUser = userService.updateUser(userDTO, userId);
		
		if (updatedUser != null) {
			return ResponseEntity.ok(updatedUser);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/userId")
	public ResponseEntity <String> deleteUser (@PathVariable Long userId) {
		userService.deleteUser(userId);
		return ResponseEntity.ok("User deleted successfully");
	}
}