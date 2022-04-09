import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class Field
{    
    private int height, width;
    private Actor[][] field;

    /**
     * Represent a field of the given dimensions.
     * @param height The height of the field.
     * @param width The width of the field.
     */
    public Field(int height, int width)
    {
                this.height=height;
                this.width=width;
        field=new Actor[height][width];
    }

    /** Empty the field */
    public void clear()
    {
        for(int i=0;i<field.length;i++)
        {
            for(int f=0;f<field[i].length;f++)
            {
                field[i][f]=null;
                    }
                }
    }

    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param animal The animal to be placed.
     * @param row Row coordinate of the location.
     * @param col Column coordinate of the location.
     */
    public void place(Actor animal, int row, int col)
    {
        field[row][col]=animal;
    }
    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param animal The animal to be placed.
     * @param location Where to place the animal.
     */
    public void place(Actor animal, Location location)
    {
        place(animal,location.getRow(),location.getCol());
    }
    
    /**
     * Move an Object from one location in the field to another
     * @param oldLocation Object's old location
     * @param newLocation Object's new location
     */
    public void move(Location oldLocation, Location newLocation)
    {
        place(field[oldLocation.getRow()][oldLocation.getCol()],newLocation);
    }
    
    /**
     * Remove an Object from a particular location
     * @param location
     */
    public void remove(Location location)
    {
        field[location.getRow()][location.getCol()]=null;
    }
    
    /**
     * Return the animal at the given location, if any.
     * @param location Where in the field.
     * @return The animal at the given location, or null if there is none.
     */
    public Actor getObjectAt(Location location)
    {
        if(location==null)
        {
            return null;
        }
        return getObjectAt(location.getRow(),location.getCol());
    }

    /**
     * Return the animal at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The animal at the given location, or null if there is none.
     */
    public Actor getObjectAt(int row, int col)
    {
        return field[row][col];
    }

    /**
     * Generate a shuffled List of locations adjacent to the given one - will 
     * not include the location itself. All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A randomized list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location)
    {
        ArrayList<Object>locations=new ArrayList<>();
        int tempRow=location.getRow();
        int tempCol=location.getCol();
        boolean onRow=false,beforeRow=false,afterRow=false;
        for(int i=0;i<field.length;i++)
        {
            for(int f=0;f<field[i].length;f++)
            {
            if(tempRow==i)
            {
                onRow=true;
            }
            else if(tempRow+1==i)
            {
                afterRow=true;
            }
            else if(tempRow-1==i)
            {
                beforeRow=true;
            }
            else
            {
                onRow=false;
                afterRow=false;
                beforeRow=false;
            }
                if(onRow==true&&(f==tempCol-1||f==tempCol+1))
                {
                    locations.add((field[i][f]));
                }
                if(beforeRow==true&&(f==tempCol-1||f==tempCol||f==tempCol+1))
                {
                    locations.add((field[i][f]));
                }
                if(afterRow==true&&(f==tempCol-1||f==tempCol||f==tempCol+1))
                {
                    locations.add((field[i][f]));
                }
                onRow=false;
                beforeRow=false;
                afterRow=false;
            }
        }
        for(int i=0;i<locations.size();i++)
        {
            if(locations.get(i)==(null))
            {
                locations.remove(i);
            }
            else if(locations.get(i).equals(location))
            {
                locations.remove(i);
            }
        }
        Collections.shuffle(locations);
        ArrayList<Location>locationsFinal=new ArrayList<>();
        for(int i=0;i<locations.size();i++)
        {
            Object obj=locations.get(i);
            if(obj instanceof Fox)
            {
                Fox f=(Fox)obj;
                locationsFinal.add((f.getLocation()));
            }
            if(obj instanceof Rabbit)
            {
                Rabbit r=(Rabbit)obj;
                locationsFinal.add((r.getLocation()));
            }
        }
        return locationsFinal;
    }

    /**
     * Try to find a free location that is adjacent to the given location. 
     * If there is none, return null. The returned location will be within 
     * the valid bounds of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid free location within the grid area, or null if all
     *         locations around are full.
     */
    public Location freeAdjacentLocation(Location location)
    {
        ArrayList<Object>locations=new ArrayList<>();
        int tempRow=location.getRow();
        int tempCol=location.getCol();
        boolean onRow=false,beforeRow=false,afterRow=false;
        for(int i=0;i<field.length;i++)
        {
            if(tempRow==i)
            {
                onRow=true;
            }
            else if(tempRow+1==i)
            {
                afterRow=true;
            }
            else if(tempRow-1==i)
            {
                beforeRow=true;
            }
            else
            {
                onRow=false;
            }
            for(int f=0;f<field[i].length;f++)
            {
                if(onRow==true&&(f==tempCol-1||f==tempCol+1))
                {
                    locations.add((field[i][f]));
                }
                if(beforeRow==true&&(f==tempCol-1||f==tempCol||f==tempCol+1))
                {
                    locations.add((field[i][f]));
                }
                if(afterRow==true&&(f==tempCol-1||f==tempCol||f==tempCol+1))
                {
                    locations.add((field[i][f]));
                }
                onRow=false;
                beforeRow=false;
                afterRow=false;
            }
        }
        Location newLoc=null;
        for(int i=0;i<locations.size();i++)
        {
            if(locations.get(i)==(null))
            {
                newLoc=(Location)locations.get(i);
            }
        }
        return newLoc;
    }

    @Override
    public String toString() //quick picture of the state of the field, for debugging
    {
        String result = "";
        
        for (int r = 0; r < this.height; r++)
        {
            for (int c = 0; c < this.width; c++)
            {
                Object obj = this.field[r][c];
                
                if (obj instanceof Rabbit)
                    result += "R\t";
                else if (obj instanceof Fox)
                    result += "F\t";
                else
                    result += "x\t";
            }
            result += "\n";
        }
        
        return result;
    }
    public void setField(Actor[][] fie)
    {
        field=fie;
    }
    public Object[][] getField()
    {
        return field;
    }
    public int getHeight() { return this.height; }
    
    public int getWidth() { return this.width; }
}
