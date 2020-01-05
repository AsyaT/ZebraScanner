package businesslogic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ScanningCommand.ListViewPresentationModel;
import serverDatabaseInteraction.ApplicationException;

public class ProductLogic {

    BarcodeStructureModel BarcodeStructureModel = null;
    NomenclatureStructureModel NomenclatureStructureModel = null;
    CharacterisiticStructureModel CharacterisiticStructureModel = null;

    public ProductLogic(
            BarcodeStructureModel barcodeStructureModel,
            NomenclatureStructureModel nomenclatureStructureModel,
            CharacterisiticStructureModel characterisiticStructureModel )
    {
       this.BarcodeStructureModel = barcodeStructureModel;
       this.NomenclatureStructureModel = nomenclatureStructureModel;
       this.CharacterisiticStructureModel = characterisiticStructureModel;
    }

    public ArrayList<ListViewPresentationModel> CreateViewModel(String scannedBarcode, BarcodeTypes type) throws ParseException, ApplicationException {
        ScanningBarcodeStructureModel parsedBarcode = new ScanningBarcodeStructureModel(scannedBarcode, type);

        List<BarcodeStructureModel.ProductStructureModel> listOfProducts = BarcodeStructureModel.FindProductByBarcode(parsedBarcode.getUniqueIdentifier());

        if(listOfProducts == null)
        {
            throw new ApplicationException("Такой штрих-код не найден в номенклатуре!");
        }
        else
        {
            ArrayList<ListViewPresentationModel> result = new ArrayList<>();

            for(businesslogic.BarcodeStructureModel.ProductStructureModel product : listOfProducts) {
                result.add(
                        new ListViewPresentationModel(
                        parsedBarcode.getUniqueIdentifier(),
                        NomenclatureStructureModel.FindProductByGuid(product.GetProductGuid()),
                        CharacterisiticStructureModel.FindCharacteristicByGuid(product.GetCharacteristicGUID()),
                        WeightCalculator(parsedBarcode, product),
                        product.GetProductGuid())
                );
            }

            return result;
        }
    }

    private Double WeightCalculator(ScanningBarcodeStructureModel scannedBarcode, businesslogic.BarcodeStructureModel.ProductStructureModel product)
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
