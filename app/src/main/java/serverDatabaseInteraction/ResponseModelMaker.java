package serverDatabaseInteraction;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import businesslogic.ApplicationException;
import businesslogic.FullDataTableControl;

public class ResponseModelMaker
{
    public static String MakeResponseJson(String accountingArea, String userId, String documentId, List<FullDataTableControl.Details> products) throws ApplicationException {

        ResponseStructureModel responseStructureModel = AnswerToServer(accountingArea,userId,documentId,products);
        return ConvertModelToJson(responseStructureModel);
    }

    protected static String ConvertModelToJson(ResponseStructureModel model)
    {
        Gson gson = new Gson();
        return gson.toJson(model);
    }

    protected static ResponseStructureModel AnswerToServer(String accountingArea, String userId, String documentId, List<FullDataTableControl.Details> products) throws ApplicationException {
        ResponseStructureModel responseStructureModel = new ResponseStructureModel();
        responseStructureModel.AccountingAreaGUID = accountingArea;
        responseStructureModel.UserID = userId;
        responseStructureModel.DocumentID = documentId;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.GERMAN );
        DecimalFormat decimalFormat = new DecimalFormat("#,###", DecimalFormatSymbols.getInstance( Locale.GERMAN ));

        for(FullDataTableControl.Details product : products)
        {
            ResponseStructureModel.ResponseProductStructureModel rpsm = new ResponseStructureModel.ResponseProductStructureModel();
            rpsm.Product = product.getProductGuid();
            rpsm.Charact = product.getCharacteristicGuid();
            rpsm.Quantity = decimalFormat.format(CalculateQuantityValue(product.getWeight(), product.getQuantityPiecesGoods()));
            rpsm.PackageCounter = String.valueOf(product.getScannedQuantity());
            rpsm.ManufactureDate = formatter.format(product.getProductionDate());
            rpsm.ExpirationDate = formatter.format(product.getExpiredDate());
            rpsm.Manufacturer = product.getManufacturerGuid();
            rpsm.PackageList = product.getPackageList();
            rpsm.BarCode = product.getScannedBarcode();
            responseStructureModel.ProductList.add(rpsm);
        }

        return responseStructureModel;
    }

    private static Double CalculateQuantityValue(Double weight, Integer quantityPiecesGoods)
    {
        if(weight == null)
        {
            return Double.valueOf(quantityPiecesGoods);
        }
        else if(quantityPiecesGoods == null)
        {
            return weight;
        }

        return null;
    }
}
