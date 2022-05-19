package com.example.mmustpark;

public class User {

    String FullName;
    String Plateno;
    String EmailAddress;
    String Phone;

    public User() {

    }

    public User(String fullName, String plateno, String emailAddress, String phone) {
        FullName = fullName;
        Plateno = plateno;
        EmailAddress = emailAddress;
        Phone = phone;

    }


    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPlateno() {
        return Plateno;
    }

    public void setPlateno(String plateno) {
        Plateno = plateno;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

}
