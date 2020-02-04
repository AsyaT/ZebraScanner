package businesslogic;

public class BaseDocumentLogic {

    BaseDocumentStructureModel BaseDocumentStructureModel = null;

    public BaseDocumentLogic(BaseDocumentStructureModel baseDocumentStructureModel)
    {
        this.BaseDocumentStructureModel = baseDocumentStructureModel;
    }

    public Boolean IsExistsInOrder(ProductModel product) throws ApplicationException {
        if(this.BaseDocumentStructureModel == null)
        {
            throw new ApplicationException("Документ-основание не отсканирован.");
        }

        if( this.BaseDocumentStructureModel.IfProductExists(product.GetProductGuid()) )
        {
            return true;
        }
        else
        {
            throw new ApplicationException("Этот продукт не содержится в документе-основании");
        }
    }
}
