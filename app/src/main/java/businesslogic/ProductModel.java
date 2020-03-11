package businesslogic;

public class ProductModel {
    protected String ProductGUID ;
    protected String CharacteristicGUID ;
    protected Double Weight;


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


    public void SetWeight(Double scannedWeight)
    {
        if(scannedWeight != null)
        {
            this.Weight = scannedWeight;
        }
    }
}
