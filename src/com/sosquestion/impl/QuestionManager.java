package com.sosquestion.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.sosquestion.data.QuestionData;
import com.sosquestion.model.Question;

public class QuestionManager implements IQuestion {

	static QuestionManager am = null;

	public static QuestionManager getInstance() {
		if (am == null) {
			am = new QuestionManager();
		}
		return am;
	}

	// Create new question

	@Override
	public Response createQuestion(String author, String questionP, String subjectID, String description, String userAuth) {

		QuestionData questionData = QuestionData.getInstance();
		List<Question> questions = questionData.getAllData();

		boolean exists = false;
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i).getQuestionP().equals(questionP)) {
				exists = true;
				break;
			}
		}

		if (exists) {
			return Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		} else {
			int newID = Integer.parseInt(questions.get(questions.size() - 1).getQuestionID().replace("question", "")) + 1;
			String QuestionID = "question" + Integer.toString(newID);
			Question question = new Question(QuestionID, author, questionP, subjectID, description);
			questionData.insertData(question);		
			// The user is created with success
			return Response.ok().entity(questionP + " created!").build();
		}

	}

	// Get all questions

	@Override
	public Object getQuestionsUser(String userRequested, String userAuth) {

		if (userRequested.equals(userAuth)) {
			QuestionData questionData = QuestionData.getInstance();
			return questionData.getData(userRequested);
		} else {
			// The user is not authorized to see questions from another user
			return Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		}

	}

	// Get specific question

	@Override
	public Object getQuestion(String userRequested, String questionID, String userAuth) {
		if (userRequested.equals(userAuth)) {
			QuestionData questionData = QuestionData.getInstance();
			return questionData.getSpecificData(userRequested, questionID);
		} else {
			// The user is not authorized to see questions from another user
			return Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		}

	}

	// Change question

	@Override
	public void changeQuestion(String questionID, String questionP, String subjectID, String description, String userAuth) {

		boolean exists = false;
		QuestionData questionData = QuestionData.getInstance();
		List<Question> questions = questionData.getAllData();

		for (Question question : questions) {
			// Check if question exists
			if (question.getQuestionID().equals(questionID)) {
				exists = true;
				// Check if the user have permission to change the question
				if (question.getAuthor().equals(userAuth)) {
					questionData.changeData(questionID, questionP, subjectID, description);
				} else {
					// The user is not authorized to change the questions from another user
					Response.serverError().status(401).type("text/plain").entity("Invalid").build();
				}
			}
		}

		if (exists == false) {
			// There are no question with that ID
			Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		}
	}

	// Remove question

	@Override
	public void removeQuestion(String questionID, String userAuth) {

		boolean exists = false;
		QuestionData questionData = QuestionData.getInstance();
		List<Question> questions = questionData.getAllData();

		for (Question question : questions) {
			// Check if question exists
			if (question.getQuestionID().equals(questionID)) {
				exists = true;
				// Check if the user have permission to change the question
				if (question.getAuthor().equals(userAuth)) {
					questionData.removeData(questionID);
				} else {
					// The user is not authorized to change the questions from another user
					Response.serverError().status(401).type("text/plain").entity("Invalid").build();
				}
			}
		}

		if (exists == false) {
			// There are no question with that ID
			Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		}

	}

	@Override
	public Object getQuestions(String userAuth, String subjectID) {
		// TODO Auto-generated method stub
		if (userAuth != null && subjectID != null) {
			QuestionData questionData = QuestionData.getInstance();
			return questionData.getRawData(subjectID);
		} else {
			// Invalid
			Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		}
		return null;
	}
}
