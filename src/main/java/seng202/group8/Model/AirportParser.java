package seng202.group8.Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ikj11 on 22/08/16.
 */
public class AirportParser extends AirportMethod {

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
    public Airport createSingleAirport(String input){
        ArrayList<String> airportInfo = refactorData(input);
        if(airportInfo != null) {
            return createParsedAirport(airportInfo);
        } else{
            return null;
        }
    }
}
