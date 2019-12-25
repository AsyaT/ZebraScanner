package serverDatabaseInteraction;

import java.util.List;

public class OrderModel  {
    public Boolean Error;
    public String OrderName;
    public List<ProductListModel> ProductList;

    public class ProductListModel
    {
        public String Product;
        public String Charact;
        public String Ordered;
        public String Done;
        public String Left;
        public Integer PiecesOrdered;
        public Integer PiecesDone;
        public Integer PiecesLeft;
    }
}
