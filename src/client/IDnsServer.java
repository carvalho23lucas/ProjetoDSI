package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDnsServer extends Remote {
	public String getReference(String repName) throws RemoteException;
	public boolean registerServer(String repName, String reference) throws RemoteException;
}