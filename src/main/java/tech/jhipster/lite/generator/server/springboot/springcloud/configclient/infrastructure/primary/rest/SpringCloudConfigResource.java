package tech.jhipster.lite.generator.server.springboot.springcloud.configclient.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.project.domain.GeneratorAction;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.springcloud.configclient.application.SpringCloudConfigClientApplicationService;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/distributed-systems/spring-cloud/config-client")
@Tag(name = "Spring Boot - Spring Cloud")
class SpringCloudConfigResource {

  private final SpringCloudConfigClientApplicationService springCloudConfigClientApplicationService;

  public SpringCloudConfigResource(SpringCloudConfigClientApplicationService springCloudConfigClientApplicationService) {
    this.springCloudConfigClientApplicationService = springCloudConfigClientApplicationService;
  }

  @Operation(summary = "Add Spring Cloud Config Client")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Spring Cloud Config Client")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.SPRING_CLOUD)
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    springCloudConfigClientApplicationService.init(project);
  }
}
