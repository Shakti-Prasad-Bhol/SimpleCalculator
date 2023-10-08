package com.example.simplecalc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;
    ImageView AC, backspace, percentage, division, multiplication, subtraction, addition, equal;
    ImageView one, two, three, four, five, six, seven, eight, nine, zero, point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        AC = findViewById(R.id.AC);
        backspace = findViewById(R.id.backspace);
        percentage = findViewById(R.id.percentage);
        division = findViewById(R.id.division);
        multiplication = findViewById(R.id.multiplication);
        subtraction = findViewById(R.id.subtraction);
        addition = findViewById(R.id.addition);
        equal = findViewById(R.id.equal);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);
        point = findViewById(R.id.point);

        setClickListeners();
    }

    private void setClickListeners() {
        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = ((ImageView) v).getContentDescription().toString();
                editText.append(number);
            }
        };

        one.setOnClickListener(numberClickListener);
        two.setOnClickListener(numberClickListener);
        three.setOnClickListener(numberClickListener);
        four.setOnClickListener(numberClickListener);
        five.setOnClickListener(numberClickListener);
        six.setOnClickListener(numberClickListener);
        seven.setOnClickListener(numberClickListener);
        eight.setOnClickListener(numberClickListener);
        nine.setOnClickListener(numberClickListener);
        zero.setOnClickListener(numberClickListener);

        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append(".");
            }
        });

        AC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                textView.setText("");
            }
        });

        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = editText.getText().toString();
                if (!currentText.isEmpty()) {
                    editText.setText(currentText.substring(0, currentText.length() - 1));
                }
            }
        });

        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append("+");
            }
        });

        subtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append("-");
            }
        });

        multiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append("*");
            }
        });

        division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append("/");
            }
        });

        percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append("%");
            }
        });

        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void calculate() {
        try {
            String expression = editText.getText().toString();
            String result = evaluateExpression(expression);
            textView.setText(result);
        } catch (Exception e) {
            textView.setText("Error");
        }
    }

    private String evaluateExpression(String expression) {
        try {
            // Replace special characters to make the expression compatible with Java's evaluation
            expression = expression.replace("÷", "/").replace("×", "*").replace("%", "/100");

            // Use the built-in JavaScript evaluation
            double result = evalExpression(expression);

            // Check if the result has no fractional part
            if (result == (int) result) {
                // If the result is an integer, convert it to int and format as a string
                return String.valueOf((int) result);
            } else {
                // If the result has a fractional part, format as a string
                return String.valueOf(result);
            }
        } catch (Exception e) {
            // Handle any exceptions during evaluation
            return "Error";
        }
    }


    private double evalExpression(String expression) {
        StringBuilder number = new StringBuilder();
        char lastOperator = '+';
        double result = 0;

        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                // If the character is a digit or a dot, append it to the current number
                number.append(c);
            } else {
                // If the character is an operator, evaluate the current number with the last operator
                double operand = Double.parseDouble(number.toString());

                switch (lastOperator) {
                    case '+':
                        result += operand;
                        break;
                    case '-':
                        result -= operand;
                        break;
                    case '*':
                        result *= operand;
                        break;
                    case '/':
                        result /= operand;
                        break;
                }

                // Reset the number for the next iteration
                number = new StringBuilder();
                lastOperator = c;
            }
        }

        // Evaluate the last number with the last operator
        double lastOperand = Double.parseDouble(number.toString());

        switch (lastOperator) {
            case '+':
                result += lastOperand;
                break;
            case '-':
                result -= lastOperand;
                break;
            case '*':
                result *= lastOperand;
                break;
            case '/':
                result /= lastOperand;
                break;
        }

        return result;
    }

}
