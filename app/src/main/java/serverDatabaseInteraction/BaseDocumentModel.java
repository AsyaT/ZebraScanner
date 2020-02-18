package serverDatabaseInteraction;

import java.util.List;

public class BaseDocumentModel {
    public Boolean Error;
    public String ErrorMessage;
    public DocumentData DocumentData;


    public class DocumentData
    {
        public String Name;
        public Boolean Order;
        public List<ProductListModel> ProductList;

    }

    public class ProductListModel
    {
        public String Product;
        public String Charact;
        public String Quantity;
        public String QuantityDone;
        public Integer Pieces;
        public Integer PiecesDone;
    }
}
