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
				
//					� interaja com o reposit�rio implementado pelo servidor:					-
//					� examinando o nome do reposit�rio e o numero de pe�as nele contidas,		void SHOW-REP-INFO () 
//					� listando as pe�as no reposit�rio,											void LIST-REP-PARTS ()
//					� buscando uma pe�a (por c�digo de pe�a) no reposit�rio,					IPart GET-PART (int)
//					� adicionando ao reposit�rio novas pe�as (primitivas ou agregadas);			void ADD-PART ()
//					� tendo uma refer�ncia a uma pe�a, refer�ncia essa previamente obtida		-
//					� examinando o nome e a descri��o da pe�a,									void SHOW-PART-INFO ()
//					� obtendo o (nome do) reposit�rio que a cont�m,								void SHOW-PART-REP ()
//					� verificando se a pe�a � primitiva ou agregada,							void SHOW-PART-TYPE ()
//					� obtendo o n�mero de subcomponentes diretos e primitivos da pe�a,			void SHOW-PART-SUBPARTS-COUNT ()
//					� listando suas subpe�as.													void SHOW-PART-SUBPARTS ()
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
						System.out.println("Comando n�o reconhecido");
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
				System.out.println("400 Bad Request: comando 'bind' requer um par�metro. Usar 'bind servername'.");
				return;
			}
			
			Registry reg = LocateRegistry.getRegistry("localhost");
			String reference = ((IDnsServer)reg.lookup("dnsserver")).getReference(args[1]);
			
			if (reference.equals("")){
				System.out.println("404 Not Found: servidor '" + args[1] + "' n�o encontrado.");
				return;
			}

			try {
				reg = LocateRegistry.getRegistry("localhost");
				currentRepository = (IPartRepository)reg.lookup(reference);
			}
			catch (ConnectException | NotBoundException e){
				System.out.println("500 Internal Server Error: falha na conex�o com o servidor.");
			}
		}
		catch (ConnectException | NotBoundException e){
			System.out.println("500 Internal Server Error: servidor DNS n�o encontrado.");
		}
	}
	
	/*public void showRepInfo() throws RemoteException {
		try {
			System.out.println(currentRepository.);
		}
		catch (NotBoundException e){
			System.out.println("500 Internal Server Error: falha na conex�o com o servidor.");
		}
	}
	
	public void method() throws RemoteException {
		try {
			if (1 > 2){
				System.out.println("400 Bad Request: comando 'bind' requer um par�metro. Usar 'bind servername'.");
				return;
			}
			
			
		}
		catch (NotBoundException e){
			System.out.println("500 Internal Server Error: falha na conex�o com o servidor.");
		}
	}
	
	public void method() throws RemoteException {
		try {
			if (1 > 2){
				System.out.println("400 Bad Request: comando 'bind' requer um par�metro. Usar 'bind servername'.");
				return;
			}
			
			
		}
		catch (NotBoundException e){
			System.out.println("500 Internal Server Error: falha na conex�o com o servidor.");
		}
	}
	
	public void method() throws RemoteException {
		try {
			if (1 > 2){
				System.out.println("400 Bad Request: comando 'bind' requer um par�metro. Usar 'bind servername'.");
				return;
			}
			
			
		}
		catch (NotBoundException e){
			System.out.println("500 Internal Server Error: falha na conex�o com o servidor.");
		}
	}
	
	public void method() throws RemoteException {
		try {
			if (1 > 2){
				System.out.println("400 Bad Request: comando 'bind' requer um par�metro. Usar 'bind servername'.");
				return;
			}
			
			
		}
		catch (NotBoundException e){
			System.out.println("500 Internal Server Error: falha na conex�o com o servidor.");
		}
	}
	
	public void method() throws RemoteException {
		try {
			if (1 > 2){
				System.out.println("400 Bad Request: comando 'bind' requer um par�metro. Usar 'bind servername'.");
				return;
			}
			
			
		}
		catch (NotBoundException e){
			System.out.println("500 Internal Server Error: falha na conex�o com o servidor.");
		}
	}*/
}
