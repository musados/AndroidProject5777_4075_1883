package com.siduron.java.iTravel.Model.Entities;

/**
 * Created by musad on 19/01/2017.
 */


/**
 * This class describes the Business profile details.
 * ID, name etc.
 * @author Moshe Nahari & Haim Milikovski
 */
public class Business implements ITravelData {
    private int id;
    private int managerID;
    private String name;
    private String email;
    private String phone;
    private String website;
    private String address;


    private String description;

    /**
     * CTOR
     * Sets the bussiness details
     *
     * @param id      bussiness's ID
     * @param managerID0 the user who is managing the business
     * @param name0   bussiness's Name
     * @param email0
     * @param phone0  bussiness's phone
     * @param address0
     * @param website0
     * @param descrip bussiness's description - more info for client
     */
    public Business(int id, int managerID0, String name0, String email0, String phone0, String address0, String website0, String descrip) {
        this.id = id;
        managerID=managerID0;
        this.name = name0;
        email = email0;
        this.phone = phone0;
        address=address0;
        website=website0;
        this.description = descrip;
    }


    //Setters and Getters methods


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public Object[] getRowData() {
        return new Object[]
                {
                        getId(),
                        getManagerID(),
                        getName(),
                        getEmail(),
                        getPhone(),
                        getAddress(),
                        getWebsite(),
                        getDescription()
                };
    }
}
