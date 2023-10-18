import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Iris> trainDataList = fileToList("./Train-set.txt");
        ArrayList<Iris> testDataList = fileToList("./Test-set.txt");

        Perceptron perceptron = new Perceptron(Double.parseDouble(args[0]), trainDataList.get(0).getNumData().size());

        perceptron.trainPerceptron(trainDataList, 50);
        System.out.println("Test accuracy is: " + String.format("%.2f", (double)perceptron.testPerceptron(testDataList))  + "%");

        userInput(perceptron);
    }

    //Input part which waits until user give new info or 0 to exit
    public static void userInput(Perceptron perceptron){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write new iris info \nWrite 0 if you want stop program");
        String input = scanner.nextLine();
        while (!input.equals("0")){
            Iris is = inputConvert(input);
            perceptron.testPerceptron(is);
            System.out.println("Write new iris info \nWrite 0 if you want stop program");
            input = scanner.nextLine();
        }
    }

    //Making from user input line Object Iris
    public static Iris inputConvert(String info){
        String[] tmpLine = info.split(",");
        ArrayList<Double> tmpNums = new ArrayList<>();

        for (int i=0; i< tmpLine.length-1; i++){
            tmpNums.add(Double.parseDouble(tmpLine[i]));
        }

        return new Iris(tmpNums,tmpLine[tmpLine.length-1]);
    }

    //Making from path of file list of lines from file
    public static ArrayList<Iris> fileToList(String path){
        ArrayList<String> rawData = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                rawData.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Iris> dataList = new ArrayList<>();

        for (String datum : rawData) {
            String[] tmpLine = datum.split(",");
            ArrayList<Double> tmpNums = new ArrayList<>();

            for (int i=0; i< tmpLine.length-1; i++){
                tmpNums.add(Double.parseDouble(tmpLine[i]));
            }

            dataList.add(new Iris(tmpNums, tmpLine[tmpLine.length-1]));
        }

        return dataList;
    }
}