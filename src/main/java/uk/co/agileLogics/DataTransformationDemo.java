package uk.co.agileLogics;

import uk.co.agileLogics.Entities.Diamond;
import uk.co.agileLogics.Utils.MyCSVReader;
import uk.co.agileLogics.Utils.MyCSVWriter;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ashishsri
 * Date: 26/08/2014
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
public class DataTransformationDemo {

    public static void main(String[] fileLocation){
        String file = "/Users/ashishsri/Downloads/yourfile.csv";
//        String file = fileLocation[0];
        File fileObj = new File(file);
        String filePath = fileObj.getParent();
        MyCSVReader csvReader = new MyCSVReader(file);
        List<Diamond> diamonds = csvReader.serializeDiamonds();
        csvReader.close();
        // use Guava multimap
        HashMap<String,HashMap<String,HashMap<String,HashMap<String,Diamond>>>> groupedByFourFields =
                groupByFourFields(diamonds,"SizeFrom","SizeTo","Color","Clarity");

        String fileOut = filePath+"/yourfileout.csv";
        MyCSVWriter csvWriter = new MyCSVWriter(fileOut);
        for(Map.Entry<String,HashMap<String,HashMap<String,HashMap<String,Diamond>>>> entryFrom :
                groupedByFourFields.entrySet()){
            String keyFrom = entryFrom.getKey();
            for(Map.Entry<String,HashMap<String,HashMap<String,Diamond>>> entryTo :
                    entryFrom.getValue().entrySet()){
                String keyTo = entryTo.getKey();
                csvWriter.writeLn(keyFrom + "-" + keyTo);
                System.out.println(keyFrom + "-" + keyTo);
                writeTable(csvWriter,entryTo.getValue());
            }
        }
        csvWriter.flush();
        csvWriter.close();
    }

    private static void writeTable(MyCSVWriter csvWriter, HashMap<String, HashMap<String, Diamond>> map) {
        String columnNames;
        String[] rows;
        Set<String> columns = new HashSet<String>();
        for(Map.Entry<String,HashMap<String,Diamond>> entryColor: map.entrySet()){
            HashMap<String,Diamond> clarityMap = entryColor.getValue();
            columns.addAll(clarityMap.keySet());
        }
        rows = map.keySet().toArray(new String[0]);
        columnNames = Arrays.toString(columns.toArray()).replace("[","").replace("]", "");
        System.out.println(","+columnNames);
        csvWriter.writeLn(","+columnNames);
        for(String row: rows){
            String lineOut=row+",";
            for(String column:columns){
                Diamond diamond = map.get(row).get(column);
                if(diamond!=null){
                    String diamondInfo = diamond.getPricePerCt()+":"+diamond.getDiscount()+":"+
                            diamond.getAjariPrice()+":"+diamond.getAjariDiscount();
                    lineOut = lineOut+diamondInfo;
                }
                lineOut = lineOut+",";
            }
            System.out.println(lineOut);
            csvWriter.writeLn(lineOut);
        }
    }

    private static HashMap<String, HashMap<String, HashMap<String, HashMap<String, Diamond>>>> groupByFourFields(
            List<Diamond> diamonds, String criteria1, String criteria2, String criteria3, String criteria4) {
        HashMap<String, HashMap<String, HashMap<String, HashMap<String, Diamond>>>> fourDMap =
                new HashMap<String, HashMap<String, HashMap<String, HashMap<String, Diamond>>>>();
        HashMap<String,List<Diamond>> groupedByCriteria1 = groupDiamondsByField(diamonds,criteria1);
        for(Map.Entry<String, List<Diamond>> entry : groupedByCriteria1.entrySet()){
            String key = entry.getKey();
            List<Diamond> diamondsForKey = entry.getValue();
            HashMap<String,HashMap<String,HashMap<String,Diamond>>> threeDMap =
                    groupByThreeFields(diamondsForKey,criteria2,criteria3,criteria4);
            fourDMap.put(key,threeDMap);
        }
        return fourDMap;
    }

    private static HashMap<String, HashMap<String, HashMap<String, Diamond>>> groupByThreeFields(
            List<Diamond> diamonds, String criteria1, String criteria2, String criteria3) {
        HashMap<String ,HashMap<String,HashMap<String,Diamond>>> threeDMap =
                new HashMap<String, HashMap<String, HashMap<String, Diamond>>>();
        HashMap<String,List<Diamond>> groupedByCriteria1 = groupDiamondsByField(diamonds,criteria1);
        for(Map.Entry<String, List<Diamond>> entry : groupedByCriteria1.entrySet()){
            String key = entry.getKey();
            List<Diamond> diamondsForKey = entry.getValue();
            HashMap<String,HashMap<String,Diamond>> twoDMap =
                    getTwoDMap(diamondsForKey,criteria2,criteria3);
            threeDMap.put(key,twoDMap);
        }
        return threeDMap;
    }

    private static HashMap<String, HashMap<String, Diamond>> getTwoDMap(List<Diamond> diamonds,
                    String criteria1, String criteria2) {
        HashMap<String, List<Diamond>> mapByCriteria1 = groupDiamondsByField(diamonds, criteria1);
        HashMap<String, HashMap<String, Diamond>> twoDMap=
                new HashMap<String, HashMap<String, Diamond>>();
        for(Map.Entry<String, List<Diamond>> entry : mapByCriteria1.entrySet()){
            String key = entry.getKey();
            HashMap<String, Diamond> mapByCriteria2 = mapDiamondsByField(entry.getValue(), criteria2);
            twoDMap.put(key,mapByCriteria2);
        }
        return twoDMap;
    }

    private static HashMap<String, Diamond> mapDiamondsByField(List<Diamond> diamonds, String field) {
        Method targetFieldGetter = null;
        HashMap<String,Diamond> diamondMap = new HashMap<String, Diamond>();
        try {
            targetFieldGetter = diamonds.get(0).getClass().getMethod("get"+field);
            for(Diamond diamond:diamonds){
                String key = (String) targetFieldGetter.invoke(diamond);
                if(diamondMap.get(key) == null)
                    diamondMap.put(key,diamond);
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return diamondMap;  //To change body of created methods use File | Settings | File Templates.
    }

    private static HashMap<String, List<Diamond>> groupDiamondsByField(List<Diamond> diamonds, String fieldName) {
        Method targetFieldGetter = null;
        HashMap<String, List<Diamond>> mapByField = new HashMap<String, List<Diamond>>();
        try {
            targetFieldGetter = diamonds.get(0).getClass().getMethod("get"+fieldName);
            //write code to get getter method (rather than explicitly providing name) to avoid NoSuchMethodException
        for(Diamond diamond : diamonds){
            String key = (String) targetFieldGetter.invoke(diamond);
            if(mapByField.get(key) == null){
                mapByField.put(key, new ArrayList<Diamond>());
            }
            mapByField.get(key).add(diamond);
        }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return mapByField;
    }
}
