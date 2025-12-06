package com.example.user.DTO;
import java.util.Calendar;
import java.util.Date;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * User Data Transfer Object (DTO) for transferring user data.
 * Includes basic manual validation for required fields and password complexity.
 * New user can only fill in their first / last / user names, email, mobile number, address, postal code, password and their role (only can do it once).
 * Only admin user can update all fields in the DB through a Java Swing GUI.
 */
@CrossOrigin(origins = "http://localhost:3000") //Allows cross-origin requests from the React frontend
public class UserDTO {
	//User fields in the DB
	private Long userId;
	private String firstName, lastName, username, email, mobileNo, address, postalCode, password, userStatus, role;

	//Special characters allowed for the password string
	private String specialCharacter = "!@#$%^&*()-_=+[]{}|;:'\",.<>/?`~";

	//Date attributes
	private Date createdDate, suspensionDate, unsuspensionDate, terminationDate, unterminationDate, inactiveDate;

	//No argument constructor
	public UserDTO() {
		this.userStatus = "Active";
		this.createdDate = getTodayDate();	//Automatically set created date as today
	}

	//Constructor with all fields
	public UserDTO(Long userId, String firstName, String lastName, String username, String email, String mobileNo, String address, String postalCode, String password, String role, String userStatus, 
	Date createdDate, Date suspensionDate, Date unsuspensionDate, Date terminationDate, Date unterminationDate, Date inactiveDate) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.mobileNo = mobileNo;
		this.address = address;
		this.postalCode = postalCode;
		this.password = password;
		this.role = role;
		this.userStatus = "Active";
		this.createdDate = getTodayDate();
		this.suspensionDate = suspensionDate;
		this.unsuspensionDate = unsuspensionDate;
		this.terminationDate = terminationDate;
		this.unterminationDate = unterminationDate;
		this.inactiveDate = inactiveDate;
	}

	//Getters
	public Long getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public String getAddress () {
		return address;
	}

	public String getPostalCode () {
		return postalCode;
	}

	public String getPassword () {
		return password;
	}
	
	public String getRole () {
		return role;
	}

	public String getUserStatus () {
		return userStatus;
	}

	public Date getCreatedDate () {
		return createdDate;
	}

	public Date getSuspensionDate () {
		return suspensionDate;
	}

	public Date getUnsuspensionDate () {
		return unsuspensionDate;
	}

	public Date getTerminationDate () {
		return terminationDate;
	}

	public Date getUnterminationDate () {
		return unterminationDate;
	}

	public Date getInactiveDate () {
		return inactiveDate;
	}

	//Setters
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setAddress (String address) {
		this.address = address;
	}

	public void setPostalCode (String postalCode) {
		this.postalCode = postalCode;
	}

	public void setPassword (String password) {
		this.password = password;
	}
	
	public void setRole (String role) {
		this.role = role;
	}

	public void setUserStatus (String userStatus) {
		this.userStatus = userStatus;
	}

	public void setCreatedDate (Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setSuspensionDate (Date suspensionDate) {
		this.suspensionDate = suspensionDate;
	}

	public void setUnsuspensionDate (Date unsuspensionDate) {
		this.unsuspensionDate = unsuspensionDate;
	}

	public void setTerminationDate (Date terminationDate) {
		this.terminationDate = terminationDate;
	}

	public void setUnterminationDate (Date unterminationDate) {
		this.unterminationDate = unterminationDate;
	}

	public void setInactiveDate (Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

	public void inactiveDate (Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

	/**
	 * Validates all required user fields and password complexity.
	 * @return Error message if invalid, otherwise null.
	 */
	public String validateUserInput() {
		//All fields cannot be null
		if (firstName == null) return "First name is required";
		if (lastName == null) return "Last name is required";
		if (mobileNo == null) return "Mobile number is requried";
		if (address == null) return "Address is required";
		if (postalCode == null) return "Postal code is required";
		
		//Validate email address
		String emailError = validateEmailAddress(email);
		if (emailError != null) return emailError;

		//Validate user status
		String userStatusError = validateUserStatus(userStatus);	
		if (userStatusError != null) return userStatusError;

		//Validate password
		String passwordError = validatePassword(password);
		if (passwordError != null) return passwordError;

		//Validate suspend date
		Date suspenionDaDateError = validateSuspensionDateIsTodayOrAfterToday(suspensionDate);
		if (suspenionDaDateError == null) {
			return "Suspend date must be today or a future date";
		}

		//Validate unsuspend date
		Date unsuspensionDateError = validateUnsuspensionDateIsTodayOrAfterToday(unsuspensionDate);
		if (unsuspensionDateError == null) {
			return "Unsuspend date must be today or after today's date";
		}

		return null;	//User object is valid
	}
	
	/** Validate email address */
	private String validateEmailAddress (String email) {
		if (email == null) return "Email address is required";
		if (!email.contains("@")) return "Please provide a valid email address";
		return null;
	}

	/** Determine user status based on  date fields 
	 *  Highest priority: termination / untermination 
	 *  2nd priority: suspension / unsuspension 
	 *  last priority: active / inactive */
	private String validateUserStatus (String userStatus) {
		if (terminationDate != null) return "Terminated";
		if (terminationDate != null && unterminationDate != null) return "Pending termination";
		if (inactiveDate != null) return "Inactive";
		if (suspensionDate != null) return "Suspended";
		if (suspensionDate != null && unsuspensionDate != null) return "Pending suspension";

		return "Active";	//Return user status as active by default
	}

	/** Validates password complexity */
	public String validatePassword(String password) {
		if (password == null) return "Password is required";
		if (password.length() < 8) return "Password must be at least 8 characters long";
		if (!passwordContainsAlphabet(password)) return "Password must contain at least 1 alphabet";
		if (!passwordContainsNumber(password)) return "Password must contain at least 1 number";
		if (!passwordCotainsSpecialCharacter(password)) return "Password must contain at least 1 special character";

		return null;	//Password is valid
	}

	/** Checks if password contains at least 1 letter */
	private boolean passwordContainsAlphabet(String a) {
		//Loop through each character of the string
		for (int i = 0; i < a.length(); i++) {
			char c = a.charAt(i);	//Get character at index i

			//Check if character is in lower or upper case
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) return true;
		}

		return false;	//Return false if no upper or lower case letters found
	}

	/** Checks if password contains at least 1 number */
	private boolean passwordContainsNumber(String n) {
		//Loop through each character of the string
		for (int i = 0; i < n.length(); i++) {
			char c = n.charAt(i);	//Get character at index i

			//Check if character is a number
			if (c >= '0' && c <= '9') return true;
		}

		return false;	//Return false if no number is found
	}

	/** Checks if password contains at least 1 special character */
	private boolean passwordCotainsSpecialCharacter (String sc) {
		//Loop through each character of the string
		for (int i = 0; i < sc.length(); i++) {
			char c = sc.charAt(i);	//Get special character at index i

			//Loop through allowed special character of the string
			for (int j = 0; j < specialCharacter.length(); j++) {
				//Check if special character match the password character
				if (c == specialCharacter.charAt(j)) return true;
			}
		}

		return false;	//Return false if no special character found
	}

	/** Return today's date **/
	public Date getTodayDate() {
		//Get today's date
		Calendar todayDate = Calendar.getInstance();
		todayDate.set(Calendar.HOUR_OF_DAY, 0);
		todayDate.set(Calendar.MINUTE, 0);
		todayDate.set(Calendar.SECOND, 0);
		todayDate.set(Calendar.MILLISECOND, 0);
		return todayDate.getTime();	//Return today's date
	}

	/** Validate if suspend date is today or after today **/
	public Date validateSuspensionDateIsTodayOrAfterToday (Date suspensionDate) {
		//Get suspend date without time
		Calendar suspensionDateCalendar = Calendar.getInstance();
		suspensionDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
		suspensionDateCalendar.set(Calendar.MINUTE, 0);
		suspensionDateCalendar.set(Calendar.SECOND, 0);
		suspensionDateCalendar.set(Calendar.MILLISECOND, 0);
		Date suspended = suspensionDateCalendar.getTime();

		//Check if suspend date is today or after today
		if (suspended.equals(getTodayDate()) || suspended.after(getTodayDate())) {
			return suspensionDate;
		} else {
			return null;	//Return null if suspend date is not today or after today
		}
	}

	/** Validate if unsuspend date is today or after today **/
	public Date validateUnsuspensionDateIsTodayOrAfterToday (Date unsuspensionDate) {
		//Get unsuspend date without time
		Calendar unsuspensionDateCalendar = Calendar.getInstance();
		unsuspensionDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
		unsuspensionDateCalendar.set(Calendar.MINUTE, 0);
		unsuspensionDateCalendar.set(Calendar.SECOND, 0);
		unsuspensionDateCalendar.set(Calendar.MILLISECOND, 0);
		Date unsuspended = unsuspensionDateCalendar.getTime();

		//Check if unsuspend date iis today or after today
		if (unsuspended.equals(getTodayDate()) || unsuspended.after(getTodayDate())) {
			return unsuspensionDate;
		} else {
			return null; //Return null if unsuspend date is not today or after today
		}
	}

	/** Validate if termination date is today or after today **/
	public Date validateTerminationDateIsTodayOrAfterToday(Date terminationDate) {
		Calendar terminationDateCalendar = Calendar.getInstance();
		terminationDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
		terminationDateCalendar.set(Calendar.MINUTE, 0);
		terminationDateCalendar.set(Calendar.SECOND, 0);
		terminationDateCalendar.set(Calendar.MILLISECOND, 0);
		Date terminated = terminationDateCalendar.getTime();

		//Check if terminate date is today or after today
		if (terminated.equals(getTodayDate()) || terminated.after(getTodayDate())) {
			return terminationDate;
		} else {
			return null;
		}
	}

	/** Validate if untermination date is today or after today **/
	public Date validateUnterminationDateIsTodayOrAfterToday(Date unterminationDate) {
		Calendar unterminationDateCalendar = Calendar.getInstance();
		unterminationDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
		unterminationDateCalendar.set(Calendar.MINUTE, 0);
		unterminationDateCalendar.set(Calendar.SECOND, 0);
		unterminationDateCalendar.set(Calendar.MILLISECOND, 0);
		Date unterminated = unterminationDateCalendar.getTime();

		//Check if untermination date is today or after today
		if (unterminated.equals(getTodayDate()) || unterminated.after(getTodayDate())) {
			return unterminationDate;
		}
		else {
			return null;
		}
	}
}