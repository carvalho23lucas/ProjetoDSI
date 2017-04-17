package server;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import client.IDnsServer;
import client.IPart;
import client.IPartRepository;

public class PartRepository extends UnicastRemoteObject implements IPartRepository {

	private String name;
	private List<IPart> parts = new LinkedList<IPart>();
	
	@Override
	public String getRepInfo() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
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
			String reference = UUID.randomUUID().toString();

			System.out.println("Escolha um nome para o servidor.");
			boolean error;
			do {
				error = false;
				String name = sc.nextLine();
				
				if (name.equals("") || name.contains(" ")){
					System.out.println("Nome não pode conter espaços ou ser vazio.");
					error = true;
				}
				else {
					try {
						Registry reg = LocateRegistry.getRegistry("localhost");
						IDnsServer dns = (IDnsServer)reg.lookup("dnsserver");
						
						if (!dns.registerServer(name, reference)){
							System.out.println("Já existe um servidor com este nome.");
							error = true;
						}
					} catch (ConnectException | NotBoundException e) {
						System.out.println(e);
					}
				}
			} while (error);
			
			Registry reg = LocateRegistry.getRegistry();
			reg.rebind(reference, new PartRepository());
			System.out.println("Servidor ativado com sucesso...");
		}
		catch (RemoteException e) {
			System.out.println(e);
		}
	}
	protected PartRepository() throws RemoteException { super(); }
	private static final long serialVersionUID = 1L;
}
