package serverDatabaseInteraction;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import businesslogic.ApplicationException;
import businesslogic.BaseDocumentStructureModel;

public class BaseDocumentHelper extends PullDataHelper
{
    public BaseDocumentHelper(String url, String userpass) throws ApplicationException, ExecutionException, InterruptedException {
        super(url, userpass);
        this.ClassToCastJson = BaseDocumentModel.class;
    }

    @Override
    public BaseDocumentStructureModel GetData() {
        return (BaseDocumentStructureModel)this.ResultModel;
    }

    @Override
    protected BaseDocumentStructureModel ParseIncomeDataToResultModel(Object inputModel) throws ApplicationException
    {

        BaseDocumentModel Model = (BaseDocumentModel) inputModel;
        if(Model.Error == true)
        {
            throw new ApplicationException(Model.ErrorMessage);
        }

        BaseDocumentStructureModel result = new BaseDocumentStructureModel(Model.DocumentData.Name, Model.DocumentData.Order, false); // TODO : replace false with data from feed

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
                result.Add(productOrderStructureModel);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
