package presentation;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import java.util.ArrayList;
import java.util.Comparator;

import businesslogic.ListViewPresentationModel;

public class DataTableControl {

    private ArrayList<ProductListViewModel> DataTable;
    private ArrayList<Integer> ItemsToDelete;

    public Integer GetSizeOfList()
    {
        return DataTable.size();
    }

    public DataTableControl()
    {
        DataTable = new ArrayList<ProductListViewModel>();
        ItemsToDelete = new ArrayList<Integer>();
    }

    public ArrayList<ProductListViewModel> GetDataTable()
    {
        return DataTable;
    }

    public ProductListViewModel FindProduct(String productGuid )
    {
        for(ProductListViewModel product : DataTable)
        {
            if(product.getProductGuid().equalsIgnoreCase(productGuid))
            {
                return product;
            }
        }

        return null;
    }

    public Boolean IsProductExists(String productGuid)
    {
        for(ProductListViewModel product : DataTable)
        {
            if(product.getProductGuid().equalsIgnoreCase(productGuid))
            {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

    public void ItemClicked(View view, int index)
    {
        if(ItemsToDelete.contains(index))
        {
            view.setBackgroundColor(Color.WHITE);
            ItemsToDelete.remove((Integer) index);
        }
        else
        {
            view.setBackgroundColor(Color.YELLOW);
            ItemsToDelete.add(index);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void AddOne(ListViewPresentationModel model)
    {
        ProductListViewModel result = null;

        ProductListViewModel existingTableModel =  this.FindProduct( model.ProductGuid);
        if(existingTableModel == null)
        {
            Integer newStringNumber = this.GetSizeOfList()+1;
            result = new ProductListViewModel(
                    model.ProductGuid,
                    newStringNumber.toString(),
                    model.Characteristic,
                    model.Nomenclature,
                    "1",
                    model.Weight.toString());
        }
        else
        {
            DataTable.remove(existingTableModel);

            Integer newCoefficient = Integer.parseInt( existingTableModel.getCoefficient()) + 1;
            Double newWeight = Double.parseDouble( existingTableModel.getWeight()) + model.Weight;

            result = new ProductListViewModel(
                    model.ProductGuid,
                    existingTableModel.getStringNumber(),
                    model.Characteristic,
                    model.Nomenclature,
                    newCoefficient.toString(),
                    newWeight.toString()
            );
        }

        DataTable.add(result);
        DataTable.sort(new Comparator<ProductListViewModel>() {
            @Override
            public int compare(ProductListViewModel product1, ProductListViewModel product2) {
                return product1.getStringNumber().compareTo(product2.getStringNumber());
            }
        });
    }

    public void RemoveSelected()
    {
        for (Integer x : ItemsToDelete) {
            ProductListViewModel removedItem = DataTable.remove((int) x);

            for(ProductListViewModel leftProduct : DataTable)
            {
                if(Integer.valueOf( leftProduct.getStringNumber()) > Integer.valueOf(removedItem.getStringNumber()) )
                {
                    leftProduct.setStringNumber(String.valueOf(Integer.valueOf( leftProduct.getStringNumber()) - 1));
                }
            }
        };
        ItemsToDelete.clear();
    }

    public void RemoveAll()
    {
        DataTable.clear();
    }
}
