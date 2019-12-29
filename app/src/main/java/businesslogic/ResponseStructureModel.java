package businesslogic;

import java.util.ArrayList;

public class ResponseStructureModel {

    public ResponseStructureModel()
    {
        this.ProductList = new ArrayList<>();
    }

    public String AccountingAreaGUID;
    public String UserID;
    public String DocumentID;
    public ArrayList<ResponseProductStructureModel> ProductList;

    public static class ResponseProductStructureModel
    {
        public String ProductGUID;
        public String ProductCharactGUID;
        public String Weigth;
        public String Pieces;
        public String DateOfProduction;
        public String DataOfExpiration;
        public String ManufacturerGUID;
    }
}
