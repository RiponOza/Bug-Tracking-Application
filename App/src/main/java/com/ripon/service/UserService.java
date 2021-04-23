/** This class is responsible for all the operations related to user. Here methods 
 * for user registration and login are provided. This class uses userDao object to 
 * perform database operations. 
 * 
 * @author Ripon Oza
 * @version 1.0
 * @since 2021
 */
package com.ripon.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ripon.dao.UserDao;
import com.ripon.entity.Login;
import com.ripon.entity.User;

@Service
public class UserService {

	@Autowired
	UserDao dao;

	/**
	 * This method takes the user provided password and create hash code of length
	 * 16 and returns it.
	 * 
	 * @param passwordToHash
	 * @return encrypted password
	 */
	public String encryptPassword(String passwordToHash) {
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(passwordToHash.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	/**
	 * This method generates unique id using date, time and email for each user.
	 * 
	 * @param email This is email of the user
	 * @return the unique id generated for each user
	 */
	public String generateId(String email) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
		String id = now.format(format) + email;
		return id;
	}

	/**
	 * This method performs user registration.
	 * 
	 * @param user This is user object which holds information of a user.
	 * @return true for successful registration and false for failure.
	 */
	public boolean registerUser(User user) {
		// encrypts the user provided password
		String encPassword = encryptPassword(user.getPassword());
		// save encrypted password
		user.setPassword(encPassword);
		return dao.saveUser(user);
	}

	/**
	 * This method checks whether a user is registered one or not.
	 * 
	 * @param email    This is user provided email.
	 * @param password This is user provided password.
	 * @return true if user is a registered one, else returns false.
	 */
	public boolean loginUser(Login loginDetail) {
		return dao.isUserAvailable(loginDetail.getEmail(), encryptPassword(loginDetail.getPassword()));
	}

	/**
	 * This method takes user email and password as argument and finds the unique id
	 * of that user.
	 * 
	 * @param email    This is user's email address.
	 * @param password This is user's password in non encrypted form.
	 * @return unique id of the user whose email and password is given.
	 */
	public String getUserid(String email, String password) {
		String userid = dao.getUserId(email, encryptPassword(password));
		return userid;
	}

	/**
	 * This method takes user email and password as argument and finds the user name
	 * of that user.
	 * 
	 * @param email    This is user's email address.
	 * @param password This is user's password in non encrypted form.
	 * @return user name of the user whose email and password is given.
	 */
	public String getUserName(String email, String password) {
		String userName = dao.getUserName(email, encryptPassword(password));
		return userName;
	}

}
