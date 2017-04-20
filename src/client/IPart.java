package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPart extends Remote {
	public String getPartInfo() throws RemoteException;
	public String getPartType() throws RemoteException;
	public String getPartRep() throws RemoteException;
	public int getSubpartsCount() throws RemoteException;
	public String getSubparts() throws RemoteException;
}
