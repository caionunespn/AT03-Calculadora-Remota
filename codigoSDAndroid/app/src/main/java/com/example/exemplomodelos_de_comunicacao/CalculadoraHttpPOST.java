package com.example.exemplomodelos_de_comunicacao;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class CalculadoraHttpPOST extends AsyncTask<String[], Void, ArrayList<String>> {
    TextView[] tvs;
    double oper1,oper2;
    PrecisaCalcular pc;
    public CalculadoraHttpPOST(TextView[] tvs, PrecisaCalcular pc, double oper1, double oper2){
        this.tvs=tvs;
        this.oper1=oper1;
        this.oper2=oper2;
        this.pc=pc;
    }
    @Override
    protected ArrayList<String> doInBackground(String[]... params) {
        String soma = this.calcular(this.oper1, this.oper2, "somar");
        String sub = this.calcular(this.oper1, this.oper2, "subtrair");
        String mult = this.calcular(this.oper1, this.oper2, "multiplicar");
        String div = this.calcular(this.oper1, this.oper2, "dividir");

        ArrayList<String> resultado = new ArrayList<String>();
        resultado.add(soma);
        resultado.add(sub);
        resultado.add(mult);
        resultado.add(div);

        return resultado;
    }

    public String calcular(double operando1, double operando2, String operacao) {
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


    @Override
    protected void onPreExecute() {
        //Codigo
    }


    @Override
    protected void onPostExecute(ArrayList<String> resultado) {
        tvs[0].setText("Resultado Soma HTTP: " + resultado.get(0));
        tvs[1].setText("Resultado Subtração HTTP: " + resultado.get(1));
        tvs[2].setText("Resultado Multiplicação HTTP: " + resultado.get(2));
        tvs[3].setText("Resultado Divisão HTTP: " + resultado.get(3));
    }

}

