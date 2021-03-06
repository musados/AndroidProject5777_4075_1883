package com.siduron.java.iTravel.Model.Entities;

/**
 * Created by musad on 19/01/2017.
 */

/**
 * Enum that sets the users gender
 *
 * @author Moshe Nahari & Haim Milikovski
 */
public enum Gender {
    UNKNOWN,  //Default index = 0
    MALE,
    FEMALE;

    public static Gender fromInt(int value) {
        switch (value) {
            case 0:
                return UNKNOWN;
            case 1:
                return MALE;
            case 2:
                return FEMALE;
            default:
                return UNKNOWN;
        }
    }

    public static Gender fromString(String key) {
        switch (key)
        {
            case "MALE":
                return MALE;
            case "FEMALE":
                return FEMALE;
            default:
                return UNKNOWN;
        }
    }
}
