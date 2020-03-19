package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import businesslogic.ApplicationException;
import businesslogic.FullDataTableControl;
import serverDatabaseInteraction.ResponseModelMaker;

public class ResponseModelMakerTest
{
    public void Init()
    {

    }

    @Test
    public void Test() throws ApplicationException {
        String AccountingAreaId= "05d648a3-f1e0-11e6-80cb-001e67e5da8c";
        String UserId = "1212";
        String BaseDocumentId = "";
        List<FullDataTableControl.Details> Products = new ArrayList<>();
        Products.add(new FullDataTableControl.Details(
                "4c9f2bb0-9c5c-11e8-80cc-a4bf011ce3c3",
                "4c9f2bb1-9c5c-11e8-80cc-a4bf011ce3c3",
                2.1,
                new Date(2020,0,20),
                new Date(2020,2,20),
                "8ed0a430-7ee8-11e6-80d7-e4115bea65d2",
                10,
                "04630037038224"));

        String actual = ResponseModelMaker.MakeResponseJson(AccountingAreaId, UserId,BaseDocumentId, Products);

        String expected = "{\"AccountingAreaGUID\":\"05d648a3-f1e0-11e6-80cb-001e67e5da8c\"," +
                "\"UserID\":\"1212\"," +
                "\"DocumentID\":\"\"," +
                "\"ProductList\":[{" +
                    "\"Product\":\"4c9f2bb0-9c5c-11e8-80cc-a4bf011ce3c3\"," +
                    "\"Charact\":\"4c9f2bb1-9c5c-11e8-80cc-a4bf011ce3c3\"," +
                    "\"ManufactureDate\":\"20200120\"," +
                    "\"ExpirationDate\":\"20200320\"," +
                    "\"Manufacturer\":\"8ed0a430-7ee8-11e6-80d7-e4115bea65d2\"" +
                    "\"PackageList\":\"\""+
                    "\"BarCode\":\"04630037038224\""+
                    "\"Quantity\":\"21.0\"," +
                    "\"PackageCounter\":\"10\"," +
                "}]}";

        Assert.assertEquals(expected,actual);
    }
}
