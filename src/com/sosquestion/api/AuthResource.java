package com.sosquestion.api;

import java.util.HashMap;
import java.util.Map;

import com.sosquestion.impl.AuthManager;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("/auth")
public class AuthResource {

	// POST auth
	@POST
	@Consumes("application/x-www-form-urlencoded")

	public Response auth(@FormParam("userID") String userID, @FormParam("password") String password) {

		if (userID != null && password != null) {
			AuthManager am = AuthManager.getInstance();
			boolean validate;

			// Validate user data in db store

			validate = am.getAuth(userID, password);

			if (validate) {

				// Create user data
				Map<String, Object> user = new HashMap<String, Object>();
				user.put("userID", userID);

				// Create JWT token
				String newToken = Jwts.builder().setClaims(user).setSubject(userID).setIssuer("Gamify")
						.signWith(SignatureAlgorithm.HS256, am.getKey()).compact();

				// Send token to the client
				Object obj = newToken;
				return Response.ok().entity(newToken).build();
			} else {
				// Invalid user
				return Response.serverError().status(401).type("text/plain").entity("Invalid Data!").build();
			}
		} else {
			// Invalid data
			return Response.serverError().status(401).type("text/plain").entity("Invalid Data!").build();
		}

	}
}
