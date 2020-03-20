package models;

import java.util.HashMap;

import businesslogic.ApplicationException;

public class NomenclatureStructureModel {

    HashMap<String,String> Product ;

    public NomenclatureStructureModel()
    {
        Product= new HashMap<>();
    }

    public String FindProductByGuid(String productGuid) throws ApplicationException {
        if(Product.containsKey(productGuid))
        {
            return Product.get(productGuid);
        }
        else
            {
            throw new ApplicationException("Продукт с таким GUID "+productGuid+" не найден");
        }
    }

    public void Add(String productGuid, String productName)
    {
        this.Product.put(productGuid,productName);
    }
}
