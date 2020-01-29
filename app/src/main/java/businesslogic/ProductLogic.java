package businesslogic;

public class ProductLogic {

    NomenclatureStructureModel NomenclatureStructureModel = null;
    CharacterisiticStructureModel CharacterisiticStructureModel = null;
    ManufacturerStructureModel ManufacturerStructureModel = null;


    public ProductLogic(
            NomenclatureStructureModel nomenclatureStructureModel,
            CharacterisiticStructureModel characterisiticStructureModel,
            ManufacturerStructureModel manufacturerStructureModel
            )
    {
       this.NomenclatureStructureModel = nomenclatureStructureModel;
       this.CharacterisiticStructureModel = characterisiticStructureModel;
       this.ManufacturerStructureModel = manufacturerStructureModel;

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




}
