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
	@SuppressWarnings("unchecked")
	@Override
	public List<IPart> getSubparts() throws RemoteException {
		return (List<IPart>)(List<?>)subparts;
	}
	
	protected Part(String repository, int cod, String name, String description, List<Part> subparts) throws RemoteException {
		super();
		this.repository = repository;
		this.cod = cod;
		this.name = name;
		this.description = description;
		this.subparts = subparts;
	}
	private static final long serialVersionUID = 1L;
}
