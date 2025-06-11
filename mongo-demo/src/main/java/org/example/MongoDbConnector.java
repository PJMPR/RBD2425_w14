package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDbConnector {
    private static final String USER = "admin";
    private static final String PASSWORD = "haslo123";
    private static final String HOST = "localhost"; // lub adres MongoDB Atlas
    private static final String DATABASE = "workdb";

    public static MongoDatabase getDatabase() {
        String uri = String.format("mongodb://%s:%s@%s:27017", USER, PASSWORD, HOST);
        MongoClient mongoClient = MongoClients.create(uri);
        return mongoClient.getDatabase(DATABASE);
    }
}
