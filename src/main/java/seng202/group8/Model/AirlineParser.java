package seng202.group8.Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sophie on 22/08/16.
 */
public class AirlineParser {

    /* Method to separate an input string based off of comma positioning */
    private ArrayList<String> splitByComma(String inputRoute) {
        /* Returns a new ArrayList, containing all of the string elements */
        return new ArrayList<String>(Arrays.asList(inputRoute.split(",")));
    }

    /* Method to parse a string to an integer with error handling */
    private int parseToInt(String inputNum) {
        try {
            return Integer.parseInt(inputNum);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /* Method to check whether airline has an alias */
    private void checkAlias(Airline airline, String alias) {
        if (alias.equals("\\N")) {
            airline.setAlias(null);
        } else {
            airline.setAlias(alias);
        }
    }

    /* Method to check whether an airline is currently active*/
    private void checkActive(Airline airline, String active) {
        if(active.equals("Y")) {
            airline.setActive(true);
        } else {
            airline.setActive(false);
        }
    }

    private Airline createParsedAirline(ArrayList<String> airlineInfo) {
        /* Create the Airline Object */
        Airline airline = new Airline();

        /* Sets the Airline ID*/
        airline.setAirlineID(parseToInt(airlineInfo.get(0)));

        /* Sets the name of the Airline */
        airline.setName(airlineInfo.get(1));

        /* Checks whether the airline has an alias. If it does, the alias is set to given string */
        checkAlias(airline, airlineInfo.get(2));

        /* Sets IATA value*/
        airline.setIATA(airlineInfo.get(3));

        /* Sets ICAO value*/
        airline.setICAO(airlineInfo.get(4));

        /* Sets Callsign */
        airline.setCallsign(airlineInfo.get(5));

        /* Sets Country */
        airline.setCountry(airlineInfo.get(6));

        /* Checks whether airline is active ("Y") or not ("N") */
        checkActive(airline, airlineInfo.get(7));

        return airline;
    }

    /* Public method which is used to create a single airline */
    public Airline createSingleAirline(String input) {
        ArrayList<String> airlineInfo = splitByComma(input);
        return createParsedAirline(airlineInfo);
    }
}
