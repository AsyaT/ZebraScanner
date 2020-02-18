package ru.zferma.zebrascanner;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import businesslogic.ApplicationException;
import businesslogic.ScanningBarcodeStructureModel;
import businesslogic.BarcodeTypes;

import static org.junit.Assert.assertEquals;

public class ScanningBarcodeStructureModelUnitTest {

    private final String EAN13_BARCODE="4603502137574";
    private final String WEIGHT_EAN13_BARCODE ="2209983009489";
    private final String GS1_EXP_BARCODE="0104630037036817310302530010082011190820171908252100001923000";
    private final String GS1_EXP_EGG_BARCODE="010463004929467011190820171909091008202100001926000";
    private final String GS1_EXP_EGG_BARCODE_SPOIL="01046300492946701119082017190909100820a2100001926000";

    private ScanningBarcodeStructureModel underTest_EAN13_Scanning_BarcodeStructureModel;
    private ScanningBarcodeStructureModel underTest_WEIGHTEAN13_Scanning_BarcodeStructureModel;
    private ScanningBarcodeStructureModel underTest_GS1_Scanning_BarcodeStructureModel;
    private ScanningBarcodeStructureModel underTest_GS1_EGG_Scanning_BarcodeStructureModel;
    private ScanningBarcodeStructureModel underTest_GS1_EGG_Scanning_Spoil_BarcodeStructureModel;

    @Before
    public void InitBarcodeStructure() throws ParseException, ApplicationException {
        underTest_EAN13_Scanning_BarcodeStructureModel = new ScanningBarcodeStructureModel(EAN13_BARCODE, BarcodeTypes.LocalEAN13);
        underTest_WEIGHTEAN13_Scanning_BarcodeStructureModel = new ScanningBarcodeStructureModel(WEIGHT_EAN13_BARCODE, BarcodeTypes.LocalEAN13);
        underTest_GS1_Scanning_BarcodeStructureModel = new ScanningBarcodeStructureModel(GS1_EXP_BARCODE, BarcodeTypes.LocalGS1_EXP);
        underTest_GS1_EGG_Scanning_BarcodeStructureModel = new ScanningBarcodeStructureModel(GS1_EXP_EGG_BARCODE, BarcodeTypes.LocalGS1_EXP);
        underTest_GS1_EGG_Scanning_Spoil_BarcodeStructureModel = new ScanningBarcodeStructureModel(GS1_EXP_EGG_BARCODE_SPOIL, BarcodeTypes.LocalGS1_EXP);
    }

    @Test
    public void test_EAN13()
    {
        assertEquals(underTest_EAN13_Scanning_BarcodeStructureModel.getFullBarcode(),EAN13_BARCODE);
        assertEquals(underTest_EAN13_Scanning_BarcodeStructureModel.getUniqueIdentifier(),EAN13_BARCODE);
        assertEquals(underTest_EAN13_Scanning_BarcodeStructureModel.getWeight(),null);
        assertEquals(underTest_EAN13_Scanning_BarcodeStructureModel.getExpirationDate(),null);
        assertEquals(underTest_EAN13_Scanning_BarcodeStructureModel.getInternalEquipment(),null);
        assertEquals(underTest_EAN13_Scanning_BarcodeStructureModel.getInternalProducer(),null);
        assertEquals(underTest_EAN13_Scanning_BarcodeStructureModel.getLotNumber(),null);
        assertEquals(underTest_EAN13_Scanning_BarcodeStructureModel.getProductionDate(),null);
        assertEquals(underTest_EAN13_Scanning_BarcodeStructureModel.getSerialNumber(),null);
    }

    @Test
    public void test_weight_EAN13()
    {
        assertEquals(underTest_WEIGHTEAN13_Scanning_BarcodeStructureModel.getFullBarcode(), WEIGHT_EAN13_BARCODE);
        assertEquals(underTest_WEIGHTEAN13_Scanning_BarcodeStructureModel.getUniqueIdentifier(), "2209983");
        assertEquals(underTest_WEIGHTEAN13_Scanning_BarcodeStructureModel.getWeight(), (Double) 0.948);
        assertEquals(underTest_WEIGHTEAN13_Scanning_BarcodeStructureModel.getExpirationDate(),null);
        assertEquals(underTest_WEIGHTEAN13_Scanning_BarcodeStructureModel.getInternalEquipment(),null);
        assertEquals(underTest_WEIGHTEAN13_Scanning_BarcodeStructureModel.getInternalProducer(),null);
        assertEquals(underTest_WEIGHTEAN13_Scanning_BarcodeStructureModel.getLotNumber(),null);
        assertEquals(underTest_WEIGHTEAN13_Scanning_BarcodeStructureModel.getProductionDate(),null);
        assertEquals(underTest_WEIGHTEAN13_Scanning_BarcodeStructureModel.getSerialNumber(),null);
    }

