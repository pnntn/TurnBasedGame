package entities;

import java.util.ArrayList;

public class Enemy extends Entity {

    private double healthPoints;

    public Enemy(String name, double maxHealthPoints, double baseAttack, double baseDefense, double critical) {
        super(name, maxHealthPoints, baseAttack, baseDefense, critical);
        this.healthPoints = maxHealthPoints;
    }

    public Enemy(String name) {
        super(name);
    }

    public void takeDamage(Hero hero) {
        if (hero.getAttack() - getDefense() > 0) {
            setHealthPoints(getHealthPoints() - (hero.getAttack() - getDefense()));
            System.out.println("You inflicted " + (int) (hero.getAttack() - getDefense()) + " damage to the enemy");
        } else {
            System.out.println("Your attack was ineffective, you inflicted 0 damage\n");
        }
    }

    public void attackHero(Hero hero, ArrayList<String> moveList) {
        int random = (int) Math.floor(Math.random() * moveList.size());

        if (getAttack() - hero.getDefense() > 0) {
            hero.setHealthPoints(hero.getHealthPoints() - (this.getAttack() - hero.getDefense()));
            System.out.println("\n" + getName() + " uses " + moveList.get(random) + " and inflicts " +
                    (int) (this.getAttack() - hero.getDefense()) + " damage\n");
        } else {
            System.out.println(getName() + " uses " + moveList.get(random) + " but it's ineffective, you take 0 damage\n");
        }
    }

    public double getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(double healthPoints) {
        this.healthPoints = healthPoints;
    }
}