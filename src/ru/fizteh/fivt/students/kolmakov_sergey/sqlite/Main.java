package ru.fizteh.fivt.students.kolmakov_sergey.sqlite;


import ru.fizteh.fivt.students.kolmakov_sergey.sqlite.interpreter.Command;
import ru.fizteh.fivt.students.kolmakov_sergey.sqlite.interpreter.Interpreter;

import java.sql.*;
import java.util.function.Consumer;

public class Main {
    public static void main( String args[] )
    {
        final String JDBC_DRIVER = "org.sqlite.JDBC";
        final String databasePath = "jdbc:sqlite:src\\ru\\fizteh\\fivt\\students\\kolmakov_sergey\\"
                + "sqlite\\database\\database.db";
        try {
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(databasePath);
            System.out.println("Opened database successfully");
            Statement statement = connection.createStatement();
            Interpreter dbInterpreter = new Interpreter(new Command[] {
                    new Command("show_authors", 0, 0, new Consumer<String[]>() {
                        @Override
                        public void accept(String[] args) {
                            String sql = "SELECT  * FROM authors;";
                            ResultSet resultSet;
                            try {
                                resultSet = statement.executeQuery(sql);
                                printResultSet(resultSet);
                            } catch (SQLException e){
                                System.out.println(e.getMessage());
                            }
                        }
                    }),
                    new Command("show_books", 0, 0, new Consumer<String[]>() {
                        @Override
                        public void accept(String[] args) {
                            String sql = "SELECT  * FROM books;";
                            ResultSet resultSet;
                            try {
                                resultSet = statement.executeQuery(sql);
                                printResultSet(resultSet);
                            } catch (SQLException e){
                                System.out.println(e.getMessage());
                            }
                        }
                    }),
                    new Command("insert_book", 3, 3, new Consumer<String[]>() {
                        @Override
                        public void accept(String[] args) {
                            String sql = "INSERT INTO books VALUES ('" + args[0]
                                    + "', '" + args[1] + "', '" + args[2] + "');";
                            System.out.println(sql);
                            try {
                                statement.executeUpdate(sql);
                            } catch (SQLException e){
                                System.out.println(e.getMessage());
                            }
                        }
                    }),
                    new Command("insert_author", 2, 2, new Consumer<String[]>() {
                        @Override
                        public void accept(String[] args) {
                            String sql = "INSERT INTO authors VALUES ('" + args[0]
                                    + "', '" + args[1] + "');";
                            System.out.println(sql);
                            try {
                                statement.executeUpdate(sql);
                            } catch (SQLException e){
                                System.out.println(e.getMessage());
                            }
                        }
                    })});
            System.exit(dbInterpreter.run(args));
            statement.close();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
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


