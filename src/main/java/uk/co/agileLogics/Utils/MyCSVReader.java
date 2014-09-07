package uk.co.agileLogics.Utils;

import uk.co.agileLogics.Entities.Diamond;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ashishsri
 * Date: 27/08/2014
 * Time: 05:26
 * To change this template use File | Settings | File Templates.
 */
public class MyCSVReader {
    LineNumberReader lineNumberReader;
    public MyCSVReader(String file) {
        try {
            lineNumberReader = new LineNumberReader(new FileReader(file));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public List<Diamond> serializeDiamonds() {
        List<Diamond> diamonds = new ArrayList<Diamond>();
        List<HashMap<String,String>> listOfMap = parseCsvToMap(lineNumberReader);
        for(HashMap<String,String> map : listOfMap){
            Diamond diamond = new Diamond(map);
            if(diamond!=null) diamonds.add(diamond);
        }
        return diamonds;  //To change body of created methods use File | Settings | File Templates.
    }

    private List<HashMap<String, String>> parseCsvToMap(LineNumberReader lineNumberReader) {
        String line;
        List<String> fieldNames=null;
        String fieldDelimiter = ",";
        List<HashMap<String,String>> listOfMap = new ArrayList<HashMap<String, String>>();
        try {
            while((line=lineNumberReader.readLine()) !=null){
                HashMap<String,String> csvMap = new HashMap<String, String>();
                if(lineNumberReader.getLineNumber() == 1){
                    fieldNames = formatFieldNames(line.split(fieldDelimiter));
                    // assumption is the csv file has first row as field names.
                }else {
                    String[] fieldValues = line.split(fieldDelimiter);
//                    int usableFieldCount = Math.min(fieldNames.size(),fieldValues.length);
//                    for(int i=0;i<usableFieldCount;i++){
//                        csvMap.put(fieldNames.get(i), fieldValues[i]);
//                    }
//                  XXXXXXXXXXXXXXXXX
                    for(int i=0;i<fieldValues.length;i++){
                        if(i>=fieldNames.size()){
                            String s = "";
                            for(int j=i;j<fieldValues.length;j++){
                                s = s+fieldValues[j];
                            }
                            csvMap.put("Misc",s);
                            break;
                        }
                        else {
                            csvMap.put(fieldNames.get(i), fieldValues[i]);
                        }
                    }
//                  XXXXXXXXXXXXXXXXX
                }
                if(csvMap.size()!=0) listOfMap.add(csvMap);
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return listOfMap;
    }

    private List<String> formatFieldNames(String[] fieldNames) {
            List<String> formattedFieldName = new ArrayList<String>();
            for(String field:fieldNames){
                formattedFieldName.add(StringUtils.toCamelCase(field));
            }
            return formattedFieldName;
    }

    public void close() {
        try {
            lineNumberReader.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
