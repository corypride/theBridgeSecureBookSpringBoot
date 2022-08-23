package org.launchcode.theBridge2.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class ReportName extends AbstractEntity{

    @NotNull
    private String rptName;
    @NotNull
    private String displayName;

    private int navPg2Order;

    public ReportName(){}

    public ReportName( String rptName,String displayName) {
        this.rptName = rptName;
        this.displayName = displayName;
    }

    public String getRptName() {
        return rptName;
    }

    public void setRptName(String rptName) {
        this.rptName = rptName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getNavPg2Order() {
        return navPg2Order;
    }

    public void setNavPg2Order(int navPg2Order) {
        this.navPg2Order = navPg2Order;
    }

}
