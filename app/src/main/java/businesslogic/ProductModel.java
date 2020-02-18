package businesslogic;

import java.util.Date;

public class ProductModel {
    protected String ProductGUID ;
    protected String CharacteristicGUID ;
    protected Double Weight;
    protected String ManufacturerGUID;
    protected Date ProductionDate;
    protected Date ExpirationDate;

    protected ProductModel()
    {

    }

    public String GetProductGuid()
    {
        return ProductGUID;
    }

    public String GetCharacteristicGUID()
    {
        return CharacteristicGUID;
    }

    public Double GetWeight()
    {
        return Weight;
    }

    public String GetManufacturerGuid(){return ManufacturerGUID;}

    public Date GetProductionDate() { return ProductionDate;}

    public Date getExpirationDate() { return ExpirationDate;}

    public void SetWeight(Double scannedWeight)
    {
        if(scannedWeight != null)
        {
            this.Weight = scannedWeight;
        }
    }

    public void SetProductionDate(Date scannedDate)
    {
        this.ProductionDate = scannedDate;
    }
    public void SetExpirationDate(Date scannedDate)
    {
        this.ExpirationDate = scannedDate;
    }
    public void SetManufacturerGuid(String scannedGuid)
    {
        this.ManufacturerGUID = scannedGuid;
    }
}
