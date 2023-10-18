import java.util.ArrayList;

public class Perceptron{
    private ArrayList<Double> weights = new ArrayList<>();
    private ArrayList<Double> bestWeights = new ArrayList<>();
    private double bias;
    private double alpha;
    private String positiveStr;

    public Perceptron(double alpha, int perceptronWeightsNum) {
        this.alpha = alpha;
        bias = 0;
        for (int i = 0; i < perceptronWeightsNum; i++) {
            this.weights.add(Math.random());
        }
    }

    public void trainPerceptron(ArrayList<Iris> trainSet, int erasNum){
        int rightNum = 0;
        int checks = 1;

        double d;
        double y;
        int eraNum=0;
        double bestAccuracy = 0;

        positiveStr = trainSet.get(0).getDecision();
        while (rightNum/checks*100<100 && eraNum<erasNum){
            System.out.println(this);

            eraNum++;
            checks=0;
            rightNum=0;
            for (int i = 0; i < trainSet.size(); i++) {
                //Deciding what is positive and negative; First type from trainSet = 1, Other = 0
                if (trainSet.get(i).getDecision().equals(positiveStr))
                    d=1;
                else
                    d=0;

                //Checking if multiply is bigger then bias
                if (checkMul(trainSet.get(i).getNumData())>bias)
                    y=1;
                else
                    y=0;

                //If perceptron made miss, learn it weights
                if (d!=y){
                    learnWeights(trainSet.get(i).getNumData(), d, y);
                } else {
                    rightNum++;
                }

                checks++;
            }

            //If this iterarion accurcy better than previus better accuracy - change it
            if (rightNum/checks*100>bestAccuracy){
                this.bestWeights = weights;
            }

            System.out.println("Right numbers: " + rightNum + "/" + checks);
            System.out.println("Accuracy is: " + String.format("%.2f", (double)rightNum/checks*100)  + "%");
            System.out.println(this);
        }

        //After 50 era's or reaching 100% accuracy overriding bestWeights
        this.weights = this.bestWeights;

        System.out.println("Era's number is: " + eraNum);
    }

    public double testPerceptron(ArrayList<Iris> testDataList){
        int rightNum = 0;
        int checks = 0;

        double d;
        double y;
        for (Iris iris : testDataList) {
            //Deciding what is positive and negative; Setosa = 1, Versicolor = 0
            if (iris.getDecision().equals(positiveStr))
                d=1;
            else
                d=0;

            //Checking if multiply is bigger then bias
            if (checkMul(iris.getNumData())>bias)
                y=1;
            else
                y=0;

            //If perceptron made miss, learn it weights
            if (d==y)
                rightNum++;
            checks++;
        }
        return rightNum/checks*100;
    }

    public void testPerceptron(Iris iris){
        double d;
        double y;

        //Deciding what is positive and negative; First type from trainSet = 1, Other = 0
        if (iris.getDecision().equals(positiveStr))
            d=1;
        else
            d=0;

        //Checking if multiply is bigger then bias
        if (checkMul(iris.getNumData())>bias)
            y=1;
        else
            y=0;

        System.out.println("Your typed Iris info: " + iris);
        String percStr = y==1 ? positiveStr : "NOT " + positiveStr;
        System.out.println("Perceptron's decision: " + percStr);

        //If perceptron made miss, learn it weights
        if (d==y)
            System.out.println("Perceptron's decision is right");
    }

    private double checkMul(ArrayList<Double> inputVec){
        double tmpsum = 0;
        for (int i = 0; i < inputVec.size(); i++) {
            tmpsum += inputVec.get(i) * weights.get(i);
        }
        return tmpsum;
    }

    private void learnWeights(ArrayList<Double> inputVec, double d, double y) {
        ArrayList<Double> tmpVec = new ArrayList<>();
        ArrayList<Double> newWeights = new ArrayList<>();
        inputVec.forEach(x -> tmpVec.add((d-y) * alpha * x));

        for (int i = 0; i < tmpVec.size(); i++) {
            newWeights.add(tmpVec.get(i) + this.weights.get(i));
        }

        this.weights = newWeights;
        bias += (d-y) * alpha * (-1.0);
        System.out.println("Perceptron learned.");
    }

    @Override
    public String toString() {
        return "Perceptron weights: " + weights.toString() + "; Bias is: " + bias;
    }
}
