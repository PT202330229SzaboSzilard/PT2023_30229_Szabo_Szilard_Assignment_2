package ro.tuc.logic;

import ro.tuc.model.Server;
import ro.tuc.model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private List<Server> servers = new ArrayList<Server>();
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer)
    {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        for(int i = 0; i < maxNoServers; i++)
        {
            Server server = new Server();
            Thread t = new Thread(server);
            servers.add(server);
            t.start();
        }
    }

    public void changeStrategy(SelectionPolicy sp)
    {
        if(sp == SelectionPolicy.SHORTEST_TIME)
        {
            strategy = new ConcreteStrategyTime();
        }
    }
    public void dispatchTask(Task t)
    {
        strategy.addTask(servers, t);
    }

    public List<Server> getServers()
    {
        return servers;
    }


}
