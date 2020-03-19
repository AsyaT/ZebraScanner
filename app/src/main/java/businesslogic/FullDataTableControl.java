package businesslogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

   public List<String> GetListOfBarcode()
   {
        List<String> result = new ArrayList<>();
        for(Details product : ListOfProducts)
        {
            result.add(product.ScannedBarcode);
        }
        return result;
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
                   product.ScannedBoxesQuantity = newProduct.ScannedBoxesQuantity + 1;
                   this.ListOfProducts.add(product);
           }
           else
           {
                  product.ScannedBoxesQuantity = 1;
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
       Integer QuantityPiecesGoods; // TODO: use for eggs
       Integer ScannedBoxesQuantity;
       Date ProductionDate;
       Date ExpiredDate;
       String ManufacturerGuid;
       String ScannedBarcode;
       String PackageListGuid; // TODO: use at scanning

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
           this.Weight = weight;
           this.QuantityPiecesGoods = null;
            MapFromConstructor(productGuid,characteristicGuid,productionDate,expiredDate,manufacturerGuid,scannedQuantity,scannedBarcode);
       }

       public Details(String productGuid,
                      String characteristicGuid,
                      Integer quantityPiecesGoods,
                      Date productionDate,
                      Date expiredDate,
                      String manufacturerGuid,
                      Integer scannedQuantity,
                      String scannedBarcode
       )
       {
           this.QuantityPiecesGoods = quantityPiecesGoods;
           this.Weight = null;
           MapFromConstructor(productGuid,characteristicGuid,productionDate,expiredDate,manufacturerGuid,scannedQuantity,scannedBarcode);
       }

       private void MapFromConstructor(String productGuid,
                                       String characteristicGuid,
                                       Date productionDate,
                                       Date expiredDate,
                                       String manufacturerGuid,
                                       Integer scannedQuantity,
                                       String scannedBarcode
       )
       {
           this.ProductGuid = productGuid;
           this.CharacteristicGuid= characteristicGuid;
           this.ProductionDate = productionDate;
           this.ExpiredDate = expiredDate;
           this.ManufacturerGuid = manufacturerGuid;
           this.ScannedBoxesQuantity = scannedQuantity;
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
               return this.ScannedBoxesQuantity;
           }

       public Double getWeight() { return this.Weight;}
       public Integer getQuantityPiecesGoods () { return this.QuantityPiecesGoods;}

       public Date getProductionDate() { return this.ProductionDate;}

       public Date getExpiredDate() { return this.ExpiredDate;}

       public String getManufacturerGuid() { return this.ManufacturerGuid;}

       public String getScannedBarcode() { return  this.ScannedBarcode;}

       @Override
       public boolean equals(Object input) {
           Details obj = (Details) input;
           return this.ProductGuid.equals(obj.ProductGuid)
                   && this.CharacteristicGuid.equals(obj.CharacteristicGuid)
                   && ((this.ManufacturerGuid == null && obj.ManufacturerGuid == null) || this.ManufacturerGuid.equals(obj.ManufacturerGuid))
                   && ((this.ProductionDate==null && obj.ProductionDate==null) || this.ProductionDate.equals(obj.ProductionDate) )
                   && ((this.ExpiredDate==null && obj.ExpiredDate==null) || this.ExpiredDate.equals(obj.ExpiredDate) )
                   && this.Weight.equals(obj.Weight)
                   && this.ScannedBoxesQuantity.equals(obj.ScannedBoxesQuantity);
       }
   }
}
