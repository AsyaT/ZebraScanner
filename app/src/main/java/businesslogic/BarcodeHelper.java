package businesslogic;

import java.util.ArrayList;
import java.util.List;

public class BarcodeHelper {

    private ProductModel Model;

    public BarcodeHelper(String url, String userpass)  {
        //List<ProductModel.ProductListModel> result = PullResult(url,userpass);

        this.Model = new ProductModel();
        //

        this.Model.BarCodeList = new ArrayList<ProductModel.ProductListModel>();
        ProductModel.ProductListModel plm = new ProductModel.ProductListModel();
        plm.Barcode = "4660017708243";
        plm.PropertiesList = new ArrayList<ProductModel.PropertiesListModel>();
        ProductModel.PropertiesListModel productModel = new ProductModel.PropertiesListModel();
        productModel.ProductName = "Бедрышко куриное \\\"Здоровая Ферма\\\", охл.~8,00 кг*1/~8,0 кг/ (гофрокороб, пленка пнд)";
        productModel.ProductGUID = "f50d315d-7ca8-11e6-80d7-e4115bea65d2";
        productModel.ProductCharactName = "Метро";
        productModel.ProductCharactGUID = "41dbf472-19d8-11e7-80cb-001e67e5da8c";
        productModel.Quant = "8";
        plm.PropertiesList.add(productModel);
        this.Model.BarCodeList.add(plm);

         //

        //this.Model.BarCodeList = result;
    }

    private List<ProductModel.ProductListModel> PullResult(String url, String userpass)  {
        try {
            if(url.isEmpty())
            {
                 return null;
            }
            else {
                return (new WebServiceProductRead()).execute(url, userpass).get();
            }

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

    public ProductModel.PropertiesListModel FindProductByGuid(String productGuid)
    {
        for(ProductModel.ProductListModel productPosition : Model.BarCodeList)
        {
            for(ProductModel.PropertiesListModel propertiesListModel : productPosition.PropertiesList)
            {
                if(propertiesListModel.ProductGUID.equalsIgnoreCase(productGuid))
                {
                    return propertiesListModel;
                }
            }
        }
        return null;
    }

    public ProductModel.PropertiesListModel FindCharacteristicByGuid(String characteristicGuid)
    {
        for(ProductModel.ProductListModel productPosition : Model.BarCodeList)
        {
            for(ProductModel.PropertiesListModel propertiesListModel : productPosition.PropertiesList)
            {
                if(propertiesListModel.ProductCharactGUID.equalsIgnoreCase(characteristicGuid))
                {
                    return propertiesListModel;
                }
            }
        }
        return null;
    }
}
