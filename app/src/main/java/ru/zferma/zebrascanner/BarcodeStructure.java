package ru.zferma.zebrascanner;

import com.symbol.emdk.barcode.ScanDataCollection;

public class BarcodeStructure {
    private String UniqueIdentifier;
    private Double Weight;
    private ScanDataCollection.LabelType LabelType;

    public String getUniqueIdentifier()
    {
        return this.UniqueIdentifier;
    }

    public Double getWeight()
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

        if(LabelType == ScanDataCollection.LabelType.GS1_DATABAR_EXP)
        {
            String stringWeight = fullBarcode.substring(20,26);
            Weight = Double.parseDouble(stringWeight.substring(0,3) + "." + stringWeight.substring(3)) ;
            UniqueIdentifier = fullBarcode.substring(2,16);
        }
        else if( LabelType == ScanDataCollection.LabelType.EAN13)
        {
            if (fullBarcode.startsWith("2")) {
                String stringWeight = fullBarcode.substring(7, 12);
                Weight = Double.parseDouble( stringWeight.substring(0,2) + "." + stringWeight.substring(2) );
                UniqueIdentifier = fullBarcode.substring(0, 7);
            }
            else
            {
                UniqueIdentifier = fullBarcode;
                Weight = null;
            }
        }
    }
}
