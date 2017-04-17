package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IPartRepository extends Remote {
	public String getRepInfo() throws RemoteException;
	public List<IPart> listRepParts() throws RemoteException;
	public IPart getPart(int partId) throws RemoteException;
	public List<IPart> getPartSubparts(IPart part) throws RemoteException;
	public void addPart(IPart part, List<IPart> subparts) throws RemoteException;
}