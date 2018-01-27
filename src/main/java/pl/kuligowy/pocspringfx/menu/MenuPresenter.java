package pl.kuligowy.pocspringfx.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MenuPresenter implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(MenuPresenter.class);

    @FXML
    private Button module1;
    @FXML
    private Button module2;
    @FXML
    private Button module3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initialize");
        module1.setOnAction(event -> enterModule1(event));
    }

    private void enterModule1(ActionEvent event) {
        logger.debug("I'm in module 1");
    }

}
