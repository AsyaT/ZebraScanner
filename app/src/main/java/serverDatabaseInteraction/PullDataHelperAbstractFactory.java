package serverDatabaseInteraction;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

import businesslogic.ApplicationException;


public abstract class PullDataHelperAbstractFactory
{
    private Object ResultModel;
    protected Type ClassToCastJson;
    protected Type ClassToCastResultModel;

    public PullDataHelperAbstractFactory(String url, String userpass ) throws ApplicationException, ExecutionException, InterruptedException
    {
        SetClassesToCast();
        String jsonString = PullStringData(url,userpass);
        Object InputModel = ParseJsonToModel(jsonString, this.ClassToCastJson);
        if(InputModel != null) {
            this.ResultModel = ParseIncomeDataToResultModel(InputModel);
        }
    }

    public PullDataHelperAbstractFactory(Object inputModel)throws ApplicationException
    {
        SetClassesToCast();
        if(inputModel != null) {
            this.ResultModel = ParseIncomeDataToResultModel(inputModel);
        }
    }

    protected abstract void SetClassesToCast();

    public Object GetData()
    {
        if(this.ResultModel.getClass().isInstance(this.ClassToCastResultModel))
        {
            this.ResultModel = this.ClassToCastResultModel.getClass().cast(this.ResultModel);
        }
        return this.ResultModel;
    }

    protected String PullStringData(String url, String userpass) throws ApplicationException, ExecutionException, InterruptedException {

        if(url.isEmpty())
        {
            return null;
        }

        String result =  (new AsyncWebService()).execute(url,userpass).get();

        if(result == null || result.isEmpty())
        {
            throw new ApplicationException("Сервер не отвечает.");
        }
        else {
            return result;
        }
    }

    protected Object ParseJsonToModel(String jsonString, Type classToCast)
    {
        Gson g = new Gson();
        return g.fromJson(jsonString, classToCast);
    }

    protected abstract Object ParseIncomeDataToResultModel(Object inputModel) throws ApplicationException;
}
