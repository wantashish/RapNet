package uk.co.agileLogics.Entities;

/**
 * Created with IntelliJ IDEA.
 * User: ashishsri
 * Date: 26/08/2014
 * Time: 17:30
 * To change this template use File | Settings | File Templates.
 */
public class Diamond {

    private final String sizeTo;
    private final String shape;
    private final String clarity;
    private final String color;

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

    private final String pricePerCt;
    private final String discount;
    private final String ajariPrice;
    private final String ajariDiscount;
    private String sizeFrom;

    public String getSizeFrom() {
        return sizeFrom;
    }

    public Diamond(String[] fields) {
        sizeFrom = fields[0];
        sizeTo = fields[1];
        shape = fields[2];
        clarity = fields[3];
        color = fields[4];
        pricePerCt = fields[5];
        discount = fields[6];
        ajariPrice = fields[7];
        ajariDiscount = fields[8];
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
