package uk.co.agileLogics;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import uk.co.agileLogics.Entities.Diamond;
import uk.co.agileLogics.Utils.MyCSVReader;
import uk.co.agileLogics.Utils.MyCSVWriter;
import uk.co.agileLogics.Utils.StringUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ashishsri
 * Date: 26/08/2014
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
public class DataTransformationDemo {

    public static void main(String[] fileLocation){
//        String file = "/Users/ashishsri/Downloads/yourfile.csv";
        String file = fileLocation[0];
        File fileObj = new File(file);
        String filePath = fileObj.getParent();
        MyCSVReader csvReader = new MyCSVReader(file);
        List<Diamond> diamonds = csvReader.serializeDiamonds();
        csvReader.close();
        Table<String,String,Table<String,String,Diamond>> sizingTable = HashBasedTable.create();
        String fileOut = filePath+"/yourfileout.csv";
        MyCSVWriter csvWriter = new MyCSVWriter(fileOut);
        HashMap<String,List<Diamond>> sizeFromMap = groupDiamondsByField(diamonds,"SizeFrom");
        for(Map.Entry<String, List<Diamond>> sizeFromEntry : sizeFromMap.entrySet()){
            String sizeFrom = sizeFromEntry.getKey();
            HashMap<String,List<Diamond>> sizeToMap = groupDiamondsByField(sizeFromEntry.getValue(),"SizeTo");
            for(Map.Entry<String,List<Diamond>> sizeToEntry:sizeToMap.entrySet()){
                String sizeTo = sizeToEntry.getKey();
                Table<String,String,Diamond> colorClarityTable = HashBasedTable.create();
                for(Diamond d:sizeToEntry.getValue()){
                    colorClarityTable.put(d.getColor(),d.getClarity(),d);
                }
                sizingTable.put(sizeFrom,sizeTo,colorClarityTable);
            }
        }
        for(String sizeFrom:sizingTable.rowKeySet()){
            for(String sizeTo:sizingTable.columnKeySet()){
                Table<String,String,Diamond> diamondsForThisSize = sizingTable.row(sizeFrom).get(sizeTo);
                if(diamondsForThisSize!=null){
                    System.out.println(sizeFrom+"-"+sizeTo);
                    csvWriter.writeLn(sizeFrom+"-"+sizeTo);
                    writeTable(csvWriter,diamondsForThisSize);
                }
            }
        }
        csvWriter.flush();
        csvWriter.close();
    }

    private static void writeTable(MyCSVWriter csvWriter, Table<String, String, Diamond> map) {
        String columnNames = StringUtils.concatStringsInSet(map.columnKeySet());
        System.out.println(","+columnNames);
        csvWriter.writeLn(","+columnNames);
        for(String row:map.rowKeySet()){
            String out = row;
            for(String column:map.columnKeySet()){
                out = out+",";
                Diamond d = map.row(row).get(column);
                if(d!=null)out = out+d.getPricePerCt()+":"+d.getDiscount()+":"+
                        d.getAjariPrice()+":"+d.getAjariDiscount()+":"+d.getTotal();
            }
            System.out.println(out);
            csvWriter.writeLn(out);
        }
    }

    private static HashMap<String, List<Diamond>> groupDiamondsByField(List<Diamond> diamonds, String fieldName) {
        Method targetFieldGetter = null;
        HashMap<String, List<Diamond>> mapByField = new HashMap<String, List<Diamond>>();
        try {
            targetFieldGetter = diamonds.get(0).getClass().getMethod("get"+fieldName);
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
