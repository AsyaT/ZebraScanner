package businesslogic;

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

    public ArrayList<ProductListViewModel> GetDataTable()
    {
        return DataTable;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ProductListViewModel GetExitingProduct(String uniqueBarcode, String productGuid )
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void AddOne(ListViewPresentationModel model)
    {
        ProductListViewModel result = null;

        ProductListViewModel existingTableModel =  this.GetExitingProduct(model.UniqueCode, model.ProductGuid);
        if(existingTableModel == null)
        {
            Integer newStringNumber = this.GetSizeOfList()+1;
            result = new ProductListViewModel(
                    model.ProductGuid,
                    newStringNumber.toString(),
                    model.Characteristic,
                    model.Nomenclature,
                    model.UniqueCode,
                    "1",
                    model.Weight.toString());
        }
        else
        {
            DataTable.remove(existingTableModel);

            Integer newCoefficient = Integer.parseInt( existingTableModel.getCoefficient()) + 1;
            Double newWeight = Double.parseDouble(existingTableModel.getWeight()) + model.Weight;

            result = new ProductListViewModel(
                    model.ProductGuid,
                    existingTableModel.getStringNumber(),
                    model.Characteristic,
                    model.Nomenclature,
                    model.UniqueCode,
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
            DataTable.remove((int) x);
        };
        ItemsToDelete.clear();
    }

    public void RemoveAll()
    {
        DataTable.clear();
    }
}
