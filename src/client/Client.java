package client;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.InvalidParameterException;
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
		System.out.println("Cliente ativado. Aguardando comandos...");
		try (Scanner sc = new Scanner(System.in)) {
			while (true){
				try {
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
				catch (NullPointerException e){
					if (currentRepository == null)
						System.out.println("CLIENT ERROR: É necessário se conectar a um servidor."
								+ " Usar: bind nomedoservidor.");
					else if (currentPart == null)
						System.out.println("CLIENT ERROR: É necessário selecionar uma parte."
								+ " Usar 'get-part nomedaparte'.");
					else
						System.out.println(e);
				}
				catch (NumberFormatException e){
					System.out.println(e.getMessage());
				}
				catch (ArrayIndexOutOfBoundsException e){
					System.out.println(e.getMessage());
				}
				catch (ConnectException e){
					System.out.println("SERVER ERROR: falha na conexão com o servidor.");
				}
				catch (NotBoundException e){
					System.out.println("CLIENT ERROR: não há um servidor com este nome.");
				}
				catch (RemoteException e) {
					System.out.println(e);
				}
			}
		}
	}
	//----------------------------------------------------------------------------------- bind (String[])
	public void bind(String[] args) throws RemoteException, NotBoundException {
		if (args.length < 2) throw new ArrayIndexOutOfBoundsException(
				"CLIENT ERROR: comando 'bind' requer um parâmetro. " +
				"Usar: bind nomedoservidor.");

		Registry reg = LocateRegistry.getRegistry("localhost");
		currentRepository = (IPartRepository)reg.lookup(args[1]);
		System.out.println("Conectado com sucesso.");
	}
	//----------------------------------------------------------------------------------- showRepInfo ()
	public void showRepInfo() throws RemoteException {
		System.out.println(currentRepository.getRepInfo());
	}
	//----------------------------------------------------------------------------------- showRepParts ()
	public void showRepParts() throws RemoteException {
		System.out.print(currentRepository.listRepParts());
	}
	//----------------------------------------------------------------------------------- getPart (String[])
	public void getPart(String[] args) throws RemoteException {
		if (args.length < 2) throw new ArrayIndexOutOfBoundsException(
				"CLIENT ERROR: comando 'get-part' requer um parâmetro. " +
				"Usar 'get-part código'.");
		if (!args[1].matches("^[0-9]+$")) throw new NumberFormatException(
				"CLIENT ERROR: Use um número inteiro para a quantidade.");
		
		IPart part = currentRepository.getPart(Integer.parseInt(args[1]));
		if (part == null)
			System.out.println("CLIENT ERROR: Parte não encontrada.");
		else {
			currentPart = part;
			System.out.println("Parte encontrada.");				
		}
	}
	//----------------------------------------------------------------------------------- addPart (String[])
	public void addPart(String command) throws RemoteException {
		if (!command.matches("^.* '.*' '.*'$")) throw new InvalidParameterException(
				"CLIENT ERROR: sintaxe inválida." +
				"Usar: add-part 'nome' 'descrição'.");
		
		String[] args = command.split(" ", 2);
		args = args[1].substring(1, args[1].length() - 1).split("' '", -1);
		int cod = currentRepository.addPart(args[0], args[1], currentSubpartList);
		System.out.println("Parte criada com sucesso (Código: " + cod + ").");
		currentSubpartList.clear();
	}
	//----------------------------------------------------------------------------------- showPartInfo ()
	public void showPartInfo() throws RemoteException {
		System.out.println(currentPart.getPartInfo());
	}
	//----------------------------------------------------------------------------------- showPartRep ()
	public void showPartRep() throws RemoteException {
		System.out.println(currentPart.getPartRep());
	}
	//----------------------------------------------------------------------------------- showPartType ()
	public void showPartType() throws RemoteException {
		System.out.println(currentPart.getPartType());
	}
	//----------------------------------------------------------------------------------- showPartSubpartsCount ()
	public void showPartSubpartsCount() throws RemoteException {
		System.out.println(currentPart.getSubpartsCount());
	}
	//----------------------------------------------------------------------------------- showPartSubparts ()
	public void showPartSubparts() throws RemoteException {
		System.out.print(currentPart.getSubparts());
	}
	//----------------------------------------------------------------------------------- clearList ()
	public void clearList() throws RemoteException {
		currentSubpartList.clear();
		System.out.println("Lista apagada com sucesso.");
	}
	//----------------------------------------------------------------------------------- addAsSubpart (String[])
	public void addAsSubpart(String[] args) throws RemoteException {
		if (currentPart == null) throw new NullPointerException();
		if (args.length < 2) throw new ArrayIndexOutOfBoundsException(
				"CLIENT ERROR: comando 'add-as-subpart' requer um parâmetro. " +
				"Usar 'add-as-subpart quantidade'.");
		if (!args[1].matches("^[0-9]+$")) throw new NumberFormatException(
				"CLIENT ERROR: Use um número inteiro para a quantidade.");
		
		currentSubpartList.put(currentPart, currentSubpartList.getOrDefault(currentPart, 0) + Integer.parseInt(args[1]));
		System.out.println("Parte adicionada à lista de subpartes.");
	}
	//----------------------------------------------------------------------------------- quit ()
	public void quit() {
		System.out.println("Finalizando...");
		System.exit(0);
	}
}
