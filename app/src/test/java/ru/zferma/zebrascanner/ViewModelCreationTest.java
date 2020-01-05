package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.text.ParseException;
import java.util.ArrayList;

import ScanningCommand.ListViewPresentationModel;
import businesslogic.BarcodeStructureModel;
import businesslogic.BarcodeTypes;
import businesslogic.CharacterisiticStructureModel;
import businesslogic.NomenclatureStructureModel;
import businesslogic.ProductLogic;
import serverDatabaseInteraction.ApplicationException;

public class ViewModelCreationTest {

    ProductLogic productLogic;

    @Before
    public void Init()
    {
        BarcodeStructureModel BarcodeStructureModel = new BarcodeStructureModel();
        ArrayList<businesslogic.BarcodeStructureModel.ProductStructureModel> productList = new ArrayList<>();
        productList.add(new BarcodeStructureModel.ProductStructureModel("f50d315d-7ca8-11e6-80d7-e4115bea65d2","41dbf472-19d8-11e7-80cb-001e67e5da8c",8.0));
        BarcodeStructureModel.Add("4660017708243", productList);

        NomenclatureStructureModel nomenclatureStructureModel = new NomenclatureStructureModel();
        nomenclatureStructureModel.Add("f50d315d-7ca8-11e6-80d7-e4115bea65d2", "Бедрышко куриное \"Здоровая Ферма\", охл.~8,00 кг*1/~8,0 кг/ (гофрокороб, пленка пнд)");

        CharacterisiticStructureModel characterisiticStructureModel = new CharacterisiticStructureModel();
        characterisiticStructureModel.Add("41dbf472-19d8-11e7-80cb-001e67e5da8c","Метро");
        productLogic = new ProductLogic(BarcodeStructureModel, nomenclatureStructureModel, characterisiticStructureModel);
    }

    @Test
    public void TestEAN13()
    {
       String scannedBarcode = "04660017708243";

        ListViewPresentationModel actual = null;
        try {
            actual = productLogic.CreateViewModel(scannedBarcode, BarcodeTypes.LocalEAN13).get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        ListViewPresentationModel expected = new ListViewPresentationModel(
                "4660017708243",
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
            actual = productLogic.CreateViewModel(scannedBarcode, BarcodeTypes.LocalEAN13).get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        ListViewPresentationModel expected = new ListViewPresentationModel(
                "2308107",
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
            actual = productLogic.CreateViewModel(scannedBarcode, BarcodeTypes.LocalGS1_EXP).get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        ListViewPresentationModel expected = new ListViewPresentationModel(
                "4660017707116",
                "Грудка куриная \"Здоровая Ферма\", охл.~0,80 кг*5/~4,0 кг/ (подложка, стрейч)",
                "Монетка",
                25.6,
                "b1cc5c45-7ca8-11e6-80d7-e4115bea65d2"
        );

        Assert.assertTrue(new ReflectionEquals(expected).matches(actual));
    }
}
