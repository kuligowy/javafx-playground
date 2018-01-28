package pl.kuligowy.pocspringfx.person;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kuligowy.pocspringfx.model.person.Person;
import pl.kuligowy.pocspringfx.model.person.PersonDTO;
import pl.kuligowy.pocspringfx.model.person.PersonRepository;
import pl.kuligowy.pocspringfx.person.details.DetailsPresenter;
import pl.kuligowy.pocspringfx.person.details.DetailsView;

import javax.xml.soap.Detail;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class PersonFullTablePresenter implements Initializable {

    @FXML
    private Label lId;
    @FXML
    private Label lFirstName;
    @FXML
    private Label lLastName;
    @FXML
    private Label lBirthday;
    @FXML
    private Label jobPosition;
    @FXML
    private TableView<PersonDTO> tableView;
    @FXML
    private TableColumn<PersonDTO, String> tcName;
    @FXML
    private TableColumn<PersonDTO, String> tcSurname;
    @FXML
    private TableColumn<PersonDTO, LocalDate> tcBirthday;
    @Autowired
    private PersonRepository repository;
    @FXML
    private Button delete;
    @FXML
    private Button add;
    @FXML
    private Button edit;
    @FXML
    private Button refreshList;
    @Autowired
    private DetailsView detailsView;
    private Scene dialogScene;
    private ObservableList<PersonDTO> personList;
    private static final Logger logger = LoggerFactory.getLogger(PersonFullTablePresenter.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<PersonDTO> dtos = repository.findAll().stream().map(PersonDTO::new).collect(Collectors.toList());
        personList = FXCollections.observableArrayList(dtos);
        logger.debug("Dtos: {}", dtos.size());
        logger.debug("Initialize");
        tcName.setCellValueFactory(cell -> cell.getValue().firstNameProperty());
        tcSurname.setCellValueFactory(cell -> cell.getValue().lastNameProperty());
        tcBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
//        tcBirthday.setCellFactory(cell -> cell.getValue().birthdayProperty());
        tableView.setItems(personList);

        tableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showDetails(newValue));
        tableView.getItems().addListener((ListChangeListener<PersonDTO>) c -> {
            while (c.next()) {
                logger.debug("Record was removed: {}", c.wasRemoved());
                logger.debug("Iterating:");
                int noOfRemovedItems = c.getRemovedSize();
                if (noOfRemovedItems == 1) {
                    PersonDTO personDTO = c.getRemoved().get(0);
                    repository.delete(personDTO.getId());
                }
                c.getRemoved().stream().forEach(obj -> logger.debug("{}", obj));
            }
        });
        delete.setOnAction(event -> deletePerson());
        add.setOnAction(this::addPerson);
//        edit.setOnAction(this::editPerson);
        refreshList.setOnAction(this::refresh);
    }

    private void addPerson(ActionEvent event) {
        PersonDTO personDTO = new PersonDTO();
        boolean isOk = showDetailsDialog(personDTO);
        if (isOk) {
            Person person = repository.save(personDTO.toPerson());
            personList.add(new PersonDTO(person));
        }
    }

    private void editPerson(ActionEvent event) {
        PersonDTO selectedPerson = tableView.getSelectionModel().getSelectedItem();
        boolean isOk = showDetailsDialog(selectedPerson);
        if (isOk) {
            repository.save(selectedPerson.toPerson());
        }
    }

    public boolean showDetailsDialog(PersonDTO person) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Person");
        dialogStage.initModality(Modality.WINDOW_MODAL);
//        dialogStage.initOwner(primaryStage);
        if (dialogScene == null) {
            dialogScene = new Scene(detailsView.getView());
        } else {
            dialogScene.setRoot(detailsView.getView());
        }
        dialogStage.setScene(dialogScene);

        // Set the person into the controller.
        DetailsPresenter controller = (DetailsPresenter) detailsView.getPresenter();
        controller.setStage(dialogStage);
        controller.setPerson(person);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
        return controller.isOk();
    }

    private void deletePerson() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        logger.debug("selected index {}", selectedIndex);
        tableView.getItems().remove(selectedIndex);
    }


    private void refresh(ActionEvent event) {
        logger.debug("Repo list size {}", repository.findAll().size());
    }

    private void showDetails(PersonDTO selectedPerson) {
        if (selectedPerson != null) {
            lFirstName.setText(selectedPerson.getFirstName());
            lLastName.setText(selectedPerson.getLastName());
            jobPosition.setText(selectedPerson.getJob() != null ? selectedPerson.getJob().getName() : null);
        } else {
            lFirstName.setText("");
            lLastName.setText("");
            jobPosition.setText("");
        }
    }

}
