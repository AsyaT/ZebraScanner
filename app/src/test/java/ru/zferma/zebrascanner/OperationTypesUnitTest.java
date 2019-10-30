package ru.zferma.zebrascanner;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class OperationTypesUnitTest {
    @Test
    public void GetDataTest()
    {
        OperationTypes op = new OperationTypes();
        OperationTypesAndAccountingAreasModel data = op.GetData();

        assertEquals(false, data.Error);
        assertEquals(6, data.AccountingAreasAndTypes.size());
    }
}
