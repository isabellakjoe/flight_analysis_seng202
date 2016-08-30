package seng202.group8.Model.Objects;

import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Callum on 25/08/16.
 */
public abstract class RouteMethod {

    /* Method to check whether element in data is null */
    public String checkNull(String inputString) {
        if (inputString.equals("\\N")) {
            return null;
        } else {
            return inputString;
        }
    }

    /* Method to separate an input string based off of comma positioning and eliminate any extra white space and quote marks */
    public ArrayList<String> refactorData(String inputAirport) {

        ArrayList<String> splitData = new ArrayList<String>(Arrays.asList(inputAirport.split(",")));
        int size = splitData.size();
        ArrayList<String> refactoredData = new ArrayList<String>();
        if (size == 9) {
            for (int i = 0; i < size; i++) {
                String checkedString = checkNull(splitData.get(i));
                if(checkedString != null) {
                    refactoredData.add(splitData.get(i).trim().replace("\"", ""));
                } else{
                    refactoredData.add("");
                }
            }
            /* Returns a new ArrayList, containing all of the refactored string elements */
            return refactoredData;
        } else{
            /* Returns null if the input string had too many or not enough elements */
            return null;
        }
    }

    /* Method to parse a string to an integer with error handling */
    public int parseToInt(String inputNum) {
        try {
            return Integer.parseInt(inputNum);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /* Temporary method to create an airline for a route */
    public Airline createAirline(String airlineName, int airlineID) {
        Airline airline = new Airline();
        airline.setName(airlineName);
        airline.setAirlineID(airlineID);
        return airline;
    }

    /* Temporary method to create an airport for a route */
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

    /* Method to check whether a route is codeshared */
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
