package quest;

public class Task {

    public String name;
    public String description;
    public boolean completed = false;

    public int targetX;
    public int targetY;
    public int radius = 48;

    public Task(String name, String description, int x, int y) {
        this.name = name;
        this.description = description;
        this.targetX = x;
        this.targetY = y;
    }

    public boolean checkCompletion(int playerX, int playerY) {

        int dx = playerX - targetX;
        int dy = playerY - targetY;

        double distance = Math.sqrt(dx*dx + dy*dy);

        if(distance < radius) {
            completed = true;
            return true;
        }

        return false;
    }
}