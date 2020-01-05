package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import businesslogic.BarcodeTypes;
import businesslogic.FullDataTableControl;
import businesslogic.ScanningBarcodeStructureModel;

public class FullDataControlTest {

    FullDataTableControl fullDataTableControl;

    @Before
    public void Init()
    {
        fullDataTableControl = new FullDataTableControl();

        ScanningBarcodeStructureModel scannedBarcode = null;
        try {
            scannedBarcode = new ScanningBarcodeStructureModel("0104660017707529310300490010082011190820171908252100001923000", BarcodeTypes.LocalGS1_EXP);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        fullDataTableControl.Add(
                "ddc4578e-e49f-11e7-80c5-a4bf011ce3c3",
                "400211e8-a6e7-11e9-80d1-a4bf011ce3c3",
                10.0,
                scannedBarcode);
    }

    @Test
    public void AddSame_Test()
    {
        ScanningBarcodeStructureModel scannedBarcode = null;
        try {
            scannedBarcode = new ScanningBarcodeStructureModel("0104660017707529310300490010082011190820171908252100001923000", BarcodeTypes.LocalGS1_EXP);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        fullDataTableControl.Add(
                "ddc4578e-e49f-11e7-80c5-a4bf011ce3c3",
                "400211e8-a6e7-11e9-80d1-a4bf011ce3c3",
                10.0,
                scannedBarcode);

        Assert.assertEquals(1, fullDataTableControl.GetListOfProducts().size());

        Integer quantity = 2;

        Assert.assertEquals(quantity, fullDataTableControl.GetListOfProducts().get(0).getScannedQuantity());

    }

    @Test
    public void AddDifferent_Test()
    {
        //Barcode different

        ScanningBarcodeStructureModel scannedBarcode = null;
        try {
            scannedBarcode = new ScanningBarcodeStructureModel("0104660088807529310300490010082011190820171908252100001923000", BarcodeTypes.LocalGS1_EXP);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        fullDataTableControl.Add(
                "ddc4578e-e49f-11e7-80c5-a4bf011ce3c3",
                "400211e8-a6e7-11e9-80d1-a4bf011ce3c3",
                1.0,
                scannedBarcode);

        Assert.assertEquals(2, fullDataTableControl.GetListOfProducts().size());

        // Weight different

        try {
            scannedBarcode = new ScanningBarcodeStructureModel("0104660088807529310302560010082011190820171908252100001923000", BarcodeTypes.LocalGS1_EXP);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        fullDataTableControl.Add(
                "ddc4578e-e49f-11e7-80c5-a4bf011ce3c3",
                "400211e8-a6e7-11e9-80d1-a4bf011ce3c3",
                25.0,
                scannedBarcode);

        Assert.assertEquals(3, fullDataTableControl.GetListOfProducts().size());

        // Date different

        try {
            scannedBarcode = new ScanningBarcodeStructureModel("0104660088807529310302560010082011190120171912252100001923000", BarcodeTypes.LocalGS1_EXP);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        fullDataTableControl.Add(
                "ddc4578e-e49f-11e7-80c5-a4bf011ce3c3",
                "400211e8-a6e7-11e9-80d1-a4bf011ce3c3",
                4.0,
                scannedBarcode);

        Assert.assertEquals(4, fullDataTableControl.GetListOfProducts().size());

        // Date producer

        try {
            scannedBarcode = new ScanningBarcodeStructureModel("0104660088807529310302560010082011190120171912252100001921000", BarcodeTypes.LocalGS1_EXP);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        fullDataTableControl.Add(
                "ddc4578e-e49f-11e7-80c5-a4bf011ce3c3",
                "400211e8-a6e7-11e9-80d1-a4bf011ce3c3",
                1.0,
                scannedBarcode);

        Assert.assertEquals(5, fullDataTableControl.GetListOfProducts().size());
    }

    @Test
    public void AddEAN13_Test()
    {
        ScanningBarcodeStructureModel scannedBarcode = null;

        try {
            scannedBarcode = new ScanningBarcodeStructureModel("04660088807529", BarcodeTypes.LocalEAN13);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        fullDataTableControl.Add(
                "ddc4578e-e49f-11e7-80c5-a4bf011ce3c3",
                "400211e8-a6e7-11e9-80d1-a4bf011ce3c3",
                10.0,
                scannedBarcode);

        Assert.assertEquals(2, fullDataTableControl.GetListOfProducts().size());
    }
}
