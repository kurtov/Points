package points;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;

public class Point implements JSONAware{
    private final int x;
    private final int y;
    private long radius;
    private int index;
    private Plane plane;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }
    
    public long getRadius() {
        return radius;
    }

    public void setRadius(long radius) {
        this.radius = radius;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
        
    public ArrayList<Point> getNeighbours() {
        return plane.getNeighbours(this);
        }
    
    long dist(Point p) {
        return (long)Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2));
    }
    
    
    // Представить точку в видет json
    public String toJSONString(){
        JSONArray array = new JSONArray();
        array.add(this.getX());
        array.add(this.getY());
        
        return array.toString();
  }
}