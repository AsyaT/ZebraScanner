package ru.zferma.zebrascanner;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import java.util.ArrayList;
import java.util.Comparator;

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

    public ArrayList<ProductListViewModel> GetDataControl()
    {
        return DataTable;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ProductListViewModel GetExistingModel(String uniqueBarcode, String productGuid )
    {
        return DataTable.stream().filter(x-> uniqueBarcode.equalsIgnoreCase(x.getBarCode()) && productGuid.equalsIgnoreCase(x.getProductGuid())).findAny().orElse(null);
    }

    public void ItemClicked(View view, int position)
    {
        if(ItemsToDelete.contains(position))
        {
            view.setBackgroundColor(Color.WHITE);
            ItemsToDelete.remove((Integer) position);
        }
        else
        {
            view.setBackgroundColor(Color.YELLOW);
            ItemsToDelete.add(position);
        }
    }

    public void RemoveOne(ProductListViewModel model)
    {
        DataTable.remove(model);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void AddOne(ProductListViewModel model)
    {
        DataTable.add(model);
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
            DataTable.remove((int) x);
        };
        ItemsToDelete.clear();
    }

    public void RemoveAll()
    {
        DataTable.clear();
    }
}
