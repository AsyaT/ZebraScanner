package businesslogic;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BarcodeStructureModel {
    private HashMap<String, ArrayList<ProductStructureModel>> BarcodeUnique;

    public class ProductStructureModel{
        private Pair<String,String> Product;
        private Pair<String,String> Characteristic;
        private Double Quantity;

        public String GetNomenclature()
        {
            return Product.second;
        }

        public String GetProductGuid()
        {
            return Product.first;
        }

        public String GetCharacteristicName()
        {
            return Characteristic.second;
        }

        public Double GetQuantity()
        {
            return Quantity;
        }

    }

    public List<ProductStructureModel> FindProductByBarcode(String uniqueBarcode)
    {
        return BarcodeUnique.get(uniqueBarcode);
    }
}
