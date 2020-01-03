package parsers;

import model.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Represents Task parser
public class TaskParser {


    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray

    public List<Task> parse(String input) {
        JSONArray inputJson = new JSONArray(input);

        List<Task> output = new ArrayList<>();
        for (Object object: inputJson)  {
            // this should be where the magic happens

            JSONObject currentJson = (JSONObject) object; // the JSON for each Task
            Task finishedTask = new Task(" ");

            //String that has the description for the TASK
            try {
                output.add(eachTask(currentJson, finishedTask));
            } catch (JSONException e) {
                continue;
            }
        }

        return output;   // stub
    }

    private Task eachTask(JSONObject json, Task task) throws JSONException {
        jsonToDescription(json, task);
        jsonToDueDate(json, task);
        jsonToTags(json, task);
        jsonToPriority(json, task);
        jsonToStatus(json, task);

        return task;
    }

    private Task jsonToDescription(JSONObject js, Task t) throws JSONException {
        String curDescription = js.getString("description");
        t.setDescription(curDescription);
        return t;
    }

    private Task jsonToDueDate(JSONObject js, Task t) throws JSONException {
        if (js.get("due-date").equals(JSONObject.NULL)) {
            t.setDueDate(null);
            return t;
        }
        JSONObject curDueDate = (JSONObject) js.get("due-date");

        Date h = new Date();
        h.setYear(curDueDate.getInt("year") - 1900);
        h.setMonth(curDueDate.getInt("month"));
        h.setDate(curDueDate.getInt("day"));
        h.setHours(curDueDate.getInt("hour"));
        h.setMinutes(curDueDate.getInt("minute"));
        DueDate finishedDueDate = new DueDate(h);
        t.setDueDate(finishedDueDate);
        return t;
    }

    private Task jsonToPriority(JSONObject js, Task t) throws JSONException {
        JSONObject curPriority = js.getJSONObject("priority");
        Priority finishedPriority = new Priority();
        finishedPriority.setImportant(curPriority.getBoolean("important"));
        finishedPriority.setUrgent(curPriority.getBoolean("urgent"));
        t.setPriority(finishedPriority);
        return t;
    }

    private Task jsonToStatus(JSONObject js, Task t) throws JSONException {
        String curStatus = js.getString("status");
        if (curStatus.equals("UP_NEXT")) {
            t.setStatus(Status.UP_NEXT);
        }
        if (curStatus.equals("TODO")) {
            t.setStatus(Status.TODO);
        }
        if (curStatus.equals("IN_PROGRESS")) {
            t.setStatus(Status.IN_PROGRESS);
        }
        if (curStatus.equals("DONE")) {
            t.setStatus(Status.DONE);
        }

        return t;
    }

    private Task jsonToTags(JSONObject js, Task t) throws JSONException {
        JSONArray curTags = js.getJSONArray("tags");
        for (int m = 0; m < curTags.length(); m++) {
            JSONObject curTag = curTags.getJSONObject(m);
            t.addTag(new Tag(curTag.getString("name")));
        }
        return t;
    }


}
