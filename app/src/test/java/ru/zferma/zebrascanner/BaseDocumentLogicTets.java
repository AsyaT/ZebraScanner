package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import businesslogic.ApplicationException;
import businesslogic.BaseDocumentLogic;
import businesslogic.BaseDocumentStructureModel;
import businesslogic.ProductStructureModel;

public class BaseDocumentLogicTets {

    BaseDocumentLogic baseDocumentLogic;

    @Before
    public void Init()
    {
        BaseDocumentStructureModel model = new BaseDocumentStructureModel("Заказ");
        model.Add(new BaseDocumentStructureModel.ProductOrderStructureModel(
                "345-333-444",
                "475-222-22",
                12.0,2.0,
                6,1));

        baseDocumentLogic = new BaseDocumentLogic(model);
    }

    @Test
    public void ExistsInOrder()
    {
        ProductStructureModel product = new ProductStructureModel("345-333-444","475-222-22",2.0);
        try
        {
            Assert.assertTrue(baseDocumentLogic.IsExistsInOrder(product));
        }
        catch (ApplicationException e)
        {
            Assert.fail();
        }
    }

    @Test
    public void DoesNotExistsInOrder()
    {
        ProductStructureModel product = new ProductStructureModel("5830-444-333","475-222-22",2.0);
        try
        {
            Assert.assertTrue(baseDocumentLogic.IsExistsInOrder(product));
        }
        catch (ApplicationException e)
        {
            Assert.assertEquals("Этот продукт не содержится в документе-основании", e.getMessage());
        }
    }
}
