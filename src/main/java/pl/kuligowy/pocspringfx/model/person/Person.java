package pl.kuligowy.pocspringfx.model.person;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Person {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}