package serverDatabaseInteraction;

import com.google.gson.Gson;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

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

        this.ReturnModel = new OrderStructureModel(Model.OrderName);

        for(OrderModel.ProductListModel plm : Model.ProductList)
        {
            NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
            try {
                Number OrderedNumber = format.parse(plm.Ordered);
                Number DoneNumber = format.parse(plm.Done);
                Number LeftNumber = format.parse(plm.Left);

            OrderStructureModel.ProductOrderStructureModel productOrderStructureModel = new OrderStructureModel.ProductOrderStructureModel(
                    plm.Product,plm.Charact,
                    OrderedNumber.doubleValue(), DoneNumber.doubleValue(), LeftNumber.doubleValue(),
                    plm.PiecesOrdered, plm.PiecesDone, plm.PiecesLeft
            );
            this.ReturnModel.Add(productOrderStructureModel);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public OrderStructureModel GetModel()
    {
        return ReturnModel;
    }

}
