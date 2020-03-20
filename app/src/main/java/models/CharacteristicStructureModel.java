package models;

import java.util.HashMap;

import businesslogic.ApplicationException;

public class CharacteristicStructureModel {
    HashMap<String,String> Characteristic ;

    public CharacteristicStructureModel()
    {
        Characteristic = new HashMap<>();
    }

    public String FindCharacteristicByGuid(String characteristicGuid) throws ApplicationException {
        if(Characteristic.containsKey(characteristicGuid)) {
            return Characteristic.get(characteristicGuid);
        }
        else
            {
                throw new ApplicationException("Характеристика продкута с GUID "+characteristicGuid+" не найдена");
            }
    }

    public void Add(String characteristicGuid, String characteristicName)
    {
        this.Characteristic.put(characteristicGuid,characteristicName);
    }
}
