package games.kingdoms.kingdoms.publiccmds.kingdoms.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoConnect {
    private MongoDatabase database;
    private MongoCollection playerData;

    public void playerDataConnect() {
        MongoClient client = new MongoClient(new MongoClientURI("mongodb+srv://Kingdoms:Minime453-2-2@kingdoms.vfbicgo.mongodb.net/test"));
        setDatabase(client.getDatabase("Kingdoms"));
        setPlayerDataCollection(database.getCollection("PlayerData"));
        MessageManager.consoleGood("Database connected");
    }

    public void setPlayerDataDocument(Object value, String identifier, String uuid) {
        Document document = new Document("uuid", uuid);
        Bson newValue = new Document(identifier, value);
        Bson updateOperation = new Document("$set", newValue);
        playerData.updateOne(document, updateOperation);
    }

    public Object getPlayerDataDocument(String identifier, String uuid) {
        Document document = new Document("uuid", uuid);
        if (playerData.find(document).first() != null) {
            Document found = (Document) playerData.find().first();
            return found.get(identifier);
        }
            MessageManager.consoleBad("Document is null for UUID: " + uuid);
        return null;
    }

    public MongoCollection getPlayerDataCollection() {
        return playerData;
    }

    public void setPlayerDataCollection(MongoCollection playerData) {
        this.playerData = playerData;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }
}
