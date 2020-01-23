package serverDatabaseInteraction;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.Gson;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import businesslogic.BarcodeStructureModel;
import businesslogic.CharacterisiticStructureModel;
import businesslogic.NomenclatureStructureModel;
import businesslogic.ProductStructureModel;

public class BarcodeHelper {

    private BarcodeModel Model;
    public BarcodeStructureModel BarcodeModel;
    public NomenclatureStructureModel ProductModel;
    public CharacterisiticStructureModel CharacteristicModel;


    public BarcodeHelper(String result) throws ParseException {

        Gson g = new Gson();
        this.Model = g.fromJson(result, BarcodeModel.class);
        this.BarcodeModel = new BarcodeStructureModel();
        this.ProductModel = new NomenclatureStructureModel();
        this.CharacteristicModel = new CharacterisiticStructureModel();

        for(serverDatabaseInteraction.BarcodeModel.ProductListModel barcode : Model.BarCodeList)
        {
            ArrayList<ProductStructureModel> listModel = new ArrayList<>();

            for(serverDatabaseInteraction.BarcodeModel.PropertiesListModel plm : barcode.PropertiesList)
            {
                NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
                Number number = format.parse(plm.Quant);

                ProductStructureModel productModel =
                        new ProductStructureModel(plm.ProductGUID, plm.ProductCharactGUID, number.doubleValue());
                listModel.add(productModel);

                this.ProductModel.Add(plm.ProductGUID, plm.ProductName);
                this.CharacteristicModel.Add(plm.ProductCharactGUID, plm.ProductCharactName);
            }

            this.BarcodeModel.Add(barcode.Barcode, listModel);
        }
    }

    public BarcodeHelper(String url, String userpass) {
        List<BarcodeModel.ProductListModel> result = PullResult(url,userpass);
        this.Model = new BarcodeModel();
        this.Model.BarCodeList = result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String readFileAsString(String fileName)throws Exception
    {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
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
