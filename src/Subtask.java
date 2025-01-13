public class Subtask extends Task {
    private Epic epic;

    public Subtask(String name, String description, int id, TaskStatus status, Epic epic) {
        super(name, description, id, status);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public String getType() {
        return "Subtask";
    }
}
