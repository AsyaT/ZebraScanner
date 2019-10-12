package ru.zferma.zebrascanner;

import java.util.HashMap;
import java.util.Map;

public class OrderCollection {

    private Map<String, IncomeCollectionModel> Data;

    public OrderCollection()
    {
        Data = new HashMap<String,IncomeCollectionModel>();
        Data.put("9785389076990",new IncomeCollectionModel("Cat-cat",1, 0.2));
        Data.put("9785431508530",new IncomeCollectionModel("Little car",1, 0.01));
        Data.put("4607097079818",new IncomeCollectionModel("Corn flacks",1, 0.5));
        Data.put("7322540387483",new IncomeCollectionModel("Libress Super",1, 0.01));
        Data.put("7322540581171",new IncomeCollectionModel("Libress Night",1, 0.01));
        Data.put("2203383",new IncomeCollectionModel("Пимидоры весовые на веточках",1, 0.0));
        Data.put("2203233",new IncomeCollectionModel("Кабачки",1, 0.0));
        Data.put("04630037036817",new IncomeCollectionModel("Филе цыпленка",1, 0.0));
    }

    public IncomeCollectionModel IsBarcodeExists(String barcodeUniqueIdentifier)
    {
        return Data.get(barcodeUniqueIdentifier);
    }
}
