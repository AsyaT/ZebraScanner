package businesslogic;

public class ProductLogic {

    NomenclatureStructureModel NomenclatureStructureModel = null;
    CharacteristicStructureModel characteristicStructureModel = null;
    ManufacturerStructureModel ManufacturerStructureModel = null;


    public ProductLogic(
            NomenclatureStructureModel nomenclatureStructureModel,
            CharacteristicStructureModel characterisiticStructureModel,
            ManufacturerStructureModel manufacturerStructureModel
            )
    {
       this.NomenclatureStructureModel = nomenclatureStructureModel;
       this.characteristicStructureModel = characterisiticStructureModel;
       this.ManufacturerStructureModel = manufacturerStructureModel;

    }

    public ListViewPresentationModel CreateListView(ProductModel product)
    {
        return
                new ListViewPresentationModel(
                        NomenclatureStructureModel.FindProductByGuid(product.GetProductGuid()),
                        characteristicStructureModel.FindCharacteristicByGuid(product.GetCharacteristicGUID()),
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
