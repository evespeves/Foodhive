package com.example.evaaherne.fypfoodhive;

/**USER OBJECT FOR USER DATA**/

public class Users {

    //DECLARATIONS
    String userId;
    String userName;
    String userEmail;
    String userNutValue;
    String userDairyValue;
    String userGlutenValue;


    public Users(){
        //EMPTY CONSTRUCTOR USED FOR RETRIEVING DATA
    }

    public Users(String userId, String userName, String userEmail, String userNutValue, String userDairyValue, String userGlutenValue) {

        //CONSTRUCTORS
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userNutValue = userNutValue;
        this.userDairyValue = userDairyValue;
        this.userGlutenValue = userGlutenValue;
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


    public String getUserNutValue() {
        return userNutValue;
    }

    public String getUserDairyValue() {
        return userDairyValue;
    }

    public String getUserGlutenValue() {
        return userGlutenValue;
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

    public void setUserNutValue(String userNutValue) {
        this.userNutValue = userNutValue;
    }

    public void setUserDairyValue(String userDairyValue) {
        this.userDairyValue = userDairyValue;
    }

    public void setUserGlutenValue(String userGlutenValue) {
        this.userGlutenValue = userGlutenValue;
    }
}
