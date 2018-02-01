package pl.kuligowy.pocspringfx.module.nr1;

import io.ebean.EbeanServer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kuligowy.pocspringfx.menu.MenuPresenter;
import pl.kuligowy.pocspringfx.menu.MenuView;
import pl.kuligowy.pocspringfx.model.person.Person;
import pl.kuligowy.pocspringfx.module.nr1.table.PersonFullTableView;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class MainPresenter implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(MenuPresenter.class);

    /**
     * Main layout
     */
    @FXML
    private StackPane stack;
    /**
     * This View components
     */
    @Autowired
    private PersonFullTableView fullTableView;

    @Autowired
    EbeanServer server;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initalizing");
        //toolbar.getChildren().add(menuView.getView());
        stack.getChildren().add(fullTableView.getView());
//        toolbar.getChildren().add(menuView.getView());
        Person p = new Person();
        p.setFirstName("Johny");
        p.setLastName("Cage");
        logger.debug("saving Person {}", p);
        server.save(p);
        List<Person> list = server.find(Person.class).findList();
        logger.debug("found list {}, size{}", list, list.size());
        Optional<Person> optPerson = list.stream().filter(o -> o.getFirstName().equals("Johny")).findFirst();
        logger.debug("Found: {}", optPerson);
        Person op = optPerson.get();
        op.setBirthday(LocalDate.now());
        logger.debug("trying to save: {} ", op);
        server.save(op);
        List<Person> fl = server.find(Person.class).findList();
        logger.debug("found list {}, size{}", fl, fl.size());
    }
}
