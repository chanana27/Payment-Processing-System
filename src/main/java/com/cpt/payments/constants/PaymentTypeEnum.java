package com.cpt.payments.constants;

import java.util.HashMap;
import java.util.Map;

public enum PaymentTypeEnum {
	SALE(1, "SALE");

    private final int id;
    private final String name;

    private static final Map<Integer, PaymentTypeEnum> ID_MAP = new HashMap<>();
    private static final Map<String, PaymentTypeEnum> NAME_MAP = new HashMap<>();

    static {
        for (PaymentTypeEnum e : values()) {
            ID_MAP.put(e.id, e);
            NAME_MAP.put(e.name.toUpperCase(), e);
        }
    }

    PaymentTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public static PaymentTypeEnum getEnumById(int id) {
        PaymentTypeEnum e = ID_MAP.get(id);
        if (e == null) throw new IllegalArgumentException("Invalid PaymentTypeEnum id: " + id);
        return e;
    }

    public static PaymentTypeEnum getEnumByName(String name) {
        PaymentTypeEnum e = NAME_MAP.get(name.toUpperCase());
        if (e == null) throw new IllegalArgumentException("Invalid PaymentTypeEnum name: " + name);
        return e;
    }
}
