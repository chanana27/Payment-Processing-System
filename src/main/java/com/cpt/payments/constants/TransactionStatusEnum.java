package com.cpt.payments.constants;

import java.util.HashMap;
import java.util.Map;

public enum TransactionStatusEnum {
	CREATED(1, "CREATED"),
    INITIATED(2, "INITIATED"),
    PENDING(3, "PENDING"),
    SUCCESS(4, "SUCCESS"),
    FAILED(5, "FAILED");

    private final int id;
    private final String name;

    private static final Map<Integer, TransactionStatusEnum> ID_MAP = new HashMap<>();
    private static final Map<String, TransactionStatusEnum> NAME_MAP = new HashMap<>();

    static {
        for (TransactionStatusEnum e : values()) {
            ID_MAP.put(e.id, e);
            NAME_MAP.put(e.name.toUpperCase(), e);
        }
    }

    TransactionStatusEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public static TransactionStatusEnum getEnumById(int id) {
        TransactionStatusEnum e = ID_MAP.get(id);
        if (e == null) throw new IllegalArgumentException("Invalid TransactionStatusEnum id: " + id);
        return e;
    }

    public static TransactionStatusEnum getEnumByName(String name) {
        TransactionStatusEnum e = NAME_MAP.get(name.toUpperCase());
        if (e == null) throw new IllegalArgumentException("Invalid TransactionStatusEnum name: " + name);
        return e;
    }
}
