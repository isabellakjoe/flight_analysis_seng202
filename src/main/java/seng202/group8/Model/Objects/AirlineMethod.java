package seng202.group8.Model.Objects;

import seng202.group8.Model.Objects.Airline;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Callum on 25/08/16.
 */
public abstract class AirlineMethod {

    /* Method to separate an input string based off of comma positioning */
    public ArrayList<String> splitByComma(String inputRoute) {
        /* Returns a new ArrayList, containing all of the string elements */
        return new ArrayList<String>(Arrays.asList(inputRoute.split(",")));
    }

    /* Method to parse a string to an integer with error handling */
    public int parseToInt(String inputNum) {
        try {
            return Integer.parseInt(inputNum);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /* Method to check whether airline has an alias */
    public void checkAlias(Airline airline, String alias) {
        if (alias.equals("\\N")) {
            airline.setAlias(null);
        } else {
            airline.setAlias(alias);
        }
    }

    /* Method to check whether an airline is currently active*/
    public void checkActive(Airline airline, String active) {
        if(active.equals("Y")) {
            airline.setActive(true);
        } else {
            airline.setActive(false);
        }
    }

}
