package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds;

    public Epic(String name, String description, int id, TaskStatus status, ArrayList<Integer> subtaskIds) {
        super(name, description, id, status);
        this.subtaskIds = subtaskIds;
    }

    public ArrayList<Integer> getSubtaskIds() {
        return new ArrayList<>(subtaskIds);
    }

    public void setSubtaskIds(ArrayList<Integer> subtaskIds) {
        this.subtaskIds = subtaskIds;
    }

    public void addSubtaskId(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public void removeSubtaskId(int subtaskId) {
        subtaskIds.remove(Integer.valueOf(subtaskId));
    }

    public void clearSubtaskIds() {
        subtaskIds.clear();
    }

    @Override
    public String getType() {
        return "Epic";
    }
}

