import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class CalculadoraCliente {
	
	public static void main(String[] args) {
		Registry reg = null;
		ICalculadora calc;		
		try {
			reg = LocateRegistry.getRegistry(1099); //Obter o registry que está na porta 1099
			calc = (ICalculadora) reg.lookup("calculadora"); //Obter a referência para o objeto "calculadora"

			System.out.println(calc.soma(10,2)); //Chamada do método soma
			System.out.println(calc.subtracao(10,2)); //Chamada do método subtração
			System.out.println(calc.multiplicacao(10,2)); ///Chamada do método multiplicação
			System.out.println(calc.divisao(10,2)); //Chamada do método divisão

		} catch (RemoteException | NotBoundException e) {
				System.out.println(e);
				System.exit(0);
		}
	}		

}
