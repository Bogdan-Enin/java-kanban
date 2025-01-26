package taskmanager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();

    private int counter = 0;

    private int generateId() {
        return ++counter;
    }

    //Получение всех простых задач
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    //Получение всех подзадач
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    //Получение всех эпиков
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    //Очистка всех простых задач
    public void removeAllTasks() {
        tasks.clear();
    }

    //Очистка всех подзадач
    public void removeAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtaskIds();
            updateEpicStatus(epic);
        }
    }

    //Очистка всех эпиков и связанных подзадач
    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    //Создание обычной задачи
    public void createTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    //Создание подзадачи
    public void createSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            subtask.setId(generateId());
            subtasks.put(subtask.getId(), subtask);
            epic.addSubtaskId(subtask.getId());
            updateEpicStatus(epic);
        }
    }

    public void createEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                updateEpicStatus(epic);
            }
        }
    }

    //Обновление эпика
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic currentEpic = epics.get(epic.getId());
            currentEpic.setName(epic.getName());
            currentEpic.setDescription(epic.getDescription());
        }
    }

    //Удаление обычной задачи
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    //Удаление подзадачи
    public void deleteSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Subtask subtask = subtasks.get(id);
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtaskId(id);
                updateEpicStatus(epic);
            }
            subtasks.remove(id);
        }
    }

    public void deleteEpic(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            for (int subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
        }
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    private void updateEpicStatus(Epic epic) {
        List<Integer> subtaskIds = epic.getSubtaskIds();
        if (subtaskIds.isEmpty()) {
            epic.setStatus(Task.TaskStatus.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (int subtaskId : subtaskIds) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask.getStatus() != Task.TaskStatus.NEW) {
                allNew = false;
            }
            if (subtask.getStatus() != Task.TaskStatus.DONE) {
                allDone = false;
            }
        }

        if (allNew) {
            epic.setStatus(Task.TaskStatus.NEW);
        } else if (allDone) {
            epic.setStatus(Task.TaskStatus.DONE);
        } else {
            epic.setStatus(Task.TaskStatus.IN_PROGRESS);
        }
    }
}
