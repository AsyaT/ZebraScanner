package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import businesslogic.ApplicationException;
import businesslogic.BarcodeScanningLogic;
import businesslogic.BarcodeTypes;
import businesslogic.OperationTypesStructureModel;

public class BarcodeScanningLogicTest
{
    BarcodeScanningLogic barcodeScanningLogic = null;

    @Before
    public void Init()
    {
        HashMap<BarcodeTypes, Boolean> permissions = new HashMap<>();
        permissions.put(BarcodeTypes.LocalEAN13, true);
        permissions.put(BarcodeTypes.LocalGS1_EXP, false);

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
            Assert.assertTrue(barcodeScanningLogic.IsAllowedToScan( BarcodeTypes.LocalEAN13));
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
            Assert.assertFalse(barcodeScanningLogic.IsAllowedToScan( BarcodeTypes.LocalGS1_EXP ));
        }
        catch (ApplicationException ex)
        {
            Assert.assertEquals("Тип LocalGS1_EXP запрещен к сканирванию",ex.getMessage());
        }
    }

    //TODO: test if scanned not EAN13 or GS1_DATABAR_EXP, but something other, e.g. SSCC
    /*
    @Test
    public void BarcodeDisallowedNotInList()
    {
        try{
            Assert.assertFalse(barcodeScanningLogic.IsAllowedToScan());
        }
        catch (ApplicationException ex)
        {
            Assert.assertEquals("Тип GS1_DATABAR запрещен к сканирванию",ex.getMessage());
        }
    }

     */
}
