import java.util.ArrayList;

public class Iris {
    private ArrayList<Double> numData;
    private String decision;

    public Iris(ArrayList<Double> numData, String decision) {
        this.numData = numData;
        this.decision = decision;
    }

    public ArrayList<Double> getNumData() { return numData; }
    public String getDecision() {
        return decision;
    }

    public String toString() {
        return "Iris{" +
                "numData=" + numData +
                ", decision='" + decision + '\'' +
                '}';
    }
}
