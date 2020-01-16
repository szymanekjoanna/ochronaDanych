package com.od.cryptography.validation_module.model;

public class ValidationModel {

    private String pesel;
    private String luhnaValue;
    private String crcSum;
    private String crcDivider;

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getLuhnaValue() {
        return luhnaValue;
    }

    public void setLuhnaValue(String luhnaValue) {
        this.luhnaValue = luhnaValue;
    }

    public String getCrcSum() {
        return crcSum;
    }

    public void setCrcSum(String crcSum) {
        this.crcSum = crcSum;
    }

    public String getCrcDivider() {
        return crcDivider;
    }

    public void setCrcDivider(String crcDivider) {
        this.crcDivider = crcDivider;
    }
}
