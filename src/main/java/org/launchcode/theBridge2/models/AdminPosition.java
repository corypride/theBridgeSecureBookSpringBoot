package org.launchcode.theBridge2.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AdminPosition extends AbstractEntity{

    @NotBlank(message="Admin position cannot be blank")
    @NotNull(message="Admin position cannot be null")
    private String position;


    @Valid
    @OneToMany(mappedBy = "adminPosition")
    private final List<Administrator> administrators = new ArrayList<>();

    public AdminPosition(String position) {
        this.position = position;
    }

    public AdminPosition() {}

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
