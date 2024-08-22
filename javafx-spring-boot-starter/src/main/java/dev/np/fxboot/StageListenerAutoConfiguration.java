package dev.np.fxboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class StageListenerAutoConfiguration {

    @Bean
    public ApplicationListener<StageReadyEvent> stageReadyEventListener(
            JavaFXApplicationSpringBootAdapter adapter) {
        log.info("configured StageReadyEventListener");
        return new StageReadyEventListener(adapter);
    }
}
