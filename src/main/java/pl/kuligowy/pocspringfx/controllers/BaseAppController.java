package pl.kuligowy.pocspringfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

@Component
public class BaseAppController {

    @FXML
    private Label firstName;

    @FXML
    public void initialize() {
        firstName.setText("aaaaaaaaaaaa");
    }
}
