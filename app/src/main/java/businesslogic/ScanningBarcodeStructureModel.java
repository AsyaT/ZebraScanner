package businesslogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScanningBarcodeStructureModel {
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

    public Boolean isEqual(ScanningBarcodeStructureModel toCompare)
    {
        if(this.getFullBarcode().equalsIgnoreCase(toCompare.getFullBarcode()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void Cycle(String fullBarcode) throws ParseException, ApplicationException {
        String gtin = "01";
        String newWeight = "3103";
        String lotNumber="10";
        String productionDate = "11";
        String expirationDate = "17";
        String serialNumber = "21";
        String internalCompanyCodes = "92";

        Integer maxCycleNumber = 100;

        while (fullBarcode.length()>0 && maxCycleNumber > 0)
        {
            if(fullBarcode.startsWith(gtin))
            {
                UniqueIdentifier = fullBarcode.substring(gtin.length(), gtin.length()+14);
                fullBarcode = fullBarcode.substring(gtin.length()+14);
            }

            if(fullBarcode.startsWith(newWeight))
            {
                String stringWeight = fullBarcode.substring(newWeight.length(), newWeight.length() + 6);
                Weight = Double.parseDouble(stringWeight.substring(0,3) + "." + stringWeight.substring(3)) ;
                fullBarcode = fullBarcode.substring(newWeight.length()+6);
            }

            if(fullBarcode.startsWith(lotNumber))
            {
                LotNumber = fullBarcode.substring(lotNumber.length(), lotNumber.length() + 4);
                fullBarcode = fullBarcode.substring(lotNumber.length() + 4);
            }

            if(fullBarcode.startsWith(productionDate))
            {
                String productionDateString = fullBarcode.substring(productionDate.length(), productionDate.length() + 6);
                ProductionDate = (new SimpleDateFormat("yyMMdd")).parse(productionDateString);
                fullBarcode = fullBarcode.substring(productionDate.length()+6);
            }

            if(fullBarcode.startsWith(expirationDate))
            {
                String expirationDateString = fullBarcode.substring(expirationDate.length(), expirationDate.length() + 6);
                ExpirationDate = (new SimpleDateFormat("yyMMdd")).parse(expirationDateString);
                fullBarcode = fullBarcode.substring(expirationDate.length()+6);
            }

            if(fullBarcode.startsWith(serialNumber))
            {
                SerialNumber = fullBarcode.substring(serialNumber.length(), serialNumber.length() + 5);
                fullBarcode = fullBarcode.substring(serialNumber.length() + 5);
            }

            if(fullBarcode.startsWith(internalCompanyCodes))
            {
                InternalProducer =  Byte.parseByte(fullBarcode.substring(internalCompanyCodes.length(),internalCompanyCodes.length()+1 ));
                InternalEquipment= Short.parseShort(fullBarcode.substring(internalCompanyCodes.length()+1, internalCompanyCodes.length()+ 1 + 3));
                fullBarcode = fullBarcode.substring(internalCompanyCodes.length() + 4);
            }

            maxCycleNumber--;
        }

        if(maxCycleNumber == 0)
        {
            throw new ApplicationException("Штрих-код не корректен. Невозможно распознать " + fullBarcode);
        }
    }

    public ScanningBarcodeStructureModel(String fullBarcode, BarcodeTypes labelType) throws ParseException, ApplicationException {
        FullBarcode = fullBarcode;
        LabelType = labelType;

        if(LabelType == BarcodeTypes.LocalGS1_EXP)
        {
            Cycle(fullBarcode);
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

        while ( UniqueIdentifier.startsWith("0"))
        {
            UniqueIdentifier = UniqueIdentifier.substring(1);
        };

    }
}
