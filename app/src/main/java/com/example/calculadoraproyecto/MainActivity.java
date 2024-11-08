package com.example.calculadoraproyecto;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText pantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pantalla = findViewById(R.id.pantalla);
        pantalla.setText("0");
    }

    public void insertar(View view) {
        String valor = ((Button) view).getText().toString();
        if (pantalla.getText().toString().equals("0")) {
            pantalla.setText(valor);
        } else {
            pantalla.append(valor);
        }
    }

    public void insertarOperador(View view) {
        String operador = ((Button) view).getText().toString();
        String textoPantalla = pantalla.getText().toString();
        if (!textoPantalla.isEmpty() && !textoPantalla.endsWith("+") && !textoPantalla.endsWith("-") &&
                !textoPantalla.endsWith("*") && !textoPantalla.endsWith("/")) {
            pantalla.append(operador);
        }
    }

    public void clear(View view) {
        pantalla.setText("0");
    }

    public void resultado(View view) {
        String expr = pantalla.getText().toString();
        try {
            double result = eval(expr);
            pantalla.setText(String.valueOf(result));
        } catch (Exception e) {
            Toast.makeText(this, "Error en la expresión", Toast.LENGTH_SHORT).show();
            clear(view);
        }
    }
    private double eval(String expr) throws Exception {
        String[] tokens = expr.split("(?<=[-+*/])|(?=[-+*/])");
        double result = 0;
        double currentNumber = 0;
        String currentOperator = "+";

        for (String token : tokens) {
            token = token.trim();
            if (token.isEmpty()) continue;

            if (isNumeric(token)) {
                currentNumber = Double.parseDouble(token);
                switch (currentOperator) {
                    case "+":
                        result += currentNumber;
                        break;
                    case "-":
                        result -= currentNumber;
                        break;
                    case "*":
                        result *= currentNumber;
                        break;
                    case "/":
                        if (currentNumber == 0) {
                            throw new ArithmeticException("División por cero");
                        }
                        result /= currentNumber;
                        break;
                }
            } else {
                currentOperator = token;
            }
        }

        return result;
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}


