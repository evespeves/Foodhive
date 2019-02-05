package com.example.evaaherne.fypfoodhive;


import java.util.Calendar;
import java.util.Date;

/** PRODUCT OBJECT FOR PRODUCT DATA **/
public class Product {
    String prodId;
    String prodName;
    String prodBBDate;
    String prodCategory;

 //Constructors

    public Product(String prodId, String prodName, String prodBBDate, String prodCategory) {
        this.prodId = prodId;
       this.prodName = prodName;
        this.prodBBDate = prodBBDate;
        this.prodCategory = prodCategory;

    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdBBDate() {
        return prodBBDate;
    }

    public void setProdBBDate(String prodBBDate) {
        this.prodBBDate = prodBBDate;
    }

    public String getProdCategory() {
        return prodCategory;
    }

    public void setProdCategory(String prodCategory) {
        this.prodCategory = prodCategory;
    }
}
