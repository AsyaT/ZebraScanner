package businesslogic;

import java.util.HashMap;

public class CharacterisiticStructureModel {
    HashMap<String,String> Characteristic = new HashMap<>();

    public String FindCharacteristicByGuid(String characteristicGuid)
    {
        return Characteristic.get(characteristicGuid);
    }

    public void Add(String characteristicGuid, String characteristicName)
    {
        this.Characteristic.put(characteristicGuid,characteristicName);
    }
}
