package pl.kuligowy.pocspringfx.module.empty;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kuligowy.pocspringfx.menu.MenuView;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class EmptyPresenter implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(EmptyPresenter.class);

    @FXML
    private StackPane top;
    @Autowired
    private MenuView menuView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initialize");
        top.getChildren().add(menuView.getView());
    }
}
