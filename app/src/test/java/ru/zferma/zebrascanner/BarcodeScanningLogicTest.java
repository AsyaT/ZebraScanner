package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import businesslogic.ApplicationException;
import businesslogic.BarcodeScanningLogic;
import businesslogic.BarcodeTypes;
import businesslogic.BaseDocumentStructureModel;
import businesslogic.OperationTypesStructureModel;
import businesslogic.ScannerState;

public class BarcodeScanningLogicTest
{
    BarcodeScanningLogic barcodeScanningLogicPLOnly = null;
    BarcodeScanningLogic barcodeScanningLogicPrAndPL = null;
    BarcodeScanningLogic barcodeScanningLogicStopLogic = null;
    BarcodeScanningLogic barcodeScanningLogicPrOnly = null;

    @Before
    public void Init()
    {
        HashMap<BarcodeTypes, Boolean> permissions = new HashMap<>();
        permissions.put(BarcodeTypes.LocalEAN13, true);
        permissions.put(BarcodeTypes.LocalGS1_EXP, false);

        OperationTypesStructureModel modelPackageListAllowed = new OperationTypesStructureModel(
                "Ротация",
                "Ротация",
                "Ротация",
                "97e2d02c-ad73-11e7-80c4-a4bf011ce3c3",
                permissions,
                true);

        OperationTypesStructureModel modelPackageListDenied = new OperationTypesStructureModel(
                "Ротация",
                "Ротация",
                "Ротация",
                "97e2d02c-ad73-11e7-80c4-a4bf011ce3c3",
                permissions,
                false);

        BaseDocumentStructureModel baseDocumentByPackageList = new BaseDocumentStructureModel(
                "The order to compile by package lists only",
                true);
        BaseDocumentStructureModel baseDocumentByProductsAndPL = new BaseDocumentStructureModel(
                "The order to compile by products and package lists",
                false);

       barcodeScanningLogicPLOnly = new BarcodeScanningLogic(modelPackageListAllowed,baseDocumentByPackageList);
       barcodeScanningLogicPrAndPL = new BarcodeScanningLogic(modelPackageListAllowed,baseDocumentByProductsAndPL);
       barcodeScanningLogicStopLogic = new BarcodeScanningLogic(modelPackageListDenied,baseDocumentByPackageList);
       barcodeScanningLogicPrOnly = new BarcodeScanningLogic(modelPackageListDenied,baseDocumentByProductsAndPL);
    }

    @Test
    public void BarcodeAllowed()
    {
        try{
            Assert.assertTrue(barcodeScanningLogicPrAndPL.IsBarcodeTypeAllowedToScan( BarcodeTypes.LocalEAN13));
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
            Assert.assertFalse(barcodeScanningLogicPrAndPL.IsBarcodeTypeAllowedToScan( BarcodeTypes.LocalGS1_EXP ));
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

    @Test
    public void IsGoodTest() throws ApplicationException
    {
         Assert.assertTrue(
                 barcodeScanningLogicPLOnly.IsBarcodeAllowedToScan(ScannerState.PACKAGELIST)
         );

        Assert.assertTrue(
                barcodeScanningLogicPrAndPL.IsBarcodeAllowedToScan(ScannerState.PACKAGELIST)
        );

        Assert.assertTrue(
                barcodeScanningLogicPrAndPL.IsBarcodeAllowedToScan(ScannerState.PRODUCT)
        );
        Assert.assertTrue(
                barcodeScanningLogicPrOnly.IsBarcodeAllowedToScan(ScannerState.PRODUCT)
        );
    }

    @Test(expected = ApplicationException.class)
    public void IsExceptionTest_1_1() throws ApplicationException
    {
        barcodeScanningLogicPLOnly.IsBarcodeAllowedToScan(ScannerState.PRODUCT);
    }
    @Test(expected = ApplicationException.class)
    public void IsExceptionTest_1_2() throws ApplicationException
    {
        barcodeScanningLogicStopLogic.IsBarcodeAllowedToScan(ScannerState.PACKAGELIST);
    }

    @Test(expected = ApplicationException.class)
    public void IsExceptionTest_2_1() throws ApplicationException
    {
        barcodeScanningLogicStopLogic.IsBarcodeAllowedToScan(ScannerState.PRODUCT);
    }

    @Test(expected = ApplicationException.class)
    public void IsExceptionTest_2_2() throws ApplicationException
    {
        barcodeScanningLogicPrOnly.IsBarcodeAllowedToScan(ScannerState.PACKAGELIST);
    }
}
