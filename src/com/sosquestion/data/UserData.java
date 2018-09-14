package com.sosquestion.data;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sosquestion.model.User;

import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class UserData {
	static UserData ud = null;
	static MongoCollection<User> colUser;

	public static UserData getInstance() {
		if (ud == null) {
			ud = new UserData();

			CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
					fromProviders(PojoCodecProvider.builder().automatic(true).build()));
			@SuppressWarnings("resource")
			MongoClient mongoClient = new MongoClient("localhost",
					MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
			MongoDatabase dbGame = mongoClient.getDatabase("SOS");
			colUser = dbGame.getCollection("User", User.class);
		}
		return ud;
	}

	public void insertData(User user) {
		colUser.insertOne(user);
	}

	public List<User> getRawData() {

		List<User> users = new ArrayList<User>();

		Block<User> printBlock = new Block<User>() {
			@Override
			public void apply(final User user) {
				users.add(user);
			}
		};

		colUser.find().forEach(printBlock);

		return users;

	}

	public List<User> getData() {

		List<User> users = new ArrayList<User>();

		Block<User> printBlock = new Block<User>() {
			@Override
			public void apply(final User user) {
				users.add(user);
			}
		};

		colUser.find().projection(fields(include("userID", "email"), excludeId())).forEach(printBlock);

		return users;

	}

	public List<User> getData(String userID) {

		final List<User> users = new ArrayList<User>();

		Block<User> printBlock = new Block<User>() {
			public void apply(final User user) {
				users.add(user);
			}
		};

		colUser.find(eq("userID", userID)).projection(fields(include("userID", "email"), excludeId()))
				.forEach(printBlock);

		return users;

	}

	public void changeData(String userID, String newPassword, String newEmail) {
		Document setData = new Document();
		setData.append("password", newPassword).append("email", newEmail);
		Document update = new Document();
		update.append("$set", setData);

		colUser.updateOne(eq("userID", userID), update);
	}

	public void removeData(String userID) {
		colUser.deleteOne(eq("userID", userID));
	}

}