    @Test
    public void test_GS1() throws ParseException {
        assertEquals(underTest_GS1_Scanning_BarcodeStructureModel.getFullBarcode(), GS1_EXP_BARCODE);
        assertEquals(underTest_GS1_Scanning_BarcodeStructureModel.getUniqueIdentifier(), (String)"4630037036817");
        assertEquals(underTest_GS1_Scanning_BarcodeStructureModel.getWeight(), (Double) 25.3);
        assertEquals(underTest_GS1_Scanning_BarcodeStructureModel.getProductionDate(),new SimpleDateFormat("dd/MM/yyyy").parse("20/08/2019"));
        assertEquals(underTest_GS1_Scanning_BarcodeStructureModel.getExpirationDate(),new SimpleDateFormat("dd/MM/yyyy").parse("25/08/2019"));
        assertEquals(underTest_GS1_Scanning_BarcodeStructureModel.getLotNumber(),"0820");
        assertEquals(underTest_GS1_Scanning_BarcodeStructureModel.getSerialNumber(),"00001");
        Short expectedEquipment = 0;
        assertEquals(underTest_GS1_Scanning_BarcodeStructureModel.getInternalEquipment(),  expectedEquipment);
        Byte expectedProducer = 3;
        assertEquals(underTest_GS1_Scanning_BarcodeStructureModel.getInternalProducer(), expectedProducer);
    }

    @Test
    public void test_GS1_Egg() throws ParseException
    {
        assertEquals(underTest_GS1_EGG_Scanning_BarcodeStructureModel.getFullBarcode(), GS1_EXP_EGG_BARCODE);
        assertEquals(underTest_GS1_EGG_Scanning_BarcodeStructureModel.getUniqueIdentifier(), "4630049294670");
        assertEquals(underTest_GS1_EGG_Scanning_BarcodeStructureModel.getWeight(), null);
        assertEquals(underTest_GS1_EGG_Scanning_BarcodeStructureModel.getProductionDate(), new SimpleDateFormat("dd/MM/yyyy").parse("20/08/2019"));
        assertEquals(underTest_GS1_EGG_Scanning_BarcodeStructureModel.getExpirationDate(), new SimpleDateFormat("dd/MM/yyyy").parse("09/09/2019"));
        assertEquals(underTest_GS1_EGG_Scanning_BarcodeStructureModel.getLotNumber(),"0820");
        assertEquals(underTest_GS1_EGG_Scanning_BarcodeStructureModel.getSerialNumber(),"00001");
        Short expectedEquipment = 0;
        assertEquals(underTest_GS1_EGG_Scanning_BarcodeStructureModel.getInternalEquipment(), expectedEquipment);
        Byte expectedProducer = 6;
        assertEquals(underTest_GS1_EGG_Scanning_BarcodeStructureModel.getInternalProducer(), expectedProducer);
    }

    @Test
    public void test_GS1_Egg_Spoil() throws ParseException
    {
        assertEquals(underTest_GS1_EGG_Scanning_Spoil_BarcodeStructureModel.getFullBarcode(), GS1_EXP_EGG_BARCODE);
        assertEquals(underTest_GS1_EGG_Scanning_Spoil_BarcodeStructureModel.getUniqueIdentifier(), "4630049294670");
        assertEquals(underTest_GS1_EGG_Scanning_Spoil_BarcodeStructureModel.getWeight(), null);
        assertEquals(underTest_GS1_EGG_Scanning_Spoil_BarcodeStructureModel.getProductionDate(), new SimpleDateFormat("dd/MM/yyyy").parse("20/08/2019"));
        assertEquals(underTest_GS1_EGG_Scanning_Spoil_BarcodeStructureModel.getExpirationDate(), new SimpleDateFormat("dd/MM/yyyy").parse("09/09/2019"));
        assertEquals(underTest_GS1_EGG_Scanning_Spoil_BarcodeStructureModel.getLotNumber(),"0820");
        assertEquals(underTest_GS1_EGG_Scanning_Spoil_BarcodeStructureModel.getSerialNumber(),"00001");
        Short expectedEquipment = 0;
        assertEquals(underTest_GS1_EGG_Scanning_Spoil_BarcodeStructureModel.getInternalEquipment(), expectedEquipment);
        Byte expectedProducer = 6;
        assertEquals(underTest_GS1_EGG_Scanning_Spoil_BarcodeStructureModel.getInternalProducer(), expectedProducer);
    }
}