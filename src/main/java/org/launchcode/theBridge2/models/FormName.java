package org.launchcode.theBridge2.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class FormName extends AbstractEntity{

    @NotNull
    private String formNm;
    @NotNull
    private String displayName;

    private int navPg2Order;

    public FormName(){}

    public FormName(String formNm, String displayName,
                    int navPg2Order) {
        this.formNm = formNm;
        this.displayName = displayName;
        this.navPg2Order = navPg2Order;
    }


    public String getFormNm() {
        return formNm;
    }

    public void setFormNm(String formNm) {
        this.formNm = formNm;
    }

    public int getNavPg2Order() {
        return navPg2Order;
    }

    public void setNavPg2Order(int navPg2Order) {
        this.navPg2Order = navPg2Order;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
