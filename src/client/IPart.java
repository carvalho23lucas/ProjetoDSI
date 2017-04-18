package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IPart extends Remote {
	public String getPartInfo() throws RemoteException;
	public String getPartType() throws RemoteException;
	public String getPartRep() throws RemoteException;
	public int getSubpartsCount() throws RemoteException;
	public List<IPart> getSubparts() throws RemoteException;
}
