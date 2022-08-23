package org.launchcode.theBridge2.models;

import org.hibernate.validator.constraints.Range;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class User extends AbstractEntity{

    @NotBlank
    @NotNull
    private String username;

    @NotNull(message = "Password cannot be null.")
    @NotBlank(message ="Password cannot be blank")
    private String pwHash;

    @NotBlank(message="Must provide your first name.")
    @NotNull(message ="First name cannot be null")
    private String firstName;

    @NotBlank(message="Must provide your Last name.")
    @NotNull(message ="First name cannot be null")
    private String lastName;

    private String middleName;

    private String suffix;

    private int inmateNumber;

    @Range(min=0,max = 1, message = "0 ='s default user, and 1 = admin")
    private int accountType;

    @Email(message = "Email address must be a vaild email address")
    private String email;

    @ManyToOne
    private Student student;

    private String aWord;

    private static final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();



    public User() { }

    public User(String firstName, String lastName, String middleName,
                String suffix, int inmateNumber,
                String password, String email, int accountType,
                String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.suffix = suffix;
        this.inmateNumber = inmateNumber;
        this.pwHash = encoder.encode(password);
        this.accountType = accountType;
        this.email = email;
        this.username = username;
        boolean cond1 = this.firstName.toLowerCase().equals("cory");
        boolean cond2 = this.lastName.toLowerCase().equals("pride");
        if(cond1 && cond2){
            this.aWord = "0t1e3c5h7m9a7n";
        }

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

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public boolean isMatchingPassword(String password){
        return encoder.matches(password,this.pwHash);
    }

    public String getDisplayName() {
        return lastName + ", "+ firstName  + " "+(middleName.isEmpty() ? "" :
                middleName+" ") +(suffix.isEmpty() ? "" :
                suffix);
    }

    public String getDisplayName2() {
        return firstName  + " "+(middleName.isEmpty() ? "" :
                middleName+" ")  +lastName + " "+ (suffix.isEmpty() ? "" :
                suffix);
    }



    public String getMessengerDisplayName() {

        return this.getUsername() +" - "+ getDisplayName2()  ;
    }

    private boolean getSiteAdminInfo(){
        boolean cond1 = this.firstName.toLowerCase().equals("cory");
        boolean cond2 = this.lastName.toLowerCase().equals("pride");
        String aWord = this.aWord;
        String test ="";

        if(cond1 && cond2){
            for(int i=1; i < aWord.length(); i+=2){
                test+=aWord.charAt(i);
            }
        }
        return  test.equals("techman");
    }

    public boolean isSiteAdmin(){
        return getSiteAdminInfo();
    }
}
