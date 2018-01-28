package pl.kuligowy.pocspringfx.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kuligowy.pocspringfx.main.MainPresenter;
import pl.kuligowy.pocspringfx.person.details.DetailsView;
import pl.kuligowy.pocspringfx.person.wrapper.WrapperPresenter;
import pl.kuligowy.pocspringfx.person.wrapper.WrapperView;
import pl.kuligowy.pocspringfx.views.FXMLView;

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
    @Autowired
    private MainPresenter mainPresenter;
    @Autowired
    private WrapperView wrapperView;
    @Autowired
    private DetailsView detailsView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initialize");
        module1.setOnAction(event -> enterModule1(wrapperView));
        module2.setOnAction(event -> enterModule1(detailsView));
    }

    private void enterModule1(FXMLView view) {
        logger.debug("Switching to vies {]",view);
        mainPresenter.changeBottomView(view);
    }

}
