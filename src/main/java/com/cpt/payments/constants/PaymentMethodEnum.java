package com.cpt.payments.constants;

import java.util.HashMap;
import java.util.Map;

public enum PaymentMethodEnum {
	APM(1, "APM");

    private final int id;
    private final String name;

    private static final Map<Integer, PaymentMethodEnum> ID_MAP = new HashMap<>();
    private static final Map<String, PaymentMethodEnum> NAME_MAP = new HashMap<>();

    static {
        for (PaymentMethodEnum e : values()) {
            ID_MAP.put(e.id, e);
            NAME_MAP.put(e.name.toUpperCase(), e);
        }
    }

    PaymentMethodEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public static PaymentMethodEnum getEnumById(int id) {
        PaymentMethodEnum e = ID_MAP.get(id);
        if (e == null) throw new IllegalArgumentException("Invalid PaymentMethodEnum id: " + id);
        return e;
    }

    public static PaymentMethodEnum getEnumByName(String name) {
        PaymentMethodEnum e = NAME_MAP.get(name.toUpperCase());
        if (e == null) throw new IllegalArgumentException("Invalid PaymentMethodEnum name: " + name);
        return e;
    }
}
