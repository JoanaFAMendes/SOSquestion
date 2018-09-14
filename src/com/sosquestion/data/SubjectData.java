package com.sosquestion.data;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.sosquestion.model.Subject;

import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class SubjectData {
	static SubjectData ad = null;
	static MongoCollection<Subject> colSubject;

	public static SubjectData getInstance() {
		if (ad == null) {
			ad = new SubjectData();

			CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
					fromProviders(PojoCodecProvider.builder().automatic(true).build()));
			@SuppressWarnings("resource")
			MongoClient mongoClient = new MongoClient("localhost",
					MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
			MongoDatabase dbGame = mongoClient.getDatabase("SOS");
			colSubject = dbGame.getCollection("Subject", Subject.class);
		}
		return ad;
	}

	public void insertData(Subject subject) {
		colSubject.insertOne(subject);
	}

	public static List<Subject> getAllData() {
		List<Subject> subjects = new ArrayList<Subject>();

		Block<Subject> printBlock = new Block<Subject>() {
			@Override
			public void apply(final Subject subject) {
				subjects.add(subject);
			}
		};

		colSubject.find().forEach(printBlock);

		return subjects;
	}

	/*public List<Subject> getData(String userRequested) {

		List<Question> questions = new ArrayList<Question>();

		Block<Question> printBlock = new Block<Question>() {
			@Override
			public void apply(final Question question) {
				questions.add(question);
			}
		};

		colSubject.find(eq("author", userRequested)).forEach(printBlock);

		return questions;

	}*/
	
	public List<Subject> getRawData() {

		List<Subject> subjects = new ArrayList<Subject>();

		Block<Subject> printBlock = new Block<Subject>() {
			@Override
			public void apply(final Subject subject) {
				subjects.add(subject);
			}
		};

		colSubject.find().forEach(printBlock);

		return subjects;

	}

	public Subject getSpecificData(String userRequested, String subjectID) {

		final List<Subject> subjects = new ArrayList<Subject>();

		Block<Subject> printBlock = new Block<Subject>() {
			@Override
			public void apply(final Subject subject) {
				subjects.add(subject);
			}
		};
		Bson filter = Filters.and(Filters.eq("subjectID", subjectID), Filters.eq("author", userRequested));

		colSubject.find(filter).forEach(printBlock);

		return subjects.get(0);
	}

	public void changeData(String subjectID, String description) {
		Document setData = new Document();
		setData.append("description", description);
		Document update = new Document();
		update.append("$set", setData);

		colSubject.updateOne(eq("subjectID", subjectID), update);
	}

	public void removeData(String subjectID) {
		colSubject.deleteOne(eq("subjectID", subjectID));
	}

}
