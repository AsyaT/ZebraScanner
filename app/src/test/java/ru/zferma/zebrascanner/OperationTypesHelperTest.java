package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;

import businesslogic.OperationTypesAndAccountingAreasModel;
import businesslogic.OperationTypesHelper;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ OperationTypesHelper.class })
class OperationTypesHelperTest {

    @Mock
    OperationTypesHelper helper;

    @Mock
    OperationTypesAndAccountingAreasModel model;

    public OperationTypesHelperTest()
    {

    }

    @Test
    public void DoTest() throws Exception {

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
        opType_1.AccountingAreas.add(op_1_aa);

        model.AccountingAreasAndTypes.add(opType_1);

        OperationTypesAndAccountingAreasModel.OperationTypeModel opType_2 = new OperationTypesAndAccountingAreasModel.OperationTypeModel();
        opType_2.OperationTypeName="Приемка";
        opType_2.OperationTypeID="Приемка";
        opType_2.AccountingAreas = new ArrayList<OperationTypesAndAccountingAreasModel.AccountingAreaModel>();

        OperationTypesAndAccountingAreasModel.AccountingAreaModel op_2_aa_1 = new OperationTypesAndAccountingAreasModel.AccountingAreaModel();
        op_2_aa_1.Name = "Приемка на 6-4-1";
        op_2_aa_1.GUID = "414d48d4-f210-11e6-80cb-001e67e5da8c";
        opType_2.AccountingAreas.add(op_2_aa_1);

        OperationTypesAndAccountingAreasModel.AccountingAreaModel op_2_aa_2 = new OperationTypesAndAccountingAreasModel.AccountingAreaModel();
        op_2_aa_2.Name = "Приемка на 6-4-2";
        op_2_aa_2.GUID = "b50985a2-ddad-11e8-80cd-a4bf011ce3c3";
        opType_2.AccountingAreas.add(op_2_aa_2);

        model.AccountingAreasAndTypes.add(opType_2);

        PowerMockito.whenNew(OperationTypesHelper.class).withAnyArguments().thenReturn(helper);
        Whitebox.setInternalState(helper,"InputModel",model);

        PowerMockito.when(helper.HasSeveralAccountingAreas("Ротация")).thenReturn(Boolean.FALSE);
        Assert.assertFalse(helper.HasSeveralAccountingAreas("Ротация"));

        Boolean result = helper.HasSeveralAccountingAreas("Приемка");
        PowerMockito.when(helper.HasSeveralAccountingAreas("Приемка")).thenReturn(Boolean.TRUE);
        Assert.assertTrue(result);


        ArrayList<String> accountingAreas = new ArrayList<String>();
        accountingAreas.add("Приемка на 6-4-1");
        accountingAreas.add("Приемка на 6-4-2");
        Assert.assertEquals(accountingAreas,helper.GetAccountingAreas("Приемка"));

    }
}
