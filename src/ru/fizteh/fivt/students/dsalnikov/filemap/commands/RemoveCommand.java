package ru.fizteh.fivt.students.dsalnikov.filemap.commands;

import ru.fizteh.fivt.students.dsalnikov.filemap.Table;
import ru.fizteh.fivt.students.dsalnikov.shell.commands.Command;

public class RemoveCommand implements Command {

    private Table db;

    public RemoveCommand(Table t) {
        db = t;
    }

    @Override

    public void execute(String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("wrong amount of arguments");
        } else {
            db.remove(args[1]);
        }
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public int getArgsCount() {
        return 2;
    }
}
