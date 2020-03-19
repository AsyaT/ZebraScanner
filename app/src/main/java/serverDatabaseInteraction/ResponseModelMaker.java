package serverDatabaseInteraction;

import com.google.gson.Gson;

import businesslogic.ApplicationException;
import businesslogic.FullDataTableControl;
import ru.zferma.zebrascanner.ScannerApplication;

public class ResponseModelMaker
{
    public static String MakeResponseJson(ScannerApplication appState) throws ApplicationException {

        ResponseStructureModel responseStructureModel = AnswerToServer(appState);
        return ConvertModelToJson(responseStructureModel);
    }

    protected static String ConvertModelToJson(ResponseStructureModel model)
    {
        Gson gson = new Gson();
        return gson.toJson(model);
    }

    protected static ResponseStructureModel AnswerToServer(ScannerApplication scannerApplication) throws ApplicationException {
        ResponseStructureModel responseStructureModel = new ResponseStructureModel();
        responseStructureModel.AccountingAreaGUID = scannerApplication.GetLocationContext().GetAccountingAreaGUID();
        responseStructureModel.UserID = scannerApplication.GetBadgeGuid();
        responseStructureModel.DocumentID = scannerApplication.GetBaseDocument().GetOrderId();

        for(FullDataTableControl.Details product : scannerApplication.ScannedProductsToSend.GetListOfProducts())
        {
            ResponseStructureModel.ResponseProductStructureModel rpsm = new ResponseStructureModel.ResponseProductStructureModel();
            rpsm.Product = product.getProductGuid();
            rpsm.Charact = product.getCharacteristicGuid();
            rpsm.Quantity = String.valueOf(product.getWeight() * product.getScannedQuantity());
            rpsm.PackageCounter = String.valueOf(product.getScannedQuantity());
            rpsm.ManufactureDate = String.valueOf(product.getProductionDate());
            rpsm.ExpirationDate = String.valueOf(product.getExpiredDate());
            rpsm.Manufacturer = product.getManufacturerGuid();
            responseStructureModel.ProductList.add(rpsm);
        }

        return responseStructureModel;
    }
}
