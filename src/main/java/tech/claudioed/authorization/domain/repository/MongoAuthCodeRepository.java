package tech.claudioed.authorization.domain.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.bson.Document;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import tech.claudioed.authorization.domain.AuthCode;
import tech.claudioed.authorization.domain.CheckedAuthCode;
import tech.claudioed.authorization.domain.resources.data.RequestNewAuthCode;

/** @author claudioed on 2019-04-06. Project payment-authorization */
@ApplicationScoped
public class MongoAuthCodeRepository implements AuthCodeRepository {

  private final Logger logger;

  private final MongoClient mongoClient;

  private final String database;

  private final Integer validBy;

  @Inject
  public MongoAuthCodeRepository(
      Logger logger,
      MongoClient mongoClient,
      @ConfigProperty(name = "mongo.user") String database,
      @ConfigProperty(name = "auth.valid.by") Integer validBy) {
    this.logger = logger;
    this.mongoClient = mongoClient;
    this.database = database;
    this.validBy = validBy;
  }

  @Override
  public AuthCode register(RequestNewAuthCode requestNewAuthCode) {
    final MongoDatabase db = this.mongoClient.getDatabase(this.database);
    final MongoCollection<Document> dbCollection = db.getCollection("authcodes");
    final AuthCode authCode =
        AuthCode.builder()
            .createdAt(LocalDateTime.now())
            .userId(requestNewAuthCode.getUserId())
            .value(requestNewAuthCode.getValue())
            .validUntil(LocalDateTime.now().plusMinutes(this.validBy))
            .id(UUID.randomUUID().toString())
            .build();
    dbCollection.insertOne(authCode.toDoc());
    return authCode;
  }

  @Override
  public CheckedAuthCode registerChecked(AuthCode code) {
    final MongoDatabase db = this.mongoClient.getDatabase(this.database);
    final MongoCollection<Document> dbCollection = db.getCollection("checked-authcodes");
    final CheckedAuthCode checkedAuthCode =
        CheckedAuthCode.builder()
            .id(UUID.randomUUID().toString())
            .usedAt(LocalDateTime.now())
            .data(code)
            .build();
    dbCollection.insertOne(checkedAuthCode.toDoc());
    return checkedAuthCode;
  }

  @Override
  public AuthCode find(String id, String userId) {
    final MongoDatabase db = this.mongoClient.getDatabase(this.database);
    final MongoCollection<Document> dbCollection = db.getCollection("authcodes");
    final Document query = new Document().append("userId", userId).append("_id", id);
    final FindIterable<Document> documents = dbCollection.find(query);
    final FindIterable<Document> doc = documents.limit(1);
    final Document data = doc.first();
    final AuthCode authCode =
        AuthCode.builder()
            .id(data.getString("_id"))
            .userId(data.getString("userId"))
            .value(data.get("value", BigDecimal.class))
            .validUntil(data.get("validUntil", LocalDateTime.class))
            .createdAt(data.get("createdAt", LocalDateTime.class))
            .build();
    return authCode;
  }
}
