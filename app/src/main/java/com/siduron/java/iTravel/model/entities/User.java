package com.siduron.java.iTravel.model.entities;

import android.util.Log;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by musad on 19/01/2017.
 */

public class User {
    //User account data
    private int id;
    private String username;
    private String password;

    //User personal details
    private String firstName = "";
    private String lastName = "";
    private Gender gender = Gender.UNKNOWN;
    private Date birthDay=new Date();

    //Extended personal user details
    private String phone = "";
    private String address = "";

    //public User(){}

    /**
     * "Full CTOR"
     * -takes all of the class parameters
     * -----------------------------------
     * @param id0 ID
     * @param uname Login Username
     * @param pass Login password
     * @param fname First name
     * @param lname Last name
     * @param gen User's Gender
     * @param birth User's age
     * @param phone0 User's phone number
     * @param address0 User's address
     */
    public User(int id0,String uname,String pass,
                String fname,String lname,Gender gen,Date birth,
                String phone0,String address0)
    {
        id=id0;
        username=uname;
        password=pass;

        firstName=fname;
        lastName=lname;
        gender=gen;
        birthDay=birth;
        phone=phone0;
        address=address0;

        Log.i("New username created: ",uname);
    }


    /**
     * CTOR
     * -takes the login details only
     * all other details will be reseted by their default values.
     * @param id0
     * @param uname
     * @param pass
     */
    public User(int id0,String uname,String pass)
    {
        id=id0;
        username=uname;
        password=pass;

        try {
            String temp = "1/01/1000";
            DateFormat format = new SimpleDateFormat("dd/mm/yyyy");

            birthDay = format.parse(temp);
        }
        catch (Exception e)
        {
            Log.i("New user date:","Field to set date.");
            birthDay=new Date();
        }

        Log.i("New user instance",uname);
    }


    //Set methods

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //Get methods

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
