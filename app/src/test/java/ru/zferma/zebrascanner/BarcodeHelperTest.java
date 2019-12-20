package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;

import serverDatabaseInteraction.BarcodeHelper;
import serverDatabaseInteraction.BarcodeModel;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BarcodeHelper.class })
public class BarcodeHelperTest {

    BarcodeHelper barcodeHelper;
    BarcodeModel model;


    public BarcodeHelperTest() {
        model = new BarcodeModel();
        model.BarCodeList = new ArrayList<BarcodeModel.ProductListModel>();

        BarcodeModel.ProductListModel productListModel = new BarcodeModel.ProductListModel();
        productListModel.Barcode = "2407394";
        productListModel.PropertiesList = new ArrayList<BarcodeModel.PropertiesListModel>();

        BarcodeModel.PropertiesListModel propertiesListModel =new BarcodeModel.PropertiesListModel();
        propertiesListModel.ProductName="Филе бедра \\\"Здоровая Ферма\\\", охл.~0,80 кг*6/~4,8 кг/ (подложка, стрейч)";
        propertiesListModel.ProductGUID="ddc4578e-e49f-11e7-80c5-a4bf011ce3c3";
        propertiesListModel.ProductCharactName="Дикси";
        propertiesListModel.ProductCharactGUID="a947f0a5-3c92-11e8-80c7-a4bf011ce3c3";
        propertiesListModel.Quant="1";

        productListModel.PropertiesList.add(propertiesListModel);
        model.BarCodeList.add(productListModel);

        barcodeHelper = new BarcodeHelper("","");

        Whitebox.setInternalState(barcodeHelper,"Model",model);
    }

    @Test
    public void Test_FindProductByBarcode()
    {
        BarcodeModel.ProductListModel result = barcodeHelper.FindProductByBarcode("2407394");

        Assert.assertEquals(model.BarCodeList.get(0),result);
    }
}
