package serverDatabaseInteraction;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    public Boolean Error;
    public String OrderName;
    public List<ProductListModel> ProductList;

    public class ProductListModel
    {
        public String Product;
        public String Charact;
        private String Ordered;
        private String Done;
        private String Left;
        public Integer PiecesOrdered;
        public Integer PiecesDone;
        public Integer PiecesLeft;
    }
}
