package pl.kuligowy.pocspringfx.menu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.kuligowy.pocspringfx.PocSpringfxApplication;
import pl.kuligowy.pocspringfx.module.empty.EmptyView;
import pl.kuligowy.pocspringfx.module.nr1.table.PersonFullTableView;

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
    private PersonFullTableView personTable;
    @Autowired
    private EmptyView emptyView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initialize");
        module1.setOnAction(event -> enterModule1());
        module2.setOnAction(event -> enterModule2());
        primaryStage = (Stage) context.getBean("primaryStage");
        logger.debug("primary Stage: {}", primaryStage);
        KeyCombination keyCombination1 = new KeyCodeCombination(KeyCode.NUMPAD1, KeyCombination.SHIFT_ANY);
        KeyCombination keyCombination2 = new KeyCodeCombination(KeyCode.NUMPAD2, KeyCombination.SHIFT_ANY);
        Mnemonic mnemonic1 = new Mnemonic(module1, keyCombination1);
        Mnemonic mnemonic2 = new Mnemonic(module2, keyCombination2);
    }

    private void enterModule1() {
        changeScene(emptyView.getView());
        logger.debug("entering module 1");
    }

    private void enterModule2() {
        logger.debug("entering module 2");
        changeScene(personTable.getView());
    }

    private void changeScene(Parent page) {
        PocSpringfxApplication.getRoot().setCenter(page);
    }
}
