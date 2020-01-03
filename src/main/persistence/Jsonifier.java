package persistence;


import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        JSONObject tagJson = new JSONObject();
        tagJson.put("name", tag.getName());
        return tagJson;
    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject priJson = new JSONObject();
        priJson.put("important", priority.isImportant());
        priJson.put("urgent", priority.isUrgent());
        return priJson;
    }

    // EFFECTS: returns JSON representation of dueDate
    public static Object dueDateToJson(DueDate dueDate) {
        if (dueDate == null) {

            return JSONObject.NULL;
        }
        JSONObject dueDateJson = new JSONObject();

        Date thisDate = dueDate.getDate();
        dueDateJson.put("year", thisDate.getYear() + 1900);
        dueDateJson.put("month", thisDate.getMonth());
        dueDateJson.put("day", thisDate.getDate());
        dueDateJson.put("hour", thisDate.getHours());
        dueDateJson.put("minute", thisDate.getMinutes());
        return dueDateJson;
    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {

        JSONObject taskJson = new JSONObject();
        taskJson.put("description", task.getDescription());
        taskJson.put("due-date", dueDateToJson(task.getDueDate()));

        JSONArray tagsJson = new JSONArray();
        for (Tag t: task.getTags()) {
            tagsJson.put(tagToJson(t));
        }
        taskJson.put("tags", tagsJson);
        taskJson.put("priority", priorityToJson(task.getPriority()));

        statusToJson(task, taskJson);

        return taskJson;
    }

    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        JSONArray tasksJson = new JSONArray();
        for (Task t: tasks) {
            tasksJson.put(taskToJson(t));
        }
        return tasksJson;
    }

    public static JSONObject statusToJson(Task task, JSONObject js) {
        if (task.getStatus().toString().contains(" ")) {
            if (task.getStatus().toString().contains("UP")) {
                js.put("status", "UP_NEXT");
            } else {
                js.put("status", "IN_PROGRESS");
            }
        } else {
            if (task.getStatus().toString().contains("TO")) {
                js.put("status", "TODO");
            } else {
                js.put("status", "DONE");
            }
        }
        return js;
    }
}
