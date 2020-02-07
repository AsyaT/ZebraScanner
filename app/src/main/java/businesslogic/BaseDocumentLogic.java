package businesslogic;

public class BaseDocumentLogic {

    BaseDocumentStructureModel BaseDocumentStructureModel = null;

    public BaseDocumentLogic(BaseDocumentStructureModel baseDocumentStructureModel)
    {
        this.BaseDocumentStructureModel = baseDocumentStructureModel;
    }

    public Boolean IsExistsInOrder(ProductModel product) throws DoesNotExistsInOrderException {
        if(this.BaseDocumentStructureModel == null)
        {
            throw new DoesNotExistsInOrderException("Документ-основание не отсканирован.");
        }

        if( this.BaseDocumentStructureModel.IfProductExists(product.GetProductGuid()) )
        {
            return true;
        }
        else
        {
            throw new DoesNotExistsInOrderException("Этот продукт не содержится в документе-основании");
        }
    }

    public Boolean IsBaseDocumentScanned() throws DoesNotExistsInOrderException {
        if(this.BaseDocumentStructureModel == null)
        {
            throw new DoesNotExistsInOrderException("Документ-основание не отсканирован.");
        }
        else
        {
            return true;
        }
    }
}
