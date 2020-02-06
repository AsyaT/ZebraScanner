package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import businesslogic.ApplicationException;
import businesslogic.ManufacturerStructureModel;

public class ManufacturerStructureModelTest
{
    ManufacturerStructureModel model;

    @Before
    public void Init()
    {
        model = new ManufacturerStructureModel();
        model.Add((byte) 1,"УРАЛБРОЙЛЕР ЗАО (Ишалино)","23504297-7ee1-11e6-80d7-e4115bea65d2");
        model.Add((byte) 2,"Уральская мясная компания ООО ИНН 7438028838","a839c521-3ac5-11e6-80d2-e4115bea65d6");
    }

    @Test
    public void FindTest() throws ApplicationException
    {
        Assert.assertEquals("23504297-7ee1-11e6-80d7-e4115bea65d2", model.GetManufacturerGuid((byte)1) );
        Assert.assertEquals("Уральская мясная компания ООО ИНН 7438028838", model.GetManufacturerName((byte)2) );
    }

    @Test(expected = NullPointerException.class)
    public void OutOfRangeTest() throws ApplicationException

    {
        model.GetManufacturerGuid((byte)5);
    }

}
