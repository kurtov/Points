package points;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class Plane implements JSONAware{
    ArrayList<Point> points = new ArrayList<>();
    ArrayList<ArrayList<Long>> matrix = new ArrayList<>();
    int size;
    
    void add(Point point) {
        int index = size++;
        ArrayList<Long> distances = new ArrayList<>(index);
        
        points.add(point);
        point.setIndex(index);
        point.setPlane(this);
        
        for(int i=0; i<index; i++) {
            Point p = points.get(i);
            long distance = point.dist(p);
            
            p.setRadius(index == 1 ? distance : Math.min(p.getRadius(), distance));
            distances.add(distance);
        }
        if(index > 0) {
            point.setRadius(Collections.min(distances));
        }
        
        matrix.add(distances);
    }
    
    ArrayList<Point> getPoints() {
        return points;
    }
    
    ArrayList<Point> getNeighbours(Point point) {
        long neighbourRradius = 2 * point.getRadius();
        int index = point.getIndex();
        ArrayList<Point> neighbours = new ArrayList<>();
        ArrayList<Long> distances;
        
        if(size < 2) {
            return neighbours;
        }
        
        distances = matrix.get(index);
        for(int i = 0; i < index; i++) {
            if(distances.get(i) <= neighbourRradius) {
                neighbours.add(points.get(i));
            }
        }
        for(int i = index+1; i < size; i++) {
            if(matrix.get(i).get(index) <= neighbourRradius) {
                neighbours.add(points.get(i));
            }            
        }
        
        return neighbours;
    }
    
    public String toJSONString(){
        JSONArray array = new JSONArray();
        
        
        Iterator<Point> iter = this.points.iterator();
        while(iter.hasNext()) {
            Point point = iter.next();
            JSONObject obj = new JSONObject();

            obj.put("point", point);
            obj.put("radius", point.getRadius());
            obj.put("neighbours", point.getNeighboursCount());

            array.add(obj);
        }
      
        return array.toString();
    }
}
