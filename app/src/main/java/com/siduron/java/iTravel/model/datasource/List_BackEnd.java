package com.siduron.java.iTravel.model.datasource;

import com.siduron.java.iTravel.model.backend.IBackEnd;

/**
 * Created by musad on 17/01/2017.
 */

public class List_BackEnd implements IBackEnd {

    static List_BackEnd Instance = new List_BackEnd();


    /**
     * Singleton provider - get method
     *
     * @return "Instace" of the class  - List_BackEnd class
     * @author Moshe Nahari & Haim Milikovski
     */
    public static List_BackEnd getInstance(){ return Instance;}

    /**
     * Default CTOR (private)
     * Because the class have a Singleton instance
     */
    private List_BackEnd()
    {}

}
