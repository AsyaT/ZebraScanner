package businesslogic;

import java.util.ArrayList;
import java.util.Date;

public class FullDataTableControl
{
   private ArrayList<Details> ListOfProducts;

   private ArrayList<String> ProductsToRemove = new ArrayList<>();

   public FullDataTableControl()
   {
       ListOfProducts = new ArrayList<>();
   }

   public ArrayList<Details> GetListOfProducts()
   {
       return ListOfProducts;
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

       public Details(String productGuid,
                      String characteristicGuid,
                      Double weight,
                      Date productionDate,
                      Date expiredDate,
                      String manufacturerGuid,
                      Integer scannedQuantity)
       {
           this.ProductGuid = productGuid;
           this.CharacteristicGuid= characteristicGuid;
           this.Weight = weight;
           this.ProductionDate = productionDate;
           this.ExpiredDate = expiredDate;
           this.ManufacturerGuid = manufacturerGuid;
           this.ScannedQuantity = scannedQuantity;
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
   }
}
