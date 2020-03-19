package businesslogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import presentation.ProductListViewModel;

public class FullDataTableControl
{
   private ArrayList<Details> ListOfProducts;
   private ArrayList<String> ProductsToRemove = new ArrayList<>();
    private ArrayList<ProductListViewModel> DataTable;

   public FullDataTableControl()
   {
       ListOfProducts = new ArrayList<>();
       DataTable = new ArrayList<ProductListViewModel>();
   }

    public ArrayList<ProductListViewModel> GetDataTable()
    {
        return DataTable;
    }

   public ArrayList<Details> GetListOfProducts()
   {
       return ListOfProducts;
   }

   public List<String> GetListOfBarcode()
   {
        List<String> result = new ArrayList<>();
        for(Details product : ListOfProducts)
        {
            result.add(product.ScannedBarcode);
        }
        return result;
   }

   public Boolean IsProductExists(String productGuid)
   {
       for(Details existsProduct : ListOfProducts)
       {
           if(existsProduct.ProductGuid.equalsIgnoreCase(productGuid))
           {
               return true;
           }
       }
       return false;
   }

   private Details IsProductExists(Details product)
   {
           for(Details existsProduct : ListOfProducts)
           {
                   if(
                           (existsProduct.ProductGuid.equalsIgnoreCase(product.getProductGuid())) &&
                                   (existsProduct.CharacteristicGuid.equalsIgnoreCase(product.getCharacteristicGuid())) &&
                                   (existsProduct.Weight.equals(product.Weight)) &&
                                   (existsProduct.ProductionDate.equals(product.getProductionDate())) &&
                                   (existsProduct.ExpiredDate.equals(product.getExpiredDate())) &&
                                   (existsProduct.ManufacturerGuid.equals(product.getManufacturerGuid()))
                   )
                   {
                        return existsProduct;
                   }
           }
           return null;
   }

   private ArrayList<Details> FindProductsByGuid(String productGuid)
   {
       ArrayList<Details> result = new ArrayList<>();
       for(Details existsProduct : ListOfProducts)
       {
           if(  existsProduct.ProductGuid.equalsIgnoreCase(productGuid) )
           {
               result.add( existsProduct );
           }
       }
       return result;
   }

   public Double GetSummaryKilosByProductGuid(String productGuid)
   {
       Double result = 0.0;
       for(Details product : FindProductsByGuid(productGuid))
       {
           result = result + product.getWeight();
       }
       return result;
   }

   public Integer GetItemsByProductGuid(String productGuid)
   {
       Integer result = 0;
       for(Details product : FindProductsByGuid(productGuid))
       {
           result = result + product.getScannedQuantity();
       }
       return result;
   }

   public void Add(Details product)
   {
           Details newProduct = IsProductExists(product);

           if( newProduct != null)
           {
                   this.ListOfProducts.remove(newProduct);
                   product.ScannedQuantity = newProduct.ScannedQuantity + 1;
                   this.ListOfProducts.add(product);
           }
           else
           {
                  product.ScannedQuantity = 1;
                  this.ListOfProducts.add(product);
           }

           //TOD: modify to REDUCE method
           this.DataTable.add(new ProductListViewModel(
                   product.getProductGuid(),
                   "1",
                   product.getCharacteristicGuid(),
                   product.getProductGuid(),
                   "1",
                   product.getWeight().toString()));
   }

    public void ItemIsClicked(Integer index) // index starts from 0
    {
        this.DataTable.get(index);
    }

   public void ItemIsClicked(String productGuid)
   {
       this.ProductsToRemove.add(productGuid);
   }

   public void RemoveSelected()
   {
       for(String productGuid : this.ProductsToRemove)
       {
           ArrayList<Details> products = this.FindProductsByGuid(productGuid);

           for(Details product : products)
           {
               this.ListOfProducts.remove(product);
           }
       }

       // TODO: modify DataTable
   }

   public static class Details
   {
       String ProductGuid;
       String CharacteristicGuid;
       Double Weight;
       Integer ScannedQuantity;
       Date ProductionDate;
       Date ExpiredDate;
       String ManufacturerGuid;
       String ScannedBarcode;

       public Details(String productGuid,
                      String characteristicGuid,
                      Double weight,
                      Date productionDate,
                      Date expiredDate,
                      String manufacturerGuid,
                      Integer scannedQuantity,
                      String scannedBarcode
       )
       {
           this.ProductGuid = productGuid;
           this.CharacteristicGuid= characteristicGuid;
           this.Weight = weight;
           this.ProductionDate = productionDate;
           this.ExpiredDate = expiredDate;
           this.ManufacturerGuid = manufacturerGuid;
           this.ScannedQuantity = scannedQuantity;
           this.ScannedBarcode = scannedBarcode;
       }

       public String getProductGuid()
       {
           return this.ProductGuid;
       }

       public String getCharacteristicGuid()
       {
           return this.CharacteristicGuid;
       }

       public Integer getScannedQuantity()
           {
               return this.ScannedQuantity;
           }

       public Double getWeight() { return this.Weight;}

       public Date getProductionDate() { return this.ProductionDate;}

       public Date getExpiredDate() { return this.ExpiredDate;}

       public String getManufacturerGuid() { return this.ManufacturerGuid;}

       @Override
       public boolean equals(Object input) {
           Details obj = (Details) input;
           return this.ProductGuid.equals(obj.ProductGuid)
                   && this.CharacteristicGuid.equals(obj.CharacteristicGuid)
                   && ((this.ManufacturerGuid == null && obj.ManufacturerGuid == null) || this.ManufacturerGuid.equals(obj.ManufacturerGuid))
                   && ((this.ProductionDate==null && obj.ProductionDate==null) || this.ProductionDate.equals(obj.ProductionDate) )
                   && ((this.ExpiredDate==null && obj.ExpiredDate==null) || this.ExpiredDate.equals(obj.ExpiredDate) )
                   && this.Weight.equals(obj.Weight)
                   && this.ScannedQuantity.equals(obj.ScannedQuantity);
       }
   }
}
