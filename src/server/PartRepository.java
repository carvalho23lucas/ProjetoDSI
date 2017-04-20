package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import client.IPart;
import client.IPartRepository;

public class PartRepository extends UnicastRemoteObject implements IPartRepository {

	private String name;
	private List<Part> parts = new LinkedList<Part>();
	
	@Override
	public String getRepInfo() throws RemoteException {
		return "Nome: " + name + "\r\nPartes: " + parts.size();
	}
	@Override
	public String listRepParts() throws RemoteException {
		String result = "";
		for(Part part : this.parts){
			result +=   "Código: " + part.cod + "\r\n" +
						"Nome: " + part.name + "\r\n" + 
						"Descrição: " + part.description + "\r\n" + 
						"Sub-Partes: " + part.subparts.size() + "\r\n";
		}
		if(result == ""){
			return "Servidor sem partes";
		}
		else{
			return result;
		}
	}
	@Override
	public IPart getPart(int partCod) throws RemoteException {
		for (Part part : parts){
			if (part.cod == partCod)
				return (IPart)part;
		}
		return null;
	}
	@Override
	public String getPartSubparts(IPart part) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void addPart(int cod, String name, String description, List<IPart> subparts) throws RemoteException {
		Part part = new Part(this.name, cod, name, description, subparts);
		parts.add(part);
	}
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			Registry reg = null;
			try {
				reg = LocateRegistry.createRegistry(1099);
			}
			catch (ExportException e) { 
				reg = LocateRegistry.getRegistry();
			}
			
			System.out.println("Escolha um nome para o servidor.");
			String name;
			do {
				name = sc.nextLine();
				if (name.equals("")) continue;
				else if (name.contains(" ")){
					System.out.println("Nome não pode conter espaços.");
					name = "";
				}
				else{
					for (String str : reg.list()){
						if (str.equals(name)){
							System.out.println("Já existe um servidor com este nome.");
							name = "";
							break;
						}
					}
				}
			} while (name.equals(""));


			reg.rebind(name, new PartRepository(name));
			System.out.println("Servidor ativado com sucesso...");
		}
		catch (RemoteException e) {
			System.out.println(e);
		}
	}
	protected PartRepository(String name) throws RemoteException { 
		super();
		this.name = name;
	}
	private static final long serialVersionUID = 1L;
}