package com.example.MySqlTester.adapters;

public class EditTextDataModel {

    private int adapterID;
    private String value;

    public EditTextDataModel(int adapterPosition, String value) {
        this.adapterID = adapterPosition;
        this.value = value;
    }

    public int getAdapterID() {
        return adapterID;
    }

    public void setAdapterID(int adapterID) {
        this.adapterID = adapterID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
