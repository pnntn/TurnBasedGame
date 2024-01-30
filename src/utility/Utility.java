package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import consumables.*;
import entities.*;

public class Utility {

	private ArrayList<Enemy> enemies = new ArrayList<>();
	private ArrayList<GenericPowerUp> powerUpsList = new ArrayList<>();
	private ArrayList<String> movesList = new ArrayList<>();

	String output = "";
	boolean inventory = false;
	int random = 0;

	Scanner input = new Scanner(System.in);

	public Utility() {

		try {
			String enemiesPath = "src/res/enemies.csv";
			String movesPath = "src/res/moves.txt";
			Scanner enemiesListFile = new Scanner(new File(enemiesPath));
			Scanner movesListFile = new Scanner(new File(movesPath));

			Enemy enemy;
			String[] row;

			while (enemiesListFile.hasNextLine()) {
				row = enemiesListFile.nextLine().split(",");
				enemy = new Enemy(row[0], Double.parseDouble(row[1]), Double.parseDouble(row[2]),
						Double.parseDouble(row[3]), Double.parseDouble(row[4]));
				enemies.add(enemy);
			}

			while (movesListFile.hasNextLine()) {
				movesList.add(movesListFile.nextLine());
			}
		}
		catch(FileNotFoundException e) {
			System.out.println(e);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	public void battle(Hero hero, Enemy enemy) {

		System.out.println("\nYou are battling " + enemy.getName() + "\n");

		BattleMenu:
			do {

				// Print enemy and character during battle
				System.out.println("----------------------------------");
				System.out.println(enemy.getName() + "\n" + (int) enemy.getHealthPoints() + "/" + (int) enemy.getMaxHealthPoints() + "HP\n");
				System.out.println("\u001B[31mVS\u001B[0m");
				System.out.println("\n" + hero.getName() + "\n" + (int) hero.getHealthPoints() + "/" + (int) hero.getMaxHealthPoints() + "HP");
				System.out.println("----------------------------------");

				// Interactive menu for the hero's turn
				System.out.println("It's your turn, choose an option\n");
				System.out.println("0 - Run away");
				System.out.println("1 - Attack");

				if (powerUpsList.size() > 0) {
					System.out.println("2 - View PowerUp inventory");
					inventory = true;
				}

				System.out.println("----------------------------------");

				output = input.nextLine();
				System.out.println();

				switch (output) {
				case "0": // Escape from the battle

					// 30% chance of failing to escape
					if (!(enemy instanceof Boss)) {
						random = (int) Math.floor(Math.random() * 100);

						if (random >= 0 && random < 30) {
							System.out.println("You failed to escape from the enemy, you lose your turn");
							printEnemyAttack(hero, enemy);
						} else if (random >= 30 && random < 100) {
							// If escape is successful, take 20% damage and print escape message
							hero.setHealthPoints(hero.getHealthPoints() * 0.80);
							System.out.println("Congratulations, you managed to escape from " + enemy.getName() +
									", but unfortunately you suffered 20% damage");
							System.out.println("Remaining \u001B[34mHero\u001B[0m HP: " + (int) hero.getHealthPoints() +
									" out of " + (int) hero.getMaxHealthPoints());
							break BattleMenu;
						}
					} else {
						System.out.println("You cannot escape from the Bossfight!\n");
					}

					break;

				case "1": // Hero and enemy attack turn

					// Check for a critical hit
					random = (int) Math.floor(Math.random() * 100);

					if (random > 0 && random < hero.getCritical()) {
						System.out.println("\u001B[31mCRITICAL HIT! YOU DEALT CRITICAL DAMAGE\u001B[0m");
						hero.setAttack(hero.getAttack() * 1.5);
						printHeroAttack(hero, enemy);
					} else {
						// Hero attack method
						printHeroAttack(hero, enemy);
					}

					// Check if the enemy's HP is below 0, in which case print victory message
					if (enemy.getHealthPoints() <= 0 && !(enemy instanceof Boss)) {
						System.out.println("\nYou defeated " + enemy.getName() +
								" and can proceed to the next room");
						randomPowerUp();
						hero.setBaseAttack(200); // Reset attack and defense statistics in case amulets were used
						hero.setBaseDefense(40);
						hero.setAttack(hero.getBaseAttack()); // After resetting statistics to their initial values, recalculate hero's attack and defense
						hero.setDefense(hero.getBaseDefense());
						enemy.setHealthPoints(enemy.getMaxHealthPoints()); // Set the defeated monster's health back to maximum
						break BattleMenu;
					}

					// Enemy's turn against the hero
					if (enemy.getHealthPoints() > 0) {
						// Check for enemy critical hit
						random = (int) Math.floor(Math.random() * 100);

						if (random > 0 && random < enemy.getCritical()) {
							// Check for critical hit in case of a boss fight
							if (enemy instanceof Boss) {
								System.out.println("\n" + ((Boss) enemy).getName().toUpperCase() +
										" \u001B[31mDEALT YOU A CRITICAL HIT!\u001B[0m");
								enemy.setAttack(enemy.getAttack() * 1.5);
								printEnemyAttack(hero, ((Boss) enemy));
							} else {
								System.out.println("\n\u001B[31mDEALT YOU A CRITICAL HIT!\u001B[0m");
								enemy.setAttack(enemy.getAttack() * 1.5);
								printEnemyAttack(hero, enemy);
							}
						} else {
							if (enemy instanceof Boss) {
								printEnemyAttack(hero, ((Boss) enemy));
							} else {
								// Enemy attack method
								printEnemyAttack(hero, enemy);
							}
						}
					}

					// Reset attributes after each combat iteration
					hero.setAttack(hero.getBaseAttack());
					enemy.setAttack((enemy.getBaseAttack()));

					break;

				case "2": // Display inventory of power-ups

					// Check if there are items in the power-up array, call the choosePowerUp method
					if (inventory) {
						choosePowerUp(hero, enemy);
						enemy.setAttack(enemy.getBaseAttack());
						inventory = false;
					} else {
						System.out.println("\nInvalid choice, please try again\n");
					}
					break;

				default:
					System.out.println("\nInvalid choice, please try again\n");
					break;
				}

				// Check if hero has HP below 0 after each combat turn
				if (hero.getHealthPoints() <= 0) {
					break BattleMenu;
				}

				if (enemy instanceof Boss && enemy.getHealthPoints() <= 0) {
					break BattleMenu;
				}
			} while (true);
	}

	public void choosePowerUp(Hero hero, Enemy enemy) {

		// Check if there are power-ups
		int smallPotionCount = 0;
		int largePotionCount = 0;
		int commonDefenseAmuletCount = 0;
		int rareDefenseAmuletCount = 0;
		int epicDefenseAmuletCount = 0;
		int commonStrengthAmuletCount = 0;
		int rareStrengthAmuletCount = 0;
		int epicStrengthAmuletCount = 0;

		for (int i = 0; i < powerUpsList.size(); i++) {

			if (powerUpsList.get(i) instanceof HealingPotion) {
				switch (powerUpsList.get(i).getName().toLowerCase()) {
				case "small potion":
					smallPotionCount++;
					break;

				case "large potion":
					largePotionCount++;
					break;
				}
			} 
			else if (powerUpsList.get(i) instanceof DefenseAmulet) {
				switch (powerUpsList.get(i).getName().toLowerCase()) {
				case "common defense amulet":
					commonDefenseAmuletCount++;
					break;

				case "rare defense amulet":
					rareDefenseAmuletCount++;
					break;

				case "epic defense amulet":
					epicDefenseAmuletCount++;
					break;
				}
			}
			else if (powerUpsList.get(i) instanceof AttackAmulet) {
				switch (powerUpsList.get(i).getName().toLowerCase()) {
				case "common strength amulet":
					commonStrengthAmuletCount++;
					break;

				case "rare strength amulet":
					rareStrengthAmuletCount++;
					break;

				case "epic strength amulet":
					epicStrengthAmuletCount++;
					break;
				}
			}
		}

		// Print the quantity of power-ups if found
		System.out.println("CHOOSE A POWER-UP TO USE");
		if (smallPotionCount > 0) System.out.println("t - Small Potions: " + smallPotionCount + " in possession");
		if (largePotionCount > 0) System.out.println("y - Large Potions: " + largePotionCount + " in possession");
		if (commonDefenseAmuletCount > 0) System.out.println("g - Common Defense Amulets: " + commonDefenseAmuletCount + " in possession");
		if (rareDefenseAmuletCount > 0) System.out.println("h - Rare Defense Amulets: " + rareDefenseAmuletCount + " in possession");
		if (epicDefenseAmuletCount > 0) System.out.println("j - Epic Defense Amulets: " + epicDefenseAmuletCount + " in possession");
		if (commonStrengthAmuletCount > 0) System.out.println("v - Common Strength Amulets: " + commonStrengthAmuletCount + " in possession");
		if (rareStrengthAmuletCount > 0) System.out.println("b - Rare Strength Amulets: " + rareStrengthAmuletCount + " in possession");
		if (epicStrengthAmuletCount > 0) System.out.println("n - Epic Strength Amulets: " + epicStrengthAmuletCount + " in possession");
		System.out.println("0 - Back to the fight");

		String choice = input.nextLine().toLowerCase();
		// Check to see if the switch cases were entered, only in that case will the enemy attack
		boolean item = false;

		ItemSelection:
			switch (choice) {
			case "0":
				System.out.println("\nClosing inventory");
				break;
			case "t":

				for (GenericPowerUp p : powerUpsList) {

					if (p.getName().equalsIgnoreCase("small potion")) {

						hero.useHealingPotion((HealingPotion) p);
						System.out.println("\nYou used " + p.getName() + " and healed " +
								(int) ((HealingPotion) p).getHealthPointsPotion() + " HP\nYour current HP is " +
								(int) hero.getHealthPoints() + "/" + (int) hero.getMaxHealthPoints());
						powerUpsList.remove(p);
						item = true;
						break ItemSelection;
					}
				}
				System.out.println("\nInvalid choice, please try again");
				break;

			case "y":

				for (GenericPowerUp p : powerUpsList) {

					if (p.getName().equalsIgnoreCase("large potion")) {

						hero.useHealingPotion((HealingPotion) p);
						System.out.println("\nYou used " + p.getName() + " and healed all your HP");
						powerUpsList.remove(p);
						item = true;
						break ItemSelection;
					}
				}
				System.out.println("\nInvalid choice, please try again");
				break;

			case "g":

				for (GenericPowerUp p : powerUpsList) {

					if (p.getName().equalsIgnoreCase("common defense amulet")) {

						hero.useDefenseAmulet((DefenseAmulet) p);
						System.out.println("\nYou used " + p.getName() + " and increased your defense by 15%");
						powerUpsList.remove(p);
						item = true;
						break ItemSelection;
					}
				}
				System.out.println("\nInvalid choice, please try again");
				break;

			case "h":

				for (GenericPowerUp p : powerUpsList) {

					if (p.getName().equalsIgnoreCase("rare defense amulet")) {

						hero.useDefenseAmulet((DefenseAmulet) p);
						System.out.println("\nYou used " + p.getName() + " and increased your defense by 20%");
						powerUpsList.remove(p);
						item = true;
						break ItemSelection;
					}
				}
				System.out.println("\nInvalid choice, please try again");
				break;

			case "j":

				for (GenericPowerUp p : powerUpsList) {

					if (p.getName().equalsIgnoreCase("epic defense amulet")) {

						hero.useDefenseAmulet((DefenseAmulet) p);
						System.out.println("\nYou used " + p.getName() + " and increased your defense by 30%");
						powerUpsList.remove(p);
						item = true;
						break ItemSelection;
					}
				}
				System.out.println("\nInvalid choice, please try again");
				break;


			case "v":

				for (GenericPowerUp p : powerUpsList) {

					if (p.getName().equalsIgnoreCase("common strength amulet")) {

						hero.useAttackAmulet((AttackAmulet) p);
						System.out.println("\nYou used " + p.getName() + " and increased your attack by 15%");
						powerUpsList.remove(p);
						item = true;
						break ItemSelection;
					}
				}
				System.out.println("\nInvalid choice, please try again");
				break;

			case "b":

				for (GenericPowerUp p : powerUpsList) {

					if (p.getName().equalsIgnoreCase("rare strength amulet")) {

						hero.useAttackAmulet((AttackAmulet) p);
						System.out.println("\nYou used " + p.getName() + " and increased your attack by 20%");
						powerUpsList.remove(p);
						item = true;
						break ItemSelection;
					}
				}
				System.out.println("\nInvalid choice, please try again");
				break;

			case "n":

				for (GenericPowerUp p : powerUpsList) {

					if (p.getName().equalsIgnoreCase("epic strength amulet")) {

						hero.useAttackAmulet((AttackAmulet) p);
						System.out.println("\nYou used " + p.getName() + " and increased your attack by 30%");
						powerUpsList.remove(p);
						item = true;
						break ItemSelection;
					}
				}
				System.out.println("\nInvalid choice, please try again");
				break;

			default:
				System.out.println("\nInvalid choice, please try again");
				break;
			}

		hero.setAttack(hero.getBaseAttack());
		hero.setDefense(hero.getBaseDefense());

		if (!(choice.equalsIgnoreCase("0")) && item) {
			printEnemyAttack(hero, enemy);
		}
	}

	public void randomPowerUp() {

		GenericPowerUp powerUp;

		int random = (int) Math.floor(Math.random() * 100);

		if (random < 34) {
			powerUp = new HealingPotion();
			powerUpsList.add(powerUp);
		} else if (random >= 34 && random <= 66) {
			powerUp = new AttackAmulet();
			powerUpsList.add(powerUp);
		} else {
			powerUp = new DefenseAmulet();
			powerUpsList.add(powerUp);
		}

		System.out.println("\nPowerUp found: " + powerUp.getName());
		System.out.println(powerUp.getDescription());
	}

	public Enemy getRandomEnemy() {
		int random = (int) Math.floor(Math.random() * (enemies.size()));
		return enemies.get(random);
	}

	// Method for printing after an Enemy attack
	public void printEnemyAttack(Hero hero, Enemy enemy) {
		enemy.attackHero(hero, movesList);
	}

	// Method for printing after a hero's attack
	public void printHeroAttack(Hero hero, Enemy enemy) {
		hero.attackEnemy(enemy);
	}
}