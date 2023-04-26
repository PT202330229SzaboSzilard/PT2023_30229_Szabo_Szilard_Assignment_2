package ro.tuc.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    public Server()
    {
        tasks = new LinkedBlockingDeque<Task>();
        waitingPeriod = new AtomicInteger(0);
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void addTask(Task newTask)
    {
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    public void run()
    {
        while(true)
        {
            if (tasks.size() != 0) {
                Task currentTask = tasks.peek();

                try {
                    while (currentTask.getServiceTime() > 0) {
                        Thread.sleep(1000);
                        waitingPeriod.decrementAndGet();
                        int currentServiceTime = currentTask.getServiceTime();
                        //AtomicInteger currentServiceTime = new AtomicInteger(currentTask.getServiceTime().decrementAndGet());
                        currentServiceTime--;
                        currentTask.setServiceTime(currentServiceTime);
                        //System.out.println(currentTask.getID() + " " + currentTask.getServiceTime().get());
                        if (currentTask.getServiceTime() == 0)
                            tasks.remove(currentTask);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public String afisare()
    {
        if(!(tasks.size() == 0))
        {
            String ok = "";
            for(Task z: tasks)
            {
                ok = ok + z.toString() + " ";
            }
            return ok;
        }
        return "closed";
    }


}
