package serverDatabaseInteraction;

import java.util.concurrent.ExecutionException;

import businesslogic.ApplicationException;
import businesslogic.ManufacturerStructureModel;

public class ManufacturerHelper extends PullDataHelper
{


    public ManufacturerHelper(String url, String userpass) throws ApplicationException, ExecutionException, InterruptedException {
        super(url, userpass);
        this.ClassToCastJson = ManufacturerModel.class;
    }

    @Override
    public ManufacturerStructureModel GetData() {
        return (ManufacturerStructureModel) this.ResultModel;
    }

    @Override
    protected Object ParseIncomeDataToResultModel(Object inputModel) throws ApplicationException
    {
        ManufacturerModel model = (ManufacturerModel) inputModel;

        ManufacturerStructureModel ResultModel = new ManufacturerStructureModel();

        for(ManufacturerModel.ManufacturerDetails manufacturer : model.ManufacturerList)
        {
            ResultModel.Add(Byte.valueOf(manufacturer.Code), manufacturer.Name, manufacturer.GUID);
        }

        return ResultModel;
    }

}
