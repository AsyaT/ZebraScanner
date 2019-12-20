package businesslogic;

import java.util.ArrayList;
import java.util.List;

public class OrderStructureModel {

    private String Name;
    private List<ProductOrderStructureModel> ProductList;

    public class ProductOrderStructureModel
    {
        private String ProductGuid;
        private String CharacteristicGuid;
        private Double OrderedKilos;
        private Double DoneKilos;
        private Double LeftKilos;
        private Integer OrderedItems;
        private Integer DoneItems;
        private Integer LeftItems;

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
