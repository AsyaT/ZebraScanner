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

   private Details IsProductExists(String productGuid, String characteristicGuid, Double weightFromDatabase, ScanningBarcodeStructureModel barcodeParsed)
   {
           for(Details existsProduct : ListOfProducts)
           {
                   if(
                           (existsProduct.ProductGuid.equalsIgnoreCase(productGuid)) &&
                                   (existsProduct.CharacteristicGuid.equalsIgnoreCase(characteristicGuid)) &&
                                   (existsProduct.WeightFromDatabase.equals(weightFromDatabase)) &&
                                   (existsProduct.InformationFromScanner.isEqual(barcodeParsed))
                   )
                   {
                        return existsProduct;
                   }
           }
           return null;
   }

   public void Add(String productGuid, String characteristicGuid, Double weightFromDatabase, ScanningBarcodeStructureModel barcodeParsed)
   {
           Details newProduct = IsProductExists(productGuid,characteristicGuid, weightFromDatabase, barcodeParsed);

           if( newProduct != null)
           {
                   this.ListOfProducts.remove(newProduct);
                   newProduct.ScannedQuantity = newProduct.ScannedQuantity + 1;
                   this.ListOfProducts.add(newProduct);
           }
           else
           {
                   newProduct = new Details();
                   newProduct.ProductGuid = productGuid;
                   newProduct.CharacteristicGuid = characteristicGuid;
                   newProduct.WeightFromDatabase = weightFromDatabase;
                   newProduct.InformationFromScanner = barcodeParsed;
                   newProduct.ScannedQuantity = 1;
                   this.ListOfProducts.add(newProduct);
           }
   }

   public static class Details
   {
       ScanningBarcodeStructureModel InformationFromScanner;
       String ProductGuid;
       String CharacteristicGuid;
       Double WeightFromDatabase;
       Integer ScannedQuantity;

       public String getProductGuid()
       {
           return this.ProductGuid;
       }

       public String getCharacteristicGuid()
       {
           return this.CharacteristicGuid;
       }

       public ScanningBarcodeStructureModel getInfoFromScanner() { return this.InformationFromScanner; }

       public Integer getScannedQuantity()
           {
               return this.ScannedQuantity;
           }

       public Double getWeightFromDatabase() { return this.WeightFromDatabase;}
   }
}
