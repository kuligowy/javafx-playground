package pl.kuligowy.pocspringfx.person.table;

import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class PersonTablePresenter implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(PersonTablePresenter.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initialize");
    }
}
