package consumables;

import entities.Hero;

public class DefenseAmulet extends GenericPowerUp {

    private double defense;

    public DefenseAmulet() {
        setName(getName());
        setDescription(getDescription());
    }

    public DefenseAmulet(String name, String description, double defense) {
        super(name, description);
        this.defense = defense;
    }

    @Override
    public void setName(String name) {
        int random = (int) Math.floor(Math.random() * 100);

        if (random >= 0 && random < 50) {
            name = "Common Defense Amulet";
        } else if (random >= 50 && random < 80) {
            name = "Rare Defense Amulet";
        } else if (random >= 80 && random < 100) {
            name = "Epic Defense Amulet";
        }
        super.setName(name);
    }

    public void setDefense(Hero hero) {
        switch (getName().toLowerCase()) {
            case "common defense amulet":
                this.defense = hero.getBaseDefense() * 0.15;
                break;

            case "rare defense amulet":
                this.defense = hero.getBaseDefense() * 0.20;
                break;

            case "epic defense amulet":
                this.defense = hero.getBaseDefense() * 0.30;
                break;
        }
    }

    public double getDefense() {
        return defense;
    }

    @Override
    public void setDescription(String description) {
        switch (getName().toLowerCase()) {
            case "common defense amulet":
                super.setDescription("A common amulet that increases your defense by 15% for the rest of the battle");
                break;

            case "rare defense amulet":
                super.setDescription("A rare amulet that increases your defense by 20% for the rest of the battle");
                break;

            case "epic defense amulet":
                super.setDescription("An epic amulet that increases your defense by 30% for the rest of the battle");
                break;
        }
    }
}