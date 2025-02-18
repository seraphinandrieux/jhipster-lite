package tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_PATH;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain.JwtSecurity.jjwtApiDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain.JwtSecurity.jjwtImplDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain.JwtSecurity.jjwtJacksonDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain.JwtSecurity.jwtSecurityFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain.JwtSecurity.jwtTestSecurityFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain.JwtSecurity.springBootStarterSecurityDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain.JwtSecurity.springSecurityTestDependency;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.mvc.security.common.domain.CommonSecurityService;

public class JwtSecurityDomainService implements JwtSecurityService {

  public static final String SOURCE = "server/springboot/mvc/security/jwt";
  public static final String SECURITY_JWT_PATH = "security/jwt";

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;
  private final CommonSecurityService commonSecurityService;

  public JwtSecurityDomainService(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService,
    CommonSecurityService commonSecurityService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
    this.commonSecurityService = commonSecurityService;
  }

  @Override
  public void init(Project project) {
    projectRepository.gitInit(project);

    addPropertyAndDependency(project);
    addJavaFiles(project);
    addProperties(project);
    addLoggerInConfiguration(project);

    updateExceptionTranslator(project);
    updateIntegrationTestWithMockUser(project);
  }

  @Override
  public void addBasicAuth(Project project) {
    addBasicAuthJavaFiles(project);
    addBasicAuthProperties(project);
  }

  private void updateExceptionTranslator(Project project) {
    commonSecurityService.updateExceptionTranslator(project);
  }

  private void updateIntegrationTestWithMockUser(Project project) {
    commonSecurityService.updateIntegrationTestWithMockUser(project);
  }

  private void addPropertyAndDependency(Project project) {
    this.buildToolService.getVersion(project, "jjwt")
      .ifPresentOrElse(
        version -> {
          buildToolService.addProperty(project, "jjwt.version", version);

          buildToolService.addDependency(project, springBootStarterSecurityDependency());
          buildToolService.addDependency(project, jjwtApiDependency());
          buildToolService.addDependency(project, jjwtImplDependency());
          buildToolService.addDependency(project, jjwtJacksonDependency());

          buildToolService.addDependency(project, springSecurityTestDependency());
        },
        () -> {
          throw new GeneratorException("Json Web Token version not found");
        }
      );
  }

  private void addJavaFiles(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);

    String sourceSrc = getPath(SOURCE, "src");
    String sourceTest = getPath(SOURCE, "test");
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    String destinationSrc = getPath(MAIN_JAVA, packageNamePath, SECURITY_JWT_PATH);
    String destinationTest = getPath(TEST_JAVA, packageNamePath, SECURITY_JWT_PATH);

    List<ProjectFile> sourceFiles = jwtSecurityFiles()
      .entrySet()
      .stream()
      .map(entry ->
        ProjectFile
          .forProject(project)
          .withSource(sourceSrc, entry.getKey())
          .withDestinationFolder(getPath(destinationSrc, entry.getValue()))
      )
      .toList();
    projectRepository.template(sourceFiles);

    List<ProjectFile> testFiles = jwtTestSecurityFiles()
      .entrySet()
      .stream()
      .map(entry ->
        ProjectFile
          .forProject(project)
          .withSource(sourceTest, entry.getKey())
          .withDestinationFolder(getPath(destinationTest, entry.getValue()))
      )
      .toList();
    projectRepository.template(testFiles);
  }

  private void addBasicAuthJavaFiles(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    String sourceSrc = getPath(SOURCE, "src/account");
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    String destinationSrc = getPath(MAIN_JAVA, packageNamePath, "account/infrastructure/primary/rest");

    projectRepository.template(
      ProjectFile.forProject(project).withSource(sourceSrc, "AuthenticationResource.java").withDestinationFolder(destinationSrc)
    );
    projectRepository.template(
      ProjectFile.forProject(project).withSource(sourceSrc, "AccountResource.java").withDestinationFolder(destinationSrc)
    );
    projectRepository.template(
      ProjectFile.forProject(project).withSource(sourceSrc, "LoginDTO.java").withDestinationFolder(destinationSrc)
    );

    String sourceTest = getPath(SOURCE, "test/account");
    String destinationTest = getPath(TEST_JAVA, packageNamePath, "account/infrastructure/primary/rest");
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(sourceTest, "AuthenticationResourceIT.java")
        .withDestinationFolder(getPath(destinationTest))
    );
    projectRepository.template(
      ProjectFile.forProject(project).withSource(sourceTest, "AccountResourceIT.java").withDestinationFolder(getPath(destinationTest))
    );
    projectRepository.template(
      ProjectFile.forProject(project).withSource(sourceTest, "LoginDTOTest.java").withDestinationFolder(getPath(destinationTest))
    );
  }

  private void addBasicAuthProperties(Project project) {
    springBootCommonService.addProperties(project, "spring.security.user.name", "admin");
    springBootCommonService.addProperties(
      project,
      "spring.security.user.password",
      "\\$2a\\$12\\$cRKS9ZURbdJIaRsKDTDUmOrH4.B.2rokv8rrkrQXr2IR2Hkna484O"
    );
    springBootCommonService.addProperties(project, "spring.security.user.roles", "ADMIN");
    springBootCommonService.addPropertiesNewLine(project);

    springBootCommonService.addPropertiesTest(project, "spring.security.user.name", "admin");
    springBootCommonService.addPropertiesTest(
      project,
      "spring.security.user.password",
      "\\$2a\\$12\\$cRKS9ZURbdJIaRsKDTDUmOrH4.B.2rokv8rrkrQXr2IR2Hkna484O"
    );
    springBootCommonService.addPropertiesTest(project, "spring.security.user.roles", "ADMIN");
    springBootCommonService.addPropertiesTestNewLine(project);
  }

  private void addProperties(Project project) {
    String commentSecurityJwt = "Spring Security JWT";
    springBootCommonService.addPropertiesComment(project, commentSecurityJwt);
    springBootCommonService.addPropertiesTestComment(project, commentSecurityJwt);
    springBootCommonService.addPropertiesLocalComment(project, commentSecurityJwt);
    jwtProperties()
      .forEach((k, v) -> {
        springBootCommonService.addProperties(project, k, v);
        springBootCommonService.addPropertiesTest(project, k, v);
      });
    springBootCommonService.addPropertiesNewLine(project);
    springBootCommonService.addPropertiesTestNewLine(project);
    springBootCommonService.addPropertiesLocalNewLine(project);
  }

  private Map<String, Object> jwtProperties() {
    Map<String, Object> result = new LinkedHashMap<>();
    result.put(
      "application.security.authentication.jwt.base64-secret",
      "bXktc2VjcmV0LWtleS13aGljaC1zaG91bGQtYmUtY2hhbmdlZC1pbi1wcm9kdWN0aW9uLWFuZC1iZS1iYXNlNjQtZW5jb2RlZAo="
    );
    result.put("application.security.authentication.jwt.token-validity-in-seconds", "86400");
    result.put("application.security.authentication.jwt.token-validity-in-seconds-for-remember-me", "2592000");
    return result;
  }

  private void addLoggerInConfiguration(Project project) {
    springBootCommonService.addLogger(project, "org.springframework.security", Level.WARN);
    springBootCommonService.addLoggerTest(project, "org.springframework.security", Level.WARN);
  }
}
