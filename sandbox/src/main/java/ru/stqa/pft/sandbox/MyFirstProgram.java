package ru.stqa.pft.sandbox;

public class MyFirstProgram {

    public static void main(String[] args) {

        System.out.println("Hello, world!");

        Point p1 = new Point(3,1);
        Point p2 = new Point(6,5);

        System.out.println("Заданы точки: p1(" + p1.x + "," + p1.y + "); p2(" + p2.x + "," + p2.y + ")");
        System.out.println("Расстояние между двумя заданными точками: " + Point.distance(p1,p2));

    }

}