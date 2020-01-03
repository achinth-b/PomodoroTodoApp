package utility;

import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import parsers.TaskParser;
import persistence.Jsonifier;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;

// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");


    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() {

        try {
            Scanner scanner = new Scanner(jsonDataFile);
            String input = "";
            while (scanner.hasNext()) {
                input += scanner.useDelimiter("\\Z").next();
            }
            List<Task> output = new TaskParser().parse(input);
            return output;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {
       // use bufferedWriter;
        try {
            JSONArray arrayTasks = Jsonifier.taskListToJson(tasks);
            FileWriter tobeSaved = new FileWriter("./resources/json/tasks.json");
            PrintWriter printWriter = new PrintWriter((tobeSaved));
            printWriter.write(arrayTasks.toString());
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}

