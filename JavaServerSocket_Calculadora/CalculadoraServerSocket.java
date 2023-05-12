import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculadoraServerSocket {

	public static void main(String[] args) {
		// Definindo variáveis para comunicação com o cliente
	    ServerSocket welcomeSocket;
	    DataOutputStream socketOutput;     	
	    DataInputStream socketInput;
	    BufferedReader socketEntrada;
	    Calculadora calc = new Calculadora();

	    try {
	        // Criando um socket do servidor para escutar as requisições dos clientes
	        welcomeSocket = new ServerSocket(9090);
	        int i=0; //número de clientes conectados
		  
	        System.out.println ("Servidor no ar");

	        // Loop infinito para escutar as requisições dos clientes
	        while(true) { 
		  
	            // Aceitando novas conexões dos clientes
	            Socket connectionSocket = welcomeSocket.accept(); 
	            i++;
	            System.out.println ("Nova conexão");

	            // Recebendo as informações enviadas pelo cliente
	            socketEntrada = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
	            String operacao= socketEntrada.readLine(); // Recebe o tipo de operação a ser realizada
	            String oper1=socketEntrada.readLine(); // Recebe o primeiro operando
	            String oper2=socketEntrada.readLine(); // Recebe o segundo operando
	            String resultado = ""; // Variável para armazenar o resultado da operação

	            // Realizando a operação solicitada
	            switch(Integer.parseInt(operacao)) {
	                case 1:
	                    resultado = ""+calc.soma(Double.parseDouble(oper1),Double.parseDouble(oper2));
	                    break;
	                case 2:
	                    resultado = ""+calc.subtracao(Double.parseDouble(oper1),Double.parseDouble(oper2));
	                    break;
	                case 3:
	                    resultado = ""+calc.multiplicacao(Double.parseDouble(oper1),Double.parseDouble(oper2));
	                    break;
	                case 4:
	                    resultado = ""+calc.divisao(Double.parseDouble(oper1),Double.parseDouble(oper2));
	                    break;
	                default:
	                    throw new IllegalArgumentException("Operação inválida: " + operacao); // Caso a operação não seja uma das opções válidas, lança uma exceção com a mensagem de erro apropriada
	            }

	            // Enviando a resposta de volta para o cliente
	            socketOutput= new DataOutputStream(connectionSocket.getOutputStream());     	
	            socketOutput.writeBytes(resultado+ '\n'); // Escreve o resultado no socket de saída
	            System.out.println (resultado); // Exibe o resultado no console do servidor
	            socketOutput.flush(); // Limpa o buffer do socket
	            socketOutput.close(); // Fecha o socket de saída
	        }
	    } catch (IOException e) {
	        // Caso ocorra um erro de I/O, exibe o stack trace da exceção
	        e.printStackTrace();
	    } 
	}

}
