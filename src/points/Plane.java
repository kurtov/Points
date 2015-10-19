package points;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class Plane implements JSONAware, Iterable{
    ArrayList<Point> points = new ArrayList<>();
    ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
    int size;
    
    void add(Point point) {
        int index = size++;
        ArrayList<Double> distances = new ArrayList<>(index);
        
        points.add(point);
        point.setIndex(index);
        point.setPlane(this);
        
        for(int i=0; i<index; i++) {
            Point p = points.get(i);
            double distance = point.distance(p);
            
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
        double neighbourRradius = 2 * point.getRadius();
        int index = point.getIndex();
        ArrayList<Point> neighbours = new ArrayList<>();
        ArrayList<Double> distances;
        
        if(size < 2) {
            return neighbours;
        }
        
        //Что бы найти все расстояния от точки с индексом index
        //надо в строке index пройти все элементы с 0 по index-1
        //затем в столбце index пройти все элементы с index+1 до конца
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
        
        
        Iterator<Point> iter = this.iterator();
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

    public Iterator iterator() {
        return this.points.iterator();
    }
}
