package com.example.evaaherne.fypfoodhive.Models;

/**USER OBJECT FOR USER DATA**/

public class Users {

    //DECLARATIONS
    String userId;
    String userName;
    String userEmail;



    public Users(){
        //EMPTY CONSTRUCTOR USED FOR RETRIEVING DATA
    }

    public Users(String userId, String userName, String userEmail) {

        //CONSTRUCTORS
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;

    }


    // GETTERS & SETTERS
    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    }

