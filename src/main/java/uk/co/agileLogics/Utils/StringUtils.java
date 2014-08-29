package uk.co.agileLogics.Utils;

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
            camelCaseString = inputString+part.substring(0,1).toUpperCase()+part.substring(1).toLowerCase();
        }
        return camelCaseString;
    }
}
