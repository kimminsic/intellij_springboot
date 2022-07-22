package com.mysite.sbb.Util;

public class Ut {
    public static boolean empty(Object obj){
        if(obj==null){
            return true;
        }

        if(obj instanceof String ==false){
            return true;
        }

        if(!(obj instanceof String)){
            return true;
        }

        String str = (String)obj;
        return str.trim().length()==0;
    }
}
