package tech.jhipster.lite.generator.buildtool.gradle.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.TestUtils.tmpProjectWithBuildGradle;
import static tech.jhipster.lite.common.domain.FileUtils.REGEXP_SPACE_STAR;
import static tech.jhipster.lite.generator.buildtool.gradle.domain.Gradle.GRADLE_NEEDLE_DEPENDENCY;
import static tech.jhipster.lite.generator.buildtool.gradle.domain.Gradle.GRADLE_NEEDLE_REPOSITORY;
import static tech.jhipster.lite.generator.project.domain.ProjectFilesAsserter.filesCountArgument;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.buildtool.generic.domain.Repository;
import tech.jhipster.lite.generator.buildtool.gradle.domain.GradleDomainService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class GradleDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  GradleDomainService gradleDomainService;

  @Test
  void shouldInitJava() {
    Project project = tmpProject();

    gradleDomainService.initJava(project);

    verify(projectRepository, times(2)).template(any(ProjectFile.class));
    verify(projectRepository).add(filesCountArgument(4));
    verify(projectRepository, times(2)).setExecutable(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddJavaBuildGradleKts() {
    Project project = tmpProject();

    gradleDomainService.addJavaBuildGradleKts(project);

    verify(projectRepository, times(2)).template(any(ProjectFile.class));
  }

  @Test
  void shouldAddGradleWrapper() {
    Project project = tmpProject();

    gradleDomainService.addGradleWrapper(project);

    verify(projectRepository).add(filesCountArgument(4));
    verify(projectRepository, times(2)).setExecutable(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddDependency() {
    Project project = tmpProject();
    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();

    gradleDomainService.addDependency(project, dependency);

    String newTextExpected = ("implementation(\"org.springframework.boot:spring-boot-starter\")\n" + GRADLE_NEEDLE_DEPENDENCY).indent(2);
    verify(projectRepository).replaceText(project, "", "build.gradle.kts", REGEXP_SPACE_STAR + GRADLE_NEEDLE_DEPENDENCY, newTextExpected);
  }

  @Test
  void shouldDeleteDependency() {
    Project project = tmpProjectWithBuildGradle();
    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();

    gradleDomainService.deleteDependency(project, dependency);

    String regexToReplace = "(?s)" + "implementation(\"org.springframework.boot:spring-boot-starter.*\")\n".indent(2);
    verify(projectRepository).replaceRegexp(project, "", "build.gradle.kts", regexToReplace, "");
  }

  @Test
  void shouldAddRepository() {
    Project project = tmpProjectWithBuildGradle();
    Repository repository = Repository.builder().id("spring-milestone").url("https://repo.spring.io/milestone").build();

    gradleDomainService.addRepository(project, repository);

    String repositoryString =
      """
        maven {
          url = uri("https://repo.spring.io/milestone")
        }
        // jhipster-needle-gradle-repository
      """;
    verify(projectRepository).replaceText(project, "", "build.gradle.kts", REGEXP_SPACE_STAR + GRADLE_NEEDLE_REPOSITORY, repositoryString);
  }
}
