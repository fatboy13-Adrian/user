package com.example.user.DTO;
import java.util.Calendar;
import java.util.Date;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * User Data Transfer Object (DTO) for transferring user data.
 * Includes basic manual validation for required fields and password complexity.
 */
@CrossOrigin(origins = "http://localhost:3000") //Allows cross-origin requests from the React frontend
public class UserDTO {
	//User fields in the DB
	private Long userId;
	private String firstName, lastName, username, email, mobileNo, address, postalCode, password, userStatus;

	//Special characters allowed for the password string
	private String specialCharacter = "!@#$%^&*()-_=+[]{}|;:'\",.<>/?`~";

	//Date attributes
	private Date createdDate, suspendDate, unsuspendDate, terminateDate, unterminateDate, inactiveDate;

	//No argument constructor
	public UserDTO() {
		this.userStatus = "Active";
		this.createdDate = getTodayDate();	//Automatically set created date as today
	}

	//Constructor with all fields
	public UserDTO(Long userId, String firstName, String lastName, String username, String email, String mobileNo, String address, String postalCode, String password, String userStatus, 
			Date createdDate, Date suspendDate, Date unsuspendDate, Date terminateDate, Date unterminateDate, Date inactiveDate) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.mobileNo = mobileNo;
		this.address = address;
		this.postalCode = postalCode;
		this.password = password;
		this.userStatus = "Active";
		this.createdDate = getTodayDate();
		this.suspendDate = suspendDate;
		this.unsuspendDate = unsuspendDate;
		this.terminateDate = terminateDate;
		this.unterminateDate = unterminateDate;
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

	public String getUserStatus () {
		return userStatus;
	}

	public Date getCreatedDate () {
		return createdDate;
	}

	public Date getSuspendDate () {
		return suspendDate;
	}

	public Date getUnsuspendDate () {
		return unsuspendDate;
	}

	public Date getTerminateDate () {
		return terminateDate;
	}

	public Date getUnterminateDate () {
		return unterminateDate;
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
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		this.password = passwordEncoder.encode(password);	//Store hashed password into DB
	}

	public void setUserStatus (String userStatus) {
		this.userStatus = userStatus;
	}

	public void setCreatedDate (Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setSuspendDate (Date suspendDate) {
		this.suspendDate = suspendDate;
	}

	public void setUnsuspendDate (Date unsuspendDate) {
		this.unsuspendDate = unsuspendDate;
	}

	public void setTerminateDate (Date terminateDate) {
		this.terminateDate = terminateDate;
	}

	public void setUnterminateDate (Date unterminateDate) {
		this.unterminateDate = unterminateDate;
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
		if (email == null) return "Email address is required";
		if (mobileNo == null) return "Mobile number is requried";
		if (address == null) return "Address is required";
		if (postalCode == null) return "Postal code is required";

		//Validate user status
		String userStatusError = validateUserStatus(userStatus);	
		if (userStatusError != null) return userStatusError;

		//Validate password
		String passwordError = validatePassword(password);
		if (passwordError != null) return passwordError;

		//Validate suspend date
		Date suspendDateError = validateSuspendDateIsTodayOrAfterToday(suspendDate);
		if (suspendDateError == null) return "Suspend date must be today or a future date";

		//Validate unsuspend date
		Date unsuspendDateError = validateUnsuspendDateIsTodayOrAfterToday(unsuspendDate);
		if (unsuspendDateError == null) return "Unsuspend date must be today or after today's date";

		return null;	//User object is valid
	}

	/** Determine user status based on  date fields 
	 *  Highest priority: termination / untermination 
	 *  2nd priority: suspension / unsuspension 
	 *  last priority: active / inactive */
	private String validateUserStatus (String userStatus) {
		if (terminateDate != null) return "Terminated";
		if (terminateDate != null && unterminateDate != null) return "Pending termination";
		if (inactiveDate != null) return "Inactive";
		if (suspendDate != null) return "Suspended";
		if (suspendDate != null && unsuspendDate != null) return "Pending suspension";

		return "Active";	//Return user status as active by default
	}

	/** Validates password complexity */
	private String validatePassword(String password) {
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
	public Date validateSuspendDateIsTodayOrAfterToday (Date suspendDate) {
		//Get suspend date without time
		Calendar suspendDateCalendar = Calendar.getInstance();
		suspendDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
		suspendDateCalendar.set(Calendar.MINUTE, 0);
		suspendDateCalendar.set(Calendar.SECOND, 0);
		suspendDateCalendar.set(Calendar.MILLISECOND, 0);
		Date suspended = suspendDateCalendar.getTime();

		//Check if suspend date is today or after today
		if (suspended.equals(getTodayDate()) || suspended.after(getTodayDate())) return suspendDate;
		else return null;	//Return null if suspend date is not today or after today
	}

	/** Validate if unsuspend date is today or after today **/
	public Date validateUnsuspendDateIsTodayOrAfterToday (Date unsuspendDate) {
		//Get unsuspend date without time
		Calendar unsuspendDateCalendar = Calendar.getInstance();
		unsuspendDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
		unsuspendDateCalendar.set(Calendar.MINUTE, 0);
		unsuspendDateCalendar.set(Calendar.SECOND, 0);
		unsuspendDateCalendar.set(Calendar.MILLISECOND, 0);
		Date unsuspended = unsuspendDateCalendar.getTime();

		//Check if unsuspend date iis today or after today
		if (unsuspended.equals(getTodayDate()) || unsuspended.after(getTodayDate())) return unsuspendDate;
		else return null; //Return null if unsuspend date is not today or after today
	}

	/** Validate if terminate date is today or after today **/
	public Date validateTerminateDateIsTodayOrAfterToday(Date terminateDate) {
		//Get termiante date without time
		Calendar terminateDateCalendar = Calendar.getInstance();
		terminateDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
		terminateDateCalendar.set(Calendar.MINUTE, 0);
		terminateDateCalendar.set(Calendar.SECOND, 0);
		terminateDateCalendar.set(Calendar.MILLISECOND, 0);
		Date terminated = terminateDateCalendar.getTime();

		//Check if terminate date is today or after today
		if (terminated.equals(getTodayDate()) || terminated.after(getTodayDate())) return terminateDate;
		else return null;
	}

	/** Validate if unterminate date is today or after today **/
	public Date validateUntermianteDateIsTodayOrAfterToday(Date unterminateDate) {
		//Get termiante date without time
		Calendar unterminateDateCalendar = Calendar.getInstance();
		unterminateDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
		unterminateDateCalendar.set(Calendar.MINUTE, 0);
		unterminateDateCalendar.set(Calendar.SECOND, 0);
		unterminateDateCalendar.set(Calendar.MILLISECOND, 0);
		Date unterminated = unterminateDateCalendar.getTime();

		//Check if unterminate date is today or after today
		if (unterminated.equals(getTodayDate()) || unterminated.after(getTodayDate())) return unterminateDate;
		else return null;
	}
}