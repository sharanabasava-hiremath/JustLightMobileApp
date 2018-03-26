package com.apurva.assignment.justlightapp.Customer.Data;

/**
 * Created by divyankithaRaghavaUrs on 12/2/17.
 */

public class LoginInfo
{
    public String Username, Password;
    public int ID = 0;

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
