package engineersthesis.playingfieldmanagment.application.config;

import engineersthesis.playingfieldmanagment.common.security.model.User;
import engineersthesis.playingfieldmanagment.common.security.model.WorkerRequest;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Component
public class RestConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setRepositoryDetectionStrategy(RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);
        config.setBasePath("/api");
        config.exposeIdsFor(User.class);
        config.exposeIdsFor(WorkerRequest.class);
    }
}