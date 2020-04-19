package com.example.calculator;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements OnClickListener, OnLongClickListener
{

    TextView textView;
    private boolean calculated;
    private boolean point_added;
    private boolean negative_set;

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
        findViewById(R.id.buttonDEL).setOnLongClickListener(this);
        findViewById(R.id.buttonDevide).setOnClickListener(this);
        findViewById(R.id.buttonMinus).setOnClickListener(this);
        findViewById(R.id.buttonMultiply).setOnClickListener(this);
        findViewById(R.id.buttonPlus).setOnClickListener(this);
        findViewById(R.id.buttonEqual).setOnClickListener(this);
        findViewById(R.id.buttonAC).setOnClickListener(this);
        findViewById(R.id.buttonPoint).setOnClickListener(this);
        findViewById(R.id.buttonLeftBracket).setOnClickListener(this);
        findViewById(R.id.buttonRightBracket).setOnClickListener(this);
        textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        calculated = false;
        point_added = false;
        negative_set = false;
    }

    public boolean onLongClick(View v)
    {
        if (v.getId() == R.id.buttonDEL)
        {
            textView.setText("");
            calculated = false;
            point_added = false;
            negative_set = false;
            return true;
        }
        return false;
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
            case R.id.buttonLeftBracket:
                add_parameter_to_textView('(');
                break;
            case R.id.buttonRightBracket:
                add_parameter_to_textView(')');
                break;
            case R.id.buttonEqual:
                calculate();
                break;
            case R.id.buttonAC:
                textView.setText("");
                calculated = false;
                point_added = false;
                negative_set = false;
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
        negative_set = false;
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
                if (!point_added)
                {
                    textView.append("" + in);
                    point_added = true;
                }
            }
            else if (in == '-')
            {
                if (!"-".contains(last))
                {
                    textView.append("" + in);
                    if ("+*/".contains(last))
                    {
                        negative_set = true;
                    }
                }
                else
                {
                    textView.setText(sub);
                }
                point_added = false;
            }
            else
            {
                if (last.equals("("))
                {
                    if(!"+*/".contains(""+in))
                    {
                        textView.append("" + in);
                    }
                    else
                    {
                        negative_set = false;
                    }
                }
                else
                {
                    if(!negative_set)
                    {
                        if (!"+-*/".contains(last))
                        {
                            textView.append("" + in);
                            negative_set = false;
                        }
                        else
                        {
                            if (in == '(' || in == ')')
                            {
                                textView.append("" + in);
                                negative_set = false;
                            }
                            else
                            {
                                textView.setText(sub);
                            }

                        }
                    }

                }

                point_added = false;
            }


        }
        else
        {
            if (in == '.')
            {
                textView.setText(".");
                point_added = true;
            }
            else if (in == '(' || in == ')')
            {
                textView.setText("" + in);
            }
        }


    }

    private void delete_last()
    {
        String text = textView.getText().toString();

        if (text.substring(text.length()-1).equals("-"))
        {
            negative_set = false;
        }
        if (text.length() > 0)
        {

            if (text.equals("Invalid Expression"))
            {
                textView.setText("");
            }
            else
            {
                if (text.substring(text.length() - 1).equals("."))
                {
                    point_added = false;
                }
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
        Log.i("text", text);
        if (text.matches("(\\d*\\.*\\d+[+*/\\-])+"))
        {
            textView.setText("Invalid Expression");
        }
        else
        {
            Stack<String> stack = new Stack<>();
            ArrayList<String> postfix = infix2postfix(text);
            for (String string : postfix)
            {
                Log.i("String", string);
            }
            while (!postfix.isEmpty())
            {
                String next = postfix.get(0);
                postfix.remove(0);
                Log.i("next", next);
                if (next.length() > 1)
                {
                    stack.push(next);
                }
                else
                {
                    if ("+-*/".contains(next))
                    {
                        float num, num1, num2;
                        try
                        {
                            num1 = Float.parseFloat(stack.pop());
                            num2 = Float.parseFloat(stack.pop());
                            Log.i("num1", String.valueOf(num1));
                            Log.i("num2", String.valueOf(num2));
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
                        catch (Exception e)
                        {
                            Log.e("Exception", String.valueOf(e));
                        }


                    }
                    else
                    {
                        stack.push(next);
                    }
                }
            }
            if (stack.isEmpty())
            {
                textView.setText("Invalid Expression");
            }
            else
            {
                textView.setText(stack.pop());
            }
        }
        calculated = true;
    }

    public ArrayList<String> infix2postfix(String infix)
    {

        StringBuilder SB = new StringBuilder();
        ArrayList<String> stringArray = new ArrayList<>();
        boolean last_was_parameter = false;
        for (int i = 0; i < infix.length(); i++)
        {
            char current = infix.charAt(i);
            if (current == '(')
            {
                if (SB.length() > 0)
                {
                    stringArray.add(SB.toString());
                    SB.delete(0, SB.length());
                    stringArray.add("*");
                    last_was_parameter = true;
                    stringArray.add("(");
                }
                else
                {
                    stringArray.add("(");
                    last_was_parameter = true;
                }
            }
            else if (current == ')')
            {
                stringArray.add(SB.toString());
                SB.delete(0, SB.length());
                last_was_parameter = false;
                if (infix.length() > i + 1)
                {

                    stringArray.add(")");
                    if (!"+-/*".contains("" + infix.charAt(i + 1)))
                    {
                        stringArray.add("*");
                        last_was_parameter = true;

                    }
                }
                else
                {
                    stringArray.add(")");
                }

            }
            else if (current == '-')
            {
                if (!last_was_parameter)
                {
                    stringArray.add("" + current);
                }
                else
                {
                    SB.append(current);
                }
            }
            else if ("+*/".contains("" + current))
            {
                if (SB.length() > 0)
                {
                    stringArray.add(SB.toString());
                }
                stringArray.add("" + current);
                SB.delete(0, SB.length());
                last_was_parameter = true;
            }
            else
            {
                SB.append(current);
            }
        }
        if (SB.length() > 0)
        {
            stringArray.add(SB.toString());
        }

        Stack<String> stack = new Stack<>();
        ArrayList<String> postfix = new ArrayList<>();
        for (String string : stringArray)
        {
            Log.i("string", string);
            if (string.length() == 1)
            {
                if (string.matches("\\d"))
                {
                    postfix.add(string);
                }
                else if (string.equals("("))
                {
                    stack.push(string);
                }
                else if (string.equals(")"))
                {
                    while (!stack.isEmpty() && !stack.peek().equals("("))
                    {
                        postfix.add(stack.pop());
                    }
                    if (!stack.isEmpty() && !stack.peek().equals("("))
                    {
                        postfix.clear();
                        return postfix;
                    }
                    else
                    {
                        stack.pop();
                    }
                }
                else
                {
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
