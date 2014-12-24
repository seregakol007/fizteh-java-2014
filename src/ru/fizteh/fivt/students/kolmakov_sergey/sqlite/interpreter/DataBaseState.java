package ru.fizteh.fivt.students.kolmakov_sergey.sqlite.interpreter;

import java.sql.Connection;
import java.sql.Statement;

public final class DataBaseState {
    final Connection connection;
    Statement statement;
    public DataBaseState(Connection connection, Statement statement) {
        this.connection = connection;
        this.statement = statement;
    }
}
