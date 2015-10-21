package points;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Plane {
    ArrayList<Point> points = new ArrayList<>();
    ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
    int size;
    
    void add(ArrayList<Point> points) {
        this.points = points;
        
        Iterator<Point> iter = points.iterator();
        while(iter.hasNext()) {
            Point point = iter.next();
            int index = size++;
            ArrayList<Double> distances = new ArrayList<>(index);

            point.setIndex(index);

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
    }
   
    void calcNeighboursCount() {
        
        Iterator<Point> iter = points.iterator();
        while(iter.hasNext()) {
            Point point = iter.next();
            double neighbourRradius = 2 * point.getRadius();
            int index = point.getIndex();
            int neighboursCount = 0;
            ArrayList<Double> distances;

            if(size < 2) {
                point.setNeighboursCount(neighboursCount);
                continue;
            }

            //Что бы найти все расстояния от точки с индексом index
            //надо в строке index пройти все элементы с 0 по index-1
            //затем в столбце index пройти все элементы с index+1 до конца
            distances = matrix.get(index);
            for(int i = 0; i < index; i++) {
                if(distances.get(i) <= neighbourRradius) {
                    neighboursCount++;
                }
            }
            for(int i = index+1; i < size; i++) {
                if(matrix.get(i).get(index) <= neighbourRradius) {
                    neighboursCount++;
                }            
            }

            point.setNeighboursCount(neighboursCount);
        }
    }
}