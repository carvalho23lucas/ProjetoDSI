package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import client.IPart;
import client.IPartRepository;

public class PartRepository extends UnicastRemoteObject implements IPartRepository {

	private String name;
	private List<IPart> parts = new LinkedList<IPart>();
	
	@Override
	public String getRepInfo() throws RemoteException {
		return "Name: " + name + "\r\nParts:" + parts.size();
	}
	@Override
	public List<IPart> listRepParts() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IPart getPart(int partId) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<IPart> getPartSubparts(IPart part) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void addPart(IPart part, List<IPart> subparts) throws RemoteException {
		//TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Escolha um nome para o servidor.");
			String name;
			do {
				name = sc.nextLine();
				
				if (name.equals(""))
					System.out.println("Nome não pode ser vazio.");
				else if (name.contains(" "))
					System.out.println("Nome não pode conter espaços.");
			} while (name.equals("") || name.contains(" "));
			
			Registry reg = LocateRegistry.getRegistry();
			reg.rebind(name, new PartRepository());
			System.out.println("Servidor ativado com sucesso...");
		}
		catch (RemoteException e) {
			System.out.println(e);
		}
	}
	protected PartRepository() throws RemoteException { super(); }
	private static final long serialVersionUID = 1L;
}
