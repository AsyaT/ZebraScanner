package businesslogic;

import com.symbol.emdk.barcode.ScanDataCollection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BarcodeProductLogic {
    BarcodeStructureModel BarcodeStructureModel = null;
    NomenclatureStructureModel NomenclatureStructureModel = null;
    CharacterisiticStructureModel CharacterisiticStructureModel = null;
    OperationTypesStructureModel OperationTypesStructureModel = null;
    ManufacturerStructureModel ManufacturerStructureModel = null;

    private ScanningBarcodeStructureModel parsedBarcode = null;

    public BarcodeProductLogic(
            BarcodeStructureModel barcodeStructureModel,
            NomenclatureStructureModel nomenclatureStructureModel,
            CharacterisiticStructureModel characterisiticStructureModel,
            ManufacturerStructureModel manufacturerStructureModel,
            OperationTypesStructureModel operationTypesStructureModel)
    {
        this.BarcodeStructureModel = barcodeStructureModel;
        this.NomenclatureStructureModel = nomenclatureStructureModel;
        this.CharacterisiticStructureModel = characterisiticStructureModel;
        this.ManufacturerStructureModel = manufacturerStructureModel;
        this.OperationTypesStructureModel = operationTypesStructureModel;
    }

    public ArrayList<ProductStructureModel> CreateProducts(String scannedBarcode, BarcodeTypes type) throws ParseException, ApplicationException {
        parsedBarcode = new ScanningBarcodeStructureModel(scannedBarcode, type);

        ArrayList<ProductStructureModel> listOfProducts =
                BarcodeStructureModel.FindProductByBarcode(parsedBarcode.getUniqueIdentifier());

        for(ProductStructureModel product : listOfProducts)
        {
            product.SetWeight(parsedBarcode.getWeight());
            product.SetProductionDate(parsedBarcode.getProductionDate());
            product.SetExpirationDate(parsedBarcode.getExpirationDate());
            product.SetManufacturerGuid(ManufacturerStructureModel.GetManufacturerName(parsedBarcode.getInternalProducer()));
        }

        if(listOfProducts == null)
        {
            throw new ApplicationException("Такой штрих-код не найден в номенклатуре!");
        }
        else
        {
            return listOfProducts;
        }
    }

    public String CreateStringResponse(ProductModel product)
    {
        String resultText="";

        if (parsedBarcode.getLabelType() == BarcodeTypes.LocalEAN13) {
            resultText="Штрих-код: "+ parsedBarcode.getUniqueIdentifier()
                    +"\nНоменклатура: "+ NomenclatureStructureModel.FindProductByGuid(product.GetProductGuid())
                    +"\nХарактеристика: "+ CharacterisiticStructureModel.FindCharacteristicByGuid(product.GetCharacteristicGUID())
                    +"\nВес: "+ WeightCalculator(parsedBarcode, product) + " кг";
        }
        else if(parsedBarcode.getLabelType() == BarcodeTypes.LocalGS1_EXP){
            resultText=
                    "Штрих-код: "+parsedBarcode.getUniqueIdentifier()
                            +"\nНоменклатура: "+ NomenclatureStructureModel.FindProductByGuid(product.GetProductGuid())
                            +"\nХарактеристика: "+ CharacterisiticStructureModel.FindCharacteristicByGuid(product.GetCharacteristicGUID())
                            +"\nВес: "+ WeightCalculator(parsedBarcode, product)+" кг"
                            + "\nНомер партии: "+parsedBarcode.getLotNumber()
                            + "\nДата производства: "+ new SimpleDateFormat("dd-MM-yyyy").format(parsedBarcode.getProductionDate())
                            + "\nДата истечения срока годност: " + new SimpleDateFormat("dd-MM-yyyy").format(parsedBarcode.getExpirationDate())
                            + "\nСерийный номер: " + parsedBarcode.getSerialNumber()
                            + "\nВнутренний код производителя: " + parsedBarcode.getInternalProducer() +" - "+ ManufacturerStructureModel.GetManufacturerName(parsedBarcode.getInternalProducer())
                            + "\nВнутренний код оборудования: " + parsedBarcode.getInternalEquipment();
        }
        return resultText;
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

    public Boolean IsAllowedToScan(ScanDataCollection.LabelType type) throws ApplicationException {
        if(this.OperationTypesStructureModel.IsAllowed(type))
        {
            return true;
        }
        else
        {
            throw new ApplicationException("Тип "+type.name()+" запрещен к сканирванию");
        }
    }
}
