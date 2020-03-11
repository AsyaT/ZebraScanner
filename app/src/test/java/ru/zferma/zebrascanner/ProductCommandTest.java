package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import models.BarcodeStructureModel;
import models.BaseDocumentStructureModel;
import models.CharacteristicStructureModel;
import models.ManufacturerStructureModel;
import models.NomenclatureStructureModel;
import models.OperationTypesStructureModel;
import models.ProductStructureModel;
import models.ScanningBarcodeStructureModel;
import scanningcommand.ProductCommand;
import businesslogic.*;

public class ProductCommandTest
{
    ProductCommand productCommand;

    @Before
    public void Init()
    {
        BarcodeStructureModel BarcodeStructureModel = new BarcodeStructureModel();

        ArrayList<ProductStructureModel> productList = new ArrayList<>();
        productList.add(new ProductStructureModel(
                "f50d315d-7ca8-11e6-80d7-e4115bea65d2","41dbf472-19d8-11e7-80cb-001e67e5da8c",8.0));
        BarcodeStructureModel.Add("4660017708243", productList);

        productList = new ArrayList<>();
        productList.add(new ProductStructureModel(
                "6130fe3f-93ba-11e8-80cc-a4bf011ce3c3","760d9dfd-93ba-11e8-80cc-a4bf011ce3c3",10.0));
        BarcodeStructureModel.Add("2308107", productList);

        productList = new ArrayList<>();
        productList.add(new ProductStructureModel(
                "b1cc5c45-7ca8-11e6-80d7-e4115bea65d2","b9e89741-ef89-11e6-80cb-001e67e5da8c",4.0));
        BarcodeStructureModel.Add("4660017707116", productList);

        productList = new ArrayList<>();
        productList.add(new ProductStructureModel(
                "7a017e91-bf8e-11e7-80c5-a4bf011ce3c3","8d715b82-bf8e-11e7-80c5-a4bf011ce3c3",1.0));
        productList.add(new ProductStructureModel(
                "7a017e91-bf8e-11e7-80c5-a4bf011ce3c3","641e7835-ed76-11e8-80cd-a4bf011ce3c3",1.0));
        BarcodeStructureModel.Add("4660017706843", productList);

        NomenclatureStructureModel nomenclatureStructureModel = new NomenclatureStructureModel();
        nomenclatureStructureModel.Add("f50d315d-7ca8-11e6-80d7-e4115bea65d2", "Бедрышко куриное \"Здоровая Ферма\", охл.~8,00 кг*1/~8,0 кг/ (гофрокороб, пленка пнд)");
        nomenclatureStructureModel.Add("6130fe3f-93ba-11e8-80cc-a4bf011ce3c3", "Голень куриная \"Здоровая Ферма\", охл.~10,00 кг*1/~10,0 кг/ (пакет пнд, гофрокороб)");
        nomenclatureStructureModel.Add("b1cc5c45-7ca8-11e6-80d7-e4115bea65d2", "Грудка куриная \"Здоровая Ферма\", охл.~0,80 кг*5/~4,0 кг/ (подложка, стрейч)");
        nomenclatureStructureModel.Add("7a017e91-bf8e-11e7-80c5-a4bf011ce3c3", "Ноги куриные СТО, \\\"Здоровая Ферма\\\", зам. 1,00 кг*12/12,0 кг/ (пакет пнд)");

        CharacteristicStructureModel characterisiticStructureModel = new CharacteristicStructureModel();
        characterisiticStructureModel.Add("41dbf472-19d8-11e7-80cb-001e67e5da8c","Метро");
        characterisiticStructureModel.Add("760d9dfd-93ba-11e8-80cc-a4bf011ce3c3","Тандер");
        characterisiticStructureModel.Add("b9e89741-ef89-11e6-80cb-001e67e5da8c","Монетка");
        characterisiticStructureModel.Add("8d715b82-bf8e-11e7-80c5-a4bf011ce3c3","базовая");
        characterisiticStructureModel.Add("641e7835-ed76-11e8-80cd-a4bf011ce3c3","Казахстан");

        ManufacturerStructureModel manufacturerStructureModel = new ManufacturerStructureModel();
        Byte manufacturer_1 = 1;
        manufacturerStructureModel.Add(manufacturer_1, "УРАЛБРОЙЛЕР ЗАО (Ишалино)","23504297-7ee1-11e6-80d7-e4115bea65d2");

        BarcodeProductLogic barcodeProductLogic = new BarcodeProductLogic( BarcodeStructureModel);

        // --------------------

        HashMap<BarcodeTypes, Boolean> permissions = new HashMap<>();
        permissions.put(BarcodeTypes.LocalEAN13, true);
        permissions.put(BarcodeTypes.LocalGS1_EXP, true);

        OperationTypesStructureModel modelPackageListAllowed = new OperationTypesStructureModel(
                "Ротация",
                "Ротация",
                "Ротация",
                "97e2d02c-ad73-11e7-80c4-a4bf011ce3c3",
                permissions,
                true);
        BaseDocumentStructureModel baseDocumentByProductsAndPL = new BaseDocumentStructureModel(
                "The order to compile by products and package lists",
                true,
                false);

        BarcodeScanningLogic barcodeScanningLogic = new BarcodeScanningLogic(modelPackageListAllowed,baseDocumentByProductsAndPL);

        // ---------------------

        BaseDocumentStructureModel baseDocumentStructureModel = new BaseDocumentStructureModel("Заказ");
        baseDocumentStructureModel.Add(new BaseDocumentStructureModel.ProductOrderStructureModel(
                "f50d315d-7ca8-11e6-80d7-e4115bea65d2",
                "41dbf472-19d8-11e7-80cb-001e67e5da8c",
                12.0,2.0,
                6,1));

        BaseDocumentLogic baseDocumentLogic = new BaseDocumentLogic(baseDocumentStructureModel);
        // ----------------------------

        productCommand = new ProductCommand();

        Whitebox.setInternalState(productCommand,"BarcodeProductLogic", barcodeProductLogic);
        Whitebox.setInternalState(productCommand,"barcodeScanningLogic", barcodeScanningLogic);
        Whitebox.setInternalState(productCommand,"baseDocumentLogic", baseDocumentLogic);
    }

