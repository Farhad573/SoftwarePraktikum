package model;

import java.io.FileNotFoundException;
/**
 * The FileReader interface provides functionality to read a CSV file.
 * Implementing classes should define the specific behavior for reading a CSV file.
 */
public interface FileReader {

    /**
     * Reads a CSV file with the specified file name.
     *
     * @param csvFileName the name of the CSV file to be read
     * @throws FileNotFoundException if the CSV file is not found
     */
    void readCSVFile(String csvFileName) throws FileNotFoundException;
}
