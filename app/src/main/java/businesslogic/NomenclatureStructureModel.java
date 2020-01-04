package businesslogic;

import java.util.HashMap;

public class NomenclatureStructureModel {

    HashMap<String,String> Product = new HashMap<>();

    public String FindProductByGuid(String productGuid)
    {
        return Product.get(productGuid);
    }

    public void Add(String productGuid, String productName)
    {
        this.Product.put(productGuid,productName);
    }
}
