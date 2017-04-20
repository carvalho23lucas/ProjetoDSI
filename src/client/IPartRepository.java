package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IPartRepository extends Remote {
	public String getRepInfo() throws RemoteException;
	public String listRepParts() throws RemoteException;
	public IPart getPart(int partId) throws RemoteException;
	public String getPartSubparts(IPart part) throws RemoteException;
	public void addPart(int cod, String name, String description, List<IPart> subparts) throws RemoteException;
}