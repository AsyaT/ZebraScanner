package ru.zferma.zebrascanner;

import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ScanningCommand.ListViewPresentationModel;
import presentation.DataTableControl;
import presentation.ProductListViewModel;

import static junit.framework.TestCase.assertEquals;

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
                "12345",
                "Nomenclature 1",
                "Char 1",
                2.5,
                "111-111-111-111"
        );
        Table.AddOne(model);
        Table.AddOne(model);
        Table.AddOne(model);

        model = new ListViewPresentationModel(
                "67890",
                "Nomenclature 2",
                "Char 2",
                9.7,
                "222-222-222-222"
        );
        Table.AddOne(model);
        Table.AddOne(model);

        model = new ListViewPresentationModel(
                "7927922108",
                "Nomenclature 3",
                "Char 1",
                8.2,
                "789-168-038-557"
        );
        Table.AddOne(model);

        model = new ListViewPresentationModel(
                "92460265",
                "Nomenclature 4",
                "Char 2",
                25.6,
                "493-58-33"
        );
        Table.AddOne(model);
        Table.AddOne(model);
    }

    @Test
    public void SizeOfList()
    {
        Integer sizeOfList = 4;
        assertEquals(sizeOfList, Table.GetSizeOfList());
    }

    @Test
    public void GetNewLine()
    {
        ProductListViewModel presentationModel = new ProductListViewModel(
                "222-222-222-222",
                "2",
                "Char 2",
                "Nomenclature 2",
                "67890",
                "2",
                "19.4");

        ProductListViewModel result =  Table.GetExitingProduct("67890","222-222-222-222");
        CompareModels(result,presentationModel);

        result = Table.GetExitingProduct("222-222-222-222");
        CompareModels(result,presentationModel);
    }

    private void CompareModels(ProductListViewModel actual, ProductListViewModel expected)
    {
        assertEquals(actual.getProductGuid(), expected.getProductGuid());
        assertEquals(actual.getStringNumber(), expected.getStringNumber());
        assertEquals(actual.getCharacteristic(), expected.getCharacteristic());
        assertEquals(actual.getNomenclature(), expected.getNomenclature());
        assertEquals(actual.getBarCode(), expected.getBarCode());
        assertEquals(actual.getCoefficient(), expected.getCoefficient());
        assertEquals(actual.getWeight(), expected.getWeight());
    }

    @Test
    public void FindProduct()
    {
        assertEquals( null, Table.GetExitingProduct("9602845","222-222-222-222"));
        assertEquals( null,  Table.GetExitingProduct("67890","111-111-111-111"));

    }

    @Test
    public void RemoveProduct()
    {
        Table.ItemClicked(testView,2);
        Table.ItemClicked(testView,0);
        Table.RemoveSelected();

        ProductListViewModel result = Table.GetExitingProduct("493-58-33");

        assertEquals(result.getStringNumber(),"2");
        assertEquals(result.getCoefficient(),"2");
        assertEquals(result.getWeight(),"51.2");
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
