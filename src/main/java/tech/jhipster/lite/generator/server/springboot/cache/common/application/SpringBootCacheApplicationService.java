package tech.jhipster.lite.generator.server.springboot.cache.common.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.cache.common.domain.SpringBootCacheService;

@Service
public class SpringBootCacheApplicationService {

  private final SpringBootCacheService springBootCacheService;

  public SpringBootCacheApplicationService(SpringBootCacheService springBootCacheService) {
    this.springBootCacheService = springBootCacheService;
  }

  public void addDependencies(Project project) {
    springBootCacheService.addDependencies(project);
  }

  public void addEnableCaching(Project project) {
    springBootCacheService.addEnableCaching(project);
  }
}
