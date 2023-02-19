package games.kingdoms.kingdoms.publiccmds.kingdoms.mongodb.economy;

import games.kingdoms.kingdoms.Kingdoms;
import games.kingdoms.kingdoms.publiccmds.kingdoms.mongodb.MongoConnect;
import org.bson.Document;

public class PlayerManager {

    private String uuid;
    private double balance;
    private double bankAccount;
    private Kingdoms plugin;
    private MongoConnect mongoConnect = plugin.mongoConnect;

    public PlayerManager(String uuid, double balance, double bankAccount) {
        this.uuid = uuid;
        this.balance = balance;
        this.bankAccount = bankAccount;

        Document document = new Document("uuid", uuid);
        if (mongoConnect.getPlayerDataCollection().find(document).first() == null) {
            document.append("balance", balance);
            document.append("bankAccount", bankAccount);
            mongoConnect.getPlayerDataCollection().insertOne(document);
        }
    }

    public String getUuid() {
        return uuid;
    }

    public double getBalance() {
        return (double) mongoConnect.getPlayerDataDocument("balance", uuid);
    }

    public void setBalance(double balance) {
        this.balance = balance;
        mongoConnect.setPlayerDataDocument(balance, "balance", uuid);
    }

    public double getBankAccount() {
        return (double) mongoConnect.getPlayerDataDocument("bankAccount", uuid);
    }

    public void setBankAccount(double bankAccount) {
        this.bankAccount = bankAccount;
        mongoConnect.setPlayerDataDocument(bankAccount, "bankAccount", uuid);
    }
}
