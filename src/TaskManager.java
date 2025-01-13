import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();

    private int counter = 0;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.counter = 0;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public void setEpics(HashMap<Integer, Epic> epics) {
        this.epics = epics;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(HashMap<Integer, Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public void setTasks(HashMap<Integer, Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        allTasks.addAll(tasks.values());
        allTasks.addAll(subtasks.values());
        allTasks.addAll(epics.values());
        return allTasks;
    }

    public void removeAllTasks() {
        tasks.clear();
        subtasks.clear();
        epics.clear();
    }

    public Task getTaskById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        } else if (subtasks.containsKey(id)) {
            return subtasks.get(id);
        } else if (epics.containsKey(id)) {
            return epics.get(id);
        } else {
            return null;
        }
    }

    public void createTask(Task task) {
        counter++;
        task.setId(counter);

        String type = task.getType();

        if (type.equals("Subtask")) {
            subtasks.put(task.getId(), (Subtask) task);
        } else if (type.equals("Epic")) {
            epics.put(task.getId(), (Epic) task);
        } else {
            tasks.put(task.getId(), task);
        }
    }

    public void updateTask(Task task) {
        int id = task.getId();

        switch (task.getType()) {
            case "Subtask":
                subtasks.put(id, (Subtask) task);
                break;
            case "Epic":
                epics.put(id, (Epic) task);
                break;
            default:
                tasks.put(id, task);
                break;
        }
    }

    public void deleteTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else if (subtasks.containsKey(id)) {
            subtasks.remove(id);
        } else if (epics.containsKey(id)) {
            epics.remove(id);
        } else {
            System.out.println("Задача не найдена");
        }
    }
}
