package businesslogic;

import java.util.ArrayList;

public class FullDataTableControl
{
   ArrayList<Details> ListOfProducts;

   public FullDataTableControl()
   {
       ListOfProducts = new ArrayList<>();
   }

   public ArrayList<Details> GetListOfProducts()
   {
       return ListOfProducts;
   }

   public void CleanListOfProducts()
   {
       ListOfProducts = new ArrayList<>();
   }

   private Details IsProductExists(String productGuid, String characteristicGuid, ScanningBarcodeStructureModel barcodeParsed)
   {
           for(Details existsProduct : ListOfProducts)
           {
                   if(
                           (existsProduct.ProductGuid.equalsIgnoreCase(productGuid)) &&
                                   (existsProduct.CharacteristicGuid.equalsIgnoreCase(characteristicGuid)) &&
                                   (existsProduct.InformationFromScanner.isEqual(barcodeParsed))
                   )
                   {
                        return existsProduct;
                   }
           }
           return null;
   }

   public void Add(String productGuid, String characteristicGuid, ScanningBarcodeStructureModel barcodeParsed)
   {
           Details newProduct = IsProductExists(productGuid,characteristicGuid,barcodeParsed);

           if( newProduct != null)
           {
                   this.ListOfProducts.remove(newProduct);
                   newProduct.Quantity = newProduct.Quantity + 1;
                   this.ListOfProducts.add(newProduct);
           }
           else
           {
                   newProduct = new Details();
                   newProduct.ProductGuid = productGuid;
                   newProduct.CharacteristicGuid = characteristicGuid;;
                   newProduct.InformationFromScanner = barcodeParsed;
                   newProduct.Quantity = 1;
                   this.ListOfProducts.add(newProduct);
           }
   }

   public static class Details
   {
       public Details()
       {

       }

           ScanningBarcodeStructureModel InformationFromScanner;
           String ProductGuid;
           String CharacteristicGuid;
           Integer Quantity;

           public String getProductGuid()
           {
               return this.ProductGuid;
           }

           public String getCharacteristicGuid()
           {
               return this.CharacteristicGuid;
           }


           public ScanningBarcodeStructureModel getInfoFromScanner()
           {
               return this.InformationFromScanner;
           }

           public Integer getQuantity()
           {
               return this.Quantity;
           }
   }
}
