package ru.zferma.zebrascanner;

import com.google.gson.Gson;

public class ProductHelper {

    ProductModel Model;

    public ProductHelper(String url, String userpass)
    {
        String jsonString = "";

        try {
            jsonString = (new WebService()).execute(url,userpass).get();

        }
        catch (Exception ex)
        {
            ex.getMessage();
        }

        Gson g = new Gson();
        ProductModel model = g.fromJson(jsonString, ProductModel.class);
        this.Model = model;
    }
}
