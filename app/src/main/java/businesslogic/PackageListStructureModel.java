package businesslogic;

import java.util.ArrayList;
import java.util.Date;

public class PackageListStructureModel
{
    private String PackageListGuid;

    private Date DateCreated;

    private String BaseDocumentGuid;

    private String SSCCCode;

    private ArrayList<Product_PackageListStructureModel> Products;

    public ArrayList<Product_PackageListStructureModel> GetProducts()
    {
        return this.Products;
    }

    public String GetPackageListGuid()
    {
        return this.PackageListGuid;
    }

    public String GetSSCCCode()
    {
        return this.SSCCCode;
    }
}
