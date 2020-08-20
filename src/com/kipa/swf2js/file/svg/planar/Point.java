package com.kipa.swf2js.file.svg.planar;

import java.util.Objects;

public class Point {
    private int x, y;

    public Point() {
        this(0, 0);
    }
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Point) {
            Point anotherPoint = (Point)o;
            return this.x == anotherPoint.x && this.y == anotherPoint.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
