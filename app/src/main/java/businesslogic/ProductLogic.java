package businesslogic;

public class ProductLogic {

    NomenclatureStructureModel NomenclatureStructureModel = null;
    CharacterisiticStructureModel CharacterisiticStructureModel = null;
    ManufacturerStructureModel ManufacturerStructureModel = null;
    BaseDocumentStructureModel BaseDocumentStructureModel = null;

    public ProductLogic(
            NomenclatureStructureModel nomenclatureStructureModel,
            CharacterisiticStructureModel characterisiticStructureModel,
            ManufacturerStructureModel manufacturerStructureModel,
            BaseDocumentStructureModel baseDocumentStructureModel)
    {
       this.NomenclatureStructureModel = nomenclatureStructureModel;
       this.CharacterisiticStructureModel = characterisiticStructureModel;
       this.ManufacturerStructureModel = manufacturerStructureModel;
       this.BaseDocumentStructureModel = baseDocumentStructureModel;
    }

    public ListViewPresentationModel CreateListView(ProductModel product)
    {
        return
                new ListViewPresentationModel(
                        NomenclatureStructureModel.FindProductByGuid(product.GetProductGuid()),
                        CharacterisiticStructureModel.FindCharacteristicByGuid(product.GetCharacteristicGUID()),
                        product.GetWeight(),
                        product.GetProductGuid())
        ;
    }

    public FullDataTableControl.Details CreateDetails(ProductModel product)
    {
        return new FullDataTableControl.Details(
                product.GetProductGuid(),
                product.GetCharacteristicGUID(),
                product.GetWeight(),
                product.GetProductionDate(),
                product.getExpirationDate(),
                product.GetManufacturerGuid());
    }



    public Boolean IsExistsInOrder(ProductModel product) throws ApplicationException {
        if(this.BaseDocumentStructureModel != null && this.BaseDocumentStructureModel.IfProductExists(product.GetProductGuid()) )
        {
            return true;
        }
        else
        {
            throw new ApplicationException("Этот продукт не содержится в документе-основании");
        }
    }
}
