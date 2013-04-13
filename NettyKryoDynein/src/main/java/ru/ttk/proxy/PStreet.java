package ru.ttk.proxy;

import java.io.Serializable;

/**
 *
 */
public class PStreet implements Serializable, Proxyable, IStreet {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Street{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
