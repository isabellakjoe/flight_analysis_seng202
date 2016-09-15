package seng202.group8.Model.Objects;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Callum on 26/08/16.
 * Class that contains methods for error handling and checking airline objects for parsing.
 */

public abstract class AirportMethod {

    /**
     * Method to separate an input string based off of comma positioning and eliminate any extra white space and quote
     * marks.
     *
     * @param inputAirport (Airport) to be refactored.
     * @return Returns a new ArrayList,refactoredData, containing all of the refactored string elements or null if the
     * input string had too many or not enough elements.
     */
    public ArrayList<String> refactorData(String inputAirport) {

        ArrayList<String> splitData = new ArrayList<String>(Arrays.asList(inputAirport.split(",")));
        int size = splitData.size();
        ArrayList<String> refactoredData = new ArrayList<String>();
        if (size == 12) {
            for (int i = 0; i < size; i++) {
                refactoredData.add(splitData.get(i).trim().replace("\"", ""));

            }
            return refactoredData;
        } else {
            return null;
        }
    }


    /**
     * Method to parse a string to an integer with error handling
     *
     * @param inputNum
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
     * Method to parse a string to a double with error handling
     *
     * @param inputDouble
     * @return -1 for NumberFormatException
     */
    public double parseToDouble(String inputDouble) {
        try {
            return Double.parseDouble(inputDouble);
        } catch (NumberFormatException e) {
            return -1;
        }
    }


    /**
     * Method to check whether the airport code is IATA or FAA.
     * Airports located in the US have an FAA code. All others have an IATA code.
     *
     * @param airport
     * @param airportCode
     * @param country
     */
    public void checkCodeType(Airport airport, String airportCode, String country) {
        if (country.equals("United States")) {
            airport.setFAA(airportCode);
        } else {
            airport.setIATA(airportCode);
        }
    }


    /**
     * Method to check whether the airport code is the string "\\N".
     *
     * @param airportCode
     * @return airportCode, changes it to null if given code is null
     */
    /* Method to check whether the given code is null. If so, change to null. Return airportCode */
    public String checkNull(String airportCode) {

        if (airportCode.equals("\\N") || airportCode.equals("")) {
            airportCode = null;
        }
        return airportCode;
    }


}


