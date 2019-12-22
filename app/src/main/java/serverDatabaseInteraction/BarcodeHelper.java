package serverDatabaseInteraction;

import java.util.ArrayList;
import java.util.List;

import businesslogic.BarcodeStructureModel;

public class BarcodeHelper {

    private BarcodeModel Model;
    private BarcodeStructureModel ReturnModel;

    public BarcodeHelper(String url, String userpass)  {
        //List<BarcodeModel.ProductListModel> result = PullResult(url,userpass);
        this.Model = new BarcodeModel();
        //this.Model.BarCodeList = result;
        //
        this.Model.BarCodeList = new ArrayList<BarcodeModel.ProductListModel>();
        BarcodeModel.ProductListModel plm = new BarcodeModel.ProductListModel();
        plm.Barcode = "4660017708243";
        plm.PropertiesList = new ArrayList<BarcodeModel.PropertiesListModel>();
        BarcodeModel.PropertiesListModel productModel = new BarcodeModel.PropertiesListModel();
        productModel.ProductName = "Бедрышко куриное \\\"Здоровая Ферма\\\", охл.~8,00 кг*1/~8,0 кг/ (гофрокороб, пленка пнд)";
        productModel.ProductGUID = "f50d315d-7ca8-11e6-80d7-e4115bea65d2";
        productModel.ProductCharactName = "Метро";
        productModel.ProductCharactGUID = "41dbf472-19d8-11e7-80cb-001e67e5da8c";
        productModel.Quant = "8";
        plm.PropertiesList.add(productModel);
        this.Model.BarCodeList.add(plm);

         //
    }

    private List<BarcodeModel.ProductListModel> PullResult(String url, String userpass)  {
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
}
