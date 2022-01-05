package com.LogicBuild.collegekendra;

public class databas {
    String amounts;
    String amtreasons;
    String idnumb;

    public databas(){

    }

    public databas(String amounts, String amtreasons, String idnumb) {
        this.amounts = amounts;
        this.amtreasons = amtreasons;
        this.idnumb = idnumb;


    }

    public String getAmounts() {

        return amounts;
    }


    public String getAmtreasons() {
        return amtreasons;
    }

    public String getIdnumb() {
        return idnumb;
    }

}