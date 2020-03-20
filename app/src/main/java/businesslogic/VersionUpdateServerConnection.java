package businesslogic;

public class VersionUpdateServerConnection
{
    private String ServerIP;

    public VersionUpdateServerConnection(String serverIP)
    {
        this.ServerIP = serverIP;
    }

    public String GetServerAddress()
    {
        return this.ServerIP;
    }
}
