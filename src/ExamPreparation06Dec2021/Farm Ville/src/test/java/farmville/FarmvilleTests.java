package farmville;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FarmvilleTests {
    //TODO: TEST ALL THE FUNCTIONALITY OF THE PROVIDED CLASS Farm

    private Farm smallCapacityFarm;
    private Farm bigCapacityFarm;
    private List<Animal> animals;


    @Before
    public void setUp() {
        smallCapacityFarm = new Farm("MyFarm", 3);
        smallCapacityFarm.add(new Animal("Cow", 100));
        smallCapacityFarm.add(new Animal("Horse", 100));
        smallCapacityFarm.add(new Animal("Pig", 100));
        bigCapacityFarm = new Farm("BigFarm", 100);
        animals = new ArrayList<>();
    }

    @Test
    public void testAddAnimalCorrectly() {
        Animal sheep = new Animal("Sheep", 55.5);
        bigCapacityFarm.add(sheep);
        String actual = sheep.getType();
        animals.add(sheep);
        Animal expected = animals.get(0);
        assertEquals(expected.getType(), actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddThrowsExceptionForAnimalBeingFull() {
        smallCapacityFarm.add(new Animal("Goat", 50));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddThrowsExceptionForAnimalExisting() {
        bigCapacityFarm = new Farm("NewFarm", 5);
        Animal first = new Animal("Cow", 5);
        Animal second = new Animal("Cow", 5);
        bigCapacityFarm.add(first);
        bigCapacityFarm.add(second);
        assertEquals(first.getType(), second.getType());
    }

    @Test
    public void testRemoveAnimalCorrectly() {
        Animal rabbit = new Animal("Rabbit", 250);
        Animal cow = new Animal("Cow", 250);

        bigCapacityFarm.add(rabbit);
        bigCapacityFarm.add(cow);
        animals.add(rabbit);
        animals.add(cow);
        int count = bigCapacityFarm.getCount();
        assertEquals(2, count);

        boolean remove = bigCapacityFarm.remove("Rabbit");
        assertTrue(remove);

        animals.remove(rabbit);
        count = bigCapacityFarm.getCount();

        assertEquals(1, count);
        assertNotEquals(cow.getType(), rabbit.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCapacityThrowsExceptionForBeingLessThanZero() {
        new Farm("NegativeCapacity", -1);

    }

    @Test(expected = NullPointerException.class)
    public void testSetNameThrowsExceptionIsEmpty() {
        new Farm(" ", 100);
    }

    @Test(expected = NullPointerException.class)
    public void testSetNameThrowsExceptionIsNull() {
        new Farm(null, 100);
    }

    @Test
    public void testGetNameCorrect() {
        String expected = bigCapacityFarm.getName();
        assertEquals(expected, "BigFarm");
    }

    @Test
    public void testGetEnergyReturnsCorrect() {
        Animal animal = new Animal("Horse", 50);
        long actual = (long) animal.getEnergy();
        assertEquals(50L, actual);
    }
}
