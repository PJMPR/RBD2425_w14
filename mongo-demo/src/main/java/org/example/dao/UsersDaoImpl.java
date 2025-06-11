package org.example.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.MongoDbConnector;
import org.example.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersDaoImpl implements UsersDao {
    private final MongoCollection<Document> collection;

    public UsersDaoImpl() {
        this.collection = MongoDbConnector.getDatabase().getCollection("users");
    }

    @Override
    public void create(User user) {
        Document doc = new Document("name", user.getName())
                .append("age", user.getAge());
        collection.insertOne(doc);
    }

    @Override
    public User read(String id) {
        Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
        if (doc == null) return null;
        return new User(doc.getObjectId("_id").toHexString(), doc.getString("name"), doc.getInteger("age"));
    }

    @Override
    public void update(User user) {
        collection.updateOne(
                Filters.eq("_id", new ObjectId(user.getId())),
                Updates.combine(
                        Updates.set("name", user.getName()),
                        Updates.set("age", user.getAge())
                )
        );
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        for (Document doc : collection.find()) {
            users.add(new User(
                    doc.getObjectId("_id").toHexString(),
                    doc.getString("name"),
                    doc.getInteger("age")
            ));
        }
        return users;
    }
}
