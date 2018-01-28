package pl.kuligowy.pocspringfx.model.person;

import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import pl.kuligowy.pocspringfx.model.job.Job;

import java.io.Externalizable;
import java.time.LocalDate;

public class PersonDTO {

    private SimpleObjectProperty<Long> id;
    private StringProperty firstName;
    private StringProperty lastName;
    private ObjectProperty<LocalDate> birthday;
    private ObjectProperty<Job> job;
    private Person person;

    public PersonDTO(Person person) {
        this.firstName = new SimpleStringProperty(person.getFirstName());
        this.lastName = new SimpleStringProperty(person.getLastName());
        this.birthday = new SimpleObjectProperty<>(person.getBirthday());
        this.id = new SimpleObjectProperty<>(person.getId());
        this.job = new SimpleObjectProperty<>(person.getJob());
        this.person = person;
    }

    public Job getJob() {
        return job.get();
    }

    public ObjectProperty<Job> jobProperty() {
        return job;
    }

    public void setJob(Job job) {
        this.job.set(job);
    }

    public Long getId() {
        return id.get();
    }

    public SimpleObjectProperty<Long> idProperty() {
        return id;
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public Person getPerson() {
        person.setFirstName(this.getFirstName());
        person.setLastName(this.getLastName());
        person.setBirthday(this.getBirthday());
        person.setJob(this.getJob());
        return person;
    }
}
