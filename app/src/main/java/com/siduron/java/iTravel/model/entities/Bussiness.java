package com.siduron.java.iTravel.model.entities;

/**
 * Created by musad on 19/01/2017.
 */


/**
 * This class describes the Bussiness profile details.
 * ID, name etc.
 * @author Moshe Nahari & Haim Milikovski
 */
public class Bussiness implements ITravelData {
    private int id;
    private String name;
    private String phone;
    private String description;

    /**
     * CTOR
     * Sets the bussiness details
     *
     * @param id bussiness's ID
     * @param name0 bussiness's Name
     * @param phone0 bussiness's phone
     * @param descrip bussiness's description - more info for client
     */
    public Bussiness(int id,String name0,String phone0,String descrip)
    {
        this.id=id;
        this.name=name0;
        this.phone=phone0;
        this.description=descrip;
    }


    //Setters and Getters methods


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    @Override
    public Object[] getRowData() {
        return new Object[]
                {
                        getId(),
                        getName(),
                        getPhone(),
                        getDescription()
                };
    }
}
