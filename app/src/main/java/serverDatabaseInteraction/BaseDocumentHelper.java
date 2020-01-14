package serverDatabaseInteraction;

import com.google.gson.Gson;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import businesslogic.ApplicationException;
import businesslogic.BaseDocumentStructureModel;

public class BaseDocumentHelper {

    private BaseDocumentModel Model;
    private BaseDocumentStructureModel ReturnModel;

    public BaseDocumentHelper(String url, String userpass) throws ApplicationException {
        String jsonString = "";

        try {
            jsonString = (new WebService()).execute(url,userpass).get();
        }
        catch (Exception ex)
        {
            ex.getMessage();
        }

        Gson g = new Gson();
        Model = g.fromJson(jsonString, BaseDocumentModel.class);

        if(Model.Error == true)
        {
            throw new ApplicationException(Model.ErrorMessage);
        }

        this.ReturnModel = new BaseDocumentStructureModel(Model.DocumentData.Name);

        for(BaseDocumentModel.ProductListModel plm : Model.DocumentData.ProductList)
        {
            NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
            try {
                Number OrderedNumber = format.parse(plm.Quantity);
                Number DoneNumber = format.parse(plm.QuantityDone);

            BaseDocumentStructureModel.ProductOrderStructureModel productOrderStructureModel = new BaseDocumentStructureModel.ProductOrderStructureModel(
                    plm.Product,plm.Charact,
                    OrderedNumber.doubleValue(), DoneNumber.doubleValue(),
                    plm.Pieces, plm.PiecesDone
            );
            this.ReturnModel.Add(productOrderStructureModel);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public BaseDocumentStructureModel GetModel()
    {
        return ReturnModel;
    }

}
