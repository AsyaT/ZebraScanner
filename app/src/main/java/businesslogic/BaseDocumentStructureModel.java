package businesslogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BaseDocumentStructureModel implements Serializable {

    private String Name;
    private String BaseDocumentGuid;
    private Boolean CompileByPackageListOnly;
    private List<ProductOrderStructureModel> ProductList;

    public BaseDocumentStructureModel(String name)
    {
        this.Name = name;
        this.ProductList = new ArrayList<>();
    }
    public BaseDocumentStructureModel(String name, Boolean compileByPackageListOnly)
    {
        this.Name = name;
        this.ProductList = new ArrayList<>();
        this.CompileByPackageListOnly = compileByPackageListOnly;
    }

    public String GetOrderId()
    {
        return BaseDocumentGuid;
    }

    public void SetOrderGuid(String guid)
    {
        this.BaseDocumentGuid = guid;
    }

    public void Add(ProductOrderStructureModel product)
    {
        this.ProductList.add(product);
    }

    public Boolean IfProductExists(String productGuid)
    {
        for(ProductOrderStructureModel structure : this.ProductList)
        {
            if(structure.ProductGuid.equalsIgnoreCase(productGuid))
            {
                return true;
            }
        }
        return false;
    }

    public Boolean IsCompileByPackageListOnly ()
    {
        return this.CompileByPackageListOnly;
    }

    public static class ProductOrderStructureModel implements Serializable
    {
        private String ProductGuid;
        private String CharacteristicGuid;
        private Double OrderedKilos;
        private Double DoneKilos;
        private Double LeftKilos;
        private Integer OrderedItems;
        private Integer DoneItems;
        private Integer LeftItems;

        public  ProductOrderStructureModel(String productGuid, String characteristicGuid, Double orderedKilos, Double doneKilos, Integer orderedItems, Integer doneItems)
        {
            this.ProductGuid = productGuid;
            this.CharacteristicGuid = characteristicGuid;
            this.OrderedKilos = orderedKilos;
            this.DoneKilos = doneKilos;
            this.LeftKilos = orderedKilos - doneKilos;
            this.OrderedItems = orderedItems;
            this.DoneItems = doneItems;
            this.LeftItems = orderedItems - doneItems;
        }

        public String GetProductGuid()
        {
            return ProductGuid;
        }

        public String GetCharacteristicGuid()
        {
            return CharacteristicGuid;
        }

        public Double OrderedInKilos()
        {
            return OrderedKilos;
        }

        public Double DoneInKilos()
        {
            return DoneKilos;
        }

        public Double LeftInKilos()
        {
            return LeftKilos;
        }

        public Integer OrderedInItems()
        {
            return OrderedItems;
        }

        public Integer DoneInItems()
        {
            return DoneItems;
        }

        public Integer LeftInItems()
        {
            return LeftItems;
        }
    }

    public String GetOrderName()
    {
        return Name;
    }

    public ArrayList<ProductOrderStructureModel> ProductList()
    {
        return (ArrayList<ProductOrderStructureModel>) ProductList;
    }
}
