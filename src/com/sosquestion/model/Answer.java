package com.sosquestion.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "answer")
public class Answer {

	private String answerID;
	private String questionID;
	private String author;
	private String date;
	private String description;
	
	public Answer() {
	}
	
	public Answer(String answerID, String questionID, String author, String date, String description) {
		super();
		this.answerID = answerID;
		this.questionID = questionID;
		this.author = author;
		this.date = date;
		this.description = description;
	}
	
	@XmlElement(name = "answerID")
	public String getAnswerID() {
		return answerID;
	}
	public void setAnswerID(String answerID) {
		this.answerID = answerID;
	}
	
	@XmlElement(name = "questionID")
	public String getQuestionID() {
		return questionID;
	}
	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}
	
	@XmlElement(name = "author")
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	@XmlElement(name = "date")
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}

