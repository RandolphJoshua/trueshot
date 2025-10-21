package com.ongtangco.trueshot.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Transform<V, K> {

    private final Class<K> clazz;

    public Transform(Class<K> clazz) {
        this.clazz = clazz;
    }

    public K transform(V source) {
        if (source == null) {
            return null;
        }
        Map<String, Object> getterValues = new HashMap<>();
        try {
            for (Method method : source.getClass().getMethods()) {
                String methodName = method.getName();
                if (methodName.startsWith("get") && method.getParameterCount() == 0) {
                    getterValues.put(methodName, method.invoke(source));
                }
                if (methodName.startsWith("is") && method.getParameterCount() == 0) {
                    getterValues.put(methodName.replaceFirst("is", "get"), method.invoke(source));
                }
            }
            K target = clazz.getDeclaredConstructor().newInstance();
            for (Method method : clazz.getMethods()) {
                String methodName = method.getName();
                if (methodName.startsWith("set") && method.getParameterCount() == 1) {
                    String getterKey = methodName.replaceFirst("s", "g");
                    if (!getterValues.containsKey(getterKey)) {
                        continue;
                    }
                    Object value = getterValues.get(getterKey);
                    if (value != null || !method.getParameterTypes()[0].isPrimitive()) {
                        method.invoke(target, value);
                    }
                }
            }
            return target;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new IllegalStateException("Failed to transform instance of " + source.getClass().getName(), ex);
        }
    }
}
