package tech.jhipster.lite.generator.server.springboot.database.mysql.domain;

import java.util.Map;
import java.util.TreeMap;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

public class MySQL {

  public static final String DOCKER_IMAGE_NAME = "mysql";
  public static final String MYSQL_GROUP_ID = "mysql";
  public static final String SOURCE = "server/springboot/database/mysql";

  private MySQL() {}

  public static String getDockerImageName() {
    return DOCKER_IMAGE_NAME;
  }

  public static Dependency mysqlConnectorJava() {
    return Dependency.builder().groupId(MYSQL_GROUP_ID).artifactId("mysql-connector-java").build();
  }

  public static Map<String, Object> springProperties(String baseName) {
    TreeMap<String, Object> result = new TreeMap<>();
    result.put("spring.datasource.url", "jdbc:mysql://localhost:3306/" + baseName);
    result.put("spring.datasource.username", "root");
    return result;
  }

  public static Map<String, Object> springPropertiesForTest(String baseName, String mysqlVersion) {
    TreeMap<String, Object> result = new TreeMap<>();
    result.put("spring.datasource.driver-class-name", "org.testcontainers.jdbc.ContainerDatabaseDriver");
    result.put("spring.datasource.url", "jdbc:tc:mysql:" + mysqlVersion + ":///" + baseName);
    result.put("spring.datasource.username", baseName);
    result.put("spring.datasource.password", "");
    result.put("spring.datasource.hikari.maximum-pool-size", 2);
    return result;
  }
}
