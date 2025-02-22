package businesslogic;

import java.text.SimpleDateFormat;

import models.CharacteristicStructureModel;
import models.ListViewPresentationModel;
import models.ManufacturerStructureModel;
import models.NomenclatureStructureModel;
import models.ProductStructureModel;
import models.Product_PackageListStructureModel;
import models.ScanningBarcodeStructureModel;

public class ResponseModelGenerator
{
    models.NomenclatureStructureModel NomenclatureStructureModel = null;
    models.CharacteristicStructureModel CharacteristicStructureModel = null;
    models.ManufacturerStructureModel ManufacturerStructureModel = null;


    public ResponseModelGenerator(
            NomenclatureStructureModel nomenclatureStructureModel,
            CharacteristicStructureModel characteristicStructureModel,
            ManufacturerStructureModel manufacturerStructureModel
    )
    {
        this.NomenclatureStructureModel = nomenclatureStructureModel;
        this.CharacteristicStructureModel = characteristicStructureModel;
        this.ManufacturerStructureModel = manufacturerStructureModel;

    }

    public ListViewPresentationModel CreateListViewResponse(ScanningBarcodeStructureModel barcode, ProductStructureModel product) throws ApplicationException
    {
        ListViewPresentationModel result = new ListViewPresentationModel
                (
                        NomenclatureStructureModel.FindProductByGuid(product.GetProductGuid()),
                        CharacteristicStructureModel.FindCharacteristicByGuid(product.GetCharacteristicGUID()),
                        WeightCalculator(barcode,product),
                        product.GetProductGuid(),
                        1
                );
        return result;
    }

    public ListViewPresentationModel CreateListViewResponse(Product_PackageListStructureModel product) throws ApplicationException {
        ListViewPresentationModel result = new ListViewPresentationModel
                (
                        NomenclatureStructureModel.FindProductByGuid(product.GetProductGuid()),
                        CharacteristicStructureModel.FindCharacteristicByGuid(product.GetCharacteristicGUID()),
                        product.GetWeight(),
                        product.GetProductGuid(),
                        product.GetItems()
                );
        return result;
    }

    public FullDataTableControl.Details CreateFullDataTableResponse(ScanningBarcodeStructureModel barcode, ProductStructureModel product) throws ApplicationException
    {
        FullDataTableControl.Details result = new FullDataTableControl.Details(
                product.GetProductGuid(),
                product.GetCharacteristicGUID(),
                WeightCalculator(barcode,product),
                barcode.getProductionDate(),
                barcode.getExpirationDate(),
                CalculateManufacturerGuidForDataTable(barcode.getInternalProducer()),
                1,
                barcode.getFullBarcode(),
                ""
                );
        return result;
    }

    public FullDataTableControl.Details CreateFullDataTableResponse(Product_PackageListStructureModel product, String packageListBarcode, String packageListGuid)
    {
        FullDataTableControl.Details result = new FullDataTableControl.Details(
                product.GetProductGuid(),
                product.GetCharacteristicGUID(),
                product.GetWeight(),
                product.GetProductionDate(),
                product.GetExpirationDate(),
                product.GetManufacturerGuid(),
                product.GetItems(),
                packageListBarcode,
                packageListGuid
        );
        return result;
    }

    public String CreateStringResponse(ScanningBarcodeStructureModel barcode, ProductStructureModel product)
    {
        String resultText="";

        String productNomenclature = CalculateNomenclature(product.GetProductGuid());
        String productCharacteristic= CalculateCharacteristic(product.GetCharacteristicGUID());

        if (barcode.getLabelType() == BarcodeTypes.LocalEAN13) {
            resultText = "Штрих-код: " + barcode.getFullBarcode()
                    + "\nНоменклатура: " + productNomenclature
                    + "\nХарактеристика: " + productCharacteristic
                    + "\nВес: " + WeightCalculator(barcode, product) + " кг";
        }
        else if (barcode.getLabelType() == BarcodeTypes.LocalGS1_EXP)
        {
            String manufacturerName = CalculateManufacturer(barcode.getInternalProducer());

            resultText =
                    "Штрих-код: " + barcode.getUniqueIdentifier()
                            + "\nНоменклатура: " + productNomenclature
                            + "\nХарактеристика: " + productCharacteristic
                            + "\nВес: " + WeightCalculator(barcode, product) + " кг"
                            + "\nНомер партии: " + barcode.getLotNumber()
                            + "\nДата производства: " + new SimpleDateFormat("dd-MM-yyyy").format(barcode.getProductionDate())
                            + "\nДата истечения срока годности: " + new SimpleDateFormat("dd-MM-yyyy").format(barcode.getExpirationDate())
                            + "\nСерийный номер: " + barcode.getSerialNumber()
                            + "\nВнутренний код производителя: " + barcode.getInternalProducer() + " - " + manufacturerName
                            + "\nВнутренний код оборудования: " + barcode.getInternalEquipment();
        }
        return resultText;
    }

    public String CreateStringResponse(Product_PackageListStructureModel product)
    {
        String result = "";
        result =
                "Номенклатура: " + CalculateNomenclature(product.GetProductGuid())
                        + "\nХарактеристика: " + CalculateCharacteristic(product.GetCharacteristicGUID())
                        + "\nВес: " + product.GetWeight() + " кг"
                        + "\nДата производства: " + new SimpleDateFormat("dd-MM-yyyy").format(product.GetProductionDate())
                        + "\nДата истечения срока годности: " + new SimpleDateFormat("dd-MM-yyyy").format(product.GetExpirationDate())
                        + "\nПроизводитель: " + CalculateManufacturer(product.GetManufacturerGuid())
                        + "\nКоличество: " + product.GetItems();

        return result;
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

    private String CalculateManufacturer(Byte id)
    {
        try {
            return ManufacturerStructureModel.GetManufacturerNameById(id);
        } catch (ApplicationException ex) {
            return ex.getMessage();
        }
    }

    private String CalculateManufacturer(String guid)
    {
        try {
            return ManufacturerStructureModel.GetManufacturerNameByGuid(guid);
        } catch (ApplicationException ex) {
            return ex.getMessage();
        }
    }

    private String CalculateManufacturerGuidForDataTable(Byte id) throws ApplicationException {
        try {
            return ManufacturerStructureModel.GetManufacturerGuidById(id);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    private String CalculateNomenclature(String productGuid)
    {
        try {
            return NomenclatureStructureModel.FindProductByGuid(productGuid);
        }
        catch (ApplicationException e)
        {
            return e.getMessage();
        }
    }

    private String CalculateCharacteristic(String characteristicGuid)
    {
        try{
            return CharacteristicStructureModel.FindCharacteristicByGuid(characteristicGuid);
        }
        catch(ApplicationException e)
        {
            return e.getMessage();
        }
    }
}
