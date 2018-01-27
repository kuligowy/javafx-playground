package pl.kuligowy.pocspringfx.main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kuligowy.pocspringfx.menu.MenuPresenter;
import pl.kuligowy.pocspringfx.menu.MenuView;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainPresenter implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(MenuPresenter.class);

    @Autowired
    private MenuView menuView;

    @FXML
    private AnchorPane topPane;
    @FXML
    private AnchorPane bottomPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Im initalizing...");
        topPane.getChildren().add(menuView.getView());
    }
}
