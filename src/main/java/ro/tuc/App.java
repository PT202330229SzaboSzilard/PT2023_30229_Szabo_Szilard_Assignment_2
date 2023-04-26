package ro.tuc;

import ro.tuc.logic.SimulationManager;

public class App 
{
    public static void main( String[] args )
    {
        SimulationManager simulation = new SimulationManager();
        Thread t = new Thread(simulation);
        t.start();
    }
}
