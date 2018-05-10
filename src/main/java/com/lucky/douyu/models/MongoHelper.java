package com.lucky.douyu.models;

import com.lucky.douyu.Constants;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


/**
 * MongoDB 操作封装.
 */
public class MongoHelper {

    private static MongoHelper sInstance;

    private MongoCollection<Document> collection;

    private Logger logger = Logger.getLogger(MongoHelper.class);

    private MongoHelper() {
    }

    public static MongoHelper getInstance() {
        if (sInstance == null) {
            sInstance = new MongoHelper();
        }

        return sInstance;
    }

    public void init() {
        MongoClient client = MongoClients.create(Constants.Mongo.CONNECT_STRING);
        MongoDatabase database = client.getDatabase(Constants.Mongo.DATABASE);
        initCollection(database);
        logger.info("mongo client init");
    }

    public void insertDocs(List<Document> docs) {
        collection.insertMany(docs);
        logger.info("insert documents");
    }

    private void initCollection(MongoDatabase database) {
        for (String name : database.listCollectionNames()) {
            if (name.equals(Constants.Mongo.COLLECTION)) {
                collection = database.getCollection(Constants.Mongo.COLLECTION);
                return;
            }
        }

        database.createCollection(Constants.Mongo.COLLECTION);
        collection = database.getCollection(Constants.Mongo.COLLECTION);
    }
}
