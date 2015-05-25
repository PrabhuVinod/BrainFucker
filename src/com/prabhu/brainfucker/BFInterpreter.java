package com.prabhu.brainfucker;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class BFInterpreter extends Activity {

    private Interpreter interpreter;
    private int inputCounter;
    private EditText inputText;
    private EditText codeText;
    private TextView outputText;
    private String output;
    private AsyncTask interpThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bfinterpreter);


        inputText = (EditText) findViewById(R.id.inputText);
        codeText = (EditText) findViewById(R.id.codeText);
        outputText = (TextView) findViewById(R.id.outputText);

        output = "";
        inputCounter = 0;
    }

   

   
    
    
    
    public boolean interprete(View v){

        interpreter = new Interpreter();
        interpreter.setIO(new UserIO() {
            @Override
            public char input() {
                try {
                    char ret = inputText.getText().toString().charAt(inputCounter);
                    inputCounter++;
                    return ret;
                } catch (Exception e) {
                    // Panu Kalliokoski behavior
                    return 0;
                }
            }

            @Override
            public void output(char out) {
                outputText.setVisibility(View.VISIBLE);
                output += String.valueOf(out);
                outputText.setText(output);
            }
        });
        try {
            output = "";
            inputCounter = 0;
            interpreter.run(codeText.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            output += getString(R.string.crash) + e.toString();
            outputText.setVisibility(View.VISIBLE);
            outputText.setText(output);
        }
        return true;
    
    }

    
    
    public void cpy(View v){

        ClipboardManager clipboard = (ClipboardManager)
            getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setText(output);
        Toast.makeText(this, getString(R.string.copied), Toast.LENGTH_SHORT).show();
    
    }
    /*
    private class InterpreterThread extends AsyncTask<String, Integer, String> {

        private int a;

        @Override
        void onPreExecute() {
            super.onPreExecute();
            output = "";
        }

        @Override
        protected String doInBackground(String... params) {
            interpreter = new Interpreter();
            interpreter.setIO(new UserIO() {
                @Override
                public char input() {
                    try {
                        char ret = inputText.getText().toString().charAt(inputCounter);
                        inputCounter++;
                        return ret;
                    } catch (Exception e) {
                        // Panu Kalliokoski behavior
                        return 0;
                    }
                }

                @Override
                public void output(char out) {
                    outputText.setVisibility(View.VISIBLE);
                    output += String.valueOf(out);
                    outputText.setText(output);
                }
            });
            try {
                output = "";
                inputCounter = 0;
                interpreter.run(codeText.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
                output += getString(R.string.crash) + e.toString();
                outputText.setVisibility(View.VISIBLE);
                outputText.setText(output);
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
    */
}
