package client;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {
	
	IPartRepository currentRepository;
	IPart currentPart;
	Map<IPart, Integer> currentSubpartList = new HashMap<IPart, Integer>();
	
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
					case "SHOW-REP-PARTS":				showRepParts();				break;
					case "GET-PART":					getPart(args);				break;
					case "ADD-PART":					addPart(command);			break;
					case "SHOW-PART-INFO":				showPartInfo();				break;
					case "SHOW-PART-REP":				showPartRep();				break;
					case "SHOW-PART-TYPE":				showPartType();				break;
					case "SHOW-PART-SUBPARTS-COUNT":	showPartSubpartsCount();	break;
					case "SHOW-PART-SUBPARTS":			showPartSubparts();			break;
					case "CLEAR-LIST":					clearList();				break;
					case "ADD-AS-SUBPART":				addAsSubpart(args);			break;
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
	//----------------------------------------------------------------------------------- bind (String[])
	public void bind(String[] args) throws RemoteException {
		try {
			Registry reg = LocateRegistry.getRegistry("localhost");
			currentRepository = (IPartRepository)reg.lookup(args[1]);
			System.out.println("Conectado com sucesso.");
		}
		catch (ConnectException e){
			System.out.println("SERVER ERROR: falha na conexão com o servidor.");
		}
		catch (NotBoundException e){
			System.out.println("CLIENT ERROR: não há um servidor com este nome.");
		}
		catch (ArrayIndexOutOfBoundsException e){
			System.out.println("CLIENT ERROR: comando 'bind' requer um parâmetro."
								+ " Usar: bind nomedoservidor.");
		}
	}
	//----------------------------------------------------------------------------------- showRepInfo ()
	public void showRepInfo() throws RemoteException {
		try {
			System.out.println(currentRepository.getRepInfo());
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário se conectar a um servidor."
					 			+ " Usar: bind nomedoservidor.");
		}
	}
	//----------------------------------------------------------------------------------- showRepParts ()
	public void showRepParts() throws RemoteException {
		try {
			System.out.print(currentRepository.listRepParts());
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário se conectar a um servidor."
		 			+ " Usar: bind nomedoservidor.");
		}
	}
	//----------------------------------------------------------------------------------- getPart (String[])
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
			System.out.println("CLIENT ERROR: É necessário se conectar a um servidor."
		 			+ " Usar 'bind servername'.");
		}
		catch (NumberFormatException e){
			System.out.println("CLIENT ERROR: Use um número inteiro para o código.");
		}
		catch (ArrayIndexOutOfBoundsException e){
			System.out.println("CLIENT ERROR: comando 'get-part' requer um parâmetro."
					+ " Usar 'get-part código'.");
		}
	}
	//----------------------------------------------------------------------------------- addPart (String[])
	public void addPart(String command) throws RemoteException {
		try {
			String[] args = command.split(" ", 2);
			args = args[1].substring(1, args[1].length() - 1).split("' '");
			int cod = currentRepository.addPart(args[0], args[1], currentSubpartList);
			System.out.println("Parte criada com sucesso (Código: " + cod + ").");
			currentSubpartList.clear();
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário se conectar a um servidor."
		 			+ " Usar 'bind servername'.");
		}
		catch (ArrayIndexOutOfBoundsException e){
			System.out.println("CLIENT ERROR: comando 'add-part' requer dois parâmetros."
					+ " Usar: add-part 'nome' 'descrição'.");
		}
	}
	//----------------------------------------------------------------------------------- showPartInfo ()
	public void showPartInfo() throws RemoteException {
		try {
			System.out.println(currentPart.getPartInfo());
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário selecionar uma parte."
					+ " Usar 'get-part nomedaparte'.");
		}
	}
	//----------------------------------------------------------------------------------- showPartRep ()
	public void showPartRep() throws RemoteException {
		try {
			System.out.println(currentPart.getPartRep());
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário selecionar uma parte."
					+ " Usar 'get-part nomedaparte'.");
		}
	}
	//----------------------------------------------------------------------------------- showPartType ()
	public void showPartType() throws RemoteException {
		try {
			System.out.println(currentPart.getPartType());
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário selecionar uma parte."
					+ " Usar 'get-part nomedaparte'.");
		}
	}
	//----------------------------------------------------------------------------------- showPartSubpartsCount ()
	public void showPartSubpartsCount() throws RemoteException {
		try {
			System.out.println(currentPart.getSubpartsCount());
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário selecionar uma parte."
					+ " Usar 'get-part nomedaparte'.");
		}
	}
	//----------------------------------------------------------------------------------- showPartSubparts ()
	public void showPartSubparts() throws RemoteException {
		try {
			System.out.print(currentPart.getSubparts());
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário selecionar uma parte."
					+ " Usar 'get-part nomedaparte'.");
		}
	}
	//----------------------------------------------------------------------------------- clearList ()
	public void clearList() throws RemoteException {
		currentSubpartList.clear();
		System.out.println("Lista apagada com sucesso.");
	}
	//----------------------------------------------------------------------------------- addAsSubpart (String[])
	public void addAsSubpart(String[] args) throws RemoteException {
		try {
			if (currentPart == null)
				throw new NullPointerException();
			currentSubpartList.put(currentPart, 
					currentSubpartList.getOrDefault(currentPart, 0) + Integer.parseInt(args[1]));
			System.out.println("Parte adicionada à lista de subpartes.");
		}
		catch (NullPointerException e){
			System.out.println("CLIENT ERROR: É necessário selecionar uma parte."
					+ " Usar 'get-part nomedaparte'.");
		}
		catch (NumberFormatException e){
			System.out.println("CLIENT ERROR: Use um número inteiro para a quantidade.");
		}
		catch (ArrayIndexOutOfBoundsException e){
			System.out.println("CLIENT ERROR: comando 'add-as-subpart' requer um parâmetro."
					+ " Usar 'add-as-subpart quantidade'.");
		}
	}
	//----------------------------------------------------------------------------------- quit ()
	public void quit() {
		System.out.println("Finalizando...");
		System.exit(0);
	}
}
