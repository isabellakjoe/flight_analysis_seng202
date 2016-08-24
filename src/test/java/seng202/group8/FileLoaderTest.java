package seng202.group8;

import org.junit.Test;
import seng202.group8.Model.Airport;
import seng202.group8.Model.AirportParser;
import seng202.group8.Model.FileLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by asc132 on 22/08/2016.
 */
public class FileLoaderTest {

    private ArrayList<String> auck = new ArrayList<String>(Arrays.asList("2006","Auckland Intl","Auckland","New Zealand","AKL","NZAA","-37.008056","174.791667","23","12","Z","Pacific/Auckland"));

    @Test
    public void buildAirportsTest() throws FileNotFoundException {
        FileLoader loader = new FileLoader(new BufferedReader(new FileReader("PerfectAirportsOnly.txt")));
        Set<Airport> airports = loader.buildAirports();
        assertTrue(airports.size() == 3);
    }

    @Test
    public void testGetFile() throws FileNotFoundException {
        FileLoader load = new FileLoader(new BufferedReader(new FileReader("test.txt")));
        BufferedReader br = new BufferedReader(new FileReader("test.txt"));
        assertEquals(br, load.getFile(br));
    }

    @Test
    public void testReadData() throws FileNotFoundException {
        FileLoader load = new FileLoader(new BufferedReader(new FileReader("test.txt")));
        BufferedReader br = new BufferedReader(new FileReader("test.txt"));
        load.getFile(br);
        ArrayList<ArrayList<String>> test = load.readData(br);
        assertEquals(auck, test.get(0));
    }
}
