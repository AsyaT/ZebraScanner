package ru.zferma.zebrascanner;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.Gson;

public class OperationTypes {

    private String jsonString="{'Error': 'False','AccountingAreasAndTypes': [{'OperationType': 'Ротация','AccountingAreas': [{'Name': 'Ротация','GUID': '97e2d02c-ad73-11e7-80c4-a4bf011ce3c3'}" +            "]}," +            "{'OperationType': 'Упаковочный лист','AccountingAreas': [{'Name': 'Упаковочный лист','GUID': '05d648a3-f1e0-11e6-80cb-001e67e5da8c'}]},{'OperationType': 'Реализация','AccountingAreas': [{'Name': 'Отгрузка ТД ЗФ','GUID': '6afd4795-f1e0-11e6-80cb-001e67e5da8c'}]},{'OperationType': 'Приемка','AccountingAreas': [{'Name': 'Приемка на 6-4-1','GUID': '414d48d4-f210-11e6-80cb-001e67e5da8c'},{'Name': 'Приемка на 6-4-2','GUID': 'b50985a2-ddad-11e8-80cd-a4bf011ce3c3'}]},{'OperationType': 'Инвентаризация','AccountingAreas': [{'Name': 'Инвентаризация','GUID': '7b1a3cbd-f210-11e6-80cb-001e67e5da8c'}]},{'OperationType': 'Передача возвратов','AccountingAreas': [{'Name': 'Передача возвратов ТД ЗФ','GUID': '0440c7d4-9251-11e9-80d1-a4bf011ce3c3'}]}]}";
   // private String jsonString="{'Error': 'False','AccountingAreasAndTypes': 'aaa'}";
    private OperationTypesAndAccountingAreasModel InputModel;

    public OperationTypes()
    {
        Gson g = new Gson();
        OperationTypesAndAccountingAreasModel inputModel = g.fromJson(jsonString,OperationTypesAndAccountingAreasModel.class);

        this.InputModel = inputModel;
    }

    public OperationTypesAndAccountingAreasModel GetData()
    {
        return InputModel;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
}
