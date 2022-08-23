package org.launchcode.theBridge2.models.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RegisterFormDTO extends LoginFormDTO{

    private String verifyPassword;

    @NotBlank(message="Must provide your First name.")
    @NotNull(message ="First name cannot be null")
    private String firstName;

    @NotBlank(message="Must provide your Last name.")
    @NotNull(message ="last name cannot be null")
    private String lastName;

    private String middleName;

    private String suffix;

    private int inmateNumber;

    @Email(message = "Email address must be a vaild email address")
    private String email;


    public String getVerifyPassword(){
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword){
        this.verifyPassword = verifyPassword;
    }

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getInmateNumber() {
        return inmateNumber;
    }

    public void setInmateNumber(int inmateNumber) {
        this.inmateNumber = inmateNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
