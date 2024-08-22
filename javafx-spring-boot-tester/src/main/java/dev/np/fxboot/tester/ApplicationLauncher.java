package dev.np.fxboot.tester;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationLauncher {
    public static void main(String[] args) {
        Application.launch(FXBootAdapterImpl.class,args);
    }
}
