package pl.kuligowy.pocspringfx.person.wrapper;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.kuligowy.pocspringfx.person.details.DetailsView;
import pl.kuligowy.pocspringfx.person.table.PersonTableView;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class WrapperPresenter implements Initializable {

    @Autowired
    private DetailsView detailsView;
    @Autowired
    private PersonTableView personTableView;

    @FXML
    private AnchorPane left;
    @FXML
    private AnchorPane right;

    private static final Logger logger = LoggerFactory.getLogger(WrapperPresenter.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initialize");
        left.getChildren().add(detailsView.getView());
        right.getChildren().add(personTableView.getView());
    }
}
