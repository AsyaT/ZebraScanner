package businesslogic;

import com.symbol.emdk.barcode.ScanDataCollection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ProductLogic {

    BarcodeStructureModel BarcodeStructureModel = null;
    NomenclatureStructureModel NomenclatureStructureModel = null;
    CharacterisiticStructureModel CharacterisiticStructureModel = null;
    ManufacturerStructureModel ManufacturerStructureModel = null;
    BaseDocumentStructureModel BaseDocumentStructureModel = null;
    OperationTypesStructureModel OperationTypesStructureModel = null;

    private ScanningBarcodeStructureModel parsedBarcode = null;

    public ProductLogic(
            BarcodeStructureModel barcodeStructureModel,
            NomenclatureStructureModel nomenclatureStructureModel,
            CharacterisiticStructureModel characterisiticStructureModel,
            ManufacturerStructureModel manufacturerStructureModel,
            BaseDocumentStructureModel baseDocumentStructureModel,
            OperationTypesStructureModel operationTypesStructureModel)
    {
       this.BarcodeStructureModel = barcodeStructureModel;
       this.NomenclatureStructureModel = nomenclatureStructureModel;
       this.CharacterisiticStructureModel = characterisiticStructureModel;
       this.ManufacturerStructureModel = manufacturerStructureModel;
       this.BaseDocumentStructureModel = baseDocumentStructureModel;
       this.OperationTypesStructureModel = operationTypesStructureModel;
    }

    public ArrayList<ProductStructureModel> CreateProducts(String scannedBarcode, BarcodeTypes type) throws ParseException, ApplicationException {
        parsedBarcode = new ScanningBarcodeStructureModel(scannedBarcode, type);

        ArrayList<ProductStructureModel> listOfProducts =
                BarcodeStructureModel.FindProductByBarcode(parsedBarcode.getUniqueIdentifier());

        if(listOfProducts == null)
        {
            throw new ApplicationException("Такой штрих-код не найден в номенклатуре!");
        }
        else
        {
            return listOfProducts;
        }
    }

    public ListViewPresentationModel CreateListView(ProductStructureModel product)
    {
        return
                new ListViewPresentationModel(
                        parsedBarcode.getUniqueIdentifier(),
                        NomenclatureStructureModel.FindProductByGuid(product.GetProductGuid()),
                        CharacterisiticStructureModel.FindCharacteristicByGuid(product.GetCharacteristicGUID()),
                        WeightCalculator(parsedBarcode, product),
                        product.GetProductGuid())
        ;
    }

    public FullDataTableControl.Details CreateDetails(ProductStructureModel product)
    {
        return new FullDataTableControl.Details(
                product.GetProductGuid(),
                product.GetCharacteristicGUID(),
                WeightCalculator(parsedBarcode, product),
                parsedBarcode.getProductionDate(),
                parsedBarcode.getExpirationDate(),
                this.ManufacturerStructureModel.GetManufacturerGuid(parsedBarcode.getInternalProducer()));
    }

    public String CreateStringResponse(ProductStructureModel product)
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

    public Boolean IsExistsInOrder(ProductStructureModel product) throws ApplicationException {
        if(this.BaseDocumentStructureModel != null && this.BaseDocumentStructureModel.IfProductExists(product.GetProductGuid()) )
        {
            return true;
        }
        else
        {
            throw new ApplicationException("Этот продукт не содержится в документе-основании");
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

    private Double WeightCalculator(ScanningBarcodeStructureModel scannedBarcode, ProductStructureModel product)
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
