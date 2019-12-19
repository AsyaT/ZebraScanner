package businesslogic;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

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

        public Double KilosOrdered() throws ParseException
        {
            NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
            Number number = format.parse(this.Ordered);
            return number.doubleValue();
        }
        public Double KilosDone()throws ParseException
        {
            NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
            Number number = format.parse(this.Done);
            return number.doubleValue();
        }
        public Double KilosLeft() throws ParseException
        {
            NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
            Number number = format.parse(this.Left);
            return number.doubleValue();
        }

        public Integer PiecesOrdered;
        public Integer PiecesDone;
        public Integer PiecesLeft;
    }
}
