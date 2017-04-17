package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import client.IDnsServer;

public class DnsServer extends UnicastRemoteObject implements IDnsServer {

	public Map<String, String> refList = new HashMap<String, String>();
	
	@Override
	public String getReference(String repName) throws RemoteException {
		String ref = refList.get(repName);
		return ref != null ? ref : "";
	}
	
	@Override
	public synchronized boolean registerServer(String repName, String reference) throws RemoteException {
		if (refList.containsKey(repName))
			return false;
		refList.put(repName, reference);
		return true;
	}
	
	public static void main(String[] args) {
		try {
			Registry reg = LocateRegistry.createRegistry(1099);
			reg.rebind("dnsserver", new DnsServer());
			System.out.println("Servidor DNS ativado com sucesso...");
		}
		catch (ExportException e){
			System.out.println("Porta 1099 está em uso.");
			System.exit(0);
		}
		catch (RemoteException e) {
			System.out.println(e);
		}
	}
	protected DnsServer() throws RemoteException { super(); }
	private static final long serialVersionUID = 1L;
}
