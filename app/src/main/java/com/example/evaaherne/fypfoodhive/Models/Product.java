package com.example.evaaherne.fypfoodhive.Models;


/** PRODUCT OBJECT FOR PRODUCT DATA **/
public class Product {
    public String prodId;
    String userId;
    public String prodName;
    public String prodBBDate;
    public String prodCategory;
    public int expDay;


 //Constructors
    public Product(){

    }


    public Product(String prodId, String prodName, String prodBBDate, String prodCategory, int expDay) {
     //   this.userId = userId;
        this.prodId = prodId;
       this.prodName = prodName;
        this.prodBBDate = prodBBDate;
        this.prodCategory = prodCategory;
        this.expDay = expDay;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getExpDay() {
        return expDay;
    }

    public void setExpDay(int expDay) {
        this.expDay = expDay;
    }
}
