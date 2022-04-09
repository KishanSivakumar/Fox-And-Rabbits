import java.util.*;


public class Hunter implements Actor
{
    private int MAX_AGE=89;
    private int      age;
    private boolean  alive;
    private Field    field;
    private Location location; //stores a reference to this Rabbit's current location

    /**
     * Create a new rabbit. A rabbit is created with age zero (a new born).
     */
    public Hunter(Field field, Location location)
    {
        age=0;
        alive=true;
        this.field=field;
        this.location=location;
    }
    public void remove()
    {
        alive=false;
    }
    public boolean isActive()
    {
        return alive;
    }
    public void setLocation(Location loc)
    {
        location=loc;
    }
    public void incrementAge()
    {
        age++;
        if(age>MAX_AGE)
        {
            alive=false;
        }
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
        List<Location> adjacentLocations = field.adjacentLocations(this.location);
        for (Location where : adjacentLocations) //look for Rabbits in adjacent locations
        {
            Actor animal = field.getObjectAt(where);

            if (animal instanceof Rabbit||animal instanceof Fox) { //if this object is a rabbit...
                Actor anim = (Actor) animal;

                anim.setDead();
            }
        }
    }

    /** Method called when a Rabbit becomes deceased */
    public void setDead()
    {
        alive=false;
        field.remove(location);
    }
    public Location getLocation()
    {
        return location;
    }
    public Field getField()
    {
        return field;
    }

    
    public boolean isAlive()
    {
        return alive;
    }
    public String toString()
    {
        return "Rabbit, " + age + " y/o, at " + location;
    }}
