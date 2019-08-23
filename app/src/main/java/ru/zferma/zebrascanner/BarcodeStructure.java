package ru.zferma.zebrascanner;

import com.symbol.emdk.barcode.ScanDataCollection;

public class BarcodeStructure {
    private String UniqueIdentifier;
    private String Weight;
    private ScanDataCollection.LabelType LabelType;

    public String getUniqueIdentifier()
    {
        return this.UniqueIdentifier;
    }

    public String getWeight()
    {
        return this.Weight;
    }

    public ScanDataCollection.LabelType getLabelType()
    {
        return this.LabelType;
    }

    public BarcodeStructure(String fullBarcode, ScanDataCollection.LabelType labelType)
    {
        LabelType = labelType;

        // Type of barcode id Weight Barcode
        if (fullBarcode.startsWith("2")) {
            Weight = fullBarcode.substring(7, 12);
            UniqueIdentifier = fullBarcode.substring(0, 7);
        }
        // Type of barcode is databar
        else if(fullBarcode.startsWith("01")) {
            Weight = fullBarcode.substring(20,26);
            UniqueIdentifier = fullBarcode.substring(2,16);
        }
        // EAN 13
        else
        {
            UniqueIdentifier = fullBarcode;
            Weight = null;
        }
    }
}
