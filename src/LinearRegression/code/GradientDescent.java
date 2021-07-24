package LinearRegression.code;

import java.util.ArrayList;

public class GradientDescent {

    public static double[] FeaturesMean,FeaturesDeviation;
    public static double computeCost(ArrayList<ArrayList<Double>> x,ArrayList<Double> y, double[] theta){
        double cost = 0.0;
        int m = x.size();
        for(int i=0;i<m;i++){
            cost = cost + Math.pow(linearRegressionError(x.get(i),y.get(i),theta),2);
        }
        return cost/(double)(2*m);
    }

    public static ArrayList<ArrayList<Double>> computeTheta(ArrayList<ArrayList<Double>> x, ArrayList<Double> y, double learningRate, int totIterations){
        int m = x.size(),n = x.get(0).size();
        featureNormalize(x);
        double[] sum;
        double[] theta = new double[n];
        ArrayList<ArrayList<Double>> res = new ArrayList<>();
        for(int iter=0;iter<totIterations;iter++){
            sum = new double[n];
            for(int i=0;i<n;i++){
                for(int sample=0;sample<m;sample++){
                    sum[i] = sum[i]+x.get(sample).get(i)*linearRegressionError(x.get(sample),y.get(sample),theta);
                }
                sum[i]=sum[i]*learningRate/m;
            }
            ArrayList<Double> temp = new ArrayList<>();
            for(int i=0;i<n;i++){
                theta[i] = theta[i]-sum[i];
                temp.add(theta[i]);
            }
            temp.add(computeCost(x,y,theta));
            res.add(temp);
        }
        return res;
    }

    public static void featureNormalize(ArrayList<ArrayList<Double>> features){
        int m = features.size(),n=features.get(0).size();
        double[] mean = new double[n-1];
        double[] deviation = new double[n-1];
        for (ArrayList<Double> feature : features) {
            for (int j = 1; j < n; j++) {
                mean[j - 1] = mean[j - 1] + feature.get(j);
            }
        }

        for(int i=0;i<n-1;i++)
            mean[i]=mean[i]/m;
        for (ArrayList<Double> feature : features) {
            for (int j = 1; j < n; j++) {
                deviation[j - 1] = deviation[j - 1] + Math.pow(feature.get(j) - mean[j - 1], 2.0);
            }
        }
        for(int i=0;i<n-1;i++)
            deviation[i]=Math.sqrt(deviation[i]/(m-1));
        //deviation[0] = 794.7024;
        //deviation[1] = 0.7610;
        FeaturesMean = mean;
        FeaturesDeviation = deviation;
        for(int i=0;i<m;i++){
            ArrayList<Double> normalizedFeature = new ArrayList<>();
            normalizedFeature.add(1.0);
            for(int j=1;j<n;j++){
                normalizedFeature.add((features.get(i).get(j)-mean[j-1])/deviation[j-1]);
            }
            features.set(i,normalizedFeature);
        }
    }

    private static double linearRegressionError(ArrayList<Double> features,double target, double[] theta){
        double error = 0.0;
        int size = features.size();
        for(int i=0;i<size;i++){
            error = error + features.get(i)*theta[i];
        }
        return error-target;
    }
}
