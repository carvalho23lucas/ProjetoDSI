package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

import client.IPart;

public class Part extends UnicastRemoteObject implements IPart {
	String repository;
	int cod;
	String name;
	String description;
	List<Part> subparts = new LinkedList<Part>();
	
	@Override
	public String getPartInfo() throws RemoteException {
		return 	"Código: " + cod + "\r\n" +
				"Nome: " + name + "\r\n" +
				"Descrição: " + description;
	}
	@Override
	public String getPartType() throws RemoteException {
		return subparts.size() == 0 ? "Primitiva" : "Agregada";
	}
	@Override
	public String getPartRep() throws RemoteException {
		return repository;
	}
	@Override
	public int getSubpartsCount() throws RemoteException {
		return subparts.size();
	}
	@Override
	public String getSubparts() throws RemoteException {
		String result = "";
		for(Part part : this.subparts){
			result +=   "Código: " + part.cod + "\r\n" +
						"Nome: " + part.name + "\r\n" + 
						"Descrição: " + part.description + "\r\n" + 
						"Sub-Partes: " + part.subparts.size() + "\r\n";
		}
		if(result == ""){
			return "Parte sem sub-partes";
		}
		else{
			return result;
		}
	}
	
	@SuppressWarnings("unchecked")
	protected Part(String repository, int cod, String name, String description, List<IPart> subparts) throws RemoteException {
		super();
		this.repository = repository;
		this.cod = cod;
		this.name = name;
		this.description = description;
		this.subparts = (List<Part>)(List<?>)subparts;
	}
	private static final long serialVersionUID = 1L;
}
