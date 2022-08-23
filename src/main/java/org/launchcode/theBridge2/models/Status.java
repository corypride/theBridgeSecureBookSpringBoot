package org.launchcode.theBridge2.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Status extends AbstractEntity {
    @NotNull(message = "Status cannot be null")
    @NotBlank(message = "Status cannot be blank")
    private String statusName;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    @Size(max=255)
    private String description;

    private String statusImgUrl;

    @OneToMany(mappedBy = "status")
    private List<AssignmentStatus> assignmentStatuses;

    public Status(){}

    public Status(String statusName, String description,String statusImgUrl) {
        this.statusName = statusName;
        this.description = description;
        this.statusImgUrl = statusImgUrl;

    }

    public String getStatusName() {
        return statusName;
    }

    public String getDescription() {
        return description;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatusImgUrl() {
        return statusImgUrl;
    }

    public void setStatusImgUrl(String statusImgUrl) {
        this.statusImgUrl = statusImgUrl;
    }
}
