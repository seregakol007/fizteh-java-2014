package ru.fizteh.fivt.students.kolmakov_sergey.sqlite.interpreter;

import java.util.function.BiConsumer;

public class Command {
    private String name;
    private int minArguments;
    private int maxArguments;
    private BiConsumer<DataBaseState, String[]> callback;
    DataBaseState dbState;

    public Command(String name, int minArguments, int maxArguments, BiConsumer<DataBaseState, String[]> callback,
                   DataBaseState dbState) {
        this.name = name;
        this.minArguments = minArguments;
        this.maxArguments = maxArguments;
        this.callback = callback;
    }

    public String getName() {
        return name;
    }

    public void execute(String[] params) throws WrongNumberOfArgumentsException {
        if (!(minArguments <= params.length && params.length <= maxArguments)) {
            throw new WrongNumberOfArgumentsException(name + ": incorrect number of arguments");
        } else {
            callback.accept(dbState, params);
        }
    }
}
