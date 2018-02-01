package pl.kuligowy.pocspringfx.menu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.kuligowy.pocspringfx.module.empty.EmptyView;
import pl.kuligowy.pocspringfx.module.nr1.MainView;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MenuPresenter implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(MenuPresenter.class);

    @Autowired
    @Qualifier("primaryStage")
    @Lazy
    private Stage primaryStage;
    @Autowired
    private ApplicationContext context;
    @FXML
    private Button module1;
    @FXML
    private Button module2;
    @FXML
    private Button module3;
    @Autowired
    private MainView module1View;
    @Autowired
    private EmptyView emptyView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initialize");
        module1.setOnAction(event -> enterModule1());
        module2.setOnAction(event -> enterModule2());
        primaryStage = (Stage) context.getBean("primaryStage");
        logger.debug("primary Stage: {}", primaryStage);
    }

    private void enterModule1() {
        logger.debug("entering module 1");
        changeScene(module1View.getView());
    }

    private void enterModule2() {
        logger.debug("entering module 2");
        changeScene(emptyView.getView());
    }

    private void changeScene(Parent page) {
        logger.debug("Parent: {}",page);
        Scene scene = primaryStage.getScene();
        if (scene == null) {
            scene = new Scene(page, 700, 450);
            primaryStage.setScene(scene);
        } else {
            primaryStage.getScene().setRoot(page);
        }
        primaryStage.sizeToScene();
    }
}
