package businesslogic;

public class ProductStructureModel extends ProductModel
{
    public ProductStructureModel(String productGUID, String characteristicGUID, Double quant) {
        this.ProductGUID = productGUID;
        this.CharacteristicGUID = characteristicGUID;
        this.Weight = quant;
    }

}