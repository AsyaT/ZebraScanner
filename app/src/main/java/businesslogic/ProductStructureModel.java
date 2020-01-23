package businesslogic;

public class ProductStructureModel{
    private String ProductGUID;
    private String CharacteristicGUID;
    private Double Weight;

    public ProductStructureModel(String productGUID, String characteristicGUID, Double quant) {
        this.ProductGUID = productGUID;
        this.CharacteristicGUID = characteristicGUID;
        this.Weight = quant;
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

}