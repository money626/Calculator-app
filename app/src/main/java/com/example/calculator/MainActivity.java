package com.example.calculator;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements OnClickListener
{

    TextView textView;
    private boolean calculated;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);
        findViewById(R.id.buttonDEL).setOnClickListener(this);
        findViewById(R.id.buttonDevide).setOnClickListener(this);
        findViewById(R.id.buttonMinus).setOnClickListener(this);
        findViewById(R.id.buttonMultiply).setOnClickListener(this);
        findViewById(R.id.buttonPlus).setOnClickListener(this);
        findViewById(R.id.buttonEqual).setOnClickListener(this);
        findViewById(R.id.buttonAC).setOnClickListener(this);
        findViewById(R.id.buttonPoint).setOnClickListener(this);
        textView = findViewById(R.id.textView);
        calculated = false;
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button0:
                add_num_to_textView('0');
                break;
            case R.id.button1:
                add_num_to_textView('1');
                break;
            case R.id.button2:
                add_num_to_textView('2');
                break;
            case R.id.button3:
                add_num_to_textView('3');
                break;
            case R.id.button4:
                add_num_to_textView('4');
                break;
            case R.id.button5:
                add_num_to_textView('5');
                break;
            case R.id.button6:
                add_num_to_textView('6');
                break;
            case R.id.button7:
                add_num_to_textView('7');
                break;
            case R.id.button8:
                add_num_to_textView('8');
                break;
            case R.id.button9:
                add_num_to_textView('9');
                break;
            case R.id.buttonDEL:
                delete_last();
                break;
            case R.id.buttonDevide:
                add_parameter_to_textView('/');
                break;
            case R.id.buttonMultiply:
                add_parameter_to_textView('*');
                break;
            case R.id.buttonMinus:
                add_parameter_to_textView('-');
                break;
            case R.id.buttonPlus:
                add_parameter_to_textView('+');
                break;
            case R.id.buttonPoint:
                add_parameter_to_textView('.');
                break;
            case R.id.buttonEqual:
                calculate();
                break;
            case R.id.buttonAC:
                textView.setText("");
        }
    }

    private void add_num_to_textView(char in)
    {
        String text = textView.getText().toString();
        if (calculated)
        {
            textView.setText("");
            calculated = false;
        }

        if (text.matches("(\\d+[*+/\\-])*0"))
        {
            textView.setText(text.substring(0, text.length() - 1) + in);
        }
        else
        {
            textView.append("" + in);
        }
    }

    private void add_parameter_to_textView(char in)
    {
        TextView textView = findViewById(R.id.textView);
        String text = textView.getText().toString();
        if (calculated)
        {
            textView.setText("");
            calculated = false;
        }
        if (text.length() > 0)
        {
            String last = text.substring(text.length() - 1);
            String sub = text.substring(0, text.length() - 1) + in;
            if (in == '.')
            {
                if (last.equals("."))
                {
                    return;
                }
                else
                {
                    if (text.matches("(\\d*\\.*\\d*[*+/\\-]*)+\\d*\\.\\d+"))
                    {
                        Log.e("err", text);
                        return;
                    }
                    textView.append("" + in);
                    return;
                }
            }

            if (!"+-*/".contains(last))
            {
                textView.append("" + in);
            }
            else
            {
                textView.setText(sub);
            }

        }
        else
        {
            if (in == '.')
            {
                textView.setText(".");
            }
        }


    }

    private void delete_last()
    {
        String text = textView.getText().toString();


        if (text.length() > 0)
        {
            if (text.equals("Invalid Expression"))
            {
                textView.setText("");
            }
            else
            {
                textView.setText(text.substring(0, text.length() - 1));
            }
        }
        else
        {
            textView.setText("");
        }


    }

    static int prec(char in)
    {
        switch (in)
        {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }

    private void calculate()
    {
        String text = textView.getText().toString();
        System.out.println(text);
        if (text.matches("(\\d*\\.*\\d+[+*/\\-])+"))
        {
            textView.setText("Invalid Expression");
        }
        else
        {
            Stack<String> stack = new Stack<>();
            ArrayList<String> postfix = infix2postfix(text);
            while (!postfix.isEmpty())
            {
                String next = postfix.get(0);
                postfix.remove(0);
                if (next.length() > 1)
                {
                    stack.push(next);
                }
                else
                {
                    if ("+-*/".contains(next))
                    {
                        float num, num1, num2;
                        num1 = Float.parseFloat(stack.pop());
                        num2 = Float.parseFloat(stack.pop());
                        switch (next.charAt(0))
                        {
                            case '+':
                                num = num2 + num1;
                                break;
                            case '-':
                                num = num2 - num1;
                                break;
                            case '*':
                                num = num2 * num1;
                                break;
                            case '/':
                                num = num2 / num1;
                                break;
                            default:
                                num = 0;
                        }

                        System.out.println(num);
                        stack.push(Float.toString(num));
                    }
                    else
                    {
                        stack.push(next);
                    }
                }
            }
            textView.setText(stack.pop());
        }
        calculated = true;
    }

    public ArrayList<String> infix2postfix(String infix)
    {
        String text = textView.getText().toString();
        StringBuilder SB = new StringBuilder();
        ArrayList<String> stringArray = new ArrayList<>();
        for (int i = 0; i < text.length(); i++)
        {
            if ("+-*/".contains("" + text.charAt(i)))
            {
                stringArray.add(SB.toString());
                stringArray.add("" + text.charAt(i));
                SB.delete(0, SB.length());
            }
            else
            {
                SB.append(text.charAt(i));
            }
        }
        stringArray.add(SB.toString());
        Stack<String> stack = new Stack<>();
        ArrayList<String> postfix = new ArrayList<>();
        for (String string : stringArray)
        {
            Log.e("string", string);
            if (string.length() == 1)
            {
                if ("+-*/".contains(string))
                {
                    System.out.println("Hello");
                    while (!stack.isEmpty() && prec(string.charAt(0)) <= prec(stack.peek().charAt(0)))
                    {
                        if (stack.peek().equals("("))
                        {
                            postfix.clear();
                            return postfix;
                        }
                        postfix.add(stack.pop());
                    }
                    stack.push(string);
                }
                else
                {
                    postfix.add(string);
                }
            }
            else
            {
                postfix.add(string);
            }
        }
        while (!stack.isEmpty())
        {
            if (stack.peek().equals("("))
            {
                postfix.clear();
                return postfix;
            }
            postfix.add(stack.pop());
        }
        for (String string : postfix)
        {
            System.out.println(string);
        }
        return postfix;
    }
}
