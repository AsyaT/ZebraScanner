package ru.zferma.zebrascanner;

import com.symbol.emdk.barcode.ScanDataCollection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import businesslogic.ApplicationException;
import businesslogic.BarcodeScanningLogic;
import businesslogic.OperationTypesStructureModel;

public class BarcodeScanningLogicTest
{
    BarcodeScanningLogic barcodeScanningLogic = null;

    @Before
    public void Init()
    {
        HashMap<ScanDataCollection.LabelType, Boolean> permissions = new HashMap<>();

        ScanDataCollection.LabelType label_1 =ScanDataCollection.LabelType.EAN13;
        permissions.put(label_1, true);
        ScanDataCollection.LabelType label_2 =ScanDataCollection.LabelType.GS1_DATABAR_EXP;
        permissions.put(label_2, false);

        OperationTypesStructureModel model = new OperationTypesStructureModel(
                "Ротация",
                "Ротация",
                "Ротация",
                "97e2d02c-ad73-11e7-80c4-a4bf011ce3c3",
                permissions,
                Boolean.TRUE);

       barcodeScanningLogic = new BarcodeScanningLogic(model);
    }

    @Test
    public void BarcodeAllowed()
    {
        try{
            Assert.assertTrue(barcodeScanningLogic.IsAllowedToScan(ScanDataCollection.LabelType.EAN13));
        }
        catch (ApplicationException ex)
        {
            Assert.fail();
        }
    }

    @Test
    public void BarcodeDisallowed()
    {
        try{
            Assert.assertFalse(barcodeScanningLogic.IsAllowedToScan(ScanDataCollection.LabelType.GS1_DATABAR_EXP));
        }
        catch (ApplicationException ex)
        {
            Assert.assertEquals("Тип GS1_DATABAR_EXP запрещен к сканирванию",ex.getMessage());
        }
    }

    @Test
    public void BarcodeDisallowedNotInList()
    {
        try{
            Assert.assertFalse(barcodeScanningLogic.IsAllowedToScan(ScanDataCollection.LabelType.GS1_DATABAR));
        }
        catch (ApplicationException ex)
        {
            Assert.assertEquals("Тип GS1_DATABAR запрещен к сканирванию",ex.getMessage());
        }
    }
}
