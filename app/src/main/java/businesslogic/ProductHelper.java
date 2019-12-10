package businesslogic;

import java.util.List;

public class ProductHelper {

    private ProductModel Model;

    public ProductHelper(String url, String userpass)  {
        List<ProductModel.ProductListModel> result = PullResult(url,userpass);

        this.Model = new ProductModel();
        this.Model.BarCodeList = result;
    }

    private List<ProductModel.ProductListModel> PullResult(String url, String userpass)  {
        try {
          // return (new WebServiceProductRead()).execute(url, userpass).get();
            return null; // TODO: return
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
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
