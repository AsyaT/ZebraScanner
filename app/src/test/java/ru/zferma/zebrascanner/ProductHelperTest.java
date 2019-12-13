package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;

import businesslogic.ProductHelper;
import businesslogic.ProductModel;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ProductHelper.class })
public class ProductHelperTest {

    ProductHelper productHelper;
    ProductModel model;


    public  ProductHelperTest() {
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

        productHelper = new ProductHelper("","");

        Whitebox.setInternalState(productHelper,"Model",model);
    }

    @Test
    public void Test_FindProductByBarcode()
    {
        ProductModel.ProductListModel result = productHelper.FindProductByBarcode("2407394");

        Assert.assertEquals(model.BarCodeList.get(0),result);
    }
}
