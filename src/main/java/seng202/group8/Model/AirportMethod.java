package seng202.group8.Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Callum on 26/08/16.
 */
public abstract class AirportMethod {

    /* Split the given input by commas' */
    public ArrayList<String> splitByComma(String inputAirport){
        /*Return a new ArrayList of all of the string elements*/
        return new ArrayList<String>(Arrays.asList(inputAirport.split(",")));
    }

    /* Method to parse a string to an integer with error handaling*/
    public int parseToInt(String inputNum){
        try{
            return Integer.parseInt(inputNum);
        } catch (NumberFormatException e){
            return -1;
        }
    }

    /* Method to parse a string to a double with error handling*/
    public double parseToDouble(String inputDouble) {
        try {
            return Double.parseDouble(inputDouble);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /* Method to check whether the airport code is IATA or FAA */
    public void checkCodeType(Airport airport, String airportCode, String country){
       /* Airports located in the US have an FAA code. All others have an IATA code. */
        if(country.equals("United States")) {
            airport.setFAA(airportCode);
        } else {
            airport.setIATA(airportCode);
        }
    }

    /* Method to check whether the given code is null. If so, change to null. Return airportCode */
    public String checkNull(String airportCode){

        if(airportCode.equals("\\N") || airportCode.equals("")) {
            airportCode = null;
        }
        return airportCode;
    }

}
