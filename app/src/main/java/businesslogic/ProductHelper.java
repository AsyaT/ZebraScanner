package businesslogic;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductHelper {

    private ProductModel Model;

    public ProductHelper(String url, String userpass) throws ExecutionException, InterruptedException
    {
        List<ProductModel.ProductListModel> result = (new WebServiceProductRead()).execute().get();

        this.Model = new ProductModel();
        this.Model.BarCodeList = result;
    }

    public ProductModel.ProductListModel FindProductByBarcode(String barcodeUniqueIdentifier)
    {
        for(ProductModel.ProductListModel productPosition : Model.BarCodeList)
        {
            if(productPosition.Barcode.equalsIgnoreCase(barcodeUniqueIdentifier))
            {
                return productPosition;
            }
        }
        return null;
    }
}
