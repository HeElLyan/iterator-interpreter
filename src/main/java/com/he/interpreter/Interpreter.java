package com.he.interpreter;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Interpreter {

    private Context context;

    private List<Wrapper> componentWrapperList;

    public Interpreter(Context context, Object... components) {
        this.context = context;
        this.componentWrapperList = new ArrayList<>();
        for (Object component : components) {
            componentWrapperList.add(new Wrapper(component));
        }
    }

    public void execute(String command) {
        try {
            for (Wrapper component : componentWrapperList) {
                if (component.respond(context, command)) {
                    return;
                }
            }
            throw new IllegalStateException("Unrecognizable");
        } catch (InvocationTargetException | IllegalAccessException | IllegalStateException e) {
            System.out.println(e.getCause().getMessage());
        }

    }

}
