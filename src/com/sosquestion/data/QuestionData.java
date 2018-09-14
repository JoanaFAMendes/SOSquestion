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
import com.sosquestion.model.Question;

import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class QuestionData {
	static QuestionData ad = null;
	static MongoCollection<Question> colQuestion;

	public static QuestionData getInstance() {
		if (ad == null) {
			ad = new QuestionData();

			CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
					fromProviders(PojoCodecProvider.builder().automatic(true).build()));
			@SuppressWarnings("resource")
			MongoClient mongoClient = new MongoClient("localhost",
					MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
			MongoDatabase dbGame = mongoClient.getDatabase("SOS");
			colQuestion = dbGame.getCollection("Question", Question.class);
		}
		return ad;
	}

	public void insertData(Question question) {
		colQuestion.insertOne(question);
	}

	public static List<Question> getAllData() {
		List<Question> questions = new ArrayList<Question>();

		Block<Question> printBlock = new Block<Question>() {
			@Override
			public void apply(final Question question) {
				questions.add(question);
			}
		};

		colQuestion.find().forEach(printBlock);

		return questions;
	}

	public List<Question> getData(String userRequested) {

		List<Question> questions = new ArrayList<Question>();

		Block<Question> printBlock = new Block<Question>() {
			@Override
			public void apply(final Question question) {
				questions.add(question);
			}
		};

		colQuestion.find(eq("author", userRequested)).forEach(printBlock);

		return questions;

	}
	
	public List<Question> getRawData(String subject) {

		List<Question> questions = new ArrayList<Question>();

		Block<Question> printBlock = new Block<Question>() {
			@Override
			public void apply(final Question question) {
				questions.add(question);
			}
		};

		colQuestion.find(eq("subject", subject)).forEach(printBlock);

		return questions;

	}

	public Question getSpecificData(String userRequested, String questionID) {

		final List<Question> questions = new ArrayList<Question>();

		Block<Question> printBlock = new Block<Question>() {
			@Override
			public void apply(final Question question) {
				questions.add(question);
			}
		};
		Bson filter = Filters.and(Filters.eq("questionID", questionID), Filters.eq("author", userRequested));

		colQuestion.find(filter).forEach(printBlock);

		return questions.get(0);
	}

	public void changeData(String questionID, String questionP, String subject, String description) {
		Document setData = new Document();
		setData.append("questionP", questionP).append("subject", subject).append("description", description);
		Document update = new Document();
		update.append("$set", setData);

		colQuestion.updateOne(eq("questionID", questionID), update);
	}

	public void removeData(String questionID) {
		colQuestion.deleteOne(eq("questionID", questionID));
	}

}
