package ru.zferma.zebrascanner;

import com.google.gson.Gson;

import java.util.ArrayList;

public class OperationTypes {

    private OperationTypesAndAccountingAreasModel InputModel;


    public OperationTypes(String jsonString)
    {

        Gson g = new Gson();
        OperationTypesAndAccountingAreasModel inputModel = g.fromJson(jsonString, OperationTypesAndAccountingAreasModel.class);

        this.InputModel = inputModel;
    }


    public OperationTypesAndAccountingAreasModel GetData()
    {
        return InputModel;
    }

    public boolean HasSeveralAccountingAreas(String operationTypeName)
    {
        for(OperationTypesAndAccountingAreasModel.OperationTypeModel otModel: InputModel.AccountingAreasAndTypes)
        {
            if (otModel.OperationType == operationTypeName && otModel.AccountingAreas.size()>1)
            {
                return true;
            }
        }

        return false;
    }

    public ArrayList<String> GetAccountingAreas(String operationTypeName)
    {
        ArrayList<String> result = new ArrayList<>();
        for(OperationTypesAndAccountingAreasModel.OperationTypeModel otModel: InputModel.AccountingAreasAndTypes)
        {
            if(((String)otModel.OperationType).equalsIgnoreCase((String)operationTypeName))
            {
                for(OperationTypesAndAccountingAreasModel.AccountingAreaModel accountingAreaModel: otModel.AccountingAreas)
                {
                    result.add(accountingAreaModel.Name);
                }
            }
        }
        return result;
    }

}
