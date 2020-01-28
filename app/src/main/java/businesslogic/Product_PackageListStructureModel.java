package businesslogic;

import java.util.Date;

public class Product_PackageListStructureModel   extends ProductModel{

    protected String SSCCCode; //TODO : Maybe it related to Package List?
    protected Integer Items; // Now is using in PackageListCommand. TODO: move to logic?

    public Product_PackageListStructureModel(String productGuid,
                                             String characteristicGuid,
                                             Double weight,
                                             Date production,
                                             Date expired,
                                             String manufacturerGuid,
                                             Integer items)
    {
        this.ProductGUID = productGuid;
        this.CharacteristicGUID = characteristicGuid;
        this.Weight = weight;
        this.ExpirationDate = production;
        this.ProductionDate = expired;
        this.ManufacturerGUID = manufacturerGuid;
        this.Items = items;
    }

    public Integer GetItems()
    {
        return this.Items;
    }

}
