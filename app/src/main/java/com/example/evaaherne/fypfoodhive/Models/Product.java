package com.example.evaaherne.fypfoodhive.Models;


/** PRODUCT OBJECT FOR PRODUCT DATA **/
public class Product {
    public String prodId;
    public String prodName;

    public String prodBBDate;
    public String prodCategory;
    public String prodDesc;
    public String qrInfo;
    public int expDay;


 //Constructors
    public Product(){

    }


    public Product(String prodId, String prodName, String prodBBDate, String prodCategory, String prodDesc, int expDay, String qrInfo) {

        this.prodId = prodId;
        this.prodName = prodName;

        this.prodBBDate = prodBBDate;
        this.prodCategory = prodCategory;
        this.prodDesc = prodDesc;
        this.expDay = expDay;
        this.qrInfo = qrInfo;

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


    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public String getQrInfo() {
        return qrInfo;
    }

    public void setQrInfo(String qrInfo) {
        this.qrInfo = qrInfo;
    }
}
