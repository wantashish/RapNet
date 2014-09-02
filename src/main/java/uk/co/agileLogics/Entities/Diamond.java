package uk.co.agileLogics.Entities;


import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ashishsri
 * Date: 26/08/2014
 * Time: 17:30
 * To change this template use File | Settings | File Templates.
 */
public class Diamond {

    private String sizeFrom="";
    private String sizeTo="";
    private String shape="";
    private String clarity="";
    private String color="";
    private String pricePerCt="";
    private String discount="";
    private String ajariPrice="";
    private String ajariDiscount="";
    private String total="";

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Diamond(HashMap<String,String> map){
        for(Map.Entry<String,String> entry: map.entrySet()){
            String key = entry.getKey();
            try {
                String parameter = entry.getValue();
                Diamond.class.getMethod("set"+key,parameter.getClass()).invoke(this,parameter);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public void setSizeTo(String sizeTo) {
        this.sizeTo = sizeTo;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPricePerCt(String pricePerCt) {
        this.pricePerCt = pricePerCt;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setAjariPrice(String ajariPrice) {
        this.ajariPrice = ajariPrice;
    }

    public void setAjariDiscount(String ajariDiscount) {
        this.ajariDiscount = ajariDiscount;
    }


    public void setSizeFrom(String sizeFrom) {
        this.sizeFrom = sizeFrom;
    }

    public String getPricePerCt() {
        return pricePerCt;
    }

    public String getDiscount() {
        return discount;
    }

    public String getAjariPrice() {
        return ajariPrice;
    }

    public String getAjariDiscount() {
        return ajariDiscount;
    }

    public String getSizeFrom() {
        return sizeFrom;
    }

    public String getSizeTo() {
        return sizeTo;
    }

    public String getShape() {
        return shape;
    }

    public String getColor() {
        return color;
    }

    public String getClarity() {
        return clarity;
    }
}
