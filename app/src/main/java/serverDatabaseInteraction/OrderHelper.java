package serverDatabaseInteraction;

import com.google.gson.Gson;

import businesslogic.OrderStructureModel;

public class OrderHelper {

    private OrderModel Model;
    private OrderStructureModel ReturnModel;

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
    }

    public OrderStructureModel GetModel()
    {
        return ReturnModel;
    }

}
