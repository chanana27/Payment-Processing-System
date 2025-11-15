package com.cpt.payments.constants;

import java.util.HashMap;
import java.util.Map;

public enum ProviderEnum {
	TRUSTLY(1, "TRUSTLY"),
    PAYPAL(2, "PAYPAL");

    private final int id;
    private final String name;

    private static final Map<Integer, ProviderEnum> ID_MAP = new HashMap<>();
    private static final Map<String, ProviderEnum> NAME_MAP = new HashMap<>();

    static {
        for (ProviderEnum e : values()) {
            ID_MAP.put(e.id, e);
            NAME_MAP.put(e.name.toUpperCase(), e);
        }
    }

    ProviderEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public static ProviderEnum getEnumById(int id) {
        ProviderEnum e = ID_MAP.get(id);
        if (e == null) throw new IllegalArgumentException("Invalid ProviderEnum id: " + id);
        return e;
    }

    public static ProviderEnum getEnumByName(String name) {
        ProviderEnum e = NAME_MAP.get(name.toUpperCase());
        if (e == null) throw new IllegalArgumentException("Invalid ProviderEnum name: " + name);
        return e;
    }
}
