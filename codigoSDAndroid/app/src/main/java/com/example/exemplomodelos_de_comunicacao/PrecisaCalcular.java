package com.example.exemplomodelos_de_comunicacao;

import android.widget.TextView;

public class PrecisaCalcular {
    public void calculoLocal(TextView[] tvs){
        Calculadora calc = new Calculadora();

        String soma = "Resultado Soma Local: " + calc.soma(10.0,20.0);
        this.result_calculo(tvs[0], soma);

        String sub = "Resultado Subtração Local: " + calc.subtracao(10.0,20.0);
        this.result_calculo(tvs[1], sub);

        String mult = "Resultado Multiplicação Local: " + calc.multiplicacao(10.0,20.0);
        this.result_calculo(tvs[2], mult);

        String div = "Resultado Divisão Local: " + calc.divisao(10.0,20.0);
        this.result_calculo(tvs[3], div);
    }

    public void calculoRemoto(TextView[] tvs){
        CalculadoraSocket shs = new CalculadoraSocket(tvs, this, 10, 20);
        shs.execute();
    }
    public void calculoRemotoHTTP(TextView[] tvs){
        CalculadoraHttpPOST shs = new CalculadoraHttpPOST(tvs, this, 10, 20);
        shs.execute();
    }
    public void result_calculo(TextView tv, String result){
        tv.setText(result);
    }

}
