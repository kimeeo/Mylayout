package com.peoplestouch.layouts;

import android.support.annotation.RawRes;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BhavinPadhiyar on 16/06/16.
 */
public class JavaScript {
    public JavaScript(@RawRes int jsFile, android.content.Context context, String[] methods) {
        try {
            InputStream is = context.getResources().openRawResource(jsFile);
            byte[]  buffer = new byte[0];
            buffer = new byte[is.available()];
            while (is.read(buffer) != -1);
            String javaScriptCode = new String(buffer);
            config(javaScriptCode,methods);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public JavaScript(String javaScriptCode, android.content.Context context, String[] methods) {
        config(javaScriptCode,methods);
    }

    public JavaScript(@RawRes int jsFile, android.content.Context context, String method) {
        try {
            InputStream is = context.getResources().openRawResource(jsFile);
            byte[]  buffer = new byte[0];
            buffer = new byte[is.available()];
            while (is.read(buffer) != -1);
            String javaScriptCode = new String(buffer);
            config(javaScriptCode,new String[]{method});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public JavaScript(String javaScriptCode, android.content.Context context, String method) {
        config(javaScriptCode,new String[]{method});
    }
    public JavaScript() {

    }

    private Context rhino;
    private Scriptable scope;
    private Map<String,Function> functions;
    private boolean isConfig=false;
    public void config(String javaScriptCode,String[] methods)
    {
        rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        scope = rhino.initStandardObjects();
        functions = new HashMap<>();
        for (String method : methods) {
            Function jsFunction = getFunction(javaScriptCode, method);
            if (jsFunction != null)
                functions.put(method, jsFunction);
        }
    }
    public void config(String javaScriptCode,String method)
    {
        config(javaScriptCode,new String[]{method});
    }
    public void config(@RawRes int jsFile, android.content.Context context,String[] methods)
    {
        try {
            InputStream is = context.getResources().openRawResource(jsFile);
            byte[] buffer = new byte[0];
            buffer = new byte[is.available()];
            while (is.read(buffer) != -1) ;
            String javaScriptCode = new String(buffer);
            config(javaScriptCode,methods);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void config(@RawRes int jsFile, android.content.Context context,String method)
    {
        config(jsFile,context,new String[]{method});
    }
    public Function getFunction(String javaScriptCode, String method)
    {
        rhino.evaluateString(scope, javaScriptCode, "JavaScript", 1, null);
        Object obj = scope.get(method, scope);
        if (obj instanceof Function) {
            Function function = (Function) obj;
            return function;
        }
        return null;
    }

    public Object call(String method,Object[] params)
    {
        for (Map.Entry<String, Function> entry : functions.entrySet()) {
            if (entry.getKey().equals(method)) {
                Function function = entry.getValue();
                if(function!=null)
                    return function.call(rhino, scope, scope, params);
            }
        }
        return null;
    }
}
