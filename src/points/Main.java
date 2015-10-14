package points;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

public class Main {
    public static final String DEFAULT_INPUT_FILE = "input/default_data.json";
    private static final String DEFAULT_OUTPUT_FILE = "result.json";

    public static void main(String[] args) throws FileNotFoundException {
        Plane plane = new Plane();
        BufferedReader in = null;
        PrintWriter out = null;
        String inputFileName;
        String outputFileName;
        JSONArray array = null;
        
       
        if(args.length == 2) {
            inputFileName = args[0];
            outputFileName = args[1];
        } else {
            inputFileName = DEFAULT_INPUT_FILE;
            outputFileName = DEFAULT_OUTPUT_FILE;
        }
        
        //Считать из входного файла данные в массив array
        try {
            in = new BufferedReader(new FileReader(inputFileName));
            array = (JSONArray)JSONValue.parse(new BufferedReader(new FileReader(inputFileName)));
            in.close();
        }
        catch (IOException e) {
            try {
                if(in != null) {
                    in.close();
                }
            }
            catch (IOException e2) {}
            finally {
                System.err.println("IO Exception with input file");
                System.exit(1);
            }
        }
        
        Iterator<JSONArray> arrIter = array.iterator();
        while(arrIter.hasNext()) {
            JSONArray pointArray = arrIter.next();
           
            plane.add( new Point(
                (int)(long)pointArray.get(0),
                (int)(long)pointArray.get(1)
            ));
        }

        //Записать результаты работы в выходной файл
        try {
            out = new PrintWriter(outputFileName);
            out.println(plane.toJSONString());
        }
        catch (IOException e) {
            System.err.println("IO Exception with output file");
        }
        finally {
            if(out != null) {
                out.close();
            }
        }
    }
}