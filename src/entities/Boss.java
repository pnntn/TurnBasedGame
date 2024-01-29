package entities;

import java.util.ArrayList;

public class Boss extends Enemy {

    private double healthPoints;
    private int hitCounter;

    public Boss(String name, double maxHealthPoints, double baseAttack, double baseDefense, double critical, String moves) {
        super(name, maxHealthPoints, baseAttack, baseDefense, critical);
        this.healthPoints = maxHealthPoints;
    }

    public Boss(String name) {
        super(name);
        setMaxHealthPoints(1700);
        setBaseAttack(120);
        setAttack(getBaseAttack());
        setBaseDefense(100);
        setDefense(getBaseDefense());
        setHealthPoints(getMaxHealthPoints());
        setCritical(10);
        setHitCounter(0);
    }

    public void setHealthPoints(double healthPoints) {
        this.healthPoints = healthPoints;
    }

    public double getHealthPoints() {
        return healthPoints;
    }

    public void attackHero(Hero hero, ArrayList<String> moveList) {
    	if (getHitCounter() == 3) {
    	    setAttack(getAttack() + 300);
    	    System.out.println("..." + this.getName().toUpperCase() + " UNLEASHES ITS FINAL MOVE...\n");

    	    String insultDescription = getName() + " invokes the Eldritch Taunt, assaulting your mind with malicious words" +
    	            "\nThe psychic onslaught inflicts " + (int) (getAttack() - hero.getDefense()) + " psychic damage.";

    	    String mysticalAssaultDescription = getName() + " summons arcane energy, casting Force Barrage upon you." +
    	            "\nThe magical assault deals " + (int) (getAttack() - hero.getDefense()) + " force damage.";

    	    System.out.println(this.getName().equalsIgnoreCase("Seraphina Shadowbane") ? insultDescription : mysticalAssaultDescription);
    	
            if (getAttack() - hero.getDefense() > 0) {
                hero.setHealthPoints(hero.getHealthPoints() - (getAttack() - hero.getDefense()));
                setAttack(getBaseAttack());
            } else {
                System.out.println("Boss's attack ineffective, you take 0 damage\n");
            }

            setHitCounter(0);
        } else {
            this.hitCounter++;
            super.attackHero(hero, moveList);
        }
    }

    public int getHitCounter() {
        return hitCounter;
    }

    public void setHitCounter(int hitCounter) {
        this.hitCounter = hitCounter;
    }
}