    @Test
    public void ParseTestEAN13() throws ParseException, DoesNotExistsInOrderException, ApplicationException {

        ScanningBarcodeStructureModel barcodeStructureModel = new ScanningBarcodeStructureModel("4660017708243", BarcodeTypes.LocalEAN13);

        ProductStructureModel actual = productCommand.ParseAction(barcodeStructureModel);
        ProductStructureModel expected = new ProductStructureModel(
                "f50d315d-7ca8-11e6-80d7-e4115bea65d2","41dbf472-19d8-11e7-80cb-001e67e5da8c",8.0);
        Assert.assertEquals(expected.GetProductGuid(),actual.GetProductGuid());
        Assert.assertEquals(expected.GetCharacteristicGUID(),actual.GetCharacteristicGUID());
        Assert.assertEquals(expected.GetWeight(),actual.GetWeight());
    }

    @Test
    public void ParseTestGS1EXP() throws ParseException, DoesNotExistsInOrderException, ApplicationException
    {
        ScanningBarcodeStructureModel barcodeStructureModel = new ScanningBarcodeStructureModel("0104660017708243310300745610082011190820171908252100001921000", BarcodeTypes.LocalGS1_EXP);
        ProductStructureModel actual = productCommand.ParseAction(barcodeStructureModel);
        ProductStructureModel expected = new ProductStructureModel(
                "f50d315d-7ca8-11e6-80d7-e4115bea65d2","41dbf472-19d8-11e7-80cb-001e67e5da8c",7.456);

        Assert.assertEquals(expected.GetProductGuid(),actual.GetProductGuid());
        Assert.assertEquals(expected.GetCharacteristicGUID(),actual.GetCharacteristicGUID());
        Assert.assertEquals(expected.GetWeight(),actual.GetWeight());
    }

