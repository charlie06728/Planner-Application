package Gateway;

import Interface.IGateWay;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Data reader gateway.
 */
public class Reader implements IGateWay {

    /**
     * Store an object into a .ser file.
     * @param filePath A String representing the file path you want to store.
     * @param obj The Object you want to store
     * @return A boolean value representing whether the process is successful or not.
     */
    @Override
    public boolean writeSer(String filePath, Object obj) {
        File nf = new File(filePath);
        try {
            if (nf.createNewFile() || nf.delete()) {
                FileOutputStream fileOut = new FileOutputStream(new File(filePath));
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(obj);
                objectOut.close();
                return true;
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Read .ser file into an object of given type T.
     * @param filePath A String representing the file path you want to store.
     * @return A Object of object type T, return null if failed to load in file.
     */
    @Override
    public Object readSer(String filePath) {
        File nf = new File(filePath);
        try {
            if (nf.createNewFile() && nf.delete()) {
                return false;
            }
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            objectIn.close();
            return obj;
        } catch (Exception ex) {
            System.out.println("Please check if the casting type is the correct type ");
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Read .csv file into an two dimensional ArrayList.
     * @param filePath A String representing the file path you want to store.
     * @return A two dimensional ArrayList containing String.
     */
    @Override
    public ArrayList<ArrayList<String>> readCSV(String filePath) {
        try {
            // Read in the data from csv file.
            Scanner scanner = new Scanner(new FileInputStream(filePath));

            // read in the data.
            String row;
            ArrayList<ArrayList<String >> data = new ArrayList<ArrayList<String>>();

            while (scanner.hasNext()) {
                row = scanner.nextLine();
                String[] curr = row.split(",");
                data.add(new ArrayList<>(Arrays.asList(row.split(""))));
            }
            scanner.close();
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Store the given data into a .csv file.
     * @param filePath A String representing the file path you want to store.
     * @param header An ArrayList representing the header of the csv file we want to set.
     * @param data A two dimensional ArrayList containing the body of the csv file.
     * @return A boolean value representing whether the process is successful or not.
     */
    @Override
    public boolean writeCSV(String filePath, ArrayList<String> header,
                            ArrayList<ArrayList<String>> data) {
        // read in the csv file into data attribute.
        File file = new File(filePath);
        try {
            if (file.createNewFile() || file.delete()) {
                // The file hasn't already be created before this execution.
                // Initialize the csv file.
                FileWriter csvFile = new FileWriter(file);
                int i = 0;
                for (; i < header.size() - 1; i++){
                    csvFile.append(header.get(i)).append(",");
                }
                csvFile.append(header.get(i)).append("\n");
                csvFile.flush();
                csvFile.close();
                return true;
            }

        } catch (Exception e) {
            System.out.println("Process terminated.");
            e.printStackTrace();
            return false;
        }
        return false;
    }
}