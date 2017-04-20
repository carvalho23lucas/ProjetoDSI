package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface IPartRepository extends Remote {
	public String getRepInfo() throws RemoteException;
	public String listRepParts() throws RemoteException;
	public IPart getPart(int cod) throws RemoteException;
	public int addPart(String name, String description, Map<IPart, Integer> subparts) throws RemoteException;
}