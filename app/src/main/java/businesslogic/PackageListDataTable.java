package businesslogic;

import java.util.ArrayList;

import models.PackageListStructureModel;

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

    public Boolean IsActionAllowedWithPackageList(String barcode) throws ApplicationException
    {
        for(PackageListStructureModel packageList : DataTable)
        {
            if(packageList.GetPackageListGuid().equalsIgnoreCase(barcode) || packageList.GetSSCCCode().equalsIgnoreCase(barcode))
            {
                throw new ApplicationException("Этот Упаковочный лист уже сканировался");
            }
        }

        return true;
    }
}
