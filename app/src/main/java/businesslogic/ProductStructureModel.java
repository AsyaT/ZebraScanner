package businesslogic;

public class ProductStructureModel extends ProductModel
{
public ProductStructureModel(String productGuid, String characteristicGuid, Double weight)
{
    this.ProductGUID = productGuid;
    this.CharacteristicGUID = characteristicGuid;
    this.Weight = weight;
    this.ExpirationDate = null;
    this.ProductionDate = null;
    this.ManufacturerGUID = null;
}
}