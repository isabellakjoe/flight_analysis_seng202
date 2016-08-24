package seng202.group8.Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ikj11 on 22/08/16.
 */
public class AirportParser {
    /* Split the given input by commas' */
    private ArrayList<String> splitByComma(String inputAirport){
        /*Return a new ArrayList of all of the string elements*/
        return new ArrayList<String>(Arrays.asList(inputAirport.split(",")));
    }

    /* Method to parse a string to an integer with error handaling*/
    private int parseToInt(String inputNum){
        try{
            return Integer.parseInt(inputNum);
        } catch (NumberFormatException e){
            return -1;
        }
    }

    /* Method to parse a string to a double with error handling*/
    private double parseToDouble(String inputDouble) {
        try {
            return Double.parseDouble(inputDouble);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /* Method to check whether the airport code is IATA or FAA */
    private void checkCodeType(Airport airport, String airportCode, String country){
       /* Airports located in the US have an FAA code. All others have an IATA code. */
        if(country.equals("United States")) {
            airport.setFAA(airportCode);
        } else {
            airport.setIATA(airportCode);
        }
    }

    /* Method to check whether the given code is null. If so, change to null. Return airportCode */
    private String checkNull(String airportCode){

        if(airportCode.equals("\\N") || airportCode.equals("")) {
            airportCode = null;
        }
        return airportCode;
    }

    private Airport createParsedAirport(ArrayList<String> airportInfo){
        /* Created new airport object */
        Airport airport = new Airport();

        /* Sets the airport ID */
        airport.setAirportID(parseToInt(airportInfo.get(0)));

        /* Sets the name of the airport */
        airport.setName(airportInfo.get(1));

        /* Sets the city that the airport is in.*/
        airport.setCity(airportInfo.get(2));

        /* Sets the country/territory that the airport is in */
        airport.setCountry(airportInfo.get(3));

        /* Call checkCodeType method to see if the airport code is FAA or IATA
        * Parameters are airport, airportCode and country
        * airportCode is first checked to see if null using checkNull method */
        checkCodeType(airport, checkNull(airportInfo.get(4)), airportInfo.get(3));

        /* Calls checkNull method. Parameters airport and airport ICAO code. Sets ICAO code */
        airport.setICAO(checkNull(airportInfo.get(5)));

        /* Sets the latitude of the airport. Calls parse to float */
        airport.setLatitude(parseToDouble(airportInfo.get(6)));

        /* Sets the longitude of the airport. Calls parse to float */
        airport.setLongitude(parseToDouble(airportInfo.get(7)));

        /* Sets the altitude (In feet) */
        airport.setAltitude(parseToInt(airportInfo.get(8)));

        /* Sets the timezone. Hours offset from UTC */
        airport.setTimezone(parseToInt(airportInfo.get(9)));

        /* Sets the Daylight Savings Time. Calls method to change string into char*/
        airport.setDST(airportInfo.get(10).charAt(0));

        /* Sets the Olson Timezone */
        airport.setOlsonTimezone(airportInfo.get(11));

        return airport;
    }

    /* Public method which is used to create a single airport */
    public Airport createAirport(String input){
        ArrayList<String> airportInfo = splitByComma(input);
        return createParsedAirport(airportInfo);
    }
}
