package consumables;

import entities.Hero;

public class HealingPotion extends GenericPowerUp {

    private double healthPointsPotion;

    public HealingPotion() {
        setName(getName());
        setDescription(getDescription());
    }

    public HealingPotion(String name, String description, double healthPointsPotion) {
        super(name, description);
        this.healthPointsPotion = healthPointsPotion;
    }

    // Randomizer to decide which potion (small or large) the hero will have
    @Override
    public void setName(String name) {
        int random = (int) Math.floor(Math.random() * 100);

        if (random >= 0 && random < 30) {
            name = "large potion";
        } else if (random >= 30 && random < 100) {
            name = "small potion";
        }
        super.setName(name);
    }

    @Override
    public void setDescription(String description) {
        if (this.getName().equalsIgnoreCase("small potion")) {
            super.setDescription("A small potion capable of healing you for 500 Health Points");
        } else if (this.getName().equalsIgnoreCase("large potion")) {
            super.setDescription("A large potion capable of fully restoring your health");
        }
    }

    public double getHealthPointsPotion() {
        return healthPointsPotion;
    }

    // Healing value of the potion; if it would heal more than the hero's missing health, then the new value will be the exact missing health of the hero
    public void setHealthPoints(Hero hero) {
        switch (getName().toLowerCase()) {
            case "small potion":
                this.healthPointsPotion = 500;
                break;

            case "large potion":
                this.healthPointsPotion = hero.getMaxHealthPoints() - 1;
                break;
        }

        if (hero.getHealthPoints() + healthPointsPotion > hero.getMaxHealthPoints()) {
            this.healthPointsPotion = hero.getMaxHealthPoints() - hero.getHealthPoints();
        }
    }
}