package presentation;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ProductListViewModel extends Object {
    private String ProductGuid;
    private String CharacteristicGuid;
    private Integer StringNumber;
    private String Nomenclature;
    private String Characteristic;
    private Double SummaryWeight;
    private Integer Quantity;

    public String getProductGuid() { return ProductGuid;}
    public String getCharacteristicGuid() { return CharacteristicGuid;}
    public String getNomenclature() {
        return Nomenclature;
    }
    public String getCharacteristic()
    {
        return Characteristic;
    }
    public Integer getStringNumber(){return StringNumber;}
    public String getStringNumberString() {return StringNumber.toString();}

    public Double getSummaryWeight() {return SummaryWeight;}
    public String getSummaryWeightString()
    {
        DecimalFormat formatter = new DecimalFormat("#.###", DecimalFormatSymbols.getInstance( Locale.GERMAN ));
        formatter.setRoundingMode( RoundingMode.DOWN );
        return formatter.format(SummaryWeight);
    }
    public Integer getQuantity(){
        return Quantity;
    }
    public String getQuantityString() {return Quantity.toString();}

    public ProductListViewModel(String productGuid, String characteristicGuid, Integer stringNumber,  String nomenclature, String characteristic, Double weight, Integer coefficient){
        this.ProductGuid = productGuid;
        this.CharacteristicGuid = characteristicGuid;
        this.StringNumber = stringNumber;
        this.Characteristic = characteristic;
        this.Nomenclature = nomenclature;
        this.Quantity = coefficient;
        this.SummaryWeight = weight;
    }
}