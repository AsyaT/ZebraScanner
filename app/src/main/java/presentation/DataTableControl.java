package presentation;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import businesslogic.ListViewPresentationModel;

public class DataTableControl {

    private ArrayList<ProductListViewModel> DataTable;
    private ArrayList<String> ItemsToDelete;

    public Integer GetSizeOfList()
    {
        return DataTable.size();
    }

    public DataTableControl()
    {
        DataTable = new ArrayList<ProductListViewModel>();
        ItemsToDelete = new ArrayList<String>();
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

    public void ItemClicked(View view, String productGuid)
    {
        if(ItemsToDelete.contains(productGuid))
        {
            view.setBackgroundColor(Color.WHITE);
            ItemsToDelete.remove((String) productGuid);
        }
        else
        {
            view.setBackgroundColor(Color.YELLOW);
            ItemsToDelete.add(productGuid);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void AddOne(ListViewPresentationModel model)
    {
        ProductListViewModel result = null;

        ProductListViewModel existingTableModel =  this.FindProduct( model.ProductGuid);
        if(existingTableModel == null)
        {
            Integer newStringNumber = this.GetSizeOfList() + 1;
            result = new ProductListViewModel(
                    model.ProductGuid,
                    newStringNumber.toString(),
                    model.Characteristic,
                    model.Nomenclature,
                    model.Coefficient.toString(),
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

    public ProductListViewModel GetItemByIndex(Integer index)
    {
        return DataTable.get(index);
    }

    public void RemoveSelected()
    {
        for (String productGuid : ItemsToDelete)
        {
            Iterator<ProductListViewModel> i = DataTable.iterator();

            while (i.hasNext())
            {
                ProductListViewModel existingLVModel = i.next();

                if(existingLVModel.getProductGuid().equalsIgnoreCase(productGuid))
                {
                    Integer newStringNumber = Integer.parseInt(existingLVModel.getStringNumber());
                    while(i.hasNext())
                    {
                        ProductListViewModel itemToRewriteNumber = i.next();
                        itemToRewriteNumber.setStringNumber(newStringNumber.toString());
                        newStringNumber = newStringNumber + 1 ;
                    }

                    DataTable.remove(existingLVModel);
                    break;
                }

            };
        };
        ItemsToDelete.clear();
    }

    public void RemoveAll()
    {

        DataTable.clear();
        ItemsToDelete.clear();
    }
}
