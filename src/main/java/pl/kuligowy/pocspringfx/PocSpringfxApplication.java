package pl.kuligowy.pocspringfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.kuligowy.pocspringfx.menu.MenuPresenter;
import pl.kuligowy.pocspringfx.menu.MenuView;
import pl.kuligowy.pocspringfx.module.empty.EmptyView;

import java.util.Arrays;

@SpringBootApplication
public class PocSpringfxApplication extends Application {
    private static final Logger logger = LoggerFactory.getLogger(MenuPresenter.class);

    private ConfigurableApplicationContext springContext;
    private static BorderPane root = new BorderPane();

    @Override
    public void init() throws Exception {
        logger.debug("Init...");
        int paramSize = getParameters().getRaw().size();
        logger.debug("Init... param size {}", paramSize);
        String[] args = new String[paramSize];
        args = getParameters().getRaw().toArray(args);
        logger.debug("Init... args {}", Arrays.stream(args).reduce((s, s2) -> s + ", " + s2));
        springContext = SpringApplication.run(PocSpringfxApplication.class,
                args);
    }

    //TODO change this to something else, maybe FXML view template with controller?
    public static BorderPane getRoot() {
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        springContext.getBeanFactory().registerSingleton("primaryStage", primaryStage);
        primaryStage.setTitle("Army-App");

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
