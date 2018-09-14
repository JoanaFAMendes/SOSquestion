package com.sosquestion.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sosquestion.impl.AnswerManager;
import com.sosquestion.impl.AuthManager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Path("/questions/{questionID}/answers")
public class AnswerResource {

	// Create new answer
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response createanswer(@PathParam("questionID") String questionID, @FormParam("author") String author,
			@FormParam("date") String date,
			@FormParam("description") String description, @FormParam("apiKey") String apiKey,
			@Context UriInfo uriInfo) {

		if (questionID != null && author != null && date != null
				&& description != null && apiKey != null) {
			AuthManager authManager = AuthManager.getInstance();
			Claims claims = Jwts.parser().setSigningKey(authManager.getKey()).parseClaimsJws(apiKey).getBody();
			String userAuth = claims.get("userID").toString();

			AnswerManager am = AnswerManager.getInstance();

			return am.createAnswer(questionID, author, date, description, userAuth);

		} else {
			// Invalid data
			return Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		}

	}

	// Get all answers
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Object getAnswers(@PathParam("questionID") String questionID, @QueryParam("apiKey") String apiKey) {

		if (questionID != null && apiKey != null) {
			AuthManager authManager = AuthManager.getInstance();
			Claims claims = Jwts.parser().setSigningKey(authManager.getKey()).parseClaimsJws(apiKey).getBody();
			String userAuth = claims.get("userID").toString();

			AnswerManager am = AnswerManager.getInstance();
			return am.getAnswers(questionID, userAuth);
		} else {
			// Invalid data
			return Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		}

	}

	// GET a specific answer
	@Path("/{answerID}")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Object getanswer(@PathParam("questionID") String questionID, @PathParam("answerID") String answerID,
			@QueryParam("apiKey") String apiKey) {

		if (questionID != null && answerID != null && apiKey != null) {
			AuthManager authManager = AuthManager.getInstance();
			Claims claims = Jwts.parser().setSigningKey(authManager.getKey()).parseClaimsJws(apiKey).getBody();
			String userAuth = claims.get("userID").toString();

			AnswerManager am = AnswerManager.getInstance();
			return am.getAnswer(questionID, answerID, userAuth);
		} else {
			// Invalid data
			return Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		}

	}

	// Change a specific answer
	@Path("/{answerID}")
	@PUT
	@Consumes("application/x-www-form-urlencoded")

	public Object changeAnswer(@PathParam("questionID") String questionID, @PathParam("answerID") String answerID,
			@FormParam("author") String author, @FormParam("date") String date, @FormParam("description") String description,
			@FormParam("apiKey") String apiKey) {

		if (questionID != null && answerID != null && author != null && date != null && description != null && apiKey != null) {
			AuthManager authManager = AuthManager.getInstance();
			Claims claims = Jwts.parser().setSigningKey(authManager.getKey()).parseClaimsJws(apiKey).getBody();
			String userAuth = claims.get("userID").toString();

			AnswerManager am = AnswerManager.getInstance();
			am.changeAnswer(questionID, answerID, author, date, description, userAuth);

			return Response.ok().entity("answer changed!").build();
		} else {
			// Invalid data
			return Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		}

	}

	// DELETE a specific answer
	@Path("/{answerID}")
	@DELETE
	public Object removeAnswer(@PathParam("questionID") String questionID, @PathParam("answerID") String answerID,
			@QueryParam("apiKey") String apiKey) {

		if (questionID != null && answerID != null && apiKey != null) {
			AuthManager authManager = AuthManager.getInstance();
			Claims claims = Jwts.parser().setSigningKey(authManager.getKey()).parseClaimsJws(apiKey).getBody();
			String userAuth = claims.get("userID").toString();

			AnswerManager am = AnswerManager.getInstance();
			am.removeAnswer(questionID, answerID, userAuth);

			return Response.ok().entity("answer deleted!").build();
		} else {
			// Invalid data
			return Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		}

	}

}
