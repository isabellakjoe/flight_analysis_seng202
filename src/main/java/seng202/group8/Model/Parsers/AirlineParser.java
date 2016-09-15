package seng202.group8.Model.Parsers;

import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.AirlineMethod;

import java.util.ArrayList;

/**
 * Created by Sophie on 22/08/16.
 */
public class AirlineParser extends AirlineMethod {

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

    /**
     * Public method that creates a single Airline Object by calling createParsedAirline().
     *
     * @param input: A string of parameters for an Airline.
     * @return a Airline object or null if the
     */
    public Airline createSingleAirline(String input) {
        ArrayList<String> airlineInfo = refactorData(input);
        if (airlineInfo != null) {
            return createParsedAirline(airlineInfo);
        } else {
            return null;
        }
    }
}
