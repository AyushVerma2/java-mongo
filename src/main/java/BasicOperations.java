import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.Iterator;

public class BasicOperations {

    public static void main(String args[]) {

        // Creating a Mongo client
        MongoClient mongo = new MongoClient("localhost", 27017);

        // Creating Credentials
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("myDb");
        System.out.println("Credentials ::" + credential);

        // create Collection
        //Creating a collection this should be created only once..
        if (null == database.getCollection("sampleCollection"))
            database.createCollection("sampleCollection");

        //**********************

        // Creating a collection
        System.out.println("Collection created successfully");

        // Retieving a collection
        MongoCollection<Document> collection = database.getCollection("myCollection");
        System.out.println("Collection myCollection selected successfully");

        //********
        // Retrieving a collection
        System.out.println("Collection sampleCollection selected successfully");

        Document document = new Document("title", "MongoDB")
                .append("id", 1)
                .append("description", "database")
                .append("likes", 100)
                .append("url", "http://www.tutorialspoint.com/mongodb/")
                .append("by", "tutorials point");
        collection.insertOne(document);
        System.out.println("Document inserted successfully");
        reteriveCollection(collection);
        updateCollections(collection);


        // deleting collections

        deletingCollection(database);


    }

    private static void reteriveCollection(MongoCollection<Document> collection) {
        System.out.println("Reterive All docs...");

        // Getting the iterable object
        FindIterable<Document> iterDoc = collection.find();
        int i = 1;

        // Getting the iterator
        Iterator it = iterDoc.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
            i++;
        }
    }

    private static void updateCollections(MongoCollection<Document> collection) {
        //updating docs

        System.out.println("Collection myCollection selected successfully");

        collection.updateOne(Filters.eq("id", 1), Updates.set("likes", 150));
        reteriveCollection(collection);
        System.out.println("Document update successfully...");
    }

    private static void deletingCollection(MongoDatabase database) {
        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection("sampleCollection");
        System.out.println("Collection sampleCollection selected successfully");

        // Deleting the documents
        collection.deleteOne(Filters.eq("id", 1));
        System.out.println("Document deleted successfully...");

        // Retrieving the documents after updation
        // Getting the iterable object
        FindIterable<Document> iterDoc = collection.find();
        int i = 1;

        // Getting the iterator
        Iterator it = iterDoc.iterator();

        while (it.hasNext()) {
            System.out.println("Inserted Document: " + i);
            System.out.println(it.next());
            i++;
        }
    }
}
