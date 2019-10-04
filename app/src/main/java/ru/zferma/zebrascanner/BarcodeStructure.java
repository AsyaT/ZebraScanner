package ru.zferma.zebrascanner;

import com.symbol.emdk.barcode.ScanDataCollection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BarcodeStructure {
    private String UniqueIdentifier;
    private Double Weight;
    private ScanDataCollection.LabelType LabelType;
    private String LotNumber;
    private Date ProductionDate;
    private Date ExpirationDate;
    private Integer SerialNumber;
    private Short InternalProducer;
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

    public ScanDataCollection.LabelType getLabelType()
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

    public Integer getSerialNumber() {return this.SerialNumber;}

    public Short getInternalProducer() {return this.InternalProducer;}

    public Short getInternalEquipment() {return this.InternalEquipment;}

    public BarcodeStructure( String fullBarcode, ScanDataCollection.LabelType labelType) throws ParseException {
        FullBarcode = fullBarcode;
        LabelType = labelType;

        if(LabelType == ScanDataCollection.LabelType.GS1_DATABAR_EXP)
        {
            String stringWeight = fullBarcode.substring(20,26);
            Weight = Double.parseDouble(stringWeight.substring(0,3) + "." + stringWeight.substring(3)) ;
            UniqueIdentifier = fullBarcode.substring(2,16);
            LotNumber = fullBarcode.substring(28, 32);
            ProductionDate = (new SimpleDateFormat("yyMMdd")).parse( fullBarcode.substring(34, 40) );
            ExpirationDate = (new SimpleDateFormat("yyMMdd")).parse(fullBarcode.substring(42,48));
            SerialNumber = Integer.parseInt(fullBarcode.substring(50,55));
            InternalProducer = Short.parseShort(fullBarcode.substring(57,58));
            InternalEquipment = Short.parseShort(fullBarcode.substring(58,61));
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
