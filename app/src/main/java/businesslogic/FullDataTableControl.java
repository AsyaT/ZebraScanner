package businesslogic;

import java.util.ArrayList;
import java.util.Date;

import presentation.ProductListViewModel;

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

    public ArrayList<ProductListViewModel> GetDataTable()
    {
        ArrayList<ProductListViewModel> result = new ArrayList<>();
        Integer stringNumberCounter = 1;

        for(Details product : ListOfProducts)
        {
            Integer existingQuantity = 0;
            Double existingWeight = 0.0;

            for(ProductListViewModel plm : result)
            {
                if(plm.getProductGuid().equals(product.getProductGuid()) && plm.getCharacteristic().equals(product.getCharacteristic()))
                {
                    existingQuantity = Integer.parseInt(plm.getCoefficient());
                    existingWeight = Double.parseDouble(plm.getSummaryWeight());
                    result.remove(plm);
                }
            }

            Integer newCoef = product.ScannedQuantity + existingQuantity;
            Double newWeight = product.Weight + existingWeight;

            ProductListViewModel newModel = new ProductListViewModel(
                    product.ProductGuid,
                    stringNumberCounter.toString(),
                    product.ProductNomenclature,
                    product.CharacteristicName,
                    newCoef.toString(),
                    newWeight.toString()
            );

            result.add(newModel);
            stringNumberCounter ++;
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
       String ProductNomenclature;
       String CharacteristicGuid;
       String CharacteristicName;
       Double Weight;
       Integer ScannedQuantity;
       Date ProductionDate;
       Date ExpiredDate;
       String ManufacturerGuid;

       public Details(String productGuid,
                      String productNomenclature,
                      String characteristicGuid,
                      String characteristicName,
                      Double weight,
                      Date productionDate,
                      Date expiredDate,
                      String manufacturerGuid,
                      Integer scannedQuantity)
       {
           this.ProductGuid = productGuid;
           this.ProductNomenclature = productNomenclature;
           this.CharacteristicGuid= characteristicGuid;
           this.CharacteristicName = characteristicName;
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

       public String getCharacteristic() {return this.CharacteristicName;}

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
