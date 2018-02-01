package pl.kuligowy.pocspringfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.kuligowy.pocspringfx.module.empty.EmptyView;

@SpringBootApplication
public class PocSpringfxApplication extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(PocSpringfxApplication.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        springContext.getBeanFactory().registerSingleton("primaryStage", primaryStage);
        primaryStage.setTitle("Hello World");
        EmptyView emptyView = springContext.getBean(EmptyView.class);
        Scene scene = new Scene(emptyView.getView(), 800, 600);
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
