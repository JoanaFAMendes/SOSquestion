package com.sosquestion.impl;

public interface IAnswer {

	public Object createAnswer (String questionID, String author, String date, String description, String userAuth);

	public Object getAnswers(String questionID, String userAuth);

	public Object getAnswer(String userID, String answerID, String userAuth);

	public void changeAnswer(String questionID, String answerID, String author, String date, String description, String userAuth);

	public void removeAnswer(String questionID, String answerID, String userAuth);


}
