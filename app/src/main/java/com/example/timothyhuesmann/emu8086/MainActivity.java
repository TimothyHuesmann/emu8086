package com.example.timothyhuesmann.emu8086;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView chosenRegister;
    private Resources resources;

    private EditText instructionET;
    private TextView outputTV;
    private TextView AXValue;
    private TextView AHValue;
    private TextView ALValue;
    private TextView BXValue;
    private TextView BHValue;
    private TextView BLValue;
    private TextView CXValue;
    private TextView CHValue;
    private TextView CLValue;
    private TextView DXValue;
    private TextView DHValue;
    private TextView DLValue;
    private String[] registers = {"AX", "AH", "AL", "BX", "BH", "BL", "CX", "CH", "CL", "DX", "DH", "DL"};
    private Map registerMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.instructionET = (EditText)this.findViewById(R.id.instructionET);
        this.outputTV = (TextView)this.findViewById(R.id.outputTV);

        //Setup of Register Containers
        this.AXValue = (TextView)this.findViewById(R.id.AXValue);
        this.AHValue = (TextView)this.findViewById(R.id.AHValue);
        this.ALValue = (TextView)this.findViewById(R.id.ALValue);
        this.BXValue = (TextView)this.findViewById(R.id.BXValue);
        this.BHValue = (TextView)this.findViewById(R.id.BHValue);
        this.BLValue = (TextView)this.findViewById(R.id.BLValue);
        this.CXValue = (TextView)this.findViewById(R.id.CXValue);
        this.CHValue = (TextView)this.findViewById(R.id.CHValue);
        this.CLValue = (TextView)this.findViewById(R.id.CLValue);
        this.DXValue = (TextView)this.findViewById(R.id.DXValue);
        this.DHValue = (TextView)this.findViewById(R.id.DHValue);
        this.DLValue = (TextView)this.findViewById(R.id.DLValue);

        this.registerMap = new HashMap();
        this.registerMap.put("AX", AXValue);
        this.registerMap.put("AH", AHValue);
        this.registerMap.put("AL", ALValue);
        this.registerMap.put("BX", BXValue);
        this.registerMap.put("BH", BHValue);
        this.registerMap.put("BL", BLValue);
        this.registerMap.put("CX", CXValue);
        this.registerMap.put("CH", CHValue);
        this.registerMap.put("CL", CLValue);
        this.registerMap.put("DX", DXValue);
        this.registerMap.put("DH", DHValue);
        this.registerMap.put("DL", DLValue);
        showRegisterValues();
    }

    //**
    //METHOD: emulateButtonPressed -> when the "emulate" button is pressed, this determines the action being called
    //INPUT: Button pressed and View v
    //OUTPUT: void
    //**
    public void emulateButtonPressed(View v){
        String entry = this.instructionET.getText().toString();
        LinkedList<String> parts = this.getParts(entry);
        CPU.processInstruction(parts);
        this.showRegisterValues();
    }

    private void showRegisterValues(){
        this.AXValue.setText(((Register)CPU.registers.get("ax")).getValue());
        this.BXValue.setText(((Register)CPU.registers.get("bx")).getValue());
        this.CXValue.setText(((Register)CPU.registers.get("cx")).getValue());
        this.DXValue.setText(((Register)CPU.registers.get("dx")).getValue());
    }

    private LinkedList<String> getParts(String entry)
    {
        //mov      ax  ,   bx
        //get command
        LinkedList<String> answer = new LinkedList<String>();
        entry = entry.trim();
        String command = "";
        int pos = 0;
        while(entry.charAt(pos) != ' ')
        {
            command += entry.charAt(pos);
            pos++;
        }
        answer.addLast(command);

        //was this a command with no params
        if(pos == entry.length())
        {
            return answer;
        }

        //skip whitespace
        while(entry.charAt(pos) == ' ')
        {
            pos++;
        }

        //read dest
        String dest = "";
        while(pos != entry.length() && entry.charAt(pos) != ',' && entry.charAt(pos) != ' ')
        {
            dest += entry.charAt(pos);
            pos++;
        }
        answer.addLast(dest);

        //was this a command with a single param
        if(pos == entry.length())
        {
            return answer;
        }

        while(pos != entry.length())
        {
            //skip whitespace
            while(entry.charAt(pos) == ' ')
            {
                pos++;
            }

            //burn past comma
            pos++;

            //skip whitespace
            while(entry.charAt(pos) == ' ')
            {
                pos++;
            }

            //read param
            String param = "";
            while(pos != entry.length() && entry.charAt(pos) != ',' && entry.charAt(pos) != ' ')
            {
                param += entry.charAt(pos);
                pos++;
            }
            answer.addLast(param);
        }
        return answer;
    }

}
