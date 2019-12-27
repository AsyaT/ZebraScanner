package ru.zferma.zebrascanner;

import org.junit.Before;
import org.junit.Test;

import presentation.DataTableControl;
import ScanningCommand.ListViewPresentationModel;
import presentation.ProductListViewModel;

import static junit.framework.TestCase.assertEquals;

public class DataTableControlTest {

    DataTableControl Table;
    @Before
    public void Init()
    {
        Table = new DataTableControl();
        ListViewPresentationModel model = new ListViewPresentationModel(
                "12345",
                "Nomenclature 1",
                "Char 1",
                2.5,
                "111-111-111-111"
        );
        Table.AddOne(model);

        model = new ListViewPresentationModel(
                "67890",
                "Nomenclature 2",
                "Char 2",
                9.7,
                "222-222-222-222"
        );
        Table.AddOne(model);
    }

    @Test
    public void AddNewLine()
    {
        Integer sizeOfList = 2;
        assertEquals(sizeOfList, Table.GetSizeOfList());

        ProductListViewModel presentationModel = new ProductListViewModel(
                "222-222-222-222",
                "2",
                "Char 2",
                "Nomenclature 2",
                "67890",
                "1",
                "9.7");

        ProductListViewModel result =  Table.GetExitingProduct("67890","222-222-222-222");
        assertEquals(presentationModel.getProductGuid(), result.getProductGuid());
        assertEquals(presentationModel.getStringNumber(), result.getStringNumber());
        assertEquals(presentationModel.getCharacteristic(), result.getCharacteristic());
        assertEquals(presentationModel.getNomenclature(), result.getNomenclature());
        assertEquals(presentationModel.getBarCode(), result.getBarCode());
        assertEquals(presentationModel.getCoefficient(), result.getCoefficient());
        assertEquals(presentationModel.getWeight(), result.getWeight());


        assertEquals( Table.GetExitingProduct("9602845","222-222-222-222"), null);
        assertEquals( Table.GetExitingProduct("67890","111-111-111-111"), null);

        Table.RemoveAll();

        sizeOfList = 0;
        assertEquals(sizeOfList, Table.GetSizeOfList());
    }
}
