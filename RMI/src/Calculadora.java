import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Calculadora  implements ICalculadora { //Implementa a interface remota de um objeto em RMI

	private static final long serialVersionUID = 1L;
	
	private static int chamadas = 0;

	public int soma(int a, int b) throws RemoteException { //Implementação do método soma
		System.out.println("Método soma chamado " + chamadas++);
		return a + b; 
	}

	public int subtracao(int a, int b) throws RemoteException {
		System.out.println("Método subtracao chamado " + chamadas++);
		return a - b;
	}

	public int multiplicacao(int a, int b) throws RemoteException {
		System.out.println("Método multiplicacao chamado " + chamadas++);
		return a * b;
	}

	public int divisao(int a, int b) throws RemoteException {
		System.out.println("Método divisao chamado " + chamadas++);
		return a / b;
	}

	public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException  {
		Calculadora calculadora = new Calculadora(); //Cria um objeto calculadora
		Registry reg = null; 
		ICalculadora stub = (ICalculadora) UnicastRemoteObject.
				exportObject(calculadora, 1100); //Cria o stub, exportando o servidor calculadora na porta 1100
		try {
			System.out.println("Creating registry...");
			reg = LocateRegistry.createRegistry(1099); //Se não existir um registry na porta 1099, será criado
			System.out.println("Registry is ready!");
		} catch (Exception e) {
			try {
				reg = LocateRegistry.getRegistry(1099); //Se já tiver um registry criado, ele apenas vai salvar a referência do registry
			} catch (Exception e1) {
				System.exit(0);
			}
		}
		reg.rebind("calculadora", stub); //Associar o identificador "calculadora", usando o stub que foi criado anteriormente
	}
}
