package pl.kuligowy.pocspringfx.module.nr1.details;

import io.ebean.EbeanServer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kuligowy.pocspringfx.model.job.Job;
import pl.kuligowy.pocspringfx.model.person.PersonDTO;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class DetailsPresenter implements Initializable {


    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private DatePicker dpBirthday;
    @FXML
    private ComboBox<Job> jobCombo;
    private Stage dialogStage;
    private PersonDTO person;
    private boolean handleOk = false;
    private static final Logger logger = LoggerFactory.getLogger(DetailsPresenter.class);
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @Autowired
    EbeanServer server;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initialize");
        okButton.setOnAction(this::handleOk);
        cancelButton.setOnAction(this::handleCancel);

        List<Job> jobList = server.find(Job.class).findList();
        jobCombo.setItems(FXCollections.observableArrayList(jobList));
        jobCombo.setConverter(new StringConverter<Job>() {
            @Override
            public String toString(Job object) {
                return object.getName();
            }

            @Override
            public Job fromString(String string) {
                return jobList.stream()
                        .filter(j -> j.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
        tfFirstName.setText(person.getFirstName());
        tfLastName.setText(person.getLastName());
        dpBirthday.setValue(person.getBirthday());
        jobCombo.getSelectionModel().select(person.getJob());
    }

    public void setStage(Stage stage) {
        this.dialogStage = stage;
    }

    private void handleOk(ActionEvent event) {
        this.person.setFirstName(tfFirstName.getText());
        this.person.setLastName(tfLastName.getText());
        this.person.setBirthday(dpBirthday.getValue());
        this.person.setJob(jobCombo.getSelectionModel().getSelectedItem());
        this.handleOk = true;
        this.dialogStage.close();
    }

    public boolean isOk() {

        return handleOk;
    }

    void handleCancel(ActionEvent actionEvent) {
        this.handleOk = false;
        this.dialogStage.close();
    }
}
