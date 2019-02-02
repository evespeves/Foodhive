package com.example.evaaherne.fypfoodhive;


import java.util.Calendar;
import java.util.Date;

/** PRODUCT OBJECT FOR PRODUCT DATA **/
public class Product {

    String ProdName;
    int ProdBBDate;
    String ProdCategory;

 //Constructors

    public Product(String prodName, int prodBBDate, String ProdCategory) {
        ProdName = prodName;
        ProdBBDate = prodBBDate;
        ProdCategory = ProdCategory;

    }

    //Getters and setters
    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String prodName) {
        ProdName = prodName;
    }

    public int getProdBBDate() {
        return ProdBBDate;
    }

    public void setProdBBDate(int prodBBDate) {
        ProdBBDate = prodBBDate;
    }

    public String getProdCategory() {
        return ProdCategory;
    }

    public void setProdCategory(String prodCategory) {
        ProdCategory = prodCategory;
    }


}
