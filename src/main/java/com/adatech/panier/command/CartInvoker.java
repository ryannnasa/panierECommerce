package com.adatech.panier.command;

import java.util.ArrayDeque;
import java.util.Deque;

public class CartInvoker {
    private Deque<ICommandCancelable> history = new ArrayDeque<>();

    public void executeCommand(ICommandCancelable cmd) {
        cmd.execute();
        history.push(cmd);
    }

    public void undoLast() {
        if (!history.isEmpty()) {
            ICommandCancelable cmd = history.pop();
            cmd.undo();
        }
    }
}
