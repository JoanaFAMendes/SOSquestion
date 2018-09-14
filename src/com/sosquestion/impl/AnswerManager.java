package com.sosquestion.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.sosquestion.data.AnswerData;
import com.sosquestion.data.QuestionData;
import com.sosquestion.model.Answer;
import com.sosquestion.model.Question;

public class AnswerManager implements IAnswer {

	static AnswerManager am = null;

	public static AnswerManager getInstance() {
		if (am == null) {
			am = new AnswerManager();
		}
		return am;
	}

	// Create answer
	@Override
	public Response createAnswer(String questionID, String author, String date, String description, String userAuth) {

		QuestionData questionData = QuestionData.getInstance();
		List<Question> questions = questionData.getAllData();

		for (Question question : questions) {
			if (question.getAuthor().equals(userAuth)) {
				AnswerData answerData = AnswerData.getInstance();
				List<Answer> answers = answerData.getData(questionID);

				boolean exists = false;
				for (int i = 0; i < answers.size(); i++) {
					if (answers.get(i).getAuthor().equals(author)) {
						exists = true;
						break;
					}
				}

				if (exists) {
					return Response.serverError().status(401).type("text/plain").entity("Invalid").build();
				} else {
					String answerID = "";
					if (answers.size() > 0) {
						int newID = Integer.parseInt(
								answers.get(answers.size() - 1).getAnswerID().replace("ach", "")) + 1;
						answerID = "ach" + Integer.toString(newID);
					}
					else {
						int newID = 1;
						answerID = "ach" + Integer.toString(newID);
					}
					
					Answer answer = new Answer(answerID, questionID, author, date, description);
					answerData.insertData(answer);
					// The answer is created with success
					return Response.ok().entity("answer " + author + " created!").build();
				}

			} else {
				// The Author is not authorised to create answers from another Author
				Response.serverError().status(401).type("text/plain").entity("Invalid").build();
			}
		}
		return null;

	}

	// Get All answers
	@Override
	public Object getAnswers(String questionID, String userAuth) {

		boolean exists = false;

		QuestionData questionData = QuestionData.getInstance();
		List<Question> questions = questionData.getAllData();

		AnswerData answerData = AnswerData.getInstance();

		// Permissions for request

		for (Question question : questions) {
			if (question.getQuestionID().equals(questionID)) {

				if (question.getAuthor().equals(userAuth)) {
					return answerData.getData(questionID);
				} else {
					// The Author is not authorised to see answers from another Author
					return Response.serverError().status(401).type("text/plain").entity("Invalid").build();
				}

			}
		}
		return null;
	}

	// Get specific answer
	@Override
	public Object getAnswer(String questionID, String answerID, String userAuth) {

		boolean exists = false;

		QuestionData questionData = QuestionData.getInstance();
		List<Question> questions = questionData.getAllData();

		AnswerData answerData = AnswerData.getInstance();

		// Permissions for request

		for (Question question : questions) {
			if (question.getQuestionID().equals(questionID)) {

				if (question.getAuthor().equals(userAuth)) {

					return answerData.getSpecificData(questionID, answerID);
				} else {
					// The Author is not authorised to see answers from another Author
					Response.serverError().status(401).type("text/plain").entity("Invalid").build();
				}

			}
		}
		return null;

	}

	// Change answer
	@Override
	public void changeAnswer(String questionID, String answerID, String author, String date, String description, String userAuth) {

		boolean questionExists = false;
		boolean permissions = true;
		QuestionData questionData = QuestionData.getInstance();
		List<Question> questions = questionData.getAllData();

		AnswerData answerData = AnswerData.getInstance();

		for (Question question : questions) {
			// Check if question exists
			if (question.getQuestionID().equals(questionID) && question.getAuthor().equals(userAuth)) {
				questionExists = true;
				permissions = true;
				break;
			}
		}

		if (!questionExists) {
			// question doesn't exist
			Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		} else if (questionExists && permissions) {
			Object answer = answerData.getSpecificData(questionID, answerID);

			if (answer == null) {
				// answer doesn't exist
				Response.serverError().status(401).type("text/plain").entity("Invalid").build();
			} else {
				answerData.changeData(questionID, answerID, author, date, description);
			}
		} else if (!permissions) {
			// The Author is not authorised to change answers from another Author
			Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		}

	}

	// Remove answer
	@Override
	public void removeAnswer(String questionID, String answerID, String userAuth) {
		boolean questionExists = false;
		boolean permissions = true;
		QuestionData questionData = QuestionData.getInstance();
		List<Question> questions = questionData.getAllData();

		AnswerData answerData = AnswerData.getInstance();

		for (Question question : questions) {
			// Check if question exists
			if (question.getQuestionID().equals(questionID) && question.getAuthor().equals(userAuth)) {
				questionExists = true;
				permissions = true;
				break;
			}
		}

		if (!questionExists) {
			// question doesn't exist
			Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		} else if (questionExists && permissions) {
			Object answer = answerData.getSpecificData(questionID, answerID);

			if (answer == null) {
				// answer doesn't exist
				Response.serverError().status(401).type("text/plain").entity("Invalid").build();
			} else {
				answerData.removeData(questionID, answerID);
			}
		} else if (!permissions) {
			// The Author is not authorised to change answers from another Author
			Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		}
	}
}
