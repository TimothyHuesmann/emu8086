package com.example.timothyhuesmann.emu8086;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by timothyhuesmann on 9/27/16.
 */

public class CPU {
    static Register ax = new Register("ax", 16, "ah", "al");
    static Register bx = new Register("bx", 16, "bh", "bl");
    static Register cx = new Register("cx", 16, "ch", "cl");
    static Register dx = new Register("dx", 16, "dh", "dl");
    static HashMap<String, Storage> registers = new HashMap<String, Storage>(){{
        put("ax",ax);
        put("bx",bx);
        put("cx",cx);
        put("dx",dx);
    }};
    static HashMap<String, String> variables = new HashMap<String, String>();

    static void processInstruction(LinkedList<String> parts){
        String command = parts.get(0).toLowerCase();
        if(command.equals("mov"))
        {
            Storage dest = registers.get(parts.get(1).toLowerCase());
            if(dest == null) //dest must be a variable or a subregister
            {

                boolean placed = false;
                for(Storage s : registers.values())
                {
                    if(s instanceof Register)
                    {
                        if (((Register) s).hasHighSubRegister(parts.get(1).toLowerCase())) {
                            ((Register) s).loadHigh(parts.get(2).toLowerCase());
                            placed = true;
                            break;
                        } else if (((Register) s).hasLowSubRegister(parts.get(1).toLowerCase())) {
                            ((Register) s).loadLow(parts.get(2).toLowerCase());
                            placed = true;
                            break;
                        }
                    }
                }
                if(!placed)
                {
                    //destination must be a variable or does not exist
                    variables.put(parts.get(1).toLowerCase(), parts.get(2).toLowerCase());
                }
            }
            else
            {
                //we have our destination register
                ((Register)dest).load(parts.get(2));
            }

        }
    }
}
