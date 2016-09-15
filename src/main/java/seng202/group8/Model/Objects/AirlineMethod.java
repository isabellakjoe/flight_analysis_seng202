package seng202.group8.Model.Objects;

import seng202.group8.Model.Objects.Airline;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Callum on 25/08/16.
 * Class that contains methods for error handling and checking airline objects for parsing.
 */
public abstract class AirlineMethod {

    /**
     * Method to separate an input string based off of comma positioning and eliminate any extra white space and quote marks
     * @param inputAirport
     * @return Returns a new ArrayList, containing all of the refactored string elements refactoredData
     * @return null if the input string had too many or not enough elements
     */
    public ArrayList<String> refactorData(String inputAirport) {

        ArrayList<String> splitData = new ArrayList<String>(Arrays.asList(inputAirport.split(",")));
        int size = splitData.size();
        ArrayList<String> refactoredData = new ArrayList<String>();
        if (size == 8) {
            for (int i = 0; i < size; i++) {
                refactoredData.add(splitData.get(i).trim().replace("\"", ""));
            }
            return refactoredData;
        } else{
            return null;
        }
    }

    /**
     * Method to parse a string to an integer with error handling
     * @param inputNum
     * @return string integer parsed to an Integer
     * @return -1 for NumberFormatException
     */
    public int parseToInt(String inputNum) {
        try {
            return Integer.parseInt(inputNum);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Method to check whether airline has an alias
     * @param airline
     * @param alias (null if no value available)
     */
    public void checkAlias(Airline airline, String alias) {
        if (alias.equals("\\N")) {
            airline.setAlias(null);
        } else {
            airline.setAlias(alias);
        }
    }

    /**
     * Method to check whether an airline is currently active
     * @param airline
     * @param active
     */
    public void checkActive(Airline airline, String active) {
        if(active.equals("Y")) {
            airline.setActive(true);
        } else {
            airline.setActive(false);
        }
    }

}
