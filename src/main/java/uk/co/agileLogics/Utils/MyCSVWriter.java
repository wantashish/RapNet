package uk.co.agileLogics.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ashishsri
 * Date: 27/08/2014
 * Time: 03:10
 * To change this template use File | Settings | File Templates.
 */
public class MyCSVWriter {

    String fileName;
    FileWriter fw;
    BufferedWriter bw;

    public MyCSVWriter(String fileName) {
        this.fileName = fileName;
        File fileOutObj = new File(fileName);
        try {
            if(fileOutObj.exists()){
                fileOutObj.delete();
            }
            fileOutObj.createNewFile();
            fw = new FileWriter(fileOutObj);
            bw = new BufferedWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public void writeLn(String lineOut) {
        try {
            bw.write(lineOut);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void flush() {
        try {
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void close() {
        try {
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
