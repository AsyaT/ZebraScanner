package ru.zferma.zebrascanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BarcodeStructure {
    private String UniqueIdentifier;
    private Double Weight;
    private BarcodeTypes LabelType;
    private String LotNumber;
    private Date ProductionDate;
    private Date ExpirationDate;
    private String SerialNumber;
    private Byte InternalProducer;
    private Short InternalEquipment;

    private String FullBarcode;

    public String getUniqueIdentifier()
    {
        return this.UniqueIdentifier;
    }

    public Double getWeight()
    {
        return this.Weight;
    }

    public BarcodeTypes getLabelType()
    {
        return this.LabelType;
    }

    public String getLotNumber(){return this.LotNumber;}

    public String getFullBarcode()
    {
        return this.FullBarcode;
    }

    public Date getProductionDate() {return this.ProductionDate; }

    public Date getExpirationDate() {return this.ExpirationDate; }

    public String getSerialNumber() {return this.SerialNumber;}

    public Byte getInternalProducer() {return this.InternalProducer;}

    public Short getInternalEquipment() {return this.InternalEquipment;}

    public BarcodeStructure( String fullBarcode, BarcodeTypes labelType) throws ParseException {
        FullBarcode = fullBarcode;
        LabelType = labelType;

        if(LabelType == BarcodeTypes.LocalGS1_EXP)
        {
            String stringWeight = fullBarcode.substring(20,26);
            Weight = Double.parseDouble(stringWeight.substring(0,3) + "." + stringWeight.substring(3)) ;
            UniqueIdentifier = fullBarcode.substring(2,16);
            LotNumber = fullBarcode.substring(28, 32);
            ExpirationDate = (new SimpleDateFormat("yyMMdd")).parse( fullBarcode.substring(42, 48) );
            ProductionDate = (new SimpleDateFormat("yyMMdd")).parse(fullBarcode.substring(34,40));
            SerialNumber = fullBarcode.substring(50,55);
            InternalProducer = Byte.parseByte(fullBarcode.substring(57,58));
            InternalEquipment = Short.parseShort(fullBarcode.substring(58,61));
        }
        else if( LabelType == BarcodeTypes.LocalEAN13)
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
