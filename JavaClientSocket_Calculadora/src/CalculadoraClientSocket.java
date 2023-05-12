import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class CalculadoraClientSocket {
	public static void main(String[] args) {
        double operando1 = 10;
        double operando2 = 20;

        String operacao = "somar";
        String resultado = calcular(operando1, operando2, operacao, conectarSocket());
        System.out.println("Resultado da operação " + operacao + " entre " + operando1 + " e " + operando2 + " é: " + resultado);

        operacao = "subtrair";
        resultado = calcular(operando1, operando2, operacao, conectarSocket());
        System.out.println("Resultado da operação " + operacao + " entre " + operando1 + " e " + operando2 + " é: " + resultado);

        operacao = "multiplicar";
        resultado = calcular(operando1, operando2, operacao, conectarSocket());
        System.out.println("Resultado da operação " + operacao + " entre " + operando1 + " e " + operando2 + " é: " + resultado);

        operacao = "dividir";
        resultado = calcular(operando1, operando2, operacao, conectarSocket());
        System.out.println("Resultado da operação " + operacao + " entre " + operando1 + " e " + operando2 + " é: " + resultado);

    }

    public static Socket conectarSocket() {
        try {
        	// Criando um objeto de socket e estabelecendo a conexão com o servidor, passando o endereço IP do servidor e a porta que ele está escutando.
            Socket clientSocket = new Socket("192.168.0.109", 9090);
            // Retornando o socket conectado com o servidor
            return clientSocket;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    

    public static String calcular(double oper1, double oper2, String operacao, Socket clientSocket) {
        String resultado = "";

        int codigoOperacao = 0;
        switch (operacao.toLowerCase()) {
            case "somar":
                codigoOperacao = 1;
                break;
            case "subtrair":
                codigoOperacao = 2;
                break;
            case "multiplicar":
                codigoOperacao = 3;
                break;
            case "dividir":
                codigoOperacao = 4;
                break;
            default:
                throw new IllegalArgumentException("Operação inválida: " + operacao);
        }

        try {
        	// Cria um objeto do tipo DataOutputStream para enviar dados para o servidor
            DataOutputStream socketSaidaServer = new DataOutputStream(clientSocket.getOutputStream());

            // Envia os dados da operação e dos operandos para o servidor. O servidor le a informação quebrando por linha então ele está sempre esperando 3 linhas para realizar uma operação.
            socketSaidaServer.writeBytes(codigoOperacao + "\n");
            socketSaidaServer.writeBytes(oper1 + "\n");
            socketSaidaServer.writeBytes(oper2 + "\n");
            socketSaidaServer.flush();

            // Cria um objeto do tipo BufferedReader para receber a resposta do servidor
            BufferedReader messageFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // Lê a resposta do servidor e armazena na variável resultado
            resultado = messageFromServer.readLine();
            // Fecha a conexão com o servidor
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultado;
    }
}
