package taskmanager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();

    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Task> getTasks() {
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

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
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
        if (task instanceof Subtask && !subtasks.containsKey(id)) {
            return; // Убедитесь, что подзадача существует
        }
        if (task instanceof Epic && !epics.containsKey(id)) {
            return; // Убедитесь, что эпик существует
        }
        if (task instanceof Task && !tasks.containsKey(id)) {
            return; // Убедитесь, что задача существует
        }

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
            Subtask subtask = subtasks.get(id);
            int epicId = subtask.getEpicId();
            Epic epic = epics.get(epicId);
            epic.removeSubtaskId(id); // Удалить из эпика подзадачу
            subtasks.remove(id);
            epic.updateStatus(this);
        } else if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            for (int subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId); // Удалить подзадачи эпика
            }
            epics.remove(id);
        } else {
            System.out.println("Задача не найдена");
        }
    }
}
