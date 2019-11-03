package ru.zferma.zebrascanner;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class OperationTypes {

    private String jsonString=" {'Error': 'False','AccountingAreasAndTypes': [{'OperationType': 'Ротация','AccountingAreas': [{'Name': 'Ротация','GUID': '97e2d02c-ad73-11e7-80c4-a4bf011ce3c3'}]},{'OperationType': 'Ротация','AccountingAreas': [{'Name': 'Ротация','GUID': '97e2d02c-ad73-11e7-80c4-a4bf011ce3c3'}]},{'OperationType': 'Ротация','AccountingAreas': [{'Name': 'Ротация','GUID': '97e2d02c-ad73-11e7-80c4-a4bf011ce3c3'}]},{'OperationType': 'Упаковочный лист','AccountingAreas': [{'Name': 'Упаковочный лист','GUID': '05d648a3-f1e0-11e6-80cb-001e67e5da8c'}]},{'OperationType': 'Реализация','AccountingAreas': [{'Name': 'Отгрузка ТД ЗФ','GUID': '6afd4795-f1e0-11e6-80cb-001e67e5da8c'}]},{'OperationType': 'Приемка','AccountingAreas': [{'Name': 'Приемка на 6-4-1','GUID': '414d48d4-f210-11e6-80cb-001e67e5da8c'},{'Name': 'Приемка на 6-4-2','GUID': 'b50985a2-ddad-11e8-80cd-a4bf011ce3c3'}]},{'OperationType': 'Инвентаризация','AccountingAreas': [{'Name': 'Инвентаризация','GUID': '7b1a3cbd-f210-11e6-80cb-001e67e5da8c'}]},{'OperationType': 'Передача возвратов','AccountingAreas': [{'Name': 'Передача возвратов ТД ЗФ','GUID': '0440c7d4-9251-11e9-80d1-a4bf011ce3c3'}]}]}\n";

    private OperationTypesAndAccountingAreasModel InputModel;
    private String Username ;
    private String Password ;
    private String ServerDomain ;

    public OperationTypes(String serverDomain, String username, String password)
    {
        this.Username = username;
        this.Password = password;
        this.ServerDomain = serverDomain;

        Gson g = new Gson();
        OperationTypesAndAccountingAreasModel inputModel = g.fromJson(ReadJsonFromUrl(), OperationTypesAndAccountingAreasModel.class);

        this.InputModel = inputModel;
    }

    private String ReadJsonFromUrl()
    {
        URL url;


        if(ServerDomain.isEmpty() == false)
        {
            try {

                url = new URL( "http://" + ServerDomain+"/erp_troyan/hs/TSD_Feed/AccountingArea/v1/GetListUserName="+ Username);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                String userpass = Username + ":" + Password;
                String header = "Basic " + new String(android.util.Base64.encode(userpass.getBytes(), android.util.Base64.NO_WRAP));
                urlConnection.setRequestProperty ("Authorization", header);

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();

                urlConnection.disconnect();

                return line;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
        {
            return "";
        }

        return "";
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
