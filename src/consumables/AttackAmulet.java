package consumables;

import entities.Hero;

public class AttackAmulet extends GenericPowerUp {

    private double attack;

    public AttackAmulet() {
        setName(getName());
        setDescription(getDescription());
    }

    public AttackAmulet(String name, String description, double attack) {
        super(name, description);
        this.attack = attack;
    }

    @Override
    public void setName(String name) {
        int random = (int) Math.floor(Math.random() * 100);

        if (random >= 0 && random < 50) {
            name = "Common Strength Amulet";
        } else if (random >= 50 && random < 80) {
            name = "Rare Strength Amulet";
        } else if (random >= 80 && random <= 100) {
            name = "Epic Strength Amulet";
        }

        super.setName(name);
    }

    public void setAttack(Hero hero) {
        switch (getName().toLowerCase()) {
            case "common strength amulet":
                this.attack = hero.getBaseAttack() * 0.15;
                break;

            case "rare strength amulet":
                this.attack = hero.getBaseAttack() * 0.20;
                break;

            case "epic strength amulet":
                this.attack = hero.getBaseAttack() * 0.30;
                break;
        }
    }

    public double getAttack() {
        return attack;
    }

    @Override
    public void setDescription(String description) {
        switch (getName().toLowerCase()) {
            case "common strength amulet":
                super.setDescription("A common strength amulet that increases your attack by 15% for the rest of the battle");
                break;

            case "rare strength amulet":
                super.setDescription("A rare strength amulet that increases your attack by 20% for the rest of the battle");
                break;

            case "epic strength amulet":
                super.setDescription("An epic strength amulet that increases your attack by 30% for the rest of the battle");
                break;
        }
    }
}
