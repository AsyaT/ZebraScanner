package businesslogic;

import com.google.gson.Gson;

public class OrderHelper {

    public OrderModel Model;

    public OrderHelper(String url, String userpass)
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
        Model = g.fromJson(jsonString, OrderModel.class);


        // Find all products and keep them in table
    }
}
