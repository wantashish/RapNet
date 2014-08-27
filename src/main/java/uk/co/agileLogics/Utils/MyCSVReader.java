package uk.co.agileLogics.Utils;

import uk.co.agileLogics.Entities.Diamond;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ashishsri
 * Date: 27/08/2014
 * Time: 05:26
 * To change this template use File | Settings | File Templates.
 */
public class MyCSVReader {
    String fileName;
    FileReader fr;
    BufferedReader br;
    public MyCSVReader(String file) {
        fileName = file;
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public List<Diamond> serializeDiamonds() {
        String line;
        List<Diamond> diamonds = new ArrayList<Diamond>();
        try {
            line=br.readLine(); //to exclude header
            while((line=br.readLine()) !=null){
                // use jackson csvmapper library to convert from csv to objects
                String[] fields = line.split(",");
                diamonds.add(new Diamond(fields));
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return diamonds;  //To change body of created methods use File | Settings | File Templates.
    }

    public void close() {
        try {
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
