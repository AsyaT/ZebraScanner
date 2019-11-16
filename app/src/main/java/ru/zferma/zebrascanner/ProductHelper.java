package ru.zferma.zebrascanner;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductHelper {

    private ProductModel Model;

    public ProductHelper(String url, String userpass) throws ExecutionException, InterruptedException, UnsupportedEncodingException {
        //String jsonString = "";
        List<ProductModel.ProductListModel> result = (new WebServiceStream()).execute(url,userpass).get();
        try {
          //  jsonString = (new WebService()).execute(url,userpass).get();
/*
jsonString="{\n" +
        "\"BarCodeList\": [\n" +
        "{\n" +
        "\"Barcode\": \"2300083\",\n" +
        "\"PropertiesList\": [\n" +
        "{\n" +
        "\"ProductName\": \"Голень куриная, \\\"Здоровая Ферма\\\", охл., ~0,8 кг*5/ 4,0 кг/ (подложка, стрейч)\",\n" +
        "\"ProductGUID\": \"eee2cde8-7ca8-11e6-80d7-e4115bea65d2\",\n" +
        "\"ProductCharactName\": \"базовая\",\n" +
        "\"ProductCharactGUID\": \"eee2cde9-7ca8-11e6-80d7-e4115bea65d2\",\n" +
        "\"Quant\": \"1\"\n" +
        "},\n" +
        "{\n" +
        "\"ProductName\": \"Голень куриная, \\\"Здоровая Ферма\\\", охл., ~0,8 кг*6/~4,8 кг/ (подложка, стрейч)\",\n" +
        "\"ProductGUID\": \"d07c89e5-7ca8-11e6-80d7-e4115bea65d2\",\n" +
        "\"ProductCharactName\": \"базовая\",\n" +
        "\"ProductCharactGUID\": \"4afec433-1ea6-11e7-80cb-001e67e5da8c\",\n" +
        "\"Quant\": \"4,8\"\n" +
        "}\n" +
        "]\n" +
        "},\n" +
        "{\n" +
        "\"Barcode\": \"2301248\",\n" +
        "\"PropertiesList\": [\n" +
        "{\n" +
        "\"ProductName\": \"Курочка \\\"Здоровая Ферма\\\", охл.~1,40 кг*8/~12,0 кг/ (вакуум. т/усад. пакет)\",\n" +
        "\"ProductGUID\": \"54f54b7c-ed05-11e6-800b-3085a9978fb0\",\n" +
        "\"ProductCharactName\": \"Казахстан\",\n" +
        "\"ProductCharactGUID\": \"b6b78f31-5db1-11e8-80c8-a4bf011ce3c3\",\n" +
        "\"Quant\": \"1\"\n" +
        "}\n" +
        "]\n" +
        "},{\n" +
        "\"Barcode\": \"4630037036817\",\n" +
        "\"PropertiesList\": [\n" +
        "{\n" +
        "\"ProductName\": \"Филе белое цыпленка-бройлера, охл.~25,00 кг*1/~25,0 кг/ (пакет пнд, полимерный ящик)\",\n" +
        "\"ProductGUID\": \"f210c468-8066-11e8-80cc-a4bf011ce3c3\",\n" +
        "\"ProductCharactName\": \"ЗФД\",\n" +
        "\"ProductCharactGUID\": \"f210c469-8066-11e8-80cc-a4bf011ce3c3\",\n" +
        "\"Quant\": \"25\"\n" +
        "}\n" +
        "]\n" +
        "}]}";
*/

        }
        catch (Exception ex)
        {
            ex.getMessage();
        }


        //ProductModel model = g.fromJson(jsonString, ProductModel.class);
        this.Model = new ProductModel();
        this.Model.BarCodeList = result;
    }

    public ProductModel.ProductListModel FindProductByBarcode(String barcodeUniqueIdentifier)
    {
        for(ProductModel.ProductListModel productPosition : Model.BarCodeList)
        {
            if(productPosition.Barcode.equalsIgnoreCase(barcodeUniqueIdentifier))
            {
                return productPosition;
            }
        }
        return null;
    }
}
