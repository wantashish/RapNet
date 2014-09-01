package uk.co.agileLogics.Utils;

import java.util.Arrays;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ashishsri
 * Date: 29/08/2014
 * Time: 08:55
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {

    public static String toCamelCase(String inputString) {
        String camelCaseString = "";
        String[] fieldParts = inputString.trim().split(" ");
        for(String part:fieldParts){
            camelCaseString = camelCaseString+part.substring(0,1).toUpperCase()+part.substring(1).toLowerCase();
        }
        return camelCaseString;
    }

    public static String concatStringsInSet(Set<String> inputSet) {
        return Arrays.toString(inputSet.toArray()).replace("[","").replace("]", "");  //To change body of created methods use File | Settings | File Templates.
    }
}
