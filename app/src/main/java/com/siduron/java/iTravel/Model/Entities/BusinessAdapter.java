package com.siduron.java.iTravel.Model.Entities;

import java.io.Serializable;

/**
 * Created by musad on 31/01/2017.
 */

public class BusinessAdapter implements ITravelData, Serializable {
    public int id=0;
    public int userID = 0;
    public int businessID = 0;

    public BusinessAdapter(int id0,int userId0,int businessId0)
    {
        id=id0;
        userID=userId0;
        businessID=businessId0;
    }

    @Override
    public Object[] getRowData() {
        return new Object[]
                {
                        id,
                        userID,
                        businessID
                };
    }
}
