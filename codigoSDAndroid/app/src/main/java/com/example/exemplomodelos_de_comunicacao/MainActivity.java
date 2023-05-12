package com.example.exemplomodelos_de_comunicacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvSomaLoc,tvSubLoc, tvMultLoc, tvDivLoc,
        tvSomaHt, tvSubHt, tvMultHt, tvDivHt,
            tvSomaSoc, tvSubSoc, tvMultSoc, tvDivSoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt = findViewById(R.id.button);
        tvSomaLoc= findViewById(R.id.tvSomaLoc);
        tvSubLoc = findViewById(R.id.tvSubLoc);
        tvMultLoc = findViewById(R.id.tvMultLoc);
        tvDivLoc = findViewById(R.id.tvDivLoc);
        tvSomaHt= findViewById(R.id.tvSomaHt);
        tvSubHt = findViewById(R.id.tvSubHt);
        tvMultHt = findViewById(R.id.tvMultHt);
        tvDivHt = findViewById(R.id.tvDivHt);
        tvSomaSoc= findViewById(R.id.tvSomaSoc);
        tvSubSoc = findViewById(R.id.tvSubSoc);
        tvMultSoc = findViewById(R.id.tvMultSoc);
        tvDivSoc = findViewById(R.id.tvDivSoc);

        final TextView[] localTextViews = {tvSomaLoc, tvSubLoc, tvMultLoc, tvDivLoc};
        final TextView[] httpTextViews = {tvSomaHt, tvSubHt, tvMultHt, tvDivHt};
        final TextView[] socketTextViews = {tvSomaSoc, tvSubSoc, tvMultSoc, tvDivSoc};

        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               PrecisaCalcular shc = new PrecisaCalcular();
               shc.calculoLocal(localTextViews);
               shc.calculoRemoto(socketTextViews);
               shc.calculoRemotoHTTP(httpTextViews);
            }

        });
    }
}
