package com.example.exemplomodelos_de_comunicacao;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class CalculadoraSocket extends AsyncTask<String[], Void, ArrayList<String>> {

    TextView[] tvs;
    double oper1,oper2;
    PrecisaCalcular pc;
    public CalculadoraSocket(TextView[] tvs, PrecisaCalcular pc, double oper1, double oper2){
        this.tvs=tvs;
        this.oper1=oper1;
        this.oper2=oper2;
        this.pc=pc;
    }

    @Override
    protected ArrayList<String> doInBackground(String[]... params) {
        String soma = this.calcular(this.oper1, this.oper2, "somar", this.conectarSocket());
        String sub = this.calcular(this.oper1, this.oper2, "subtrair", this.conectarSocket());
        String mult = this.calcular(this.oper1, this.oper2, "multiplicar", this.conectarSocket());
        String div = this.calcular(this.oper1, this.oper2, "dividir", this.conectarSocket());

        ArrayList<String> resultado = new ArrayList<String>();
        resultado.add(soma);
        resultado.add(sub);
        resultado.add(mult);
        resultado.add(div);

        return resultado;
    }

    public Socket conectarSocket() {
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


    public String calcular(double oper1, double oper2, String operacao, Socket clientSocket) {
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


    @Override
    protected void onPreExecute() {
        //Codigo
    }


    @Override
    protected void onPostExecute(ArrayList<String> resultado) {
        tvs[0].setText("Resultado Soma Socket: " + resultado.get(0));
        tvs[1].setText("Resultado Subtração Socket: " + resultado.get(1));
        tvs[2].setText("Resultado Multiplicação Socket: " + resultado.get(2));
        tvs[3].setText("Resultado Divisão Socket: " + resultado.get(3));
    }

}
