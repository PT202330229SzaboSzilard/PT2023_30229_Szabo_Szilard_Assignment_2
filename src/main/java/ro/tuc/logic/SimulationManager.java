package ro.tuc.logic;

import ro.tuc.model.Server;
import ro.tuc.model.Task;

import javax.swing.table.AbstractTableModel;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable{

    public int timeLimit = 200;
    public int maxProcessingTime = 9;
    public int minProcessingTime = 3;
    public int numberOfServers = 20;
    public int numberOfClients = 1000;

    private FileWriter fw;

    public SelectionPolicy selectPolicy = SelectionPolicy.SHORTEST_TIME;

    private Scheduler scheduler;
    private List<Task> generatedTask = new ArrayList<>();

    public SimulationManager()
    {
        scheduler = new Scheduler(numberOfServers,numberOfClients);
        scheduler.changeStrategy(selectPolicy);
        generateNRandomTasks();
    }

    public void generateNRandomTasks()
    {
        Random random = new Random();
        for(int i = 0; i < numberOfClients; i++)
        {
            int serviceTime = random.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime;
            int arrivalTime = random.nextInt(10,100);
            int id = i + 1;
            Task randomTask = new Task(id,arrivalTime,serviceTime);
            generatedTask.add(randomTask);
        }
        Collections.sort(generatedTask, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getArrivalTime().compareTo(o2.getArrivalTime());
            }
        });
    }

    @Override
    public void run() {
        int currentTime = 0;
        try {
            fw = new FileWriter("test3.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(currentTime < timeLimit)
        {
            List<Task> tasksRemove = new ArrayList<Task>();
            for(Task x: generatedTask)
            {
                if(x.getArrivalTime() == currentTime)
                {
                    scheduler.dispatchTask(x);
                    tasksRemove.add(x);
                }
            }
            generatedTask.removeAll(tasksRemove);
            try {
                System.out.println("currentTime: " + currentTime);
                fw.write("currentTime: " + currentTime + "\n");
                for (Task y : generatedTask)
                {
                    System.out.println(y.toString());
                    fw.write(y.toString() + "\n");
                }

                int nrQueue = 1;

                for (Server s : scheduler.getServers()) {
                    System.out.print("Queue" + nrQueue + ": ");
                    System.out.print(s.afisare() + "\n");
                    fw.write("Queue" + nrQueue + ": ");
                    fw.write(s.afisare() + "\n");
                    nrQueue++;
                }

            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
