package LinearRegression.code;

import java.io.*;
import java.util.ArrayList;

public class Main {
    static ArrayList<ArrayList<Double>> features;
    static ArrayList<Double> target;

    public static void main(String[] args) throws IOException{
        features = new ArrayList<>();
        target = new ArrayList<>();
        getData();
        ArrayList<ArrayList<Double>> results = GradientDescent.computeTheta(features, target, 0.01, 10000);
        BufferedWriter writer = new BufferedWriter(new FileWriter("src\\LinearRegression\\code\\res.txt"));
        for(ArrayList<Double> result:results){
            String out = result.toString();
            writer.write(out.substring(1,out.length()-1)+"\n");
        }
        writer.flush();
        writer = new BufferedWriter(new FileWriter("src\\LinearRegression\\code\\featuresMeanDev.txt"));
        for(int i=0;i<features.get(0).size()-1;i++){
            writer.write(GradientDescent.FeaturesMean[i]+","+GradientDescent.FeaturesDeviation[i]+"\n");
        }
        writer.flush();
        writer.close();
    }

    public static void getData() throws IOException {
        BufferedReader inp = new BufferedReader(new FileReader("src\\LinearRegression\\code\\data2.txt"));
        String line=inp.readLine();
        String[] temp;
        do{
            temp=line.split(",");
            ArrayList<Double> x=new ArrayList<>();
            x.add(1.0);
            for(int i=0;i<temp.length-1;i++){
                x.add(Double.parseDouble(temp[i]));
            }
            target.add(Double.parseDouble(temp[temp.length-1]));
            line = inp.readLine();
            features.add(x);
        }while(line!=null);
    }
}
