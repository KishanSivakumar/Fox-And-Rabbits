import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a field containing rabbits and foxes.
 */
public class Simulator
{
    /* Constants representing configuration information for the simulation. */

    private static final int    DEFAULT_HEIGHT = 50; //size of field by default
    private static final int    DEFAULT_WIDTH  = 50; 
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;    

    /* Instance variables */

    private Field field;
    private int   step;
    
    private SimulatorView view; //graphical view of the Simulator, don't mess with this

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_HEIGHT,DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param height Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int height, int width)
    {
        step=0;
        field=new Field(height,width);
        // Create a view of the state of each location in the field, don't mess with this
        view = new SimulatorView(height, width);
        view.setColor(Rabbit.class, Color.orange);
        view.setColor(Fox.class, Color.blue);

        // Setup a valid starting point, don't mess with this
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * e.g. 500 steps.
     */
    public void runLongSimulation()
    {
        simulate(500);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for (int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        try { Thread.sleep(100); } catch (Exception e) {} // Slow it down a bit, don't remove

        step++;
        for(int i=0;i<field.getHeight();i++)
        {
            for(int h=0;h<field.getWidth();h++)
            {
                Actor animal=field.getObjectAt(i,h);
                if(animal!=null)
                {
                    animal.act();
                }
                
            }
            
        }
        
        view.showStatus(step, field); // Display the new field on screen, don't remove
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        
        field.clear();
        
        this.populate();

        // Show the starting state in the view
        view.showStatus(step, field);
    }

    /**
     * Populate a field with foxes and rabbits.
     */
    private void populate()
    {
        field.clear();
        boolean probFox,probRab;
        Object[][] fieldDup=field.getField();
        boolean foxAdded=false;
        for(int i=0;i<fieldDup.length;i++)
        {
            for(int f=0;f<fieldDup[i].length;f++)
            {
                probFox=new Random().nextInt(50)==0;
                if(probFox)
                {
                    field.place(new Fox(field,new Location(i,f)),new Location(i,f));
                }
            }
        }
        for(int i=0;i<fieldDup.length;i++)
        {
            for(int f=0;f<fieldDup[i].length;f++)
            {
                probRab=new Random().nextInt(13)==0;
                //probRab=new Random().nextDouble()*(100/(RABBIT_CREATION_PROBABILITY*100))==0;
                if(probRab&&!(field.getObjectAt(i,f) instanceof Fox))
                {
                    field.place(new Rabbit(field,new Location(i,f)),new Location(i,f));
                }
            }
        }
    }
}
