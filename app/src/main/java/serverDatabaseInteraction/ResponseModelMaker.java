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
            rpsm.Quantity = decimalFormat.format(product.getWeight() * product.getScannedQuantity());
            rpsm.PackageCounter = String.valueOf(product.getScannedQuantity());
            rpsm.ManufactureDate = formatter.format(product.getProductionDate());
            rpsm.ExpirationDate = formatter.format(product.getExpiredDate());
            rpsm.Manufacturer = product.getManufacturerGuid();
            responseStructureModel.ProductList.add(rpsm);
        }

        return responseStructureModel;
    }
}
