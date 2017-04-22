package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import client.IPart;

public class Part extends UnicastRemoteObject implements IPart {
	String repository;
	int cod;
	String name;
	String description;
	Map<IPart, Integer> subparts = new HashMap<IPart, Integer>();

	protected Part(String repository, int cod, String name, String description, Map<IPart, Integer> subparts) throws RemoteException {
		super();
		this.repository = repository;
		this.cod = cod;
		this.name = name;
		this.description = description;
		this.subparts = subparts;
	}
	
	@Override
	public String getPartInfo() throws RemoteException {
		return this.toMyString();
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
		StringBuilder result = new StringBuilder();
		subparts.forEach((x,y) -> 
			{
				try {
					result.append("("+ y + "un) [" + x.toMyString() + "]\r\n");
				} catch (RemoteException e) {}
			}
		);
		return result.toString().equals("") ? "Parte sem sub-partes" : result.toString();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Part)
			return ((Part)obj).cod == this.cod;
		return false;
	}
	@Override
	public String toMyString() throws RemoteException {
		return  "Server: " + this.repository + "; " +
				"Código: " + this.cod + "; " +
				"Nome: " + this.name + "; " + 
				"Descrição: " + this.description + "; " + 
				"Sub-Partes: " + this.subparts.size();
	}
	public int hashCode() {
		return cod;
	}
	private static final long serialVersionUID = 1L;
}
