package ca.uwaterloo.crysp.acoustictracker.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public static List<float[]> readRawFile(String filePath) {
        List<float[]> results = new ArrayList<float[]>();
        try {
            FileReader fileReader = new FileReader(new File(filePath));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String st;

            while ((st = bufferedReader.readLine()) != null) {
                if (st.substring(0, 2).equals("Le")) {
                    continue;
                }
                String[] rawValues = st.split(",");
                // Create a float array of the same size
                float[] tmpValues = new float[rawValues.length];
                for(int i = 0; i < rawValues.length; ++i) {
                    float tmp = Float.parseFloat(rawValues[i]);
                    tmpValues[i] = tmp;
                }

                results.add(tmpValues);
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}
