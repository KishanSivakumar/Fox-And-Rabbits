import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fox extends Animal
{
    /* Characteristics shared by all foxes (static fields). */

    private static final int    BREEDING_AGE = 10;
    private static final int    MAX_AGE = 20;
    private static final double BREEDING_PROBABILITY = 0.17;
    private static final int    MAX_LITTER_SIZE = 4;
    private static final int    RABBIT_FOOD_VALUE = 8;

    /* Individual characteristics (instance fields). */

    private int      age;
    private boolean  alive;
    private Field    field;
    private Location location;
    private int      foodLevel;

    /**
     * Create a fox. A fox is created as a new born (age zero
     * and not hungry).
     */
    public Fox(Field field, Location location)
    {
        super(field,location);
        foodLevel=RABBIT_FOOD_VALUE;
        this.field=field;
        this.location=location;
    }
    public void setDead()
    {
        alive=false;
        field.remove(location);
    }
    public boolean isActive()
    {
        return alive;
    }
    public void remove()
    {
        alive=false;
    }
    /**
     * This is what the fox does most of the time: it hunts for rabbits. 
     * In the process, it might breed, die of hunger, or die of old age.
     */
    public void act()
    {
        foodLevel--;
        if(foodLevel<0)
        {
            alive=false;
        }
        Location loc=findFood();
        Location newSpot=field.freeAdjacentLocation(location);
        if(loc!=null)
        {
            field.move(location,loc);
        }
        else if(newSpot!=null)
        {
            field.move(location,newSpot);
        }
        else
        {
            alive=false;
        }
    }

    /**
     * Increase the age. This could result in the fox's death.
     */

    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel<0)
        {
            alive=false;
        }
    }
    /**
     * Tell the fox to look for rabbits adjacent to its current location.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        List<Location> adjacentLocations = field.adjacentLocations(this.location);

        for (Location where : adjacentLocations) //look for Rabbits in adjacent locations
        {
            Object animal = field.getObjectAt(where);

            if (animal instanceof Rabbit) { //if this object is a rabbit...
                Rabbit rabbit = (Rabbit) animal;

                rabbit.setDead();

                this.foodLevel = Fox.RABBIT_FOOD_VALUE; //Foxes goes back to full

                return where;
            }
        }
        return null;
    }

    /**
     * Generate a number representing the number of births, if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        boolean prob=new Random().nextInt((int)(100/(BREEDING_PROBABILITY*100)))==0;
        if(age<BREEDING_AGE||prob==false)
        
            return 0;
        return (int)((Math.random()*MAX_LITTER_SIZE)+1);
        }
    

    /**
     * A rabbit can breed if it has reached breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    private boolean canBreed()
    {
        if(age>=BREEDING_AGE)
        {
            return true;
        }
        
        return false; //replace
    }
}
