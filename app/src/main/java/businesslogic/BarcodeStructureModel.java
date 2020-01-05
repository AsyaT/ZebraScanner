package businesslogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BarcodeStructureModel {
    private HashMap<String, ArrayList<ProductStructureModel>> BarcodeUnique;

    public BarcodeStructureModel()
    {
        BarcodeUnique = new HashMap<>();
    }

    public static class ProductStructureModel{
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

    public List<ProductStructureModel> FindProductByBarcode(String uniqueBarcode)
    {
        return BarcodeUnique.get(uniqueBarcode);
    }

    public void Add(String barcode, ArrayList<ProductStructureModel> list)
    {
        this.BarcodeUnique.put(barcode,list);
    }
}
