package points;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import vptree.VpTreePoint;

public class Point implements JSONAware, VpTreePoint<Point>{
    private final int x;
    private final int y;
    
    private double radius;
    private int neighboursCount;
    
    private int index;
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
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


    public void setNeighboursCount(int count) {
        this.neighboursCount = count;
    }
    
    public int getNeighboursCount() {
        return this.neighboursCount;
    }
    
    public double distance(Point p) {
        double  res = Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2));;
  
        //Странное дельце: при вычислении расстояния между одними и теми же точками
        //могут получасть разные расстояния (различия в 8 знаке после запятой)
        return Math.round(res*1000)/1000.0d;
    }
    
    
    // Представить точку в видет json
    public String toJSONString(){
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();

        array.add(this.getX());
        array.add(this.getY());
        
        obj.put("point", array);
        obj.put("radius", this.getRadius());
        obj.put("neighbours", this.getNeighboursCount());

        return obj.toString();
  }
}