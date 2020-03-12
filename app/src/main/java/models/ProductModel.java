package models;

public class ProductModel
{
    protected String ProductGUID ;
    protected String CharacteristicGUID ;
    protected Double Weight;

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
}
