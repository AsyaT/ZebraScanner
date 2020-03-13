package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import businesslogic.BaseDocumentLogic;
import models.BaseDocumentStructureModel;
import businesslogic.DoesNotExistsInOrderException;
import models.ProductStructureModel;

public class BaseDocumentLogicTest {

    BaseDocumentStructureModel dataModel;
    BaseDocumentLogic baseDocumentLogic;

    @Before
    public void Init()
    {
        dataModel = new BaseDocumentStructureModel("Заказ");
        dataModel.Add(new BaseDocumentStructureModel.ProductOrderStructureModel(
                "345-333-444",
                "475-222-22",
                12.0,2.0,
                6,1));

        baseDocumentLogic = new BaseDocumentLogic(dataModel);
    }

    @Test
    public void TestBaseDocumentStructureModel()
    {
        Assert.assertTrue(dataModel.IfProductExists("345-333-444"));
        Assert.assertFalse(dataModel.IfProductExists("000"));
    }

    @Test
    public void ExistsInOrder() throws DoesNotExistsInOrderException
    {
        ProductStructureModel product = new ProductStructureModel("345-333-444","475-222-22",2.0);
        Assert.assertTrue(baseDocumentLogic.IsBaseDocumentScanned());
        Assert.assertTrue(baseDocumentLogic.IsExistsInOrder(product));

    }

    @Test(expected = DoesNotExistsInOrderException.class)
    public void DoesNotExistsInOrder() throws DoesNotExistsInOrderException
    {
        ProductStructureModel product = new ProductStructureModel("5830-444-333", "475-222-22", 2.0);
        Assert.assertTrue(baseDocumentLogic.IsBaseDocumentScanned());
        baseDocumentLogic.IsExistsInOrder(product);
    }

    @Test
    public void BaseDocumentNotScanned() throws DoesNotExistsInOrderException {
        baseDocumentLogic = new BaseDocumentLogic(null);
        Assert.assertFalse(baseDocumentLogic.IsBaseDocumentScanned());
    }
}
