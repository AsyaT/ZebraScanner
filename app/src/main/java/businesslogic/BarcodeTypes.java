package businesslogic;

import com.symbol.emdk.barcode.ScanDataCollection;

public enum BarcodeTypes {
    LocalEAN13, LocalGS1_EXP;

    public static BarcodeTypes GetType(ScanDataCollection.LabelType labelType)
    {
        switch (labelType){
            case EAN13: return LocalEAN13;
            case GS1_DATABAR_EXP: return LocalGS1_EXP;
            default:return LocalEAN13;
        }
    }
}

