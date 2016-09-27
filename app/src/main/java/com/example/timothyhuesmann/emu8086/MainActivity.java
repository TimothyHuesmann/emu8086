package com.example.timothyhuesmann.emu8086;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.in;

public class MainActivity extends AppCompatActivity {

    private static final int sizeOfIntInHalfBytes = 8;
    private static final int numberOfBitsInAHalfByte = 4;
    private static final int halfByte = 0x0F;
    private static final char[] hexDigits = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

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
    }

    //**
    //METHOD: emulateButtonPressed -> when the "emulate" button is pressed, this determines the action being called
    //INPUT: Button pressed and View v
    //OUTPUT: void
    //**
    public void emulateButtonPressed(View v){
        this.outputTV.setText(this.instructionET.getText());
        String[] instructions = this.instructionET.getText().toString().split(" ");
        String method = instructions[0];
        if(method == "mov"){
            movCall(instructions);
        }
    }

    //**
    //METHOD: movCall -> When "mov" is called this calls the move command which moves either the literal or the data to the new location
    //INPUT: String[] instructions -> String array of the split instruction string
    //OUTPUT: void
    //**
    private void movCall(String[] instructions){
        String dest = instructions[1];                      //dest -> Destination address
        String input = instructions[2];                     //input -> input value
        for(String register : registers){
            if(dest.toUpperCase() == register){
                chosenRegister = (TextView)this.registerMap.get(dest.toUpperCase());
                String hexVal = decToHex(Integer.getInteger(input));
                chosenRegister.setText(dest.toUpperCase() + " Val: " + hexVal);
            }
        }
    }

    private static String decToHex(int dec) {
        StringBuilder hexBuilder = new StringBuilder(sizeOfIntInHalfBytes);
        hexBuilder.setLength(sizeOfIntInHalfBytes);
        for (int i = sizeOfIntInHalfBytes - 1; i >= 0; --i)
        {
            int j = dec & halfByte;
            hexBuilder.setCharAt(i, hexDigits[j]);
            dec >>= numberOfBitsInAHalfByte;
        }
        return hexBuilder.toString();
    }
}
