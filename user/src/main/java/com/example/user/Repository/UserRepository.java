package com.example.user.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.user.Entity.User;
import java.util.*;

@Repository	//Marks this interface as a repository bean for spring component scanning
public interface UserRepository extends JpaRepository <User, Long> {
	//Find by username, email, role and mobile number
	Optional <User> findByUsername (String username);	
	Optional <User> findByEmail (String email);
	Optional <User> findByMobileNo (String mobileNo);
	Optional <User> findByRole (String role);
	
	//Check if username, email, role and mobile number exists in the DB
	boolean existsByUsername (String username);
	boolean existsByEmail (String email);
	boolean existsByMobileNo (String mobileNo);
	boolean existsByRole (String role);
}