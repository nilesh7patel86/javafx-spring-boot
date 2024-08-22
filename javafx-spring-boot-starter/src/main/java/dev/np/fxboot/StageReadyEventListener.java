package dev.np.fxboot;

import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

@Slf4j
@RequiredArgsConstructor
public class StageReadyEventListener implements ApplicationListener<StageReadyEvent> {
    private final JavaFXApplicationSpringBootAdapter adapter;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        log.info("received StageReadyEvent");
        Stage stage = event.getStage();
        Scene scene = new Scene(adapter.getRoot(), 1024, 768);
        stage.setScene(scene);
        stage.show();
    }
}