package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import businesslogic.ApplicationException;
import businesslogic.BarcodeTypes;
import businesslogic.FullDataTableControl;
import businesslogic.ListViewPresentationModel;
import businesslogic.ResponseModelGenerator;
import models.BarcodeStructureModel;
import models.CharacteristicStructureModel;
import models.ManufacturerStructureModel;
import models.NomenclatureStructureModel;
import models.ProductStructureModel;
import models.Product_PackageListStructureModel;
import models.ScanningBarcodeStructureModel;

public class ResponseModelGeneratorTest
{
    ResponseModelGenerator ModelGenerator;

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
                "f821d239-0d59-11e7-80cb-001e67e5da8c","6ff4018a-108b-11e7-80cb-001e67e5da8c",1.0));
        BarcodeStructureModel.Add("2370650", productList);

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
        nomenclatureStructureModel.Add("f821d239-0d59-11e7-80cb-001e67e5da8c", "Бедрышко куриное \"Турбаслинский бройлер\", охл., ~0,80 кг*6/~4,8 кг/ (подложка, стрейч)");
        nomenclatureStructureModel.Add("b1cc5c45-7ca8-11e6-80d7-e4115bea65d2", "Грудка куриная \"Здоровая Ферма\", охл.~0,80 кг*5/~4,0 кг/ (подложка, стрейч)");

        CharacteristicStructureModel characteristicStructureModel = new CharacteristicStructureModel();
        characteristicStructureModel.Add("41dbf472-19d8-11e7-80cb-001e67e5da8c","Метро");
        characteristicStructureModel.Add("6ff4018a-108b-11e7-80cb-001e67e5da8c","Тандер");
        characteristicStructureModel.Add("b9e89741-ef89-11e6-80cb-001e67e5da8c","Монетка");

        ManufacturerStructureModel manufacturerStructureModel = new ManufacturerStructureModel();
        Byte manufacturer_1 = 1;
        manufacturerStructureModel.Add(manufacturer_1, "УРАЛБРОЙЛЕР ЗАО (Ишалино)","23504297-7ee1-11e6-80d7-e4115bea65d2");
        Byte manufacturer_3 = 3;
        manufacturerStructureModel.Add(manufacturer_3, "УРАЛБРОЙЛЕР ЗАО (Кунашак)","5ef1b244-c11e-11e6-80c7-001e67e5da8b");

        ModelGenerator = new ResponseModelGenerator(nomenclatureStructureModel, characteristicStructureModel,manufacturerStructureModel);
    }

    @Test
    public void ListViewProductEAN13Test() throws ParseException, ApplicationException {
        String scannedBarcode = "04660017708243";
        ScanningBarcodeStructureModel barcode = new ScanningBarcodeStructureModel(scannedBarcode, BarcodeTypes.LocalEAN13);
        ProductStructureModel product = new ProductStructureModel(
                "f50d315d-7ca8-11e6-80d7-e4115bea65d2",
                "41dbf472-19d8-11e7-80cb-001e67e5da8c",
                8.0);

        ListViewPresentationModel actual = ModelGenerator.CreateListViewResponse(barcode, product);

        ListViewPresentationModel expected = new ListViewPresentationModel(
                "Бедрышко куриное \"Здоровая Ферма\", охл.~8,00 кг*1/~8,0 кг/ (гофрокороб, пленка пнд)",
                "Метро",
                8.0,
                "f50d315d-7ca8-11e6-80d7-e4115bea65d2",
                1
        );

        Assert.assertEquals(expected.ProductGuid, actual.ProductGuid);
        Assert.assertEquals(expected.Nomenclature,actual.Nomenclature);
        Assert.assertEquals(expected.Characteristic,actual.Characteristic);
        Assert.assertEquals(expected.Coefficient,actual.Coefficient);
        Assert.assertEquals(expected.Weight,actual.Weight);
    }

    @Test
    public void ListViewProductEAN13WeightTest() throws ParseException, ApplicationException {
        String scannedBarcode = "2370650050006";
        ScanningBarcodeStructureModel barcode = new ScanningBarcodeStructureModel(scannedBarcode, BarcodeTypes.LocalEAN13);
        ProductStructureModel product = new ProductStructureModel(
                "f821d239-0d59-11e7-80cb-001e67e5da8c",
                "6ff4018a-108b-11e7-80cb-001e67e5da8c",
                1.0);

        ListViewPresentationModel actual = ModelGenerator.CreateListViewResponse(barcode, product);

        ListViewPresentationModel expected = new ListViewPresentationModel(
                "Бедрышко куриное \"Турбаслинский бройлер\", охл., ~0,80 кг*6/~4,8 кг/ (подложка, стрейч)",
                "Тандер",
                5.0,
                "f821d239-0d59-11e7-80cb-001e67e5da8c",
                1
        );

        Assert.assertEquals(expected.ProductGuid, actual.ProductGuid);
        Assert.assertEquals(expected.Nomenclature,actual.Nomenclature);
        Assert.assertEquals(expected.Characteristic,actual.Characteristic);
        Assert.assertEquals(expected.Coefficient,actual.Coefficient);
        Assert.assertEquals(expected.Weight,actual.Weight);
    }

    @Test
    public void ListViewProductGS1tTest() throws ParseException, ApplicationException {
        String scannedBarcode = "0104660017707116310300410010082011190120171912252100001921000";
        ScanningBarcodeStructureModel barcode = new ScanningBarcodeStructureModel(scannedBarcode, BarcodeTypes.LocalGS1_EXP);
        ProductStructureModel product = new ProductStructureModel(
                "b1cc5c45-7ca8-11e6-80d7-e4115bea65d2",
                "b9e89741-ef89-11e6-80cb-001e67e5da8c",
                4.0);

        ListViewPresentationModel actual = ModelGenerator.CreateListViewResponse(barcode, product);

        ListViewPresentationModel expected = new ListViewPresentationModel(
                "Грудка куриная \"Здоровая Ферма\", охл.~0,80 кг*5/~4,0 кг/ (подложка, стрейч)",
                "Монетка",
                4.1,
                "b1cc5c45-7ca8-11e6-80d7-e4115bea65d2",
                1
        );

        Assert.assertEquals(expected.ProductGuid, actual.ProductGuid);
        Assert.assertEquals(expected.Nomenclature,actual.Nomenclature);
        Assert.assertEquals(expected.Characteristic,actual.Characteristic);
        Assert.assertEquals(expected.Coefficient,actual.Coefficient);
        Assert.assertEquals(expected.Weight,actual.Weight);
    }

    @Test
    public void ListViewPackageListTest() throws ApplicationException {
        Product_PackageListStructureModel plProduct = new Product_PackageListStructureModel(
                "b1cc5c45-7ca8-11e6-80d7-e4115bea65d2",
                "b9e89741-ef89-11e6-80cb-001e67e5da8c",
                4.1,
                new Date(2020,2,14),
                new Date(2020,2,21),
                "5ef1b244-c11e-11e6-80c7-001e67e5da8b",
                5);

        ListViewPresentationModel actual = ModelGenerator.CreateListViewResponse( plProduct);

        ListViewPresentationModel expected = new ListViewPresentationModel(
                "Грудка куриная \"Здоровая Ферма\", охл.~0,80 кг*5/~4,0 кг/ (подложка, стрейч)",
                "Монетка",
                4.1,
                "b1cc5c45-7ca8-11e6-80d7-e4115bea65d2",
                5
        );

        Assert.assertEquals(expected.ProductGuid, actual.ProductGuid);
        Assert.assertEquals(expected.Nomenclature,actual.Nomenclature);
        Assert.assertEquals(expected.Characteristic,actual.Characteristic);
        Assert.assertEquals(expected.Coefficient,actual.Coefficient);
        Assert.assertEquals(expected.Weight,actual.Weight);

    }

    @Test
    public void FullDataTableProductEAN13Test() throws ParseException, ApplicationException {
        String scannedBarcode = "04660017708243";
        ScanningBarcodeStructureModel barcode = new ScanningBarcodeStructureModel(scannedBarcode, BarcodeTypes.LocalEAN13);
        ProductStructureModel product = new ProductStructureModel(
                "f50d315d-7ca8-11e6-80d7-e4115bea65d2",
                "41dbf472-19d8-11e7-80cb-001e67e5da8c",
                8.0);

        FullDataTableControl.Details actual = ModelGenerator.CreateFullDataTableResponse(barcode, product);

        FullDataTableControl.Details expected = new FullDataTableControl.Details(
                "f50d315d-7ca8-11e6-80d7-e4115bea65d2",
                "41dbf472-19d8-11e7-80cb-001e67e5da8c",
                8.0,
                null,
                null,
                null,
                1
        );

        Assert.assertEquals(expected.getProductGuid(), actual.getProductGuid());
        Assert.assertEquals(expected.getCharacteristicGuid(), actual.getCharacteristicGuid());
        Assert.assertEquals(expected.getManufacturerGuid(), actual.getManufacturerGuid());
        Assert.assertEquals(expected.getProductionDate(), actual.getProductionDate());
        Assert.assertEquals(expected.getExpiredDate(), actual.getExpiredDate());
        Assert.assertEquals(expected.getWeight(), actual.getWeight());
        Assert.assertEquals(expected.getScannedQuantity(), actual.getScannedQuantity());
    }

    @Test
    public void FullDataTableProductEAN13WeightTest() throws ParseException, ApplicationException {
        String scannedBarcode = "2370650050006";
        ScanningBarcodeStructureModel barcode = new ScanningBarcodeStructureModel(scannedBarcode, BarcodeTypes.LocalEAN13);
        ProductStructureModel product = new ProductStructureModel(
                "f821d239-0d59-11e7-80cb-001e67e5da8c",
                "6ff4018a-108b-11e7-80cb-001e67e5da8c",
                1.0);

        FullDataTableControl.Details actual = ModelGenerator.CreateFullDataTableResponse(barcode, product);

        FullDataTableControl.Details expected = new FullDataTableControl.Details(
                "f821d239-0d59-11e7-80cb-001e67e5da8c",
                "6ff4018a-108b-11e7-80cb-001e67e5da8c",
                5.0,
                null,
                null,
                null,
                1
        );

        Assert.assertEquals(expected.getProductGuid(), actual.getProductGuid());
        Assert.assertEquals(expected.getCharacteristicGuid(), actual.getCharacteristicGuid());
        Assert.assertEquals(expected.getManufacturerGuid(), actual.getManufacturerGuid());
        Assert.assertEquals(expected.getProductionDate(), actual.getProductionDate());
        Assert.assertEquals(expected.getExpiredDate(), actual.getExpiredDate());
        Assert.assertEquals(expected.getWeight(), actual.getWeight());
        Assert.assertEquals(expected.getScannedQuantity(), actual.getScannedQuantity());
    }

    @Test
    public void FullDataTableProductGS1Test() throws ParseException, ApplicationException
    {
        String scannedBarcode = "0104660017707116310300410010082011190120171912252100001921000";
        ScanningBarcodeStructureModel barcode = new ScanningBarcodeStructureModel(scannedBarcode, BarcodeTypes.LocalGS1_EXP);
        ProductStructureModel product = new ProductStructureModel(
                "b1cc5c45-7ca8-11e6-80d7-e4115bea65d2",
                "b9e89741-ef89-11e6-80cb-001e67e5da8c",
                4.0);

        FullDataTableControl.Details actual = ModelGenerator.CreateFullDataTableResponse(barcode, product);

        FullDataTableControl.Details expected = new FullDataTableControl.Details(
                "b1cc5c45-7ca8-11e6-80d7-e4115bea65d2",
                "b9e89741-ef89-11e6-80cb-001e67e5da8c",
                4.1,
                new Date(2019,12,20),
                new Date(2019,12,25),
                "23504297-7ee1-11e6-80d7-e4115bea65d2",
                1
        );

        Assert.assertEquals(expected.getProductGuid(), actual.getProductGuid());
        Assert.assertEquals(expected.getCharacteristicGuid(), actual.getCharacteristicGuid());
        Assert.assertEquals(expected.getManufacturerGuid(), actual.getManufacturerGuid());
        Assert.assertEquals(expected.getProductionDate(), actual.getProductionDate());
        Assert.assertEquals(expected.getExpiredDate(), actual.getExpiredDate());
        Assert.assertEquals(expected.getWeight(), actual.getWeight());
        Assert.assertEquals(expected.getScannedQuantity(), actual.getScannedQuantity());
    }

    @Test
    public void FullDataTablePackageListTest()
    {
        Product_PackageListStructureModel plProduct = new Product_PackageListStructureModel(
                "b1cc5c45-7ca8-11e6-80d7-e4115bea65d2",
                "b9e89741-ef89-11e6-80cb-001e67e5da8c",
                4.1,
                new Date(2020,2,14),
                new Date(2020,2,21),
                "5ef1b244-c11e-11e6-80c7-001e67e5da8b",
                5);

        FullDataTableControl.Details actual = ModelGenerator.CreateFullDataTableResponse(plProduct);

        FullDataTableControl.Details expected = new FullDataTableControl.Details(
                "b1cc5c45-7ca8-11e6-80d7-e4115bea65d2",
                "b9e89741-ef89-11e6-80cb-001e67e5da8c",
                4.1,
                new Date(2020,2,14),
                new Date(2020,2,21),
                "5ef1b244-c11e-11e6-80c7-001e67e5da8b",
                5
        );

        Assert.assertEquals(expected.getProductGuid(), actual.getProductGuid());
        Assert.assertEquals(expected.getCharacteristicGuid(), actual.getCharacteristicGuid());
        Assert.assertEquals(expected.getManufacturerGuid(), actual.getManufacturerGuid());
        Assert.assertEquals(expected.getProductionDate(), actual.getProductionDate());
        Assert.assertEquals(expected.getExpiredDate(), actual.getExpiredDate());
        Assert.assertEquals(expected.getWeight(), actual.getWeight());
        Assert.assertEquals(expected.getScannedQuantity(), actual.getScannedQuantity());
    }

    @Test
    public void StringResponseProductEAN13Test() throws ParseException, ApplicationException {
        String scannedBarcode = "04660017708243";
        ScanningBarcodeStructureModel barcode = new ScanningBarcodeStructureModel(scannedBarcode, BarcodeTypes.LocalEAN13);
        ProductStructureModel product = new ProductStructureModel(
                "f50d315d-7ca8-11e6-80d7-e4115bea65d2",
                "41dbf472-19d8-11e7-80cb-001e67e5da8c",
                8.0);

        String actual = ModelGenerator.CreateStringResponse(barcode, product);
        String expected = "Штрих-код: 04660017708243"
                + "\nНоменклатура: Бедрышко куриное \"Здоровая Ферма\", охл.~8,00 кг*1/~8,0 кг/ (гофрокороб, пленка пнд)"
                + "\nХарактеристика: Метро"
                + "\nВес: 8.0 кг";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void StringResponseProductEAN13WeightTest() throws ParseException, ApplicationException {
        String scannedBarcode = "2370650050006";
        ScanningBarcodeStructureModel barcode = new ScanningBarcodeStructureModel(scannedBarcode, BarcodeTypes.LocalEAN13);
        ProductStructureModel product = new ProductStructureModel(
                "f821d239-0d59-11e7-80cb-001e67e5da8c",
                "6ff4018a-108b-11e7-80cb-001e67e5da8c",
                1.0);

        String actual = ModelGenerator.CreateStringResponse(barcode, product);
        String expected = "Штрих-код: 2370650050006"
                + "\nНоменклатура: Бедрышко куриное \"Турбаслинский бройлер\", охл., ~0,80 кг*6/~4,8 кг/ (подложка, стрейч)"
                + "\nХарактеристика: Тандер"
                + "\nВес: 5.0 кг";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void StringResponseProductEAN13ErrorTest() throws ParseException, ApplicationException {
        String scannedBarcode = "000000";
        ScanningBarcodeStructureModel barcode = new ScanningBarcodeStructureModel(scannedBarcode, BarcodeTypes.LocalEAN13);
        ProductStructureModel product = new ProductStructureModel(
                "0",
                "0",
                0.0);

        String actual = ModelGenerator.CreateStringResponse(barcode, product);
        String expected = "Штрих-код: 000000"
                + "\nНоменклатура: Продукт с таким GUID 0 не найден"
                + "\nХарактеристика: Характеристика продкута с GUID 0 не найдена"
                + "\nВес: 0.0 кг";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void StringResponseProductGS1Test() throws ParseException, ApplicationException {
        String scannedBarcode = "0104660017707116310300410010082011190120171912252100001921000";
        ScanningBarcodeStructureModel barcode = new ScanningBarcodeStructureModel(scannedBarcode, BarcodeTypes.LocalGS1_EXP);
        ProductStructureModel product = new ProductStructureModel(
                "b1cc5c45-7ca8-11e6-80d7-e4115bea65d2",
                "b9e89741-ef89-11e6-80cb-001e67e5da8c",
                4.0);

        String actual = ModelGenerator.CreateStringResponse(barcode, product);
        String expected = "Штрих-код: 4660017707116"
                + "\nНоменклатура: Грудка куриная \"Здоровая Ферма\", охл.~0,80 кг*5/~4,0 кг/ (подложка, стрейч)"
                + "\nХарактеристика: Монетка"
                + "\nВес: 4.1 кг"
                + "\nНомер партии: 0820"
                + "\nДата производства: 20-01-2019"
                + "\nДата истечения срока годности: 25-12-2019"
                + "\nСерийный номер: 00001"
                + "\nВнутренний код производителя: 1 - УРАЛБРОЙЛЕР ЗАО (Ишалино)"
                + "\nВнутренний код оборудования: 0" ;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void StringResponseProductGS1FailTest() throws ParseException, ApplicationException {
        String scannedBarcode = "0100000000000001310302560010082011190120171912252100001920000";
        ScanningBarcodeStructureModel barcode = new ScanningBarcodeStructureModel(scannedBarcode, BarcodeTypes.LocalGS1_EXP);
        ProductStructureModel product = new ProductStructureModel(
                "000",
                "000",
                0.0);

        String actual = ModelGenerator.CreateStringResponse(barcode, product);
        String expected = "Штрих-код: 1"
                + "\nНоменклатура: Продукт с таким GUID 000 не найден"
                + "\nХарактеристика: Характеристика продкута с GUID 000 не найдена"
                + "\nВес: 25.6 кг"
                + "\nНомер партии: 0820"
                + "\nДата производства: 20-01-2019"
                + "\nДата истечения срока годности: 25-12-2019"
                + "\nСерийный номер: 00001"
                + "\nВнутренний код производителя: 0 - Производитель с номером 0 не найден"
                + "\nВнутренний код оборудования: 0" ;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void StringResponsePackageListTest()
    {
        Product_PackageListStructureModel plProduct = new Product_PackageListStructureModel(
                "b1cc5c45-7ca8-11e6-80d7-e4115bea65d2",
                "b9e89741-ef89-11e6-80cb-001e67e5da8c",
                4.1,
                new Date(2020,2,14),
                new Date(2020,2,21),
                "5ef1b244-c11e-11e6-80c7-001e67e5da8b",
                5);

        String actual = ModelGenerator.CreateStringResponse(plProduct);

        String expected =
                "Номенклатура: Грудка куриная \"Здоровая Ферма\", охл.~0,80 кг*5/~4,0 кг/ (подложка, стрейч)"
                + "\nХарактеристика: Монетка"
                + "\nВес: 4.1 кг"
                + "\nДата производства: 14-02-2020"
                + "\nДата истечения срока годности: 21-02-2020"
                + "\nПроизводитель: УРАЛБРОЙЛЕР ЗАО (Кунашак)"
                + "\nКоличество: 5";

        Assert.assertEquals(expected, actual);
    }
}
