package edu.oakland;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "operand1";

    // Variables to hold the operands and type of calculations
    private Double operand1 = null;
    private String pendingOperation = "=";

    private Double firstNum = null;
    private Double secondNum = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);
        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);
        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);
        Button buttonNeg = (Button) findViewById(R.id.buttonNeg);
        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        Button buttonPercent = (Button) findViewById(R.id.buttonPercent);
        //Button buttonPowerOf = (Button) findViewById(R.id.buttonPowerOf);
        //Button buttonBracket = (Button) findViewById(R.id.buttonBracket);
        ImageButton buttonBackspace = (ImageButton) findViewById(R.id.buttonBackspace);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                newNumber.append(b.getText().toString());
            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();
                try{
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                }catch (NumberFormatException e){
                    newNumber.setText("");
                }
                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };

        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
        //buttonPowerOf.setOnClickListener(opListener);
        //buttonBracket.setOnClickListener(opListener);

        buttonNeg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String value = newNumber.getText().toString();
                if(value.length() == 0)
                {
                    newNumber.setText("-");
                }else{
                    try{
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        newNumber.setText(doubleValue.toString());
                    }catch(NumberFormatException e)
                    {
                        //newNumber was "-" or ".", so clear it
                        newNumber.setText("");
                    }
                }

            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                displayOperation.setText("=");
                newNumber.setText("");
                operand1 = null;
                result.setText("");

            }
        });

        buttonPercent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                double number = Double.parseDouble(newNumber.getText().toString())/100;
                newNumber.setText(number + "");
            }
        });

        /*
        buttonPowerOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                double answer = Math.pow(firstNum, secondNum);
                answer = Double.parseDouble(newNumber.getText().toString());
                newNumber.setText(answer + "");
            }
        });
         */

        buttonBackspace.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int cursorPos = newNumber.getSelectionStart();
                int textLen = newNumber.getText().length();

                if(cursorPos != 0 && textLen != 0)
                {
                    SpannableStringBuilder selection = (SpannableStringBuilder) newNumber.getText();
                    selection.replace(cursorPos-1, cursorPos, "");
                    newNumber.setText(selection);
                    newNumber.setSelection(cursorPos-1);
                }

            }
        });

        /*
        buttonBracket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                int cursorPosition = newNumber.getSelectionStart();
                int openBracket = 0;
                int closedBracket = 0;
                int textLength = newNumber.getText().length();

                for(int i=0; i<cursorPosition; i++)
                {
                    if(newNumber.getText().toString().substring(i, i+1).equals("("))
                    {
                        openBracket += 1;
                    }
                    if(newNumber.getText().toString().substring(i, i+1).equals(")"))
                    {
                        closedBracket += 1; // (8+9)*7
                    }
                }
                if(openBracket == closedBracket || newNumber.getText().toString().substring(textLength-1, textLength).equals("("))
                {
                    updateText("(");
                }
                else if(openBracket < closedBracket && !newNumber.getText().toString().substring(textLength-1, textLength).equals("("))
                {
                    updateText(")");
                }
                newNumber.setSelection(cursorPosition + 1);
            }
        });
        */
    }

    private void updateText(String strToAdd)
    {
        String oldStr = newNumber.getText().toString();
        int cursorPos = newNumber.getSelectionStart();
        String leftStr = oldStr.substring(0, cursorPos);
        String rightStr = oldStr.substring(cursorPos);
        newNumber.setText(String.format("%s%s%s", leftStr, strToAdd, rightStr));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if(operand1 != null)
        {
            outState.putDouble(STATE_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        displayOperation.setText(pendingOperation);
    }

    private void performOperation(Double value, String operation)
    {
        if(operand1 == null)
        {
            operand1 = value;
        }else{
            if(pendingOperation.equals("="))
            {
                pendingOperation = operation;
            }
            switch (pendingOperation)
            {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if(value == 0)
                    {
                        operand1 = 0.0;
                    }else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
                case "+":
                    operand1 += value;
                    break;

            }
        }

        result.setText(operand1.toString());
        newNumber.setText("");
    }
}