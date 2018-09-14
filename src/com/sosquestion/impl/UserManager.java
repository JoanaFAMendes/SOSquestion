package com.sosquestion.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Response;

import com.sosquestion.data.UserData;
import com.sosquestion.model.Question;
import com.sosquestion.model.User;

public class UserManager implements IUser {

	static UserManager um = null;

	public static UserManager getInstance() {
		if (um == null) {
			um = new UserManager();
		}
		return um;
	}

	// Create new user

	@Override
	public Response createUser(String userID, String password, String email) {
	
		UserData userData = UserData.getInstance();
		List<User> users = userData.getData();

		if (users.size() > 0 ) {
			boolean exists = false;
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getUserID().equals(userID)) {
					exists = true;
					break;
				}
			}

			if (exists) {
				return Response.serverError().status(401).type("text/plain").entity("User Already Exists!").build();
			} else {
				User user = new User(userID, password, email);
				userData.insertData(user);
				// The user is created with success
				return Response.ok().entity(userID + " created!").build();
			}
		}
		else {
			User user = new User(userID, password, email);
			userData.insertData(user);
			// The user is created with success
			return Response.ok().entity(userID + " created!").build();
		}

	}

	// Get all users

	@Override
	public List<User> getUsers(String userAuth) {
		if (userAuth != null) {
			UserData userData = UserData.getInstance();
			return userData.getData();
		} else {
			// Invalid login
			Response.serverError().status(401).type("text/plain").entity("Invalid Login!").build();
		}
		return null;
	}

	// Get specific user

	@Override
	public List<User> getUser(String userID, String userAuth) {

		if (userAuth != null) {
			UserData userData = UserData.getInstance();
			return userData.getData(userID);
		} else {
			// Invalid login
			Response.serverError().status(401).type("text/plain").entity("Invalid Login!").build();
		}
		return null;
	}

	// Change user

	@Override
	public Object changeUser(String userID, String newPassword, String newEmail, String userAuth) {

		if (userID.equals(userAuth)) {
			UserData userData = UserData.getInstance();
			userData.changeData(userID, newPassword, newEmail);
			// The user is changed with success
			return Response.ok().entity(userID + " changed!").build();
		} else {
			// The current user is not authorized to change another user
			return Response.serverError().status(401).type("text/plain").entity("Not Authorized!").build();
		}

	}

	// Remove user

	@Override
	public Object removeUser(String userID, String userAuth) {

		if (userID.equals(userAuth)) {
			UserData userData = UserData.getInstance();
			userData.removeData(userID);
			// The user is removed with success
			return Response.ok().entity(userID + " removed!").build();
		} else {
			// The current user is not authorized to remove another user
			return Response.serverError().status(401).type("text/plain").entity("Not Authorized to remove!").build();
		}

	}

}
