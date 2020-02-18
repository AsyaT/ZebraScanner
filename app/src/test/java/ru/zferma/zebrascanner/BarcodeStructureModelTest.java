package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import businesslogic.ApplicationException;
import businesslogic.BarcodeStructureModel;
import businesslogic.CharacterisiticStructureModel;
import businesslogic.NomenclatureStructureModel;
import businesslogic.ProductStructureModel;
import serverDatabaseInteraction.BarcodeHelper;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BarcodeHelper.class })
public class BarcodeStructureModelTest {

    BarcodeStructureModel barcodeStructureModel = new BarcodeStructureModel();
    NomenclatureStructureModel nomenclatureStructureModel = new NomenclatureStructureModel();
    CharacterisiticStructureModel characterisiticStructureModel = new CharacterisiticStructureModel();


    public BarcodeStructureModelTest()
    {
        nomenclatureStructureModel.Add("ddc4578e-e49f-11e7-80c5-a4bf011ce3c3","Филе бедра \\\"Здоровая Ферма\\\", охл.~0,80 кг*6/~4,8 кг/ (подложка, стрейч)");

        characterisiticStructureModel.Add("a947f0a5-3c92-11e8-80c7-a4bf011ce3c3", "Дикси");

        ArrayList<ProductStructureModel> productList = new ArrayList<>();

        ProductStructureModel psm = new ProductStructureModel(
                "ddc4578e-e49f-11e7-80c5-a4bf011ce3c3","a947f0a5-3c92-11e8-80c7-a4bf011ce3c3", 1.0);
        productList.add(psm);

        barcodeStructureModel.Add("2407394",productList);

    }

    @Test
    public void Test_FindProductsByBarcode() throws ApplicationException {
        List<ProductStructureModel> result =  barcodeStructureModel.FindProductByBarcode("2407394");
        Assert.assertEquals(1, result.size());

        Assert.assertEquals("ddc4578e-e49f-11e7-80c5-a4bf011ce3c3",result.get(0).GetProductGuid());
        Assert.assertEquals("a947f0a5-3c92-11e8-80c7-a4bf011ce3c3",result.get(0).GetCharacteristicGUID());
        Assert.assertEquals((Double)1.0,result.get(0).GetWeight());
    }

    @Test
    public void TestFindProductByGuid()
    {
        String productName = nomenclatureStructureModel.FindProductByGuid("ddc4578e-e49f-11e7-80c5-a4bf011ce3c3");
        Assert.assertEquals("Филе бедра \\\"Здоровая Ферма\\\", охл.~0,80 кг*6/~4,8 кг/ (подложка, стрейч)", productName);
    }

    @Test
    public void TestFindCharacteristic()
    {
        String characteristicName = characterisiticStructureModel.FindCharacteristicByGuid("a947f0a5-3c92-11e8-80c7-a4bf011ce3c3");
        Assert.assertEquals("Дикси", characteristicName);
    }

    @Test(expected = ApplicationException.class)
    public void TestBarcodeDoesNotExists() throws ApplicationException
    {
        barcodeStructureModel.FindProductByBarcode("000000");
    }
}
