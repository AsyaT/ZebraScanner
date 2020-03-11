package businesslogic;

import java.text.ParseException;
import java.util.ArrayList;

import models.BarcodeStructureModel;
import models.ProductStructureModel;

public class BarcodeProductLogic {
    models.BarcodeStructureModel BarcodeStructureModel = null;

    public BarcodeProductLogic(BarcodeStructureModel barcodeStructureModel )
    {
        this.BarcodeStructureModel = barcodeStructureModel;
    }

    public ArrayList<ProductStructureModel> FindProductByBarcode(String scannedBarcodeUniqueIdentifier) throws ParseException, ApplicationException
    {
        ArrayList<ProductStructureModel> listOfProducts =
                BarcodeStructureModel.FindProductByBarcode(scannedBarcodeUniqueIdentifier);

        if(listOfProducts == null)
        {
            throw new ApplicationException("Не найдено продуктов по штрих-коду "+ scannedBarcodeUniqueIdentifier);
        }

        return RemoveDuplicateProducts(listOfProducts);
    }


    protected ArrayList<ProductStructureModel> RemoveDuplicateProducts(ArrayList<ProductStructureModel> products)
    {
        ArrayList<ProductStructureModel> result = new ArrayList<>();

        for(ProductStructureModel product : products)
        {
            if(IsProductDuplicated(result,product) == false)
            {
                result.add(product);
            }
        }

        return result;
    }

    private Boolean IsProductDuplicated(ArrayList<ProductStructureModel> collection, ProductStructureModel product)
    {
        for(ProductStructureModel collectionItem : collection)
        {
            if(
                    collectionItem.GetProductGuid().equalsIgnoreCase(product.GetProductGuid()) &&
                    collectionItem.GetCharacteristicGUID().equalsIgnoreCase(product.GetCharacteristicGUID())
            )
            {
                return true;
            }
        }

        return false;
    }




}
