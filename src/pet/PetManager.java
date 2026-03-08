package pet;

import entity.*;
import main.GamePanel;

public class PetManager {

    public Pet currentPet;
    GamePanel gp;

    public PetManager(GamePanel gp) {
        this.gp = gp;
    }

    public Pet createDog() {
        currentPet = new Dog(gp);
        return currentPet;
    }

    public Pet createCat() {
        currentPet = new Cat(gp);
        return currentPet;
    }

    public Pet createKoala() {
        currentPet = new Koala(gp);
        return currentPet;
    }
}