package serverDatabaseInteraction;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

import businesslogic.ApplicationException;


public abstract class PullDataHelper
{
    protected Object ResultModel;
    protected  Type ClassToCast;

    public PullDataHelper(String url, String userpass ) throws ApplicationException, ExecutionException, InterruptedException
    {
        String jsonString = PullStringData(url,userpass);
        Object InputModel = ParseJsonToModel(jsonString, ClassToCast);
        if(InputModel != null) {
            this.ResultModel = ParseIncomeDataToResultModel(InputModel);
        }
    }

    public PullDataHelper(Object inputModel)throws ApplicationException
    {
        if(inputModel != null) {
            this.ResultModel = ParseIncomeDataToResultModel(inputModel);
        }
    }

    public abstract Object GetData();

    protected String PullStringData(String url, String userpass) throws ApplicationException, ExecutionException, InterruptedException {

        if(url.isEmpty())
        {
            return null;
        }

        String result = null;
        result =  (new WebService()).execute(url,userpass).get();
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
