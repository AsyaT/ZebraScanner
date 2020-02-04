package businesslogic;

import java.util.ArrayList;
import java.util.HashMap;

public class BarcodeStructureModel {
    private HashMap<String, ArrayList<ProductStructureModel>> BarcodeUnique;

    public BarcodeStructureModel()
    {
        BarcodeUnique = new HashMap<>();
    }

    public ArrayList<ProductStructureModel> FindProductByBarcode(String uniqueBarcode)
    {
        return BarcodeUnique.get(uniqueBarcode);
    }

    public void Add(String barcode, ArrayList<ProductStructureModel> list)
    {
        this.BarcodeUnique.put(barcode,list);
    }
}
