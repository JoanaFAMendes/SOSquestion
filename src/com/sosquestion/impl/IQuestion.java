package com.sosquestion.impl;

public interface IQuestion {
	
	public Object createQuestion(String author, String questionP, String subjectID, String description, String userAuth);
	public Object getQuestionsUser(String author, String userAuth);
	public Object getQuestions(String userAuth, String subjectID);
	public Object getQuestion(String author, String questionID, String userAuth);
	public void changeQuestion(String questionID, String questionP, String subjectID, String description, String userAuth);
	public void removeQuestion(String questionID, String userAuth);
}
