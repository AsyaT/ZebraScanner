package ru.zferma.zebrascanner;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import java.util.ArrayList;

public class DataTableControl {

    private ArrayList<OrderModel> DataTable;
    private ArrayList<Integer> ItemsToDelete;

    public DataTableControl()
    {
        DataTable = new ArrayList<OrderModel>();
        ItemsToDelete = new ArrayList<Integer>();
    }

    public ArrayList<OrderModel> GetDataControl()
    {
        return DataTable;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public OrderModel GetExistingModel(String uniqueBarcode )
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
            view.setBackgroundColor(Color.RED);
            ItemsToDelete.add(position);
        }
    }

    public void RemoveOne(OrderModel model)
    {
        DataTable.remove(model);
    }

    public void AddOne(OrderModel model)
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
