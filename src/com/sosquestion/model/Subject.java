package com.sosquestion.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "subject")
public class Subject {

	private String subjectID;
	private String description;

	public Subject() {
	}

	public Subject(String subjectID, String description) {
		super();
		this.subjectID = subjectID;
		this.description = description;
	}

	@XmlElement(name = "subjectID")
	public String getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
