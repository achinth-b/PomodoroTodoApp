package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo> {
    private String description;
    private ArrayList<Todo> tasks;

    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        super.etcHours = 0;
        this.description = description;
        tasks = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    public void add(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Task cannot be null");
        }
        if (!tasks.contains(task)) {
            if (!task.equals(this)) {
                tasks.add(task);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Task cannot be null");
        }
        if (tasks.contains(task)) {
            tasks.remove(task);
        }
    }

    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completion (rounded down to the nearest integer).
    //     the value returned is the average of the percentage of completion of
    //     all the tasks and sub-projects in this project.
    public int getProgress() {
        int size = this.getNumberOfTasks();
        int totalProgress = 0;

        for (Todo t : tasks) {
            totalProgress += t.getProgress();

        }

        if (size == 0) {
            return 0;
        }

        return totalProgress / size;
    }


    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
//     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }

    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }


    //EFFECTS: returns the estimated time of completion of all the
    // todos of this test project (and all the sub-todos and so on)
    @Override
    public int getEstimatedTimeToComplete() {

        int totalTime = 0;
        for (Todo t : tasks) {
            totalTime += t.getEstimatedTimeToComplete();
        }

        return totalTime;

    }

    //MODIFIES: this
    //EFFECTS: returns an iterator object for the list of todos of a project
    public Iterator<Todo> iterator() {
        return new ProjectIterator();
    }

    class ProjectIterator implements Iterator<Todo> {
        private Iterator<Todo> priorityOneIterator;
        private Iterator<Todo> priorityTwoIterator;
        private Iterator<Todo> priorityThreeIterator;
        private Iterator<Todo> priorityFourIterator;
        private Iterator<Todo> counter;


        public ProjectIterator() {
            priorityOneIterator = tasks.iterator();
            priorityTwoIterator = tasks.iterator();
            priorityThreeIterator = tasks.iterator();
            priorityFourIterator = tasks.iterator();
            counter = tasks.iterator();
        }

        @Override
        public boolean hasNext() {
            return counter.hasNext();
        }

        @Override
        public Todo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            if (priorityOneIterator.hasNext()) {
                return returnPriorityOne();
            } else if (priorityTwoIterator.hasNext()) {
                return returnPriorityTwo();
            } else if (priorityThreeIterator.hasNext()) {
                return returnPriorityThree();
            } else {
                return returnPriorityFour();
            }


        }

        //EFFECTS: returns the most recent task of highest priority
        // else checks for any todo of priority 2

        private Todo returnPriorityOne() {
            Todo t = priorityOneIterator.next();
            Priority test = t.getPriority();
            if (test.isImportant() && test.isUrgent()) {
                counter.next();
                return t;
            }
            return this.next();
        }

        //EFFECTS: returns the most recent task of second priority
        // else checks for any todo of priority 3
        private Todo returnPriorityTwo() {
            Todo t = priorityTwoIterator.next();
            Priority test = t.getPriority();
            if (test.isImportant() && !test.isUrgent()) {
                counter.next();
                return t;
            } else {
                return this.next();
            }
        }

        //MODIFIES: this
        //EFFECTS: returns the most recent task of third highest priority
        // else checks for any todo of priority 4

        private Todo returnPriorityThree() {
            Todo t = priorityThreeIterator.next();
            Priority test = t.getPriority();
            if (!test.isImportant() && test.isUrgent()) {
                counter.next();
                return t;
            } else {
                return this.next();
            }
        }

        //EFFECTS: returns the most recent task of lowest  priority
        // else throws a NoSuchElementException since no element exists
        // works independently of hasNext()
        private Todo returnPriorityFour() {
            Todo t = priorityFourIterator.next();
            Priority test = t.getPriority();
            if (!test.isImportant() && !test.isUrgent()) {
                counter.next();
                return t;
            } else {
                return this.next();
            }
        }

    }



}