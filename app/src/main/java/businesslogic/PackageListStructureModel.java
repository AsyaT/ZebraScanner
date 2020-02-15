package businesslogic;

import java.util.ArrayList;
import java.util.Date;

public class PackageListStructureModel implements ObjectForSaving
{
    private String PackageListGuid;

    private Date DateCreated;

    private String BaseDocumentGuid;

    private String SSCCCode;

    public PackageListStructureModel(String guid, Date dateCreated, String baseDocumentGuid, String sscc)
    {
        this.PackageListGuid = guid;
        this.DateCreated = dateCreated;
        this.BaseDocumentGuid = baseDocumentGuid;
        this.SSCCCode = sscc;

    }

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
