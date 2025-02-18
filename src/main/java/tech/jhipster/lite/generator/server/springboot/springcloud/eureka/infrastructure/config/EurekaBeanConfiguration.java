package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;
import tech.jhipster.lite.generator.server.springboot.springcloud.eureka.domain.EurekaDomainService;
import tech.jhipster.lite.generator.server.springboot.springcloud.eureka.domain.EurekaService;

@Configuration
public class EurekaBeanConfiguration {

  private final BuildToolService buildToolService;
  private final SpringCloudCommonService springCloudCommonService;

  public EurekaBeanConfiguration(BuildToolService buildToolService, SpringCloudCommonService springCloudCommonService) {
    this.buildToolService = buildToolService;
    this.springCloudCommonService = springCloudCommonService;
  }

  @Bean
  public EurekaService eurekaService() {
    return new EurekaDomainService(buildToolService, springCloudCommonService);
  }
}
