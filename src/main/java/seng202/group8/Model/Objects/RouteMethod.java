package seng202.group8.Model.Objects;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Callum on 25/08/16.
 * Class that contains methods for error handling and checking Route objects for parsing.
 */
public abstract class RouteMethod {

    /**
     * Method to check whether element in data is "\\N".
     *
     * @param inputString
     * @return inputString, changes it to null if given String is null
     */
    public String checkNull(String inputString) {
        if (inputString.equals("\\N")) {
            return null;
        } else {
            return inputString;
        }
    }

    /**
     * Method to separate an input string based off of comma positioning and eliminate any extra white space and quote marks
     *
     * @param inputAirport
     * @return refactoredData, a new arraylist containing all of the refactored string elements, null if  input string
     * has too many or not enough elements.
     */
    public ArrayList<String> refactorData(String inputAirport) {

        ArrayList<String> splitData = new ArrayList<String>(Arrays.asList(inputAirport.split(",")));
        int size = splitData.size();
        ArrayList<String> refactoredData = new ArrayList<String>();
        if (size == 9) {
            for (int i = 0; i < size; i++) {
                String checkedString = checkNull(splitData.get(i));
                if (checkedString != null) {
                    refactoredData.add(splitData.get(i).trim().replace("\"", ""));
                } else {
                    refactoredData.add("");
                }
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
     * Temporary method to ToJSONArray an airline for a route. In future we will need to search database for airlines already
     * in database to ToJSONArray route with.
     *
     * @param airlineName
     * @param airlineID
     * @return airline object.
     */
    public Airline createAirline(String airlineName, int airlineID) {
        Airline airline = new Airline();
        airline.setName(airlineName);
        airline.setAirlineID(airlineID);
        return airline;
    }

    /**
     * Temporary method to ToJSONArray an airport for a route. In future we will need to search database for airports already
     * in database to ToJSONArray route with.
     *
     * @param airportCode
     * @param airportID
     * @return airport object.
     */
    public Airport createAirport(String airportCode, int airportID) {
        Airport airport = new Airport();

        if (airportCode.length() == 3) {
            airport.setIATA(airportCode);
        } else if (airportCode.length() == 4) {
            airport.setICAO(airportCode);
        }

        airport.setAirportID(airportID);
        return airport;
    }

    /**
     * Method to check whether a route is codeshared. Sets codeshare to to true, and codeshareString to "Yes" if
     * input string equal to Y, otherwise sets codeshare to false, and codeshareString to "No".
     *
     * @param route
     * @param codeshare
     */
    public void checkCodeshared(Route route, String codeshare) {
        if (codeshare.equals("Y")) {
            route.setCodeshare(true);
            route.setCodeshareString("Yes");
        } else {
            route.setCodeshare(false);
            route.setCodeshareString("No");
        }
    }


}