    @Test(expected = DoesNotExistsInOrderException.class)
    public void ParseTestNotInOrder() throws DoesNotExistsInOrderException, ParseException, ApplicationException {
        ScanningBarcodeStructureModel barcodeStructureModel = new ScanningBarcodeStructureModel("0104660017707116310300745610082011190820171908252100001921000", BarcodeTypes.LocalGS1_EXP);
        productCommand.ParseAction(barcodeStructureModel);

    }


    @Test(expected = ApplicationException.class)
    public void ParseTestManufacturerNotFound() throws ApplicationException, DoesNotExistsInOrderException, ParseException {
        ScanningBarcodeStructureModel barcodeStructureModel = new ScanningBarcodeStructureModel("0104660017708243310300745610082011190820171908252100001922000", BarcodeTypes.LocalGS1_EXP);
        productCommand.ParseAction(barcodeStructureModel);

    }

    @Test(expected = ApplicationException.class)
    public void BarcodeNotFoundTest() throws ApplicationException, DoesNotExistsInOrderException,ParseException
    {
        ScanningBarcodeStructureModel barcodeStructureModel = new ScanningBarcodeStructureModel("0199999999999999310300745610082011190820171908252100001922000", BarcodeTypes.LocalGS1_EXP);
        productCommand.ParseAction(barcodeStructureModel);
    }

    @Test
    public void NoOrderTest() throws ApplicationException, DoesNotExistsInOrderException, ParseException
    {
        BaseDocumentLogic baseDocumentLogic = new BaseDocumentLogic(null);

        Whitebox.setInternalState(productCommand,"baseDocumentLogic", baseDocumentLogic);

        ScanningBarcodeStructureModel barcodeStructureModel = new ScanningBarcodeStructureModel("0104660017708243310300745610082011190820171908252100001921000", BarcodeTypes.LocalGS1_EXP);
        ProductStructureModel actual = productCommand.ParseAction(barcodeStructureModel);
        ProductStructureModel expected = new ProductStructureModel(
                "f50d315d-7ca8-11e6-80d7-e4115bea65d2","41dbf472-19d8-11e7-80cb-001e67e5da8c",7.456);

        Assert.assertEquals(expected.GetProductGuid(),actual.GetProductGuid());
        Assert.assertEquals(expected.GetCharacteristicGUID(),actual.GetCharacteristicGUID());
        Assert.assertEquals(expected.GetWeight(),actual.GetWeight());
    }

    @Test
    public void ScanDialogAgain() throws ParseException, DoesNotExistsInOrderException, ApplicationException {

        BaseDocumentLogic baseDocumentLogic = new BaseDocumentLogic(null);
        Whitebox.setInternalState(productCommand,"baseDocumentLogic", baseDocumentLogic);

        ProductStructureModel selectedProduct = new ProductStructureModel("7a017e91-bf8e-11e7-80c5-a4bf011ce3c3","641e7835-ed76-11e8-80cd-a4bf011ce3c3", 1.0);
        HashMap<String, ProductStructureModel> selected = new HashMap<>();
        selected.put("4660017706843", selectedProduct);

        ScannerApplication scannerApplication = new ScannerApplication();
        scannerApplication.SelectedDialogNomenclatures = selected;
        Whitebox.setInternalState(productCommand,"appState", scannerApplication);

        ScanningBarcodeStructureModel barcodeStructureModel = new ScanningBarcodeStructureModel("0104660017706843310301245610082011190820171908252100001921000", BarcodeTypes.LocalGS1_EXP);

        ProductStructureModel actual = productCommand.ParseAction(barcodeStructureModel);

        ProductStructureModel expected = new ProductStructureModel(
                "7a017e91-bf8e-11e7-80c5-a4bf011ce3c3","641e7835-ed76-11e8-80cd-a4bf011ce3c3",12.456);

        Assert.assertEquals(expected.GetProductGuid(),actual.GetProductGuid());
        Assert.assertEquals(expected.GetCharacteristicGUID(),actual.GetCharacteristicGUID());
        Assert.assertEquals(expected.GetWeight(),actual.GetWeight());
    }
}
