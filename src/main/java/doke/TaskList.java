package doke;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class to represent a TaskList.
 */
public class TaskList {

    private ArrayList<Task> taskList = new ArrayList<>();

    /**
     * A public constructor for the TaskList class.
     * It will try to read and create and ArrayList of type task from storage,
     * which, if not possible would then create an empty ArrayList of type Task.
     *
     * @param ui
     * @param storage
     */
    public TaskList(Ui ui, Storage storage) {

        try {
            Scanner s = new Scanner(Storage.dokeFile);
            while (s.hasNext()) {
                String line = s.nextLine();
                String specialString = " [|] ";
                String[] temp = line.split(specialString);
                Task addTask;

                if (temp[0].equals("T")) {
                    addTask = new ToDo(temp[2]);
                } else if (temp[0].equals("D")) {
                    addTask = new Deadline(temp[2], temp[3]);
                } else {
                    if (!temp[0].equals("E")) {
                        continue;
                    }
                    addTask = new Events(temp[2], temp[3]);
                }
                this.taskList.add(addTask);

                try {
                    if (temp[1].equals("1")) {
                        addTask.markDone();
                    } else {
                        addTask.markNotDone();
                    }
                } catch (DokeException e) {
                }
            }
        } catch (FileNotFoundException e) {
            try {
                storage.dokeFile.createNewFile();
                ui.printOut("a new Doke.txt file has been created" +
                        "it is in the path mentioned above");
            } catch (IOException a) {
                ui.printOut("An error occurred. Try again at another time.");
            }
        }
    }

    /**
     * Sends order to ui to print out the whole content of the ArrayList.
     *
     * @param ui
     */
    public void listOut(Ui ui) {
        String message;
        if (this.taskList.isEmpty()) {
            message = "_________________________ \n" + "You have no task! \n" +
            "_________________________";
             ui.printOut(message);
             return;
        }
        int len = this.taskList.size();
        int i=0;
        message = "_________________________ \n";
        while (i < len) {
            message += (i + 1) + "." + this.taskList.get(i).toString() + "\n";
            i++;
        }
        message += "_________________________ \n";
        ui.printOut(message);
    }

    /**
     * Deletes the ith task from the ArrayList.
     *
     * @param i the position + 1 of the task to delete
     */
    public void delete(int i) {
        this.taskList.remove(i - 1);
    }

    /**
     * Adds a task to the ArrayList.
     *
     * @param task the task to be added.
     */
    public void add(Task task) {
        this.taskList.add(task);
    }

    /**
     * Returns the Task at the position i-1 in the ArrayList.
     *
     * @param i the position + 1 of the task wanted.
     * @return the task desired.
     */
    public Task get(int i) {
        return taskList.get(i-1);
    }

    /**
     * Returns a copy of the ArrayList.
     *
     * @return a copy of the ArrayList.
     */
    public ArrayList<Task> getList() {
        ArrayList<Task> temp =  this.taskList;
        return temp;
    }

    /**
     * Returns the size of the ArrayList.
     *
     * @return the size of the ArrayList.
     */
    public int getSize() {
        return taskList.size();
    }

}
