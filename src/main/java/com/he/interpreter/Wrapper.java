package com.he.interpreter;

import com.he.interpreter.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class Wrapper {

    private Object component;

    private Map<String, Method> storage;

    public Wrapper(Object component) {
        this.component = component;
        this.storage = (Map<String, Method>) List.of(component.getClass().getDeclaredMethods());
    }

    public boolean respond(Context context, String command) throws InvocationTargetException, IllegalAccessException {
        for (Map.Entry<String, Method> entry : storage.entrySet()) {
            if (command.startsWith(entry.getKey())) {
                Method method = entry.getValue();
                boolean accessible = method.canAccess(component);
                method.setAccessible(true);
                method.invoke(component, context, command.substring(entry.getKey().length() + 1).split(" "));
                method.setAccessible(accessible);
                return true;
            }
        }
        return false;
    }

}

