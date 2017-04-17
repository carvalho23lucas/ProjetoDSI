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
		Client c = new Client();
		c.startClient();
	}
	public void startClient() {
		try (Scanner sc = new Scanner(System.in)) {
			while (true){
				String command = sc.nextLine();
				if (command.equals("")) continue;
				String[] args = command.split(" "); 
				
				switch(args[0].toUpperCase()){
				
//					• interaja com o repositório implementado pelo servidor:					-
//					– examinando o nome do repositório e o numero de peças nele contidas,		void SHOW-REP-INFO () 
//					– listando as peças no repositório,											void LIST-REP-PARTS ()
//					– buscando uma peça (por código de peça) no repositório,					IPart GET-PART (int)
//					– adicionando ao repositório novas peças (primitivas ou agregadas);			void ADD-PART ()
//					• tendo uma referência a uma peça, referência essa previamente obtida		-
//					– examinando o nome e a descrição da peça,									void SHOW-PART-INFO ()
//					– obtendo o (nome do) repositório que a contém,								void SHOW-PART-REP ()
//					– verificando se a peça é primitiva ou agregada,							void SHOW-PART-TYPE ()
//					– obtendo o número de subcomponentes diretos e primitivos da peça,			void SHOW-PART-SUBPARTS-COUNT ()
//					– listando suas subpeças.													void SHOW-PART-SUBPARTS ()
//																						OUTROS:
//																								void CLEAR-LIST ()
//																								void ADD-AS-SUBPART ()
//																								void QUIT ()
				
				
					case "BIND":
						bind(args);
						break;
					case "SHOW-REP-INFO":
						//showRepInfo();
						break;
						
					case "asds":
						break;
						
					case "asdd":
						break;
						
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
			if (args.length == 1){
				System.out.println("400 Bad Request: comando 'bind' requer um parâmetro. Usar 'bind servername'.");
				return;
			}
			
			Registry reg = LocateRegistry.getRegistry("localhost");
			String reference = ((IDnsServer)reg.lookup("dnsserver")).getReference(args[1]);
			
			if (reference.equals("")){
				System.out.println("404 Not Found: servidor '" + args[1] + "' não encontrado.");
				return;
			}

			try {
				reg = LocateRegistry.getRegistry("localhost");
				currentRepository = (IPartRepository)reg.lookup(reference);
			}
			catch (ConnectException | NotBoundException e){
				System.out.println("500 Internal Server Error: falha na conexão com o servidor.");
			}
		}
		catch (ConnectException | NotBoundException e){
			System.out.println("500 Internal Server Error: servidor DNS não encontrado.");
		}
	}
	
	/*public void showRepInfo() throws RemoteException {
		try {
			System.out.println(currentRepository.);
		}
		catch (NotBoundException e){
			System.out.println("500 Internal Server Error: falha na conexão com o servidor.");
		}
	}
	
	public void method() throws RemoteException {
		try {
			if (1 > 2){
				System.out.println("400 Bad Request: comando 'bind' requer um parâmetro. Usar 'bind servername'.");
				return;
			}
			
			
		}
		catch (NotBoundException e){
			System.out.println("500 Internal Server Error: falha na conexão com o servidor.");
		}
	}
	
	public void method() throws RemoteException {
		try {
			if (1 > 2){
				System.out.println("400 Bad Request: comando 'bind' requer um parâmetro. Usar 'bind servername'.");
				return;
			}
			
			
		}
		catch (NotBoundException e){
			System.out.println("500 Internal Server Error: falha na conexão com o servidor.");
		}
	}
	
	public void method() throws RemoteException {
		try {
			if (1 > 2){
				System.out.println("400 Bad Request: comando 'bind' requer um parâmetro. Usar 'bind servername'.");
				return;
			}
			
			
		}
		catch (NotBoundException e){
			System.out.println("500 Internal Server Error: falha na conexão com o servidor.");
		}
	}
	
	public void method() throws RemoteException {
		try {
			if (1 > 2){
				System.out.println("400 Bad Request: comando 'bind' requer um parâmetro. Usar 'bind servername'.");
				return;
			}
			
			
		}
		catch (NotBoundException e){
			System.out.println("500 Internal Server Error: falha na conexão com o servidor.");
		}
	}
	
	public void method() throws RemoteException {
		try {
			if (1 > 2){
				System.out.println("400 Bad Request: comando 'bind' requer um parâmetro. Usar 'bind servername'.");
				return;
			}
			
			
		}
		catch (NotBoundException e){
			System.out.println("500 Internal Server Error: falha na conexão com o servidor.");
		}
	}*/
}
