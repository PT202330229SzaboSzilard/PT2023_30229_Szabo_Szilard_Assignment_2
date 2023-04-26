package ro.tuc.logic;

import ro.tuc.model.Server;
import ro.tuc.model.Task;

import java.util.List;

public interface Strategy {
    public void addTask(List<Server> servers, Task t);
}
