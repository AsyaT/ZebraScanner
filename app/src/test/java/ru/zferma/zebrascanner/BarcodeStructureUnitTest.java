package ru.zferma.zebrascanner;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class BarcodeStructureUnitTest {

    private final String EAN13_BARCODE="4603502137574";
    private final String WEIGHT_EAN13_BARCODE ="2209983009489";
    private final String GS1_EXP_BARCODE="0104630037036817310302530010082011190820171908252100001923000";

    private BarcodeStructure UnderTest_EAN13_BarcodeStructure;
    private BarcodeStructure UnderTest_WEIGHTEAN13_BarcodeStructure;
    private BarcodeStructure UnderTest_GS1_BarcodeStructure;

    @Before
    public void InitBarcodeStructure() throws ParseException {
        UnderTest_EAN13_BarcodeStructure = new BarcodeStructure(EAN13_BARCODE, BarcodeTypes.LocalEAN13);
        UnderTest_WEIGHTEAN13_BarcodeStructure = new BarcodeStructure(WEIGHT_EAN13_BARCODE, BarcodeTypes.LocalEAN13);
        UnderTest_GS1_BarcodeStructure = new BarcodeStructure(GS1_EXP_BARCODE, BarcodeTypes.LocalGS1_EXP);
    }

    @Test
    public void test_EAN13()
    {
        assertEquals(UnderTest_EAN13_BarcodeStructure.getFullBarcode(),EAN13_BARCODE);
        assertEquals(UnderTest_EAN13_BarcodeStructure.getUniqueIdentifier(),EAN13_BARCODE);
        assertEquals(UnderTest_EAN13_BarcodeStructure.getWeight(),null);
        assertEquals(UnderTest_EAN13_BarcodeStructure.getExpirationDate(),null);
        assertEquals(UnderTest_EAN13_BarcodeStructure.getInternalEquipment(),null);
        assertEquals(UnderTest_EAN13_BarcodeStructure.getInternalProducer(),null);
        assertEquals(UnderTest_EAN13_BarcodeStructure.getLotNumber(),null);
        assertEquals(UnderTest_EAN13_BarcodeStructure.getProductionDate(),null);
        assertEquals(UnderTest_EAN13_BarcodeStructure.getSerialNumber(),null);
    }

    @Test
    public void test_weight_EAN13()
    {
        assertEquals(UnderTest_WEIGHTEAN13_BarcodeStructure.getFullBarcode(), WEIGHT_EAN13_BARCODE);
        assertEquals(UnderTest_WEIGHTEAN13_BarcodeStructure.getUniqueIdentifier(), "2209983");
        assertEquals(UnderTest_WEIGHTEAN13_BarcodeStructure.getWeight(), (Double) 0.948);
        assertEquals(UnderTest_WEIGHTEAN13_BarcodeStructure.getExpirationDate(),null);
        assertEquals(UnderTest_WEIGHTEAN13_BarcodeStructure.getInternalEquipment(),null);
        assertEquals(UnderTest_WEIGHTEAN13_BarcodeStructure.getInternalProducer(),null);
        assertEquals(UnderTest_WEIGHTEAN13_BarcodeStructure.getLotNumber(),null);
        assertEquals(UnderTest_WEIGHTEAN13_BarcodeStructure.getProductionDate(),null);
        assertEquals(UnderTest_WEIGHTEAN13_BarcodeStructure.getSerialNumber(),null);
    }

    @Test
    public void test_GS1() throws ParseException {
        assertEquals(UnderTest_GS1_BarcodeStructure.getFullBarcode(), GS1_EXP_BARCODE);
        assertEquals(UnderTest_GS1_BarcodeStructure.getUniqueIdentifier(), (String)"04630037036817");
        assertEquals(UnderTest_GS1_BarcodeStructure.getWeight(), (Double) 25.3);
        assertEquals(UnderTest_GS1_BarcodeStructure.getProductionDate(),new SimpleDateFormat("dd/MM/yyyy").parse("20/08/2019"));
        assertEquals(UnderTest_GS1_BarcodeStructure.getExpirationDate(),new SimpleDateFormat("dd/MM/yyyy").parse("25/08/2019"));
        assertEquals(UnderTest_GS1_BarcodeStructure.getLotNumber(),"0820");
        assertEquals(UnderTest_GS1_BarcodeStructure.getSerialNumber(),"00001");
        Short expectedEquipment = 0;
        assertEquals(UnderTest_GS1_BarcodeStructure.getInternalEquipment(),  expectedEquipment);
        Byte expectedProducer = 3;
        assertEquals(UnderTest_GS1_BarcodeStructure.getInternalProducer(), expectedProducer);
    }
}