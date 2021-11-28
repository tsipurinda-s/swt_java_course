package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void testDistance1(){
        Point p1 = new Point(3,1);
        Point p2 = new Point(6,5);
        double d = p1.distance(p2);

        Assert.assertEquals(d,6.0);
    }

    @Test
    public void testDistance2(){
        Point p1 = new Point(3,1);
        Point p2 = new Point(9,9);
        double d = p1.distance(p2);

        Assert.assertEquals(d,10.0);
    }
}
