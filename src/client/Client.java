package client;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.Scanner;

public class Client {
	
	IPartRepository currentRepository;
	IPart currentPart;
	LinkedList<IPart> currentSubpartList;
	
	public static void main(String[] args) {
		new Client().startClient();
	}
	public void startClient() {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Cliente ativado. Aguardando comandos...");
			while (true){
				String command = sc.nextLine();
				if (command.equals("")) continue;
				String[] args = command.split(" "); 
				
				switch(args[0].toUpperCase()){
					case "BIND": 						bind(args); 				break;
					case "SHOW-REP-INFO":				showRepInfo();				break;
					case "LIST-REP-PARTS":				listRepParts();				break;
					case "GET-PART":					getPart(args);				break;
					case "ADD-PART":					addPart(args);				break;
					case "SHOW-PART-INFO":				showPartInfo();				break;
					case "SHOW-PART-REP":				showPartRep();				break;
					case "SHOW-PART-TYPE":				showPartType();				break;
					case "SHOW-PART-SUBPARTS-COUNT":	showPartSubpartsCount();	break;
					case "SHOW-PART-SUBPARTS":			showPartSubparts();			break;
					case "CLEAR-LIST":					clearList();				break;
					case "ADD-AS-SUBPART":				addAsSubpart();				break;
					case "QUIT":						quit();						break;
					default:
						System.out.println("Comando não reconhecido");
						break;
				}
			}
		}
		catch (RemoteException e) {
			System.out.println(e);
		}
	}
	
	public void bind(String[] args) throws RemoteException {
		try {
			Registry reg = LocateRegistry.getRegistry("localhost");
			currentRepository = (IPartRepository)reg.lookup(args[1]);
			System.out.println("OK: conectado com sucesso.");
		}
		catch (ConnectException e){
			System.out.println("SERVER ERROR: falha na conexão com o servidor.");
		}
		catch (NotBoundException e){
			System.out.println("CLIENT ERROR: não há um servidor com este nome.");
		}
		catch (ArrayIndexOutOfBoundsException e){
			System.out.println("CLIENT ERROR: comando 'bind' requer um parâmetro. Usar 'bind servername'.");
		}
	}
	public void showRepInfo() throws RemoteException {
		try {
			System.out.println(currentRepository.getRepInfo());
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário se conectar a um servidor. Usar 'bind servername'.");
		}
	}
	public void listRepParts() throws RemoteException {
		// usa currentRepository
		// print info
		// trycatch
	}
	public void getPart(String[] args) throws RemoteException {
		// usa currentRepository
		// atribui currentPart
		// trycatch
	}
	public void addPart(String[] args) throws RemoteException {
		// usa currentRepository
		// usa currentPart
		// usa currentSubpartList
		// trycatch
	}
	public void showPartInfo() throws RemoteException {
		// usa currentPart
		// print info
		// trycatch
	}
	public void showPartRep() throws RemoteException {
		// usa currentPart
		// print info
		// trycatch
	}
	public void showPartType() throws RemoteException {
		// usa currentPart
		// print info
		// trycatch
	}
	public void showPartSubpartsCount() throws RemoteException {
		// usa currentPart
		// print info
		// trycatch
	}
	public void showPartSubparts() throws RemoteException {
		// usa currentPart
		// print info
		// trycatch
	}
	public void clearList() throws RemoteException {
		// atribui currentSubpartList
		// trycatch
	}
	public void addAsSubpart() throws RemoteException {
		// usa currentPart
		// atribui currentSubpartList
		// trycatch
	}
	public void quit() {
		System.exit(0);
	}
}
