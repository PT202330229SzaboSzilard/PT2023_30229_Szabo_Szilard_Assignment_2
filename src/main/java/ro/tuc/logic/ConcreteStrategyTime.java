package ro.tuc.logic;

import ro.tuc.model.Server;
import ro.tuc.model.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task t) {
        int min = Integer.MAX_VALUE;
        Server s = new Server();
        for(Server x: servers)
        {
            if(x.getWaitingPeriod().get() < min)
            {
                min = x.getWaitingPeriod().get();
                s = x;
            }
        }
        s.addTask(t);
    }
}
