package server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import client.IPart;
import client.IPartRepository;

public class PartRepository extends UnicastRemoteObject implements IPartRepository {
	private String name;
	private List<Part> parts = new LinkedList<Part>();
	
	protected PartRepository(String name) throws RemoteException { 
		super();
		this.name = name;
	}

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			Runtime.getRuntime().exec("rmiregistry");
			Registry reg = LocateRegistry.getRegistry();
			
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
		catch (IOException e) {
			System.out.println(e);
		}
	}

	@Override
	public String getRepInfo() throws RemoteException {
		return "Nome: " + name + "\r\nPartes: " + parts.size();
	}
	@Override
	public String listRepParts() throws RemoteException {
		String result = "";
		for(Part part : this.parts){
			result += part.toMyString() + "\r\n";
		}
		return result == "" ? "Servidor sem partes\r\n" : result;
	}
	@Override
	public IPart getPart(int cod) throws RemoteException {
		for (Part part : parts){
			if (part.cod == cod)
				return (IPart)part;
		}
		return null;
	}
	@Override
	public int addPart(String name, String description, Map<IPart, Integer> subparts) throws RemoteException {
		int cod = parts.size() == 0 ? 1 : parts.get(parts.size() - 1).cod + 1;
		parts.add(new Part(this.name, cod, name, description, subparts));
		return cod;
	}
	
	private static final long serialVersionUID = 1L;
}