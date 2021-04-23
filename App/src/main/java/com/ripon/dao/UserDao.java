package com.ripon.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ripon.entity.User;

@Repository
public class UserDao {

	@Autowired
	NamedParameterJdbcTemplate template;

	// save user data to database
	public boolean saveUser(User user) {
		String sqlquery = "insert into user values(:id, :email, :firstname, :lastname, :phone, :role, :password)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", user.getId());
		params.addValue("email", user.getEmail());
		params.addValue("firstname", user.getFirstname());
		params.addValue("lastname", user.getLastname());
		params.addValue("lastname", user.getLastname());
		params.addValue("phone", user.getPhone());
		params.addValue("role", user.getRole());
		params.addValue("password", user.getPassword());

		int status = template.update(sqlquery, params);
		return (status > 0 ? true : false);

	}

	// check whether an user is present in the database or not
	public boolean isUserAvailable(String email, String password) {
		String sqlquery = "select count(*) from user where email=:email and password=:password";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email", email);
		params.addValue("password", password);
		int n = template.queryForObject(sqlquery, params, Integer.class);
		return (n == 1 ? true : false);
	}

	// find the user id of an user
	public String getUserId(String email, String password) {
		String sqlquery = "select id from user where email=:email and password=:password";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email", email);
		params.addValue("password", password);
		return (template.queryForObject(sqlquery, params, String.class));
	}

	// find the user name of an user
	public String getUserName(String email, String password) {
		String sqlquery = "select firstname from user where email=:email and password=:password";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email", email);
		params.addValue("password", password);
		return (template.queryForObject(sqlquery, params, String.class));
	}

}
