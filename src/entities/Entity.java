package entities;

public class Entity {
    private String name;
    private double attack;
    private double baseAttack;
    private double defense;
    private double baseDefense;
    private double maxHealthPoints;
    private double critical;

    public Entity(String name) {
        setName(name);
    }

    public Entity(String name, double maxHealthPoints, double baseAttack, double baseDefense, double critical) {
        setName(name);
        setAttack(baseAttack);
        setDefense(baseDefense);
        setMaxHealthPoints(maxHealthPoints);
        setCritical(critical);
        setBaseAttack(baseAttack);
        setBaseDefense(baseDefense);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public void setMaxHealthPoints(double maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    public double getCritical() {
        return critical;
    }

    public void setCritical(double critical) {
        this.critical = critical;
    }

    public double getBaseAttack() {
        return baseAttack;
    }

    public void setBaseAttack(double baseAttack) {
        this.baseAttack = baseAttack;
    }

    public double getBaseDefense() {
        return baseDefense;
    }

    public void setBaseDefense(double baseDefense) {
        this.baseDefense = baseDefense;
    }
}