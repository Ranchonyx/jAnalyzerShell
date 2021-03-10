package com.ranchonyx;

import org.jetbrains.annotations.Nullable;

public interface Command {

    public void run(@Nullable String[] args);

}