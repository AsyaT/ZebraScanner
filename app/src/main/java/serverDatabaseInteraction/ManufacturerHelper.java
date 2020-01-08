package serverDatabaseInteraction;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

import businesslogic.ApplicationException;
import businesslogic.ManufacturerStructureModel;

public class ManufacturerHelper {
    private ManufacturerStructureModel ResultModel;
    private ManufacturerModel InputModel = null;

    public ManufacturerHelper(String url, String userpass)
    {
        String jsonString = null;
        try {
            jsonString = PullJsonData(url,userpass);
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.InputModel = ParseJson(jsonString);

        if(this.InputModel!=null)
        {
            ResultModel = new ManufacturerStructureModel();

            for(ManufacturerModel.ManufacturerDetails manufacturer : InputModel.ManufacturerList)
            {
                ResultModel.Add(Byte.valueOf(manufacturer.Code), manufacturer.Name, manufacturer.GUID);
            }

        }
    }

    public ManufacturerStructureModel GetData()
    {
        return this.ResultModel;
    }

    protected String PullJsonData(String url, String userpass) throws ApplicationException, ExecutionException, InterruptedException {
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

    private ManufacturerModel ParseJson(String jsonString)
    {
        Gson g = new Gson();
        return g.fromJson(jsonString, ManufacturerModel.class);
    }
}
