package com.adatech.panier.command;

public interface ICommandCancelable {
    void execute();

    void undo();
}
