package tech.jhipster.lite.generator.server.springboot.broker.kafka.infrastructure.primary.rest;

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
import tech.jhipster.lite.generator.server.springboot.broker.kafka.application.KafkaApplicationService;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/brokers/kafka")
@Tag(name = "Spring Boot - Broker")
class KafkaResource {

  private final KafkaApplicationService kafkaApplicationService;

  public KafkaResource(KafkaApplicationService kafkaApplicationService) {
    this.kafkaApplicationService = kafkaApplicationService;
  }

  @Operation(summary = "Add Kafka dependencies, with testcontainers")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Kafka")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.SPRINGBOOT_KAFKA)
  public void init(final @RequestBody ProjectDTO projectDTO) {
    final Project project = ProjectDTO.toProject(projectDTO);
    kafkaApplicationService.init(project);
  }

  @Operation(summary = "Add dummy Kafka producer and consumer")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding dummy Kafka producer and consumer")
  @PostMapping("/dummy-producer-consumer")
  @GeneratorStep(id = GeneratorAction.SPRINGBOOT_KAFKA_DUMMY_PRODUCER_CONSUMER)
  public void addDummyProducerConsumer(final @RequestBody ProjectDTO projectDTO) {
    final Project project = ProjectDTO.toProject(projectDTO);
    kafkaApplicationService.addDummyProducerConsumer(project);
  }

  @Operation(summary = "Add AKHQ")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding AKHQ")
  @PostMapping("/akhq")
  @GeneratorStep(id = GeneratorAction.SPRINGBOOT_KAFKA_AKHQ)
  public void addAkhq(final @RequestBody ProjectDTO projectDTO) {
    final Project project = ProjectDTO.toProject(projectDTO);
    kafkaApplicationService.addAkhq(project);
  }
}
