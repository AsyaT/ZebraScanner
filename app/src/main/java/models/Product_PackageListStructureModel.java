package models;

import java.util.Date;

public class Product_PackageListStructureModel extends ProductModel
{

    protected Integer Items; // Now is using in PackageListCommand. TODO: move to logic?

    protected String ManufacturerGUID;
    protected Date ProductionDate;
    protected Date ExpirationDate;

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
        this.ExpirationDate = expired;
        this.ProductionDate = production;
        this.ManufacturerGUID = manufacturerGuid;
        this.Items = items;
    }

    public Integer GetItems()
    {
        return this.Items;
    }

    public String GetManufacturerGuid(){return ManufacturerGUID;}

    public Date GetProductionDate() { return ProductionDate;}

    public Date GetExpirationDate() { return ExpirationDate;}


}
