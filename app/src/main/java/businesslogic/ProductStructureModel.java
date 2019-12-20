package businesslogic;

import java.util.HashMap;

public class ProductStructureModel {
    HashMap<String,String> Product = new HashMap<>();
    HashMap<String,String> Characteristic = new HashMap<>();

    public String FindProductByGuid(String productGuid)
    {
        return Product.get(productGuid);
    }

    public String FindCharacteristicByGuid(String characteristicGuid)
    {
        return Characteristic.get(characteristicGuid);
    }
}
