package pl.kuligowy.pocspringfx.module.nr1.table;

import io.ebean.EbeanServer;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kuligowy.pocspringfx.model.person.Person;
import pl.kuligowy.pocspringfx.model.person.PersonDTO;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import pl.kuligowy.pocspringfx.module.nr1.details.*;

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
    @Autowired
    EbeanServer server;
    private static final Logger logger = LoggerFactory.getLogger(PersonFullTablePresenter.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Person> list = server.find(Person.class).findList();
        List<PersonDTO> dtos = list.stream().map(PersonDTO::new).collect(Collectors.toList());
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
                    server.delete(personDTO.getPerson());
                }
                c.getRemoved().stream().forEach(obj -> logger.debug("{}", obj));
            }
        });
        delete.setOnAction(event -> deletePerson());
        add.setOnAction(this::addPerson);
        edit.setOnAction(this::editPerson);
        refreshList.setOnAction(this::refresh);
    }

    private void addPerson(ActionEvent event) {
        PersonDTO personDTO = new PersonDTO(new Person());
        boolean isOk = showDetailsDialog(personDTO);
        if (isOk) {
            server.save(personDTO.getPerson());
            personList.add(personDTO);
        }
    }

    private void editPerson(ActionEvent event) {
        PersonDTO selectedPerson = tableView.getSelectionModel().getSelectedItem();
        boolean isOk = showDetailsDialog(selectedPerson);
        if (isOk) {
            Person person = selectedPerson.getPerson();
            logger.debug("Saving person: {}", person);
            server.save(person);
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
        List<Person> list = server.find(Person.class).findList();
        logger.debug("Repo list size {}", list.size());
        list.forEach(p -> logger.debug("person: {}", p));
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
