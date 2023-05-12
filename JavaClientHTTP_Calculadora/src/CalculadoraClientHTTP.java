import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CalculadoraClientHTTP {

	public static void main(String[] args) {
	    double operando1 = 5.0;
	    double operando2 = 5.0;
	    
	    String operacao = "somar";
	    String resultado = calcular(operando1, operando2, operacao);
	    System.out.println("Resultado da operação " + operacao + " entre " + operando1 + " e " + operando2 + " é: " + resultado);
	    
	    operacao = "subtrair";
	    resultado = calcular(operando1, operando2, operacao);
	    System.out.println("Resultado da operação " + operacao + " entre " + operando1 + " e " + operando2 + " é: " + resultado);
	    
	    operacao = "multiplicar";
	    resultado = calcular(operando1, operando2, operacao);
	    System.out.println("Resultado da operação " + operacao + " entre " + operando1 + " e " + operando2 + " é: " + resultado);
	    
	    operacao = "dividir";
	    resultado = calcular(operando1, operando2, operacao);
	    System.out.println("Resultado da operação " + operacao + " entre " + operando1 + " e " + operando2 + " é: " + resultado);
	}

	public static String calcular(double operando1, double operando2, String operacao) {
		String resultado = "";
	    try {
	        URL url = new URL("https://double-nirvana-273602.appspot.com/?hl=pt-BR"); // Cria uma nova URL com o endereço do servidor da calculadora
	        HttpsURLConnection conexao = (HttpsURLConnection) url.openConnection(); // Abre uma conexão HTTPS com o servidor
	        conexao.setReadTimeout(10000); // Define o tempo máximo de leitura para 10 segundos
	        conexao.setConnectTimeout(15000); // Define o tempo máximo de conexão para 15 segundos
	        conexao.setRequestMethod("POST"); // Define o método de requisição como POST
	        conexao.setDoInput(true); // Habilita a leitura de dados da conexão
	        conexao.setDoOutput(true); // Habilita a escrita de dados na conexão

	        // Envio dos parâmetros
	        OutputStream saida = conexao.getOutputStream(); // Pega a saída da conexão
	        BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(saida, "UTF-8")); // Cria um escritor para a saída da conexão, utilizando codificação UTF-8

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
	                throw new IllegalArgumentException("Operação inválida: " + operacao); // Caso a operação não seja uma das opções válidas, lança uma exceção com a mensagem de erro apropriada
	        }

	        escritor.write("oper1=" + operando1 + "&oper2=" + operando2 + "&operacao=" + codigoOperacao); // Escreve os parâmetros na saída da conexão
	        escritor.flush(); // Limpa o buffer de escrita
	        escritor.close(); // Fecha o escritor
	        saida.close(); // Fecha a saída da conexão

	        int codigoResposta = conexao.getResponseCode(); // Pega o código de resposta da conexão
	        if (codigoResposta == HttpsURLConnection.HTTP_OK) { // Se a resposta for OK
	            // Recebimento do resultado
	            BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream(), "utf-8")); // Cria um leitor para a entrada da conexão, utilizando codificação UTF-8
	            StringBuilder response = new StringBuilder(); // cria uma string vazia para armazenar a resposta do servidor
	            String responseLine = null;
	            while ((responseLine = leitor.readLine()) != null) { // lê cada linha da resposta do servidor
	                response.append(responseLine.trim()); // adiciona a linha lida à string de resposta
	            }
	            resultado = response.toString(); // converte a string de resposta em uma string normal
	        }
	    } catch (IOException e) { // trata exceção de entrada/saída
	    	e.printStackTrace(); // imprime a stack trace da exceção
	    }
	    // Retorna a resposta do servidor
	    return resultado;
	}
}
