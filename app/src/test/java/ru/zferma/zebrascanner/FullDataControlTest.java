package ru.zferma.zebrascanner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import businesslogic.ApplicationException;
import businesslogic.BarcodeTypes;
import businesslogic.FullDataTableControl;
import models.ScanningBarcodeStructureModel;

public class FullDataControlTest {

    FullDataTableControl fullDataTableControl;

    @Before
    public void Init()
    {
        fullDataTableControl = new FullDataTableControl();

        ScanningBarcodeStructureModel scannedBarcode = null;
        try {
            scannedBarcode = new ScanningBarcodeStructureModel("(01)04660017707529(3103)004900(10)0820(11)190820(17)190825(21)00001(92)3000", BarcodeTypes.LocalGS1_EXP);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        catch (ApplicationException e)
        {
            e.printStackTrace();
        }

        FullDataTableControl.Details details = new FullDataTableControl.Details("ddc4578e-e49f-11e7-80c5-a4bf011ce3c3",
                "400211e8-a6e7-11e9-80d1-a4bf011ce3c3",
                10.0,
                new Date(2019,8,20),
                new Date(2019,8,25),
                "5ef1b244-c11e-11e6-80c7-001e67e5da8b",
                1,
                scannedBarcode.getFullBarcode(),
                "");

        fullDataTableControl.Add(  details);
    }

    @Test
    public void AddSame_Test()
    {
        ScanningBarcodeStructureModel scannedBarcode = null;
        try {
            scannedBarcode = new ScanningBarcodeStructureModel("(01)04660017707529(3103)004900(10)0820(11)190820(17)190825(21)00002(92)3000", BarcodeTypes.LocalGS1_EXP);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        fullDataTableControl.Add(new FullDataTableControl.Details(
                "ddc4578e-e49f-11e7-80c5-a4bf011ce3c3",
                "400211e8-a6e7-11e9-80d1-a4bf011ce3c3",
                10.0,
                new Date(2019,8,20),
                new Date(2019,8,25),
                "5ef1b244-c11e-11e6-80c7-001e67e5da8b",
                1,
                scannedBarcode.getFullBarcode(),
                ""));

        Assert.assertEquals(2, fullDataTableControl.GetListOfProducts().size());

        Integer quantityFirstRow = 1;
        Assert.assertEquals(quantityFirstRow, fullDataTableControl.GetListOfProducts().get(0).getScannedQuantity());

        Integer quantitySecondRow = 1;
        Assert.assertEquals(quantitySecondRow, fullDataTableControl.GetListOfProducts().get(1).getScannedQuantity());

        //Barcode different

        try {
            scannedBarcode = new ScanningBarcodeStructureModel("(01)04660088807529(3103)001000(10)0820(11)190820(17)190825(21)00003(92)3000", BarcodeTypes.LocalGS1_EXP);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        fullDataTableControl.Add(new FullDataTableControl.Details(
                "ddc4578e-e49f-11e7-80c5-a4bf011ce3c3",
                "400211e8-a6e7-11e9-80d1-a4bf011ce3c3",
                1.0,
                new Date(2019,8,20),
                new Date(2019,8,25),
                "5ef1b244-c11e-11e6-80c7-001e67e5da8b",
                1,
                scannedBarcode.getFullBarcode(),
                ""));

        Assert.assertEquals(3, fullDataTableControl.GetListOfProducts().size());

        // Weight different

        try {
            scannedBarcode = new ScanningBarcodeStructureModel("(01)04660088807529(3103)025600(10)0820(11)190820(17)190825(21)00004(92)3000", BarcodeTypes.LocalGS1_EXP);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        fullDataTableControl.Add(new FullDataTableControl.Details(
                "ddc4578e-e49f-11e7-80c5-a4bf011ce3c3",
                "400211e8-a6e7-11e9-80d1-a4bf011ce3c3",
                25.6,
                new Date(2019,8,20),
                new Date(2019,8,25),
                "5ef1b244-c11e-11e6-80c7-001e67e5da8b",
                9,
                scannedBarcode.getFullBarcode(),
                ""));

        Assert.assertEquals(4, fullDataTableControl.GetListOfProducts().size());

        // Date different

        try {
            scannedBarcode = new ScanningBarcodeStructureModel("(01)04660088807529(3103)025600(10)0820(11)191220(17)191225(21)00005(92)3000", BarcodeTypes.LocalGS1_EXP);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        fullDataTableControl.Add(new FullDataTableControl.Details(
                "ddc4578e-e49f-11e7-80c5-a4bf011ce3c3",
                "400211e8-a6e7-11e9-80d1-a4bf011ce3c3",
                25.6,
                new Date(2019,11,20),
                new Date(2019,11,25),
                "5ef1b244-c11e-11e6-80c7-001e67e5da8b",
                1,
                scannedBarcode.getFullBarcode(),
                ""));

        Assert.assertEquals(5, fullDataTableControl.GetListOfProducts().size());

        // Different manufacturer

        try {
            scannedBarcode = new ScanningBarcodeStructureModel("(01)04660088807529(3103)025600(10)0820(11)190120(17)190125(21)00006(92)1000", BarcodeTypes.LocalGS1_EXP);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        fullDataTableControl.Add(new FullDataTableControl.Details(
                "ddc4578e-e49f-11e7-80c5-a4bf011ce3c3",
                "400211e8-a6e7-11e9-80d1-a4bf011ce3c3",
                25.6,
                new Date(2019,0,20),
                new Date(2019,0,25),
                "23504297-7ee1-11e6-80d7-e4115bea65d2",
                1,
                scannedBarcode.getFullBarcode(),
                ""));

        Assert.assertEquals(6, fullDataTableControl.GetListOfProducts().size());
    }

    @Test
    public void AddEAN13_Test()
    {
        ScanningBarcodeStructureModel scannedBarcode = null;

        try {
            scannedBarcode = new ScanningBarcodeStructureModel("04660088807529", BarcodeTypes.LocalEAN13);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        fullDataTableControl.Add(new FullDataTableControl.Details(
                "ddc4578e-e49f-11e7-80c5-a4bf011ce3c3",
                "400211e8-a6e7-11e9-80d1-a4bf011ce3c3",
                10.0,
                null,
                null,
                null,
                1,
                scannedBarcode.getFullBarcode(),
                ""));

        Assert.assertEquals(2, fullDataTableControl.GetListOfProducts().size());
    }

    @Test
    public void FindProductByGuidTest()
    {
        try{
            ArrayList<FullDataTableControl.Details> result =  Whitebox.invokeMethod(
                    fullDataTableControl,"FindProductsByGuid","ddc4578e-e49f-11e7-80c5-a4bf011ce3c3");
            Assert.assertEquals(1,result.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    @After
    public void ItemClickedAndRemoved()
    {
        fullDataTableControl.ItemIsClicked("ddc4578e-e49f-11e7-80c5-a4bf011ce3c3");
        fullDataTableControl.RemoveSelected();

        Assert.assertEquals(0,fullDataTableControl.GetListOfProducts().size());
    }

}
