package com.kipa.swf2js.file.svg.planar;

public class Edge implements Comparable<Edge> {
    private Point start, end, control;

    public Edge(Point start, Point end) {
        this(start, end, end);
    }

    public Edge(Point start, Point end, Point control) {
        this.start = start;
        this.end = end;
        this.control = control;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public Point getSlanted() {
        return new Point(control.getX() - start.getX(), control.getY() - start.getY());
    }

    public Edge reverse() {
        if (end == control) {
            return new Edge(end, start);
        }
        return new Edge(end, start, control);
    }

    @Override
    public int compareTo(Edge edge) {
        Point thisSlanted = this.getSlanted();
        Point otherSlanted = edge.getSlanted();
        int thisFlag = Integer.compare(thisSlanted.getX(), 0) * 3 + Integer.compare(thisSlanted.getY(), 0) + 4;
        int otherFlag = Integer.compare(otherSlanted.getX(), 0) * 3 + Integer.compare(thisSlanted.getY(), 0) + 4;
        thisFlag = (new int[] {4, 3, 2, 5, 0, 1, 6, 7, 8})[thisFlag];
        otherFlag = (new int[] {4, 3, 2, 5, 0, 1, 6, 7, 8})[otherFlag];
        int res = Integer.compare(thisFlag, otherFlag);
        if (res != 0) {
            return res;
        }
        if (thisFlag == 0 || (thisFlag & 1) != 0) {
            return 0;
        }
        res = Long.compare((long)thisSlanted.getY() * otherSlanted.getX(), (long)otherSlanted.getY() * thisSlanted.getX());
        return thisSlanted.getX() < 0 ? -res : res;
    }

    @Override
    public String toString() {
        if (this.end == this.control) {
            return "M " + this.start + " L " + this.end;
        } else {
            return "M " + this.start + " Q " + this.control + ", " + this.end;
        }
    }

    public String toString(Point lastPoint) {
        if (this.start.equals(lastPoint)) {
            if (this.end == this.control) {
                return "L " + this.end;
            } else {
                return "Q " + this.control + ", " + this.end;
            }
        }
        return this.toString();
    }
}
