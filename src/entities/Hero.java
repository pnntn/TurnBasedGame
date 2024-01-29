package entities;

import consumables.AttackAmulet;
import consumables.DefenseAmulet;
import consumables.HealingPotion;

import java.util.ArrayList;

public class Hero extends Entity {

    private double healthPoints;

    public Hero(String name, double maxHealthPoints, double baseAttack, double baseDefense, double critical) {
        super(name, maxHealthPoints, baseAttack, baseDefense, critical);
        this.healthPoints = maxHealthPoints;
    }

    public Hero(String name) {
        super(name);
        setMaxHealthPoints(2000);
        setBaseAttack(200);
        setBaseDefense(40);
        setAttack(getBaseAttack());
        setDefense(getBaseDefense());
        setCritical(15);
        setHealthPoints(getMaxHealthPoints());
    }

    public void setHealthPoints(double healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void receiveDamage(Enemy enemy, ArrayList<String> moveList) {
        if (enemy.getAttack() - this.getDefense() > 0) {
            int random = (int) Math.floor(Math.random() * moveList.size());
            this.setHealthPoints(getHealthPoints() - (enemy.getAttack() - this.getDefense()));
            System.out.println("\n" + enemy.getName() + " uses " + moveList.get(random) + " and inflicts " +
                    (int) (enemy.getAttack() - getDefense()) + " damage\n");
        } else {
            System.out.println("Enemy's attack ineffective, you take 0 damage\n");
        }
    }

    public void attackEnemy(Enemy enemy) {
        if (this.getAttack() - enemy.getDefense() > 0) {
            enemy.setHealthPoints(enemy.getHealthPoints() - (this.getAttack() - enemy.getDefense()));
            System.out.println("You inflicted " + (int) (getAttack() - enemy.getDefense()) + " damage to the enemy");
        } else {
            System.out.println("Your attack was ineffective, you inflicted 0 damage");
        }
    }

    public double getHealthPoints() {
        return healthPoints;
    }

    // Method for using the healing potion
    public void useHealingPotion(HealingPotion potion) {
        potion.setHealthPoints(this);
        setHealthPoints(getHealthPoints() + potion.getHealthPointsPotion());
    }

    // Method for using the defense amulet
    public void useDefenseAmulet(DefenseAmulet defenseAmulet) {
        defenseAmulet.setDefense(this);
        setBaseDefense(getBaseDefense() + defenseAmulet.getDefense());
    }

    // Method for using the attack amulet
    public void useAttackAmulet(AttackAmulet attackAmulet) {
        attackAmulet.setAttack(this);
        setBaseAttack(getBaseAttack() + attackAmulet.getAttack());
    }
}
