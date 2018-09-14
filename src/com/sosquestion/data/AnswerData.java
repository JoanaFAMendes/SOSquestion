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
import com.sosquestion.model.Answer;

import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class AnswerData {
	static AnswerData ud = null;
	static MongoCollection<Answer> colAnswer;
	
	public static AnswerData getInstance() {
		if(ud == null) {
			ud = new AnswerData();			
			
			CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
			@SuppressWarnings("resource")
			MongoClient mongoClient = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
			MongoDatabase dbGame = mongoClient.getDatabase("SOS");
			colAnswer = dbGame.getCollection("Answer", Answer.class);
		}
		return ud;
	}
	
	
	public void insertData(Answer answer) {			
			colAnswer.insertOne(answer);
	}	

	public List<Answer> getData(String questionID) {
		
		List<Answer> answers = new ArrayList<Answer>();
		
		Block<Answer> printBlock = new Block<Answer>() {
		    @Override
		    public void apply(final Answer answer) {
		        answers.add(answer);
		    }
		};
		
		colAnswer.find(eq("questionID", questionID)).forEach(printBlock);

		return answers;
		
	}
	
	public Answer getSpecificData(String questionID, String answerID) {	
		
		final List<Answer> answers = new ArrayList<Answer>();
		
		Block<Answer> printBlock = new Block<Answer>() {
		    public void apply(final Answer answer) {
		    	answers.add(answer);
		    }
		};
		
		Bson filter = Filters.and(
                Filters.eq("questionID", questionID), 
                Filters.eq("answerID", answerID)
                );
		
		colAnswer.find(filter).forEach(printBlock);
		
		return answers.get(0);
		
	}
	
	public void changeData(String questionID, String answerID, String author, String date, String description) {
		Document setData = new Document();
        setData.append("author", author).append("date", date).append("description", description);
        Document update = new Document();
        update.append("$set", setData);
        
        Bson filter = Filters.and(
                Filters.eq("questionID", questionID), 
                Filters.eq("answerID", answerID)
                );
    
		colAnswer.updateOne(filter, update);
	}

	public void removeData(String questionID, String answerID) {
		
		Bson filter = Filters.and(
                Filters.eq("questionID", questionID), 
                Filters.eq("answerID", answerID)
                );
		
		colAnswer.deleteOne(filter);		
	}


	
	
	
	
	
}

