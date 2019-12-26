package businesslogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderStructureModel implements Serializable {

    private String Name;
    private List<ProductOrderStructureModel> ProductList;

    public OrderStructureModel(String name)
    {
        this.Name = name;
        this.ProductList = new ArrayList<>();
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

        public  ProductOrderStructureModel(String productGuid, String characteristicGuid, Double orderedKilos, Double doneKilos, Double leftKilos, Integer orderedItems, Integer doneItems, Integer leftItems)
        {
            this.ProductGuid = productGuid;
            this.CharacteristicGuid = characteristicGuid;
            this.OrderedKilos = orderedKilos;
            this.DoneKilos = doneKilos;
            this.LeftKilos = leftKilos;
            this.OrderedItems = orderedItems;
            this.DoneItems = doneItems;
            this.LeftItems = leftItems;
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
