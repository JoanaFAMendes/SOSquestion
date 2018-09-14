package com.sosquestion.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "question")
public class Question {

	private String questionID;
	private String author;
	private String questionP;
	private String subject;
	private String description;

	public Question() {
	}

	public Question(String questionID, String author, String questionP, String subject, String description) {
		super();
		this.questionID = questionID;
		this.author = author;
		this.questionP = questionP;
		this.subject = subject;
		this.description = description;
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

	@XmlElement(name = "questionP")
	public String getQuestionP() {
		return questionP;
	}

	public void setQuestionP(String questionP) {
		this.questionP = questionP;
	}

	@XmlElement(name = "subject")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
