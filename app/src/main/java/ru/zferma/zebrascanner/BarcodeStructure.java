package ru.zferma.zebrascanner;

public class BarcodeStructure {
    private String UniqueIdentifier;
    private String Weight;

    public String getUniqueIdentifier()
    {
        return UniqueIdentifier;
    }

    public String getWeight()
    {
        return Weight;
    }

    public BarcodeStructure(String fullBarcode)
    {
        if (fullBarcode.startsWith("2")) {
            Weight = fullBarcode.substring(7, 12);
            UniqueIdentifier = fullBarcode.substring(0, 7);
        }

        if(fullBarcode.startsWith("01")) {
            Weight = fullBarcode.substring(20,26);
            UniqueIdentifier = fullBarcode.substring(2,16);
        }
    }
}
