package client;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Client {
	
	IPartRepository currentRepository;
	IPart currentPart;
	List<IPart> currentSubpartList = new LinkedList<IPart>();
	
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
		try {
			System.out.println(currentRepository.listRepParts());
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário se conectar a um servidor. Usar 'bind servername'.");
		}
	}
	public void getPart(String[] args) throws RemoteException {
		try {
			currentPart = currentRepository.getPart(Integer.parseInt(args[1]));
			if(currentPart == null){
				System.out.println("Parte não encontrada.");	
			}
			else{
				System.out.println("Parte encontrada.");				
			}
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário se conectar a um servidor. Usar 'bind servername'.");
		}
		catch (NumberFormatException e){
			System.out.println("CLIENT ERROR: Use um número inteiro para o código.");
		}
		catch (ArrayIndexOutOfBoundsException e){
			System.out.println("CLIENT ERROR: comando 'get-part' requer um parâmetro. Usar 'get-part código'.");
		}
	}
	public void addPart(String[] args) throws RemoteException {
		try {
			currentRepository.addPart(Integer.parseInt(args[1]), args[2], args[3], currentSubpartList);
			System.out.println("Adicionado com sucesso.");
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário se conectar a um servidor. Usar 'bind nomedoserver'.");
		}
		catch (NumberFormatException e){
			System.out.println("CLIENT ERROR: Use um número inteiro para o código.");
		}
		catch (ArrayIndexOutOfBoundsException e){
			System.out.println("CLIENT ERROR: comando 'add-part' requer três parâmetros. Usar 'add-part código nome descrição'.");
		}
	}
	public void showPartInfo() throws RemoteException {
		try {
			System.out.println(currentPart.getPartInfo());
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário selecionar uma parte. Usar 'get-part nomedaparte'.");
		}
	}
	public void showPartRep() throws RemoteException {
		try {
			System.out.println(currentPart.getPartRep());
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário selecionar uma parte. Usar 'get-part nomedaparte'.");
		}
	}
	public void showPartType() throws RemoteException {
		try {
			System.out.println(currentPart.getPartType());
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário selecionar uma parte. Usar 'get-part nomedaparte'.");
		}
	}
	public void showPartSubpartsCount() throws RemoteException {
		try {
			System.out.println(currentPart.getSubpartsCount());
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário selecionar uma parte. Usar 'get-part nomedaparte'.");
		}
	}
	public void showPartSubparts() throws RemoteException {
		try {
			System.out.println(currentPart.getSubparts());
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário selecionar uma parte. Usar 'get-part nomedaparte'.");
		}
	}
	public void clearList() throws RemoteException {
		currentSubpartList.clear();
		System.out.println("Lista apagada com sucesso.");
	}
	public void addAsSubpart() throws RemoteException {
		currentSubpartList.add(currentPart);
		System.out.println("Parte adicionada à lista de subpartes.");
	}
	public void quit() {
		System.out.println("Finalizando...");
		System.exit(0);
	}
}
