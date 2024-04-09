package com.example.coffeecraft.Utils;

import java.util.HashMap;

public class GetValueSugar {

    HashMap<Integer, String> sugarHashMap = new HashMap<>();

    public GetValueSugar(){
        sugarHashMap.put(0, "none");
        sugarHashMap.put(1, "low");
        sugarHashMap.put(2, "medium");
        sugarHashMap.put(3, "high");
    }

    public String getSugarValue(int v){
        return sugarHashMap.get(v);
    }

}
