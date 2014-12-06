package ru.fizteh.fivt.students.pavel_voropaev.multifilehashmap;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import ru.fizteh.fivt.students.pavel_voropaev.multifilehashmap.commands.*;

public class Main {
    private static String dbPath;

    public static void main(String[] args) {
        Database database = null;
        dbPath = System.getProperty("fizteh.db.dir");
        if (dbPath == null) {
            System.err.println("You must specify fizteh.db.dir");
            System.exit(1);
        }
        try {
            database = new Database(dbPath);
        } catch (Exception e) {
            System.err.print("Incorrect directory path: " + e.getMessage());
            System.exit(1);
        }

        if (args.length > 0) {
            try {
                batchMode(args, database);
            } catch (Exception e) {
                System.err.print(e.getMessage());
                System.exit(1);
            }
        } else {
            try {
                interactiveMode(database);
            } catch (Exception e) {
                System.err.print(e.getMessage());
                System.exit(1);
            }
        }
    }

    private static void batchMode(String[] args, Database database) throws Exception {
        StringBuilder commandsLine = new StringBuilder();
        for (String arg : args) {
            if (!arg.equals(" ")) {
                commandsLine.append(arg);
                commandsLine.append(' ');
            }
        }

        String[] command = commandsLine.toString().split(";");
        for (String comm : command) {
            execCommand(comm.trim(), database);
        }
        execCommand("exit", database);
    }

    private static void interactiveMode(Database database) 
            throws IllegalStateException {
        Scanner in = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.print("$ ");
            try {
                String line = in.nextLine();
                exit = executeLine(line, database);
            } catch (NoSuchElementException e) {
                try {
                    execCommand("exit", database);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            }
        }
        in.close();
    }
    
    private static boolean executeLine(String line, Database database) {
        boolean exit = false;
        try {
            exit = execCommand(line, database);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } 
        return exit;
    }
    

    private static boolean execCommand(String p, Database database) 
            throws IllegalArgumentException, IOException {
        String[] command = p.split("\\s+");
        if (command[0].equals("")) {
            return false;
        }
        if (command[0].equals("put")) {
            Put.exec(database.getWorkingTable(), command);
            return false;
        }
        if (command[0].equals("get")) {
            Get.exec(database.getWorkingTable(), command);
            return false;
        }
        if (command[0].equals("remove")) {
            Remove.exec(database.getWorkingTable(), command);
            return false;
        }
        if (command[0].equals("list")) {
            List.exec(database.getWorkingTable(), command);
            return false;
        }
        if (command[0].equals("create")) {
            Create.exec(database, command);
            return false;
        }
        if (command[0].equals("drop")) {
            Drop.exec(database, command);
            return false;
        }
        if (command[0].equals("show") && command.length > 1 && command[1].equals("tables")) {
            ShowTables.exec(database, command);
            return false;
        }
        if (command[0].equals("use")) {
            Use.exec(database, command);
            return false;
        }
        if (command[0].equals("exit")) {
            Exit.exec(database, command);
            return true;
        }

        throw new IllegalArgumentException("Unknown command: " + p);
    }
    
}
