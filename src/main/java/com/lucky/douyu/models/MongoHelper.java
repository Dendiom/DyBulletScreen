package com.lucky.douyu.models;

import com.lucky.douyu.Constants;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * MongoDB 操作封装.
 */
public class MongoHelper {

    private static MongoHelper sInstance;

    private MongoCollection<Document> collection;
    private boolean useMongo = false;

    private MongoHelper(){}

    public static MongoHelper getInstance() {
        if (sInstance == null) {
            sInstance = new MongoHelper();
        }

        return sInstance;
    }

    public void init(String roomId) {
        if ("1209".equals(roomId)) {
            useMongo = true;
            MongoClient client = MongoClients.create(Constants.Mongo.CONNECT_STRING);
            MongoDatabase database = client.getDatabase(Constants.Mongo.DATABASE);
            initCollection(database);
            System.out.println("mongo client init");
        }
    }

    public void insertDocs(List<Document> docs) {
        if (useMongo) {
            collection.insertMany(docs);
            System.out.println("insert documents");
        }
    }

    private void initCollection(MongoDatabase database) {
        for(String name : database.listCollectionNames()) {
            if (name.equals(Constants.Mongo.COLLECTION)) {
                collection = database.getCollection(Constants.Mongo.COLLECTION);
                return;
            }
        }

        database.createCollection(Constants.Mongo.COLLECTION);
        collection = database.getCollection(Constants.Mongo.COLLECTION);
    }
}
