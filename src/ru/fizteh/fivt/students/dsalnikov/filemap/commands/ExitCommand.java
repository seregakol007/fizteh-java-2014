package ru.fizteh.fivt.students.dsalnikov.filemap.commands;

import ru.fizteh.fivt.students.dsalnikov.filemap.Table;
import ru.fizteh.fivt.students.dsalnikov.shell.commands.Command;

public class ExitCommand implements Command {

    private Table db;

    public ExitCommand(Table table) {
        db = table;
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("wrong amount of arguments");
        } else {
            db.exit();
            System.exit(0);
        }
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public int getArgsCount() {
        return 1;
    }
}
