package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.HashMap;

import businesslogic.OperationTypesAndAccountingAreasModel;
import businesslogic.OperationTypesHelper;

import static com.symbol.emdk.barcode.ScanDataCollection.LabelType;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ OperationTypesHelper.class })
class OperationTypesHelperTest {

    OperationTypesHelper helper;

    OperationTypesAndAccountingAreasModel model;

    public OperationTypesHelperTest()
    {

        model = new OperationTypesAndAccountingAreasModel();
        model.Error = false;
        model.AccountingAreasAndTypes = new ArrayList<OperationTypesAndAccountingAreasModel.OperationTypeModel>();

        OperationTypesAndAccountingAreasModel.OperationTypeModel opType_1 = new OperationTypesAndAccountingAreasModel.OperationTypeModel();
        opType_1.OperationTypeName="Ротация";
        opType_1.OperationTypeID="Ротация";
        opType_1.AccountingAreas = new ArrayList<OperationTypesAndAccountingAreasModel.AccountingAreaModel>();

        OperationTypesAndAccountingAreasModel.AccountingAreaModel op_1_aa = new OperationTypesAndAccountingAreasModel.AccountingAreaModel();
        op_1_aa.Name = "Ротация";
        op_1_aa.GUID = "97e2d02c-ad73-11e7-80c4-a4bf011ce3c3";
        op_1_aa.EAN13_Denied = Boolean.TRUE;
        op_1_aa.DataBar_Denied = Boolean.FALSE;
        op_1_aa.PackageList_Denied = Boolean.FALSE;
        opType_1.AccountingAreas.add(op_1_aa);

        model.AccountingAreasAndTypes.add(opType_1);

        OperationTypesAndAccountingAreasModel.OperationTypeModel opType_2 = new OperationTypesAndAccountingAreasModel.OperationTypeModel();
        opType_2.OperationTypeName="Приемка";
        opType_2.OperationTypeID="Приемка";
        opType_2.AccountingAreas = new ArrayList<OperationTypesAndAccountingAreasModel.AccountingAreaModel>();

        OperationTypesAndAccountingAreasModel.AccountingAreaModel op_2_aa_1 = new OperationTypesAndAccountingAreasModel.AccountingAreaModel();
        op_2_aa_1.Name = "Приемка на 6-4-1";
        op_2_aa_1.GUID = "414d48d4-f210-11e6-80cb-001e67e5da8c";
        op_2_aa_1.EAN13_Denied = Boolean.TRUE;
        op_2_aa_1.DataBar_Denied = Boolean.FALSE;
        op_2_aa_1.PackageList_Denied = Boolean.FALSE;
        opType_2.AccountingAreas.add(op_2_aa_1);

        OperationTypesAndAccountingAreasModel.AccountingAreaModel op_2_aa_2 = new OperationTypesAndAccountingAreasModel.AccountingAreaModel();
        op_2_aa_2.Name = "Приемка на 6-4-2";
        op_2_aa_2.GUID = "b50985a2-ddad-11e8-80cd-a4bf011ce3c3";
        op_2_aa_2.EAN13_Denied = Boolean.TRUE;
        op_2_aa_2.DataBar_Denied = Boolean.FALSE;
        op_2_aa_2.PackageList_Denied = Boolean.FALSE;
        opType_2.AccountingAreas.add(op_2_aa_2);

        model.AccountingAreasAndTypes.add(opType_2);


        helper = new OperationTypesHelper("","");
        Whitebox.setInternalState(helper,"InputModel",model);
    }

    @Test
    public void Test_HasSeveralAccountingAreas()
    {
        Assert.assertFalse(helper.HasSeveralAccountingAreas("Ротация"));
        
        Assert.assertTrue(helper.HasSeveralAccountingAreas("Приемка"));
    }

    @Test
    public void Test_GetAccountingAreas()
    {
        ArrayList<String> accountingAreas = new ArrayList<String>();
        accountingAreas.add("Приемка на 6-4-1");
        accountingAreas.add("Приемка на 6-4-2");
        Assert.assertEquals(accountingAreas,helper.GetAccountingAreas("Приемка"));
    }

    @Test
    public void Test_GetSingleAccountingArea()
    {
        Assert.assertEquals(model.AccountingAreasAndTypes.get(0).AccountingAreas.get(0),helper.GetSingleAccountingArea("Ротация"));
        Assert.assertEquals(null,helper.GetSingleAccountingArea("Приемка"));
    }

    @Test
    public void Test_Permissions()
    {
        HashMap<LabelType, Boolean> permissions = new HashMap<>();
        permissions.put(LabelType.EAN13, Boolean.FALSE);
        permissions.put(LabelType.GS1_DATABAR_EXP, Boolean.TRUE);

        Assert.assertEquals(permissions, helper.GetScanningPermissions("Ротация"));
        Assert.assertEquals(permissions, helper.GetScanningPermissions("Приемка на 6-4-2"));

        Assert.assertEquals(Boolean.FALSE, helper.IsPackageListScanningAllowed("Ротация"));
    }
}
