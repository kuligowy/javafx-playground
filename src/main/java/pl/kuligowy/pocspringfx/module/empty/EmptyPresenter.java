package pl.kuligowy.pocspringfx.module.empty;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class EmptyPresenter implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(EmptyPresenter.class);

    @FXML
    private AnchorPane pane;
    @FXML
    private ProgressBar pb;
    @FXML
    private ProgressIndicator pi;
    @FXML
    private Label info;
    @FXML
    private Button startButton;
    @FXML
    private Button clearButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initialize");
        Task task = new Task<Void>() {
            @Override
            public Void call() {
                int max = 1000000;

                updateProgress(0, max);
                for (int i = 1; i <= max; i++) {
                    if (isCancelled()) {
                        break;
                    }
                    updateProgress(i, max);
                }
                return null;
            }
        };
        pb.progressProperty().bind(task.progressProperty());
        pi.progressProperty().bind(task.progressProperty());
        startButton.setOnAction(e -> new Thread(task).start());
    }
}
