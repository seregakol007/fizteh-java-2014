package ru.fizteh.fivt.students.SurkovaEkaterina.MultiFileHashMap.FileMap;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ATable {

    public static final Charset CHARSET = StandardCharsets.UTF_8;

    protected final HashMap<String, String> data;

    private final String tableName;
    private int size;
    private String directory;

    protected abstract void load() throws IOException;

    protected abstract void save() throws IOException;

    public ATable(final String dir, final String tName) {
        this.directory = dir;
        this.tableName = tName;
        data = new HashMap<String, String>();
        try {
            load();
        } catch (IOException e) {
            System.err.println("Error loading table: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error loading table: " + e.getMessage());
        }
    }

    public final String get(final String key) {
        if ("".equals(key)) {
            throw new IllegalArgumentException("Key cannot be empty!");
        }
        return data.get(key);
    }

    public final String put(final String key, final String value) {
        if (key == null) {
            throw new IllegalArgumentException("Key should not be null!");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value should not be null!");
        }
        if (key.trim().isEmpty()) {
            throw new IllegalArgumentException("Key should not be empty!");
        }
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("Value should not be empty!");
        }

        String oldValue = data.put(key, value);
        if (oldValue == null) {
            size++;
        }
        return oldValue;
    }

    public final String remove(final String key) {
        if ("".equals(key)) {
            throw new IllegalArgumentException("key cannot be null");
        }
        if (get(key) == null) {
            return null;
        }
        String oldValue = data.remove(key);
        if (oldValue != null) {
            size--;
        }

        return oldValue;
    }

    public final List<String> list() {
        List<String> list = new ArrayList<String>(data.keySet());
        return list;
    }

    public final String getName() {
        return tableName;
    }

    public final int getSize() {
        return size;
    }

    protected final String getDirectory() {
        return directory;
    }
}
