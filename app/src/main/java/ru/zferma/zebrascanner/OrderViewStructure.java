package ru.zferma.zebrascanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import static ru.zferma.zebrascanner.SettingsActivity.APP_1C_PASSWORD;
import static ru.zferma.zebrascanner.SettingsActivity.APP_1C_SERVER;
import static ru.zferma.zebrascanner.SettingsActivity.APP_1C_USERNAME;
import static ru.zferma.zebrascanner.SettingsActivity.APP_PREFERENCES;

public class OrderViewStructure {

    public OrderViewModel Model;

    public OrderViewStructure(String orderGuid)
    {

        String jsonString = "";

        Context context = getContext();
        SharedPreferences spSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String userpass =  spSettings.getString(APP_1C_USERNAME,"") + ":" + spSettings.getString(APP_1C_PASSWORD,"");
        String url= "http://"+ spSettings.getString(APP_1C_SERVER,"")+"/erp_troyan/hs/TSD_Feed/ClientOrders/v1/GetByGUID?GUID="+orderGuid;

        try {
            jsonString = (new WebService()).execute(url,userpass).get();
        }
        catch (Exception ex)
        {
            ex.getMessage();
        }

        Gson g = new Gson();
        OrderModel inputModel = g.fromJson(jsonString, OrderModel.class);

        // Make WebRequest 10.74.255.29/erp_troyan/hs/TSD_Feed/ClientOrders/v1/GetByGUID?GUID=
        // Parse request
        // Find all products and keep them in table
    }
}
