package com.siduron.java.iTravel.model.backend;

import com.siduron.java.iTravel.model.datasource.DBS_BackEnd;
import com.siduron.java.iTravel.model.datasource.List_BackEnd;
import com.siduron.java.iTravel.model.entities.DBType;

/**
 * Created by musad on 20/01/2017.
 */

public class BackendFactor {

    private static IBackEnd manager=null;

    public static IBackEnd getIBackend(DBType type)
    {
        switch (type)
        {
            case LIST:
                manager= List_BackEnd.getInstance();
                break;
            case WEB_DB:
                manager= DBS_BackEnd.getInstance();
                break;
            default:
                manager= DBS_BackEnd.getInstance();
                break;
        }
        return manager;
    }

}
