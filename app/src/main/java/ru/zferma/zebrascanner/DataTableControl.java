package ru.zferma.zebrascanner;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import java.util.ArrayList;

public class DataTableControl {

    private ArrayList<ProductListViewModel> DataTable;
    private ArrayList<Integer> ItemsToDelete;

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
    public ProductListViewModel GetExistingModel(String uniqueBarcode )
    {
        return DataTable.stream().filter(x-> uniqueBarcode.equals(x.getBarCode())).findAny().orElse(null);
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

    public void AddOne(ProductListViewModel model)
    {
        DataTable.add(model);
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
