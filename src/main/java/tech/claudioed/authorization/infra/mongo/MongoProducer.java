package tech.claudioed.authorization.infra.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import lombok.SneakyThrows;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;

/** @author claudioed on 2019-04-06. Project payment-authorization */
@ApplicationScoped
public class MongoProducer {

  @Inject
  private Logger logger;

  @Inject
  @ConfigProperty(name = "mongo.user")
  private String mongoUser;

  @Inject
  @ConfigProperty(name = "mongo.password")
  private String mongoPassword;

  @Inject
  @ConfigProperty(name = "mongo.host")
  private String mongoHost;

  @Inject
  @ConfigProperty(name = "mongo.port", defaultValue = "27017")
  private int mongoPort;

  @Inject
  @ConfigProperty(defaultValue = "admin")
  private String mongoAdminDb;

  private MongoClient mongoClient;

  @Produces
  @SneakyThrows
  @ApplicationScoped
  public MongoClient produceMongoClient() {
    if (this.mongoClient == null) {
      logger.info("Creating mongodb connection...");
      ServerAddress serverAddress = new ServerAddress(this.mongoHost, this.mongoPort);
      MongoCredential mongoCredential = MongoCredential.createCredential(this.mongoUser, this.mongoAdminDb, this.mongoPassword.toCharArray());
      this.mongoClient = new MongoClient(serverAddress, mongoCredential, MongoClientOptions.builder().build());
      logger.info("Connected to MongoDB server on {}:{}", this.mongoHost, this.mongoPort);
    }
    return mongoClient;
  }

  @PreDestroy
  void cleanup() {
    if (this.mongoClient != null) {
      logger.info("Closing mongodb connection...");
      this.mongoClient.close();
      logger.info("Mongodb connection closed successfully");
    }
  }
}
