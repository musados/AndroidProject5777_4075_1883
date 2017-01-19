package com.siduron.java.iTravel.model.entities;

/**
 * Created by musad on 19/01/2017.
 */

public class User {
    //User account data
    private String id;
    private String username;
    private String password;

    //User personal details
    private String firstName = "";
    private String lastName = "";
    private Gender gender = Gender.UNKNOWN;
    private float age = -1;

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
     * @param age0 User's age
     * @param phone0 User's phone number
     * @param address0 User's address
     */
    public User(String id0,String uname,String pass,
                String fname,String lname,Gender gen,float age0,
                String phone0,String address0)
    {
        id=id0;
        username=uname;
        password=pass;

        firstName=fname;
        lastName=lname;
        gender=gen;
        age=age0;
        phone=phone0;
        address=address0;
    }


    /**
     * CTOR
     * -takes the login details only
     * @param id0
     * @param uname
     * @param pass
     */
    public User(String id0,String uname,String pass){}

}
