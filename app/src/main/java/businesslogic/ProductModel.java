package businesslogic;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class ProductModel {
    public List<ProductListModel> BarCodeList;



    public class ProductListModel {
        public String Barcode;
        public List<PropertiesListModel> PropertiesList;
    }

    public class PropertiesListModel{
        public String ProductName;
        public String ProductGUID;
        public String ProductCharactName;
        public String ProductCharactGUID;
        public String Quant;
        public Double Quantity() throws ParseException {
            NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
            Number number = format.parse(this.Quant);
            return number.doubleValue();
        }
    }
}
