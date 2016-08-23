package seng202.group8.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by asc132 on 22/08/2016.
 */
public class FileLoader {

    public BufferedReader reader;
    public ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

    public BufferedReader getFile(BufferedReader br){
        this.reader = br;
        return br;
    }

    public ArrayList<ArrayList<String>> readData(BufferedReader br){
        String line;
        try {
            do {
                line = br.readLine();
                if (line != null) {
                    ArrayList<String> list = new ArrayList<String>();
                    for(int i = 0; i <= (line.split(",").length - 1); i++) {
                        list.add((line.split(",")[i].replace("\"", "")));
                    }
                    data.add(list);
                }
            } while(line != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
