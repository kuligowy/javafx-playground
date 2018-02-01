package pl.kuligowy.pocspringfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.kuligowy.pocspringfx.menu.MenuView;
import pl.kuligowy.pocspringfx.module.empty.EmptyView;

@SpringBootApplication
public class PocSpringfxApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private static BorderPane root = new BorderPane();

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(PocSpringfxApplication.class);
    }

    //TODO change this to something else, maybe FXML view template with controller?
    public static BorderPane getRoot() {
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        springContext.getBeanFactory().registerSingleton("primaryStage", primaryStage);
        primaryStage.setTitle("Hello World");

        MenuView menuView = springContext.getBean(MenuView.class);
        EmptyView emptyView = springContext.getBean(EmptyView.class);
        root.setTop(menuView.getView());
        root.setCenter(emptyView.getView());

        Scene scene = new Scene(root, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    @Override
    public void stop() throws Exception {
        springContext.stop();
    }

    public static void main(String[] args) {
        launch(PocSpringfxApplication.class, args);
    }
}
