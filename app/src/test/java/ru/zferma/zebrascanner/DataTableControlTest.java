package ru.zferma.zebrascanner;

import android.view.View;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import models.ListViewPresentationModel;
import presentation.DataTableControl;
import presentation.ProductListViewModel;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class DataTableControlTest {

    DataTableControl Table;

    @Mock
    private View testView;

    @Before
    public void Init()
    {
        MockitoAnnotations.initMocks(this);

        Table = new DataTableControl();
        ListViewPresentationModel model = new ListViewPresentationModel(
                "Nomenclature 1",
                "Char 1",
                2.5,
                "111-111-111-111",
                1
        );
        Table.AddOne(model);
        Table.AddOne(model);
        Table.AddOne(model);

        model = new ListViewPresentationModel(
                "Nomenclature 2",
                "Char 2",
                9.7,
                "222-222-222-222",
                1
        );
        Table.AddOne(model);
        Table.AddOne(model);

        model = new ListViewPresentationModel(
                "Nomenclature 3",
                "Char 1",
                8.2,
                "789-168-038-557",
                1
        );
        Table.AddOne(model);

        model = new ListViewPresentationModel(
                "Nomenclature 4",
                "Char 2",
                25.6,
                "493-58-33",
                1
        );
        Table.AddOne(model);
        Table.AddOne(model);

        model = new ListViewPresentationModel(
                "Nomenclature 5",
                "Char 9",
                3.7,
                "0385-67-2446",
                7
        );
        Table.AddOne(model);
    }

    @Test
    public void SizeOfList()
    {
        Integer sizeOfList = 5;
        assertEquals(sizeOfList, Table.GetSizeOfList());
    }

    @Test
    public void FindProductTest()
    {
        ProductListViewModel expected = new ProductListViewModel(
                "222-222-222-222",
                "2",
                "Char 2",
                "Nomenclature 2",
                "2",
                "19.4");

        ProductListViewModel actual =  Table.FindProduct("222-222-222-222");
        CompareModels(actual,expected);

    }

    @Test
    public void AddExtraOne()
    {
        ListViewPresentationModel model = new ListViewPresentationModel(
                "Nomenclature 5",
                "Char 9",
                3.7,
                "0385-67-2446",
                3
        );
        Table.AddOne(model);

        ProductListViewModel actual  = Table.FindProduct("0385-67-2446");

        ProductListViewModel expected = new ProductListViewModel(
                "0385-67-2446",
                "5",
                "Char 9",
                "Nomenclature 5",
                "10",
                "37.0");

        CompareModels(expected,actual);

        AddExtraOneDifferentWeight();
    }

    public void AddExtraOneDifferentWeight()
    {
        ListViewPresentationModel model = new ListViewPresentationModel(
                "Nomenclature 5",
                "Char 9",
                6.5,
                "0385-67-2446",
                4
        );
        Table.AddOne(model);

        ProductListViewModel actual  = Table.FindProduct("0385-67-2446");

        ProductListViewModel expected = new ProductListViewModel(
                "0385-67-2446",
                "5",
                "Char 9",
                "Nomenclature 5",
                "14",
                "63.0");

        CompareModels(expected,actual);
    }

    private void CompareModels(ProductListViewModel actual, ProductListViewModel expected)
    {
        assertEquals(actual.getProductGuid(), expected.getProductGuid());
        assertEquals(actual.getStringNumber(), expected.getStringNumber());
        assertEquals(actual.getCharacteristic(), expected.getCharacteristic());
        assertEquals(actual.getNomenclature(), expected.getNomenclature());
        assertEquals(actual.getCoefficient(), expected.getCoefficient());
        assertEquals(actual.getSummaryWeight(), expected.getSummaryWeight());
    }

    @Test
    public void FindProductNotExists()
    {
        assertEquals( null,  Table.FindProduct("777-111-111-111"));

    }

    @Test
    public void IsProductExistsTest()
    {
        assertTrue(Table.IsProductExists("493-58-33"));
    }

    @Test
    public void IsProductDoesNotExistsTest()
    {
        assertFalse(Table.IsProductExists("024-556-224"));
    }

    @Test
    public void GetItemByIndexTest()
    {
        ProductListViewModel result1 = Table.GetItemByIndex(0);
        Assert.assertEquals("1", result1.getStringNumber());

        ProductListViewModel result2 = Table.GetItemByIndex(2);
        Assert.assertEquals("3", result2.getStringNumber());

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void GetItemByIndexExceptionTest()
    {
            Table.GetItemByIndex(100);
    }

    @Test
    public void RemoveProduct()
    {
        Table.ItemClicked(testView,"789-168-038-557");
        Table.ItemClicked(testView,"111-111-111-111");
        Table.RemoveSelected();

        ProductListViewModel result = Table.FindProduct("493-58-33");

        assertEquals("2",result.getStringNumber());
        assertEquals("2", result.getCoefficient());
        assertEquals("51.2", result.getSummaryWeight());


        ProductListViewModel removed = Table.FindProduct("111-111-111-111");
        assertNull(removed);

        Table.ItemClicked(testView, "493-58-33");
        Table.RemoveSelected();

        assertEquals(2,Table.GetDataTable().size());

        removed = Table.FindProduct("493-58-33");
        assertNull(removed);
    }

    @Test
    @After
    public void RemoveAll()
    {
        Table.RemoveAll();

        Integer sizeOfList = 0;
        assertEquals( Table.GetSizeOfList(), sizeOfList);
    }

}
