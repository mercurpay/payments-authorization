package tech.claudioed.authorization.domain.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.bson.Document;
import org.bson.types.Decimal128;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import tech.claudioed.authorization.domain.AuthCode;
import tech.claudioed.authorization.domain.CheckedAuthCode;
import tech.claudioed.authorization.domain.resources.data.RequestNewAuthCode;

/** @author claudioed on 2019-04-06. Project payment-authorization */
@ApplicationScoped
public class MongoAuthCodeRepository implements AuthCodeRepository {

  @Inject
  Logger logger;

  @Inject
  MongoClient mongoClient;

  @Inject
  @ConfigProperty(name = "mongo.database",defaultValue = "AUTHORIZATION")
  String database;

  @Inject
  @ConfigProperty(name = "auth.valid.by",defaultValue = "10")
  Integer validBy;

  @Override
  public AuthCode register(RequestNewAuthCode requestNewAuthCode) {
    final MongoDatabase db = this.mongoClient.getDatabase(this.database);
    final MongoCollection<Document> dbCollection = db.getCollection("authcodes");
    final AuthCode authCode =
        AuthCode.builder()
            .createdAt(LocalDateTime.now(ZoneOffset.UTC))
            .userId(requestNewAuthCode.getUserId())
            .value(requestNewAuthCode.getValue())
            .validUntil(LocalDateTime.now(ZoneOffset.UTC).plusMinutes(this.validBy))
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
    final Decimal128 value = data.get("value", Decimal128.class);
    final AuthCode authCode =
        AuthCode.builder()
            .id(data.getString("_id"))
            .userId(data.getString("userId"))
            .value(value.bigDecimalValue())
            .validUntil(data.get("validUntil", Date.class).toInstant().atZone(ZoneOffset.UTC).toLocalDateTime())
            .createdAt(data.get("createdAt", Date.class).toInstant().atZone(ZoneOffset.UTC).toLocalDateTime())
            .build();
    return authCode;
  }
}
