package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;

import businesslogic.BarcodeHelper;
import businesslogic.ProductModel;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BarcodeHelper.class })
public class BarcodeHelperTest {

    BarcodeHelper barcodeHelper;
    ProductModel model;


    public BarcodeHelperTest() {
        model = new ProductModel();
        model.BarCodeList = new ArrayList<ProductModel.ProductListModel>();

        ProductModel.ProductListModel productListModel = new ProductModel.ProductListModel();
        productListModel.Barcode = "2407394";
        productListModel.PropertiesList = new ArrayList<ProductModel.PropertiesListModel>();

        ProductModel.PropertiesListModel propertiesListModel =new ProductModel.PropertiesListModel();
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
        ProductModel.ProductListModel result = barcodeHelper.FindProductByBarcode("2407394");

        Assert.assertEquals(model.BarCodeList.get(0),result);
    }
}
