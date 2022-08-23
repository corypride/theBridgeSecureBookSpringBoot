package org.launchcode.theBridge2.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class RelationshipType extends AbstractEntity{

    @NotNull(message = "Relationship type cannot be null")
    @NotBlank(message = "Relationship type cannot be null")
    private String type;

    public RelationshipType(){}

    public RelationshipType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
