package models;

import java.util.HashMap;
import java.util.Map;

import businesslogic.ApplicationException;

public class ManufacturerStructureModel
{
    public ManufacturerStructureModel()
    {
        Manufacturers = new HashMap<>();
    }

    public String GetManufacturerGuidById(Byte id) throws ApplicationException
    {
        return GetManufacturer(id).Guid;
    }
    public String GetManufacturerNameById(Byte id) throws ApplicationException
    {
        return GetManufacturer(id).Name;
    }

    public String GetManufacturerNameByGuid(String guid) throws ApplicationException
    {
        for(Map.Entry<Byte, ManufacturerDetails> details : this.Manufacturers.entrySet())
        {
            if(details.getValue().Guid.equalsIgnoreCase(guid))
            {
                return details.getValue().Name;
            }
        }
        throw new ApplicationException("Производитель с таким GUID не найден");
    }

    protected ManufacturerStructureModel.ManufacturerDetails GetManufacturer(Byte id) throws ApplicationException
    {
        if(this.Manufacturers.containsKey(id))
        {
            return this.Manufacturers.get(id);
        }
        else
        {
            throw new ApplicationException("Производитель с номером " + id + " не найден");
        }

    }

    public void Add(Byte id, String name, String guid)
    {
        ManufacturerDetails details = new ManufacturerDetails();
        details.Name = name;
        details.Guid = guid;
        this.Manufacturers.put(id, details);
    }

    HashMap<Byte,ManufacturerDetails> Manufacturers;

    protected class ManufacturerDetails
    {
        String Name;
        String Guid;
    }
}
