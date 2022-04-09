import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Rabbit extends Animal
{
    /* Characteristics shared by all rabbits (static fields). */

    private static final int    BREEDING_AGE = 5;
    private static final int    MAX_AGE = 15;
    private static final double BREEDING_PROBABILITY = 0.22;
    private static final int    MAX_LITTER_SIZE = 6;

    /* Individual characteristics (instance fields). */

    private int      age;
    private boolean  alive;
    private Field    field;
    private Location location; //stores a reference to this Rabbit's current location

    /**
     * Create a new rabbit. A rabbit is created with age zero (a new born).
     */
    public Rabbit(Field field, Location location)
    {
        super(field,location);
        age=0;
        alive=true;
        this.field=field;
        this.location=location;
    }
    public void remove()
    {
        alive=false;
    }

    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     */
    public void act()
    {
        incrementAge();
        if(alive==false)
        {
            return;
        }
        int numBabies=breed();
        for(int i=0;i<numBabies;i++)
        {
            Location loc=(field.freeAdjacentLocation(location));
            if(loc!=null)
            {
                field.place((Actor) new Rabbit(field,loc),loc);
            }
        }
            Location loc1=field.freeAdjacentLocation(location);
            if(loc1!=null)
            {
                field.move(location,loc1);
            }
            else
            {
                alive=false;
            }
    }
    /**
     * Increase the Rabbit's age.
     * This could result in the rabbit's death.
     */
    private void incrementAge()
    {
        age++;
        if(age>MAX_AGE)
        {
            setDead();
        }
    }

    /**
     * Generate a number representing the number of births, if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        boolean prob=new Random().nextInt((int)(100/(BREEDING_PROBABILITY*100)))==0;
        if(age<BREEDING_AGE||prob==false)
        {
            return 0;
        }
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
    public boolean isAlive()
    {
        return alive;
    }
    public String toString()
    {
        return "Rabbit, " + age + " y/o, at " + location;
    }
}
