package businesslogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BarcodeProductLogic {
    BarcodeStructureModel BarcodeStructureModel = null;
    NomenclatureStructureModel NomenclatureStructureModel = null;
    CharacteristicStructureModel characteristicStructureModel = null;
    ManufacturerStructureModel ManufacturerStructureModel = null;

    private ScanningBarcodeStructureModel parsedBarcode = null;

    public BarcodeProductLogic(
            BarcodeStructureModel barcodeStructureModel,
            NomenclatureStructureModel nomenclatureStructureModel,
            CharacteristicStructureModel characteristicStructureModel,
            ManufacturerStructureModel manufacturerStructureModel
            )
    {
        this.BarcodeStructureModel = barcodeStructureModel;
        this.NomenclatureStructureModel = nomenclatureStructureModel;
        this.characteristicStructureModel = characteristicStructureModel;
        this.ManufacturerStructureModel = manufacturerStructureModel;
    }

    public ArrayList<ProductStructureModel> FindProductByBarcode(String scannedBarcode, BarcodeTypes type) throws ParseException, ApplicationException {
        parsedBarcode = new ScanningBarcodeStructureModel(scannedBarcode, type);

        ArrayList<ProductStructureModel> listOfProducts =
                BarcodeStructureModel.FindProductByBarcode(parsedBarcode.getUniqueIdentifier());

        if(listOfProducts == null)
        {
            throw new ApplicationException("Не найдено продуктов по штрих-коду "+ scannedBarcode);
        }

        listOfProducts = RemoveDuplicateProducts(listOfProducts);

        for(ProductStructureModel product : listOfProducts)
        {
            product.SetWeight(parsedBarcode.getWeight());
            product.SetProductionDate(parsedBarcode.getProductionDate());
            product.SetExpirationDate(parsedBarcode.getExpirationDate());
            if(parsedBarcode.getInternalProducer() !=null)
            {
                try {
                    product.SetManufacturerGuid(ManufacturerStructureModel.GetManufacturerName(parsedBarcode.getInternalProducer()));
                }
                catch (ApplicationException ex)
                {
                    throw new ApplicationException(ex.getMessage());
                }
            }
        }

        return listOfProducts;

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
                    collectionItem.ProductGUID.equalsIgnoreCase(product.ProductGUID) &&
                    collectionItem.CharacteristicGUID.equalsIgnoreCase(product.CharacteristicGUID))
            {
                return true;
            }
        }

        return false;
    }

    public String CreateStringResponse(ProductModel product)
    {
        String resultText="";

        String productNomenclature = "";
        String productCharacteristic= "";

        try {
            productNomenclature = NomenclatureStructureModel.FindProductByGuid(product.GetProductGuid());
        }
        catch (ApplicationException e)
        {
            productNomenclature = e.getMessage();
        }
        finally
        {
            try{
                productCharacteristic = characteristicStructureModel.FindCharacteristicByGuid(product.GetCharacteristicGUID());
            }
            catch(ApplicationException e)
            {
                productCharacteristic = e.getMessage();
            }
            finally {

                if (parsedBarcode.getLabelType() == BarcodeTypes.LocalEAN13) {
                    resultText = "Штрих-код: " + parsedBarcode.getFullBarcode()
                            + "\nНоменклатура: " + productNomenclature
                            + "\nХарактеристика: " + productCharacteristic
                            + "\nВес: " + WeightCalculator(parsedBarcode, product) + " кг";
                } else if (parsedBarcode.getLabelType() == BarcodeTypes.LocalGS1_EXP) {
                    String manufacturerName = "";
                    try {
                        manufacturerName = ManufacturerStructureModel.GetManufacturerName(parsedBarcode.getInternalProducer());
                    } catch (ApplicationException ex) {
                        manufacturerName = ex.getMessage();
                    }

                    resultText =
                            "Штрих-код: " + parsedBarcode.getUniqueIdentifier()
                                    + "\nНоменклатура: " + productNomenclature
                                    + "\nХарактеристика: " + productCharacteristic
                                    + "\nВес: " + WeightCalculator(parsedBarcode, product) + " кг"
                                    + "\nНомер партии: " + parsedBarcode.getLotNumber()
                                    + "\nДата производства: " + new SimpleDateFormat("dd-MM-yyyy").format(parsedBarcode.getProductionDate())
                                    + "\nДата истечения срока годност: " + new SimpleDateFormat("dd-MM-yyyy").format(parsedBarcode.getExpirationDate())
                                    + "\nСерийный номер: " + parsedBarcode.getSerialNumber()
                                    + "\nВнутренний код производителя: " + parsedBarcode.getInternalProducer() + " - " + manufacturerName
                                    + "\nВнутренний код оборудования: " + parsedBarcode.getInternalEquipment();
                }
                return resultText;
            }
        }
    }

    private Double WeightCalculator(ScanningBarcodeStructureModel scannedBarcode, ProductModel product)
    {
        if(scannedBarcode.getWeight() == null)
        {
            return product.GetWeight();
        }
        else
        {
            return scannedBarcode.getWeight();
        }
    }


}
