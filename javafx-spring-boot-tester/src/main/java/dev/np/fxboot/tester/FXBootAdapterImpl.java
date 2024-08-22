package dev.np.fxboot.tester;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.model.WorkbenchModule;
import dev.np.fxboot.JavaFXApplicationSpringBootAdapter;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class FXBootAdapterImpl extends JavaFXApplicationSpringBootAdapter {
    @Override
    public Parent getRoot() {
        return Workbench.builder(
                new DummyModule(),
                new DummyModule(),
                new DummyModule()
        )
                .navigationDrawerItems(
                        new MenuItem("shfgasdh", FontIcon.of(MaterialDesign.MDI_ACCOUNT_STAR_VARIANT)),
                        new MenuItem("shfgasdh", FontIcon.of(MaterialDesign.MDI_ACCOUNT_STAR_VARIANT)),
                        new MenuItem("shfgasdh", FontIcon.of(MaterialDesign.MDI_ACCOUNT_STAR_VARIANT)),
                        new MenuItem("shfgasdh", FontIcon.of(MaterialDesign.MDI_ACCOUNT_STAR_VARIANT)),
                        new MenuItem("shfgasdh", FontIcon.of(MaterialDesign.MDI_ACCOUNT_STAR_VARIANT))
                )
                .build();
    }

    private class DummyModule extends WorkbenchModule {
        protected DummyModule() {
            super("Dummy", MaterialDesign.MDI_ACCOUNT_CARD_DETAILS);
        }

        @Override
        public Node activate() {
            return new StackPane(new Button("sdjkfghjwsdhgfjkshdjkf"));
        }
    }
}
