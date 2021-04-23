/**
 * This entity class holds the data of user. There are total eight fields in the 
 * class with getters and setters.
 */
package com.ripon.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

/* holds info about the user */
@Component
public class User {

	private String id;
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	private String email;
	@NotBlank(message = "First name can't be empty")
	@Size(min = 3, max = 12, message = "First name must be between 3 - 12 characters !")
	private String firstname;
	@NotBlank(message = "Last name can't be empty")
	@Size(min = 3, max = 12, message = "Last name must be between 3 - 12 characters !!")
	private String lastname;
	@Size(min = 10, max = 12, message = "Phone no must be between 10 - 12 characters !!")
	private String phone;
	@NotBlank(message = "Chose your role !!")
	private String role;
	@NotBlank(message = "Password can't be empty")
	@Size(min = 4, max = 12, message = "Password name must be between 3 - 12 characters !")
	private String password;
	@NotBlank(message = "Confirm Password can't be empty")
	private String confirmPassword;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
