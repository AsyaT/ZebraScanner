package businesslogic;

public class Product_PackageListStructureModel   extends ProductModel{

    protected String SSCCCode; //TODO : Maybe it related to Package List?
    protected Integer Items; //TODO: send to result models

    public Integer GetItems()
    {
        return this.Items;
    }

}
