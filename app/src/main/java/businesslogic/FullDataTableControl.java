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

   private Details IsProductExists(String productGuid, String characteristicGuid, String manufacturerGuid, ScanningBarcodeStructureModel barcodeParsed)
   {
           for(Details existsProduct : ListOfProducts)
           {
                   if(
                           (existsProduct.ProductGuid.equalsIgnoreCase(productGuid)) &&
                                   (existsProduct.CharacteristicGuid.equalsIgnoreCase(characteristicGuid)) &&
                                   (existsProduct.ManufacturerGUID.equalsIgnoreCase(manufacturerGuid)) &&
                                   (existsProduct.InformationFromScanner.isEqual(barcodeParsed))
                   )
                   {
                        return existsProduct;
                   }
           }
           return null;
   }

   public void Add(String productGuid, String characteristicGuid, String manufacturerGuid, ScanningBarcodeStructureModel barcodeParsed)
   {
           Details newProduct = IsProductExists(productGuid,characteristicGuid,manufacturerGuid,barcodeParsed);

           if( newProduct != null)
           {
                   newProduct.Quantity =+ 1;
           }
           else
           {
                   newProduct = new Details();
                   newProduct.ProductGuid = productGuid;
                   newProduct.CharacteristicGuid = characteristicGuid;
                   newProduct.ManufacturerGUID = manufacturerGuid;
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
           String ManufacturerGUID;
           Integer Quantity;

           public String getProductGuid()
           {
               return this.ProductGuid;
           }

           public String getCharacteristicGuid()
           {
               return this.CharacteristicGuid;
           }

           public String getManufacturerGUID()
           {
               return this.ManufacturerGUID;
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
