package org.launchcode.theBridge2.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Relationships  extends AbstractEntity{

    @NotNull(message = "Student Id cannot be null")
    @NotBlank(message = "Student Id cannot be blank")
    private int bstid;

    @NotNull(message = "User Id cannot be null")
    @NotBlank(message = "User Id cannot be blank")
    private int utid;

    @NotNull(message = "Relationship Type Id cannot be null")
    @NotBlank(message = "Relationship Type Id cannot be blank")
    private int rtid;

    @Size(max = 200, message = "Max description size is 200 characters")
    private String relationshipDesc;

    private Relationships(){}

    public Relationships(int bstid, int utid, int rtid, String relationshipDesc){
        this.bstid = bstid;
        this.utid = utid;
        this.rtid = rtid;
        this.relationshipDesc = relationshipDesc;
    }

    public int getBstid() {
        return bstid;
    }

    public void setBstid(int bstid) {
        this.bstid = bstid;
    }

    public int getUtid() {
        return utid;
    }

    public void setUtid(int utid) {
        this.utid = utid;
    }

    public int getRtid() {
        return rtid;
    }

    public void setRtid(int rtid) {
        this.rtid = rtid;
    }

    public String getRelationshipDesc() {
        return relationshipDesc;
    }

    public void setRelationshipDesc(String relationshipDesc) {
        this.relationshipDesc = relationshipDesc;
    }
}
