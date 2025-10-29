package com.ongtangco.trueshot.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Transform<V, K> {

    private final Class<K> clazz;

    public Transform(Class<K> clazz) {
        this.clazz = clazz;
    }

    public K transform(V source) {
        if (source == null) return null;

        Map<String, Object> getterValues = new HashMap<>();
        try {
            for (Method m : source.getClass().getMethods()) {
                if (m.getParameterCount() != 0) continue;
                String name = m.getName();
                if (name.startsWith("get")) {
                    getterValues.put(name, m.invoke(source));
                } else if (name.startsWith("is")) {
                    // normalize boolean isX -> getX
                    getterValues.put(name.replaceFirst("is", "get"), m.invoke(source));
                }
            }

            K target = clazz.getDeclaredConstructor().newInstance();

            for (Method setter : clazz.getMethods()) {
                if (!setter.getName().startsWith("set") || setter.getParameterCount() != 1) continue;

                String getterKey = setter.getName().replaceFirst("s", "g"); // setX -> getX
                if (!getterValues.containsKey(getterKey)) continue;

                Object val = getterValues.get(getterKey);
                Class<?> toType = setter.getParameterTypes()[0];

                // null handling
                if (val == null) {
                    if (!toType.isPrimitive()) setter.invoke(target, (Object) null);
                    continue;
                }

                Class<?> fromType = val.getClass();

                // 1) Directly assignable types
                if (toType.isAssignableFrom(fromType)) {
                    setter.invoke(target, val);
                    continue;
                }

                // 2) Enum <-> String bridge
                if (toType.isEnum() && val instanceof String) {
                    @SuppressWarnings({"unchecked","rawtypes"})
                    Object enumVal = Enum.valueOf((Class<? extends Enum>) toType, ((String) val).replace(' ', '_'));
                    setter.invoke(target, enumVal);
                    continue;
                }
                if (fromType.isEnum() && toType == String.class) {
                    setter.invoke(target, ((Enum<?>) val).name());
                    continue;
                }

                // 3) BigDecimal bridges
                if (toType == BigDecimal.class && (val instanceof Number || val instanceof String)) {
                    setter.invoke(target, new BigDecimal(String.valueOf(val)));
                    continue;
                }
                if (val instanceof BigDecimal && (toType == Double.class || toType == double.class)) {
                    setter.invoke(target, ((BigDecimal) val).doubleValue());
                    continue;
                }
                if (val instanceof BigDecimal && toType == String.class) {
                    setter.invoke(target, val.toString());
                    continue;
                }

                // 4) Integer <-> Long convenience
                if (fromType == Integer.class && toType == Long.class) {
                    setter.invoke(target, Long.valueOf((Integer) val));
                    continue;
                }
                if (fromType == Long.class && toType == Integer.class) {
                    setter.invoke(target, ((Long) val).intValue());
                    continue;
                }

            }

            return target;
        } catch (InstantiationException | IllegalAccessException |
                 InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException("Failed to transform instance of " + source.getClass().getName(), e);
        }
    }
}
