package dev.np.fxboot.tester;

import dev.np.fxboot.JavaFXApplicationSpringBootAdapter;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;


public class FXBootAdapterImpl extends JavaFXApplicationSpringBootAdapter {
    @Override
    public Parent getRoot() {
        return new StackPane(new Button("Herllo"));
    }


}
