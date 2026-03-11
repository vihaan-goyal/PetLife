package inventory;

import java.util.HashMap;
import main.GamePanel;

public class InventoryManager {

    GamePanel gp;

    public HashMap<String, Integer> items = new HashMap<>();

    public InventoryManager(GamePanel gp) {
        this.gp = gp;
    }

    public void addItem(String item, int amount) {

        int current = items.getOrDefault(item, 0);
        items.put(item, current + amount);

    }

    public boolean removeItem(String item, int amount) {

        int current = items.getOrDefault(item, 0);

        if(current >= amount) {

            items.put(item, current - amount);
            return true;

        }

        return false;
    }

    public int getItemCount(String item) {

        return items.getOrDefault(item, 0);

    }

}