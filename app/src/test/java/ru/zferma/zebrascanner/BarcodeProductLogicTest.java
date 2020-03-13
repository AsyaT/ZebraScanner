package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;

import businesslogic.ApplicationException;
import businesslogic.BarcodeProductLogic;
import models.BarcodeStructureModel;
import businesslogic.BarcodeTypes;
import models.CharacteristicStructureModel;
import models.ManufacturerStructureModel;
import models.NomenclatureStructureModel;
import models.ProductStructureModel;
import models.ScanningBarcodeStructureModel;


public class BarcodeProductLogicTest {

    BarcodeProductLogic barcodeProductLogic;

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
        productList.add(new ProductStructureModel(
                "b1cc5c45-7ca8-11e6-80d7-e4115bea65d2","b9e89741-ef89-11e6-80cb-001e67e5da8c",1.0));
        BarcodeStructureModel.Add("4660017707116", productList);

        productList = new ArrayList<>();
        productList.add(new ProductStructureModel(
                "f50d315d-7ca8-11e6-80d7-e4115bea65d2","41dbf472-19d8-11e7-80cb-001e67e5da8c",8.0));
        productList.add(new ProductStructureModel(
                "b1cc5c45-7ca8-11e6-80d7-e4115bea65d2","b9e89741-ef89-11e6-80cb-001e67e5da8c",4.0));
        productList.add(new ProductStructureModel(
                "b1cc5c45-7ca8-11e6-80d7-e4115bea65d2","b9e89741-ef89-11e6-80cb-001e67e5da8c",4.0));
        BarcodeStructureModel.Add("4660017707529", productList);

        NomenclatureStructureModel nomenclatureStructureModel = new NomenclatureStructureModel();
        nomenclatureStructureModel.Add("f50d315d-7ca8-11e6-80d7-e4115bea65d2", "Бедрышко куриное \"Здоровая Ферма\", охл.~8,00 кг*1/~8,0 кг/ (гофрокороб, пленка пнд)");
        nomenclatureStructureModel.Add("6130fe3f-93ba-11e8-80cc-a4bf011ce3c3", "Голень куриная \"Здоровая Ферма\", охл.~10,00 кг*1/~10,0 кг/ (пакет пнд, гофрокороб)");
        nomenclatureStructureModel.Add("b1cc5c45-7ca8-11e6-80d7-e4115bea65d2", "Грудка куриная \"Здоровая Ферма\", охл.~0,80 кг*5/~4,0 кг/ (подложка, стрейч)");

        CharacteristicStructureModel characteristicStructureModel = new CharacteristicStructureModel();
        characteristicStructureModel.Add("41dbf472-19d8-11e7-80cb-001e67e5da8c","Метро");
        characteristicStructureModel.Add("760d9dfd-93ba-11e8-80cc-a4bf011ce3c3","Тандер");
        characteristicStructureModel.Add("b9e89741-ef89-11e6-80cb-001e67e5da8c","Монетка");

        ManufacturerStructureModel manufacturerStructureModel = new ManufacturerStructureModel();
        Byte manufacturer_1 = 1;
        manufacturerStructureModel.Add(manufacturer_1, "УРАЛБРОЙЛЕР ЗАО (Ишалино)","23504297-7ee1-11e6-80d7-e4115bea65d2");

        barcodeProductLogic = new BarcodeProductLogic(
                BarcodeStructureModel);

    }

    @Test
    public void SinglProducts() throws ParseException, ApplicationException {

        ScanningBarcodeStructureModel barcodeStructureModel = new ScanningBarcodeStructureModel("04660017708243", BarcodeTypes.LocalEAN13);

        ArrayList<ProductStructureModel> actual = barcodeProductLogic.FindProductByBarcode(barcodeStructureModel.getUniqueIdentifier());

        Assert.assertEquals(1, actual.size());
    }

    @Test
    public void TestSkipDuplicatedProducts() throws ParseException, ApplicationException {

        ScanningBarcodeStructureModel barcodeStructureModel = new ScanningBarcodeStructureModel("04660017707529", BarcodeTypes.LocalEAN13);

        ArrayList<ProductStructureModel> actual = barcodeProductLogic.FindProductByBarcode(barcodeStructureModel.getUniqueIdentifier());

        Assert.assertEquals(2, actual.size());
    }

    @Test
    public void TestSkipDuplicatedProductsWeightDiff() throws ParseException, ApplicationException {

        ScanningBarcodeStructureModel barcodeStructureModel = new ScanningBarcodeStructureModel("04660017707116", BarcodeTypes.LocalEAN13);

        ArrayList<ProductStructureModel> actual = barcodeProductLogic.FindProductByBarcode(barcodeStructureModel.getUniqueIdentifier());

        Assert.assertEquals(2, actual.size());
    }
}
