package com.sosquestion.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.sosquestion.data.SubjectData;
import com.sosquestion.model.Subject;

public class SubjectManager implements ISubject {

	static SubjectManager am = null;

	public static SubjectManager getInstance() {
		if (am == null) {
			am = new SubjectManager();
		}
		return am;
	}

	// Create new question

	@Override
	public Response createSubject(String description) {

		SubjectData subjectData = SubjectData.getInstance();
		List<Subject> subjects = subjectData.getAllData();

		boolean exists = false;
		for (int i = 0; i < subjects.size(); i++) {
			if (subjects.get(i).getDescription().equals(description)) {
				exists = true;
				break;
			}
		}

		if (exists) {
			return Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		} else {
			int newID = Integer.parseInt(subjects.get(subjects.size() - 1).getSubjectID().replace("subject", "")) + 1;
			String subjectID = "subject" + Integer.toString(newID);
			Subject subject = new Subject(subjectID, description);
			subjectData.insertData(subject);		
			// The user is created with success
			return Response.ok().entity(description + " created!").build();
		}

	}

	@Override
	public Object getSubjects(String userAuth) {
		// TODO Auto-generated method stub
		if (userAuth != null) {
			SubjectData subjectData = SubjectData.getInstance();
			return subjectData.getRawData();
		} else {
			// Invalid
			Response.serverError().status(401).type("text/plain").entity("Invalid").build();
		}
		return null;
	}
}
