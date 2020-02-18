package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import businesslogic.*;


public class BarcodeProductLogic_And_ProductLogic_Test {

    BarcodeProductLogic barcodeProductLogic;
    ProductLogic productLogic;

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
                BarcodeStructureModel,
                nomenclatureStructureModel,
                characteristicStructureModel,
                manufacturerStructureModel);

        productLogic = new ProductLogic(nomenclatureStructureModel, characteristicStructureModel,manufacturerStructureModel);
    }

    @Test
    public void TestEAN13()
    {
       String scannedBarcode = "04660017708243";

        ListViewPresentationModel actual = null;
        try {
            ProductStructureModel products = barcodeProductLogic.FindProductByBarcode(scannedBarcode, BarcodeTypes.LocalEAN13).get(0);
            actual = productLogic.CreateListView(products);

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        ListViewPresentationModel expected = new ListViewPresentationModel(
                "Бедрышко куриное \"Здоровая Ферма\", охл.~8,00 кг*1/~8,0 кг/ (гофрокороб, пленка пнд)",
                "Метро",
                8.0,
                "f50d315d-7ca8-11e6-80d7-e4115bea65d2"
        );

        Assert.assertTrue(new ReflectionEquals(expected).matches(actual));

    }

    @Test
    public void TestEAN13Weight()
    {
        String scannedBarcode = "2308107083006";

        ListViewPresentationModel actual = null;
        try {
            ProductStructureModel products = barcodeProductLogic.FindProductByBarcode(scannedBarcode, BarcodeTypes.LocalEAN13).get(0);
            actual = productLogic.CreateListView(products);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        ListViewPresentationModel expected = new ListViewPresentationModel(
                "Голень куриная \"Здоровая Ферма\", охл.~10,00 кг*1/~10,0 кг/ (пакет пнд, гофрокороб)",
                "Тандер",
                8.3,
                "6130fe3f-93ba-11e8-80cc-a4bf011ce3c3"
        );

        Assert.assertTrue(new ReflectionEquals(expected).matches(actual));
    }

    @Test
    public void TestGS1EXP()
    {
        String scannedBarcode = "0104660017707116310302560010082011190120171912252100001921000";

        ListViewPresentationModel actual = null;
        try {
            ProductStructureModel products = barcodeProductLogic.FindProductByBarcode(scannedBarcode, BarcodeTypes.LocalGS1_EXP).get(0);
            actual = productLogic.CreateListView(products);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        ListViewPresentationModel expected = new ListViewPresentationModel(
                "Грудка куриная \"Здоровая Ферма\", охл.~0,80 кг*5/~4,0 кг/ (подложка, стрейч)",
                "Монетка",
                25.6,
                "b1cc5c45-7ca8-11e6-80d7-e4115bea65d2"
        );

        Assert.assertTrue(new ReflectionEquals(expected).matches(actual));

    }

    @Test
    public void CreateSimpleProduct() throws ApplicationException
    {
        ProductStructureModel psm = new ProductStructureModel(
                "6130fe3f-93ba-11e8-80cc-a4bf011ce3c3",
                "760d9dfd-93ba-11e8-80cc-a4bf011ce3c3",
                10.0);
        ListViewPresentationModel actual = productLogic.CreateListView(psm);

        ListViewPresentationModel expected = new ListViewPresentationModel(
                "Голень куриная \"Здоровая Ферма\", охл.~10,00 кг*1/~10,0 кг/ (пакет пнд, гофрокороб)",
                "Тандер",
                10.0,
                "6130fe3f-93ba-11e8-80cc-a4bf011ce3c3"
        );

        Assert.assertEquals(expected.ProductGuid,actual.ProductGuid);
        Assert.assertEquals(expected.Nomenclature,actual.Nomenclature);
        Assert.assertEquals(expected.Characteristic,actual.Characteristic);
        Assert.assertEquals(expected.Weight,actual.Weight);
    }

    @Test(expected = ApplicationException.class)
    public void CreateProductDoesNotExists() throws ApplicationException
    {
        ProductStructureModel psm = new ProductStructureModel(
                "000",
                "000",
                0.0);
        ListViewPresentationModel actual = productLogic.CreateListView(psm);
    }

    @Test
    public void ProductFromPackageList() throws ApplicationException {
        Product_PackageListStructureModel psm = new Product_PackageListStructureModel(
                "6130fe3f-93ba-11e8-80cc-a4bf011ce3c3",
                "760d9dfd-93ba-11e8-80cc-a4bf011ce3c3",
                10.0,
                new Date(2019,9,18),
                new Date(2019,9,28),
                "23504297-7ee1-11e6-80d7-e4115bea65d2",
                1
        );
        ListViewPresentationModel actual = productLogic.CreateListView(psm);

        ListViewPresentationModel expected = new ListViewPresentationModel(
                "Голень куриная \"Здоровая Ферма\", охл.~10,00 кг*1/~10,0 кг/ (пакет пнд, гофрокороб)",
                "Тандер",
                10.0,
                "6130fe3f-93ba-11e8-80cc-a4bf011ce3c3"
        );

        Assert.assertEquals(expected.ProductGuid,actual.ProductGuid);
        Assert.assertEquals(expected.Nomenclature,actual.Nomenclature);
        Assert.assertEquals(expected.Characteristic,actual.Characteristic);
        Assert.assertEquals(expected.Weight,actual.Weight);
    }

    @Test
    public void TestDuplicate() throws ParseException, ApplicationException {
        ArrayList<ProductStructureModel> actual = barcodeProductLogic.FindProductByBarcode("04660017707529", BarcodeTypes.LocalEAN13);

        Assert.assertEquals(2,actual.size());
    }
}
