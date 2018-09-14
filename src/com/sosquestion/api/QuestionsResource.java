package com.sosquestion.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sosquestion.impl.QuestionManager;
import com.sosquestion.impl.AuthManager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Path("/questions")
public class QuestionsResource {

	// Create new question
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response createQuestion(@FormParam("questionP") String questionP, @FormParam("subjectID") String subjectID,
			@FormParam("description") String description, @FormParam("apiKey") String apiKey) {

		if (questionP != null && subjectID != null && description != null && apiKey != null) {
			AuthManager authManager = AuthManager.getInstance();
			Claims claims = Jwts.parser().setSigningKey(authManager.getKey()).parseClaimsJws(apiKey).getBody();
			String userAuth = claims.get("userID").toString();

			QuestionManager am = QuestionManager.getInstance();

			return am.createQuestion(userAuth, questionP, subjectID, description, userAuth);

		} else {
			// Invalid data
			return Response.serverError().status(401).type("text/plain").entity("Question Already exists!").build();
		}
	}

	

	// GET a specific question
	@Path("/{questionID}")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Object getQuestion(@PathParam("questionID") String questionID,
			@QueryParam("apiKey") String apiKey) {

		if (questionID != null && apiKey != null) {
			AuthManager authManager = AuthManager.getInstance();
			Claims claims = Jwts.parser().setSigningKey(authManager.getKey()).parseClaimsJws(apiKey).getBody();
			String userAuth = claims.get("userID").toString();

			QuestionManager am = QuestionManager.getInstance();
			return am.getQuestion(userAuth, questionID, userAuth);
		} else {
			// Invalid data
			return Response.serverError().status(401).type("text/plain").entity("Invalid Data!").build();
		}

	}

	// Change a specific question
	@Path("/{questionID}")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Object changeQuestion(@PathParam("questionID") String questionID, @FormParam("questionP") String questionP,
			@FormParam("subject") String subject, @FormParam("description") String description,
			@FormParam("apiKey") String apiKey) {

		if (questionID != null && questionP != null && subject != null && description != null && apiKey != null) {
			AuthManager authManager = AuthManager.getInstance();
			Claims claims = Jwts.parser().setSigningKey(authManager.getKey()).parseClaimsJws(apiKey).getBody();
			String userAuth = claims.get("userID").toString();

			QuestionManager am = QuestionManager.getInstance();
			am.changeQuestion(questionID, questionP, subject, description, userAuth);

			return Response.ok().entity("question changed!").build();
		} else {
			// Invalid data
			return Response.serverError().status(401).type("text/plain").entity("Invalid Data!").build();
		}

	}

	// DELETE a specific question
	@Path("/{questionID}")
	@DELETE

	public Object removeQuestion(@PathParam("questionID") String questionID, @QueryParam("apiKey") String apiKey) {

		if (questionID != null && apiKey != null) {
			AuthManager authManager = AuthManager.getInstance();
			Claims claims = Jwts.parser().setSigningKey(authManager.getKey()).parseClaimsJws(apiKey).getBody();
			String userAuth = claims.get("userID").toString();

			QuestionManager am = QuestionManager.getInstance();
			am.removeQuestion(questionID, userAuth);

			return Response.ok().entity("User").build();
		} else {
			// Invalid data
			return Response.serverError().status(401).type("text/plain").entity("Invalid Data!").build();
		}

	}

	// Get all questions
		@Path("/subjects/{subjectID}")
		@GET
		@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
		public Object getQuestions( @PathParam("subjectID") String subjectID, @QueryParam("apiKey") String apiKey) {

			if (subjectID != null && apiKey != null) {
				AuthManager authManager = AuthManager.getInstance();
				Claims claims = Jwts.parser().setSigningKey(authManager.getKey()).parseClaimsJws(apiKey).getBody();
				String userAuth = claims.get("userID").toString();

				QuestionManager am = QuestionManager.getInstance();
				return am.getQuestions(userAuth, subjectID);
			} else {
				// Invalid data
				return Response.serverError().status(401).type("text/plain").entity("Invalid Data!").build();
			}

		}
		
		
		// Get all questions from user
		@Path("/user/{userID}")
		@GET
		@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
		public Object getQuestionsUser(@PathParam("userID") String userID, @QueryParam("apiKey") String apiKey) {

			if (userID != null && apiKey != null) {
				AuthManager authManager = AuthManager.getInstance();
				Claims claims = Jwts.parser().setSigningKey(authManager.getKey()).parseClaimsJws(apiKey).getBody();
				String userAuth = claims.get("userID").toString();

				QuestionManager am = QuestionManager.getInstance();
				return am.getQuestionsUser(userID, userAuth);
			} else {
				// Invalid data
				return Response.serverError().status(401).type("text/plain").entity("Invalid Data!").build();
			}

		}
}
