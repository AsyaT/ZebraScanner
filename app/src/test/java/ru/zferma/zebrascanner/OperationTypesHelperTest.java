package ru.zferma.zebrascanner;

import com.symbol.emdk.barcode.ScanDataCollection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import businesslogic.OperationsTypesAccountingAreaStructureModel;
import businesslogic.ApplicationException;
import serverDatabaseInteraction.OperationTypesAndAccountingAreasModel;
import serverDatabaseInteraction.OperationTypesHelper;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ OperationTypesHelper.class })
class OperationTypesHelperTest {

    OperationTypesHelper helper;

    OperationTypesAndAccountingAreasModel model;

    OperationsTypesAccountingAreaStructureModel operationsTypesAccountingAreaStructureModel;

    public OperationTypesHelperTest()
    {
        /*
        OperationsTypesAccountingAreaStructureModel operationsTypesAccountingAreaStructureModel = new OperationsTypesAccountingAreaStructureModel();

        OperationsTypesAccountingAreaStructureModel.Operation operation = new OperationsTypesAccountingAreaStructureModel.Operation();
        operation.SetName("Ротация");

        OperationsTypesAccountingAreaStructureModel.AccountingArea accountingArea = new OperationsTypesAccountingAreaStructureModel.AccountingArea();

        HashMap<ScanDataCollection.LabelType, Boolean> rules = new HashMap<>();
        rules.put(ScanDataCollection.LabelType.EAN13, false);
        rules.put(ScanDataCollection.LabelType.GS1_DATABAR_EXP, true);

        accountingArea.Add("Ротация",rules,false);
        operation.AddAccountingArea("97e2d02c-ad73-11e7-80c4-a4bf011ce3c3",accountingArea);

        operationsTypesAccountingAreaStructureModel.Add("Ротация",operation);
*/

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


        try {
            helper = new OperationTypesHelper("","");
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Whitebox.setInternalState(helper,"InputModel",model);
        try {
            Whitebox.invokeMethod(helper,"ParseIncomeData");
        } catch (Exception e) {
            e.printStackTrace();
        }

        operationsTypesAccountingAreaStructureModel = helper.GetData();
    }

    @Test
    public void Test_HasSeveralAccountingAreas()
    {
        Assert.assertFalse(operationsTypesAccountingAreaStructureModel.HasSeveralAccountingAreas("Ротация"));
        
        Assert.assertTrue(operationsTypesAccountingAreaStructureModel.HasSeveralAccountingAreas("Приемка"));
    }

    @Test
    public void Test_GetAccountingAreas()
    {
        Assert.assertTrue(operationsTypesAccountingAreaStructureModel.GetAccountingAreas("Приемка").containsKey("414d48d4-f210-11e6-80cb-001e67e5da8c"));
        Assert.assertTrue(operationsTypesAccountingAreaStructureModel.GetAccountingAreas("Приемка").containsKey("b50985a2-ddad-11e8-80cd-a4bf011ce3c3"));
    }

    @Test
    public void Test_Permissions()
    {
        HashMap<ScanDataCollection.LabelType, Boolean> permissions = new HashMap<>();
        permissions.put(ScanDataCollection.LabelType.EAN13, Boolean.FALSE);
        permissions.put(ScanDataCollection.LabelType.GS1_DATABAR_EXP, Boolean.TRUE);

        OperationsTypesAccountingAreaStructureModel.AccountingArea aa_Rotation = operationsTypesAccountingAreaStructureModel
                .GetAccountingAreas("Ротация")
                .get("97e2d02c-ad73-11e7-80c4-a4bf011ce3c3");
        OperationsTypesAccountingAreaStructureModel.AccountingArea aa_Aception = operationsTypesAccountingAreaStructureModel
                .GetAccountingAreas("Приемка")
                .get("b50985a2-ddad-11e8-80cd-a4bf011ce3c3");

        Assert.assertEquals(permissions, aa_Rotation.GetScanningPermissions());
        Assert.assertEquals(permissions, aa_Aception.GetScanningPermissions());

        Assert.assertEquals(Boolean.TRUE, aa_Rotation.IsPackageListAllowed());
    }

    @Test
    public void Test_GetOperationName()
    {
        Assert.assertEquals("Приемка", operationsTypesAccountingAreaStructureModel.GetOperationName("Приемка"));
    }

    @Test
    public void Test_GetOperationSet()
    {
        Assert.assertEquals(2,operationsTypesAccountingAreaStructureModel.GetOperationKeys().size());
    }

}
