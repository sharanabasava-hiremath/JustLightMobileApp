package com.apurva.assignment.justlightapp.Customer.Data;

/**
 * Created by divyankithaRaghavaUrs on 12/2/17.
 */

public class UserSignUpData
{
    public String username, password;
    public int ID = 0;

    public void setUsername(String uname)
    {
        this.username = uname;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setID(int id)
    {
        this.ID = id;
    }
}
