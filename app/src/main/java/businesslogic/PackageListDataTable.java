package businesslogic;

import java.util.ArrayList;

public class PackageListDataTable {
    private ArrayList<PackageListStructureModel> DataTable;

    public PackageListDataTable()
    {
        DataTable = new ArrayList<>();
    }

    public void Add(PackageListStructureModel model)
    {
        DataTable.add(model);
    }

    public Boolean IsContains(String barcode)
    {
        for(PackageListStructureModel packageList : DataTable)
        {
            if(packageList.GetPackageListGuid().equalsIgnoreCase(barcode) || packageList.GetSSCCCode().equalsIgnoreCase(barcode))
            {
                return true;
            }
        }

        return false;
    }
}
