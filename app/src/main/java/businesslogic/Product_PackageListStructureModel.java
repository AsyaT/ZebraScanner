package businesslogic;

import java.util.Date;

public class Product_PackageListStructureModel   extends ProductModel{
    protected String ManufacturerGUID;
    protected Date Production;
    protected Date Expired;
    protected String SSCCCode;
    protected Integer Items;

    public Product_PackageListStructureModel(String productGUID, String characteristicGUID, Double quant) {
        this.ProductGUID = productGUID;
        this.CharacteristicGUID = characteristicGUID;
        this.Weight = quant;
    }

}
