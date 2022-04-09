
public abstract class Animal implements Actor
{
    
    private int      age;
    private boolean  alive;
    private Field    field;
    private Location location; 
    public Animal(Field field, Location location)
    {
        age=0;
        alive=true;
        this.field=field;
        this.location=location;
    }
    public boolean isActive()
    {
        return alive;
    }
    public void setLocation(Location loc)
    {
        location=loc;
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     */
    public abstract void act();

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
    }
}
