package ru.fizteh.fivt.students.kolmakov_sergey.sqlite;


import ru.fizteh.fivt.students.kolmakov_sergey.sqlite.interpreter.Command;
import ru.fizteh.fivt.students.kolmakov_sergey.sqlite.interpreter.DataBaseState;
import ru.fizteh.fivt.students.kolmakov_sergey.sqlite.interpreter.Interpreter;

import java.sql.*;
import java.util.function.BiConsumer;

public class Main {
    public static void main( String args[] )
    {
        final String JDBC_DRIVER = "org.sqlite.JDBC";
        final String databasePath = "jdbc:sqlite:src\\ru\\fizteh\\fivt\\students\\kolmakov_sergey\\"
                + "sqlite\\database\\database.db";
        Connection connection;
        Statement statement;
        DataBaseState state;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(databasePath);
            System.out.println("Opened database successfully");
            statement = connection.createStatement();
            state = new DataBaseState(connection, statement);
            Interpreter dbInterpreter = new Interpreter(new Command[] {
                    new Command("show_authors", 0, 0, new BiConsumer<DataBaseState, String[]>() {
                        @Override
                        public void accept(DataBaseState state, String[] args) {
                            String sql = "SELECT  * FROM authors;";
                            ResultSet resultSet;
                            try {
                                resultSet = statement.executeQuery(sql);
                                printResultSet(resultSet);
                            } catch (SQLException e){
                                System.out.println(e.getMessage());
                            }
                        }
                    }, state),
                    new Command("show_books", 0, 0, new BiConsumer<DataBaseState, String[]>() {
                        @Override
                        public void accept(DataBaseState state, String[] args) {
                            String sql = "SELECT  * FROM books;";
                            ResultSet resultSet;
                            try {
                                resultSet = statement.executeQuery(sql);
                                printResultSet(resultSet);
                            } catch (SQLException e){
                                System.out.println(e.getMessage());
                            }
                        }
                    }, state)});
            System.exit(dbInterpreter.run(args));
            statement.close();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    private static void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(" | ");
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue);
            }
            System.out.println("");
        }
    }
}


