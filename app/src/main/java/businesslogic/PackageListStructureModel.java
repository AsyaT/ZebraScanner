package businesslogic;

import java.util.ArrayList;

public class PackageListStructureModel
{
    private ArrayList<ProductStructureModel> Products;

    public class ProductStructureModel
    {
        private String ProductGuid;
        private String CharacteristicGuid;
    }

    public ArrayList<ProductStructureModel> GetProducts()
    {
        return this.Products;
    }
}
