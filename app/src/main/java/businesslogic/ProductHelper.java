package businesslogic;

import java.util.ArrayList;
import java.util.List;

public class ProductHelper {

    private List<ProductCharacteristicModel> Model;

    public ProductHelper(String getProductURL, String getUsernameAndPassword) {
        Model = new ArrayList<ProductCharacteristicModel>();

        ProductCharacteristicModel product1 = new ProductCharacteristicModel();
        product1.ProductGuid = "ddc4578e-e49f-11e7-80c5-a4bf011ce3c3";
        product1.Nomenclature = "Филе бедра \\\"Здоровая Ферма\\\", охл.~0,80 кг*6/~4,8 кг/ (подложка, стрейч)";
        product1.CharacteristicGuid = "400211e8-a6e7-11e9-80d1-a4bf011ce3c3";
        product1.Characteristic = "Молния";

        Model.add(product1);
    }

    public String FindProductByGuid(String productGuid) {

        for(ProductCharacteristicModel item : Model)
        {
            if(item.ProductGuid.equalsIgnoreCase(productGuid))
            {
                return item.Nomenclature;
            }
        }
        return "";
    }

    public String FindCharacteristicByGuid(String charactGuid) {
        for(ProductCharacteristicModel item : Model)
        {
            if(item.CharacteristicGuid.equalsIgnoreCase( charactGuid ))
            {
                return item.Characteristic;
            }
        }

        return "";
    }
}
