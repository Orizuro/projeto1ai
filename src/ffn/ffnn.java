package ffn;
import java.util.Arrays;
import java.util.Random;
//1. Initialization
//Create Network: Function to initialize a new neural network with specified parameters such as number of layers, number of neurons per layer, activation functions, etc.
//2. Configuration
//Set Parameters: Functions to set various hyperparameters like learning rate, number of epochs, batch size, etc.
//Compile Network: Prepare the network for training by setting the loss function, optimizer, and other necessary configurations.
//3. Training
//Fit: Function to train the model on provided training data. It should accept training inputs and target outputs, and optionally validation data.
//4. Evaluation
//Evaluate: Function to test the model on a separate set of data to determine its accuracy or other performance metrics.
//5. Prediction
//Predict: Function to use the trained model to make predictions on new, unseen data.
//6. Save/Load Model
//Save Model: Function to save the model's architecture and weights.
//Load Model: Function to load a saved model.
public class ffnn {
    private final int inputDimension;
    private int hiddenDimension;
    private int outputDimension;
    private double[][] hiddenWeights;
    private double[] hiddenBiases;
    private double[][] outputWeights;
    private double[] outputBiases;

    double randomLeftBoundary = -1;
    double randomRightBoundary = 1;


    public double randomValue() {
        return randomLeftBoundary + ( randomRightBoundary - randomLeftBoundary) * new Random().nextDouble();
    }

    public ffnn(int inputDimension, int hiddenDimension, int outputDimension) {
        this.inputDimension = inputDimension;
        this.hiddenDimension = hiddenDimension;
        this.outputDimension = outputDimension;
        initializeWithRandomValues();
    }

    public void initializeWithRandomValues(){
        this.hiddenWeights = new double[inputDimension][hiddenDimension];
        this.outputWeights = new double[hiddenDimension][outputDimension];

        this.hiddenBiases = new double[hiddenDimension];
        this.outputBiases = new double[outputDimension];

        for( int i = 0 ; i<inputDimension; i++){
            for(int j = 0; j < hiddenDimension; j++){

                this.hiddenWeights[i][j] =  randomValue();
            }
        }
        for( int i = 0 ; i<hiddenDimension; i++){
            for(int j = 0; j < outputDimension; j++){

                this.outputWeights[i][j] =  randomValue();
            }
        }
        for(int i = 0; i<hiddenDimension; i++){
            this.hiddenBiases[i] = randomValue();
        }
        for(int i = 0; i<outputDimension; i++){
            this.outputBiases[i] = randomValue();
        }
        /*
        System.out.println(Arrays.deepToString(hiddenWeights));
        System.out.println(Arrays.deepToString(outputWeights));
        System.out.println(Arrays.toString(hiddenBiases));
        System.out.println(Arrays.toString(outputBiases));
        */

    }

    @Override
    public String toString() {
        return Arrays.deepToString(this.hiddenWeights) + "\n" + Arrays.deepToString(this.outputWeights) + "\n"+ Arrays.toString(this.hiddenBiases) + "\n" +  Arrays.toString(this.outputBiases);
    }

    public static ffnn fromString(String s) {
        String[] parts = s.split("\n");
        double[][] hiddenWeights = parseWeights(parts[0]);
        double[][] outputWeights = parseWeights(parts[1]);
        double[] hiddenBiases = parseBiases(parts[2]);
        double[] outputBiases = parseBiases(parts[3]);

        ffnn network = new ffnn(hiddenWeights[0].length, hiddenWeights.length, outputWeights.length);
        network.hiddenWeights = hiddenWeights;
        network.outputWeights = outputWeights;
        network.hiddenBiases = hiddenBiases;
        network.outputBiases = outputBiases;
        return network;
    }

    private static double[][] parseWeights(String matrixString) {
        String[] rows = matrixString.replace("[[", "").replace("]]", "").split("\\], \\[");
        double[][] matrix = new double[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] values = rows[i].split(", ");
            matrix[i] = new double[values.length];
            for (int j = 0; j < values.length; j++) {
                matrix[i][j] = Double.parseDouble(values[j]);
            }
        }
        return matrix;
    }

    private static double[] parseBiases(String arrayString) {
        String[] values = arrayString.replace("[", "").replace("]", "").split(", ");
        double[] array = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            array[i] = Double.parseDouble(values[i]);
        }
        return array;
    }

}