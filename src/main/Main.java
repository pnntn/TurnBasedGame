package main;

import java.io.FileNotFoundException;
import java.util.Scanner;

import entities.Boss;
import entities.Hero;
import utility.Map;
import utility.Utility;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner input = new Scanner(System.in);

        String choice;
        Hero hero;
        Boss boss;

        System.out.println("Welcome to the Enchanted Realm, a mystical and perilous land,\n"
                + "where arcane spells weave through the air and mythical creatures roam freely.\n"
                + "Your quest is both daring and full of magic: overcome the inhabitants of the Shadow Abyss\n"
                + "led by a formidable Sorcerer...\n"
                + "\nPRESS ENTER TO EMBARK ON YOUR JOURNEY");
        choice = input.nextLine();

        heroSelection:
        do {
            System.out.println("CHOOSE YOUR HERO");
            System.out.println("1 - Thalwyn the Valiant");
            System.out.println("2 - Seraphina Shadowbane");

            choice = input.nextLine();

            switch (choice) {
                case "1":
                    hero = new Hero("Thalwyn the Valiant");
                    boss = new Boss("Seraphina Shadowbane");
                    break heroSelection;

                case "2":
                    hero = new Hero("Seraphina Shadowbane");
                    boss = new Boss("Thalwyn the Valiant");
                    break heroSelection;

                default:
                    System.out.println("Invalid choice, please try again\n");
                    break;
            }

        } while (true);

        Utility c = new Utility();

        difficultySelection:
        do {
            System.out.println("\nChoose the difficulty:"
                    + "\n1 - Easy: You will start with 2 PowerUps"
                    + "\n2 - Infernal: No initial PowerUps");

            choice = input.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("\nEASY difficulty selected");
                    for (int i = 0; i < 2; i++) {
                        c.randomPowerUp();
                    }
                    break difficultySelection;

                case "2":
                    System.out.println("\nINFERNAL difficulty selected");
                    break difficultySelection;

                default:
                    System.out.println("\nInvalid choice, please try again");
                    break;
            }
        } while (true);

        System.out.println("\nGreetings, " + hero.getName() + "\nYour fate has been sealed.\n"
                + "You are bestowed with the formidable quest to navigate the Enchanted Realm and vanquish the powerful members of the Shadow Abyss,\n"
                + "renowned for their mystical prowess and magical abilities. The destiny of this fantastical realm rests upon your shoulders.\n"
                + "Farewell, brave adventurer! May enchantment guide your way.\n"
                + "\nPRESS ENTER TO PROCEED");
        choice = input.nextLine();

        System.out.println("YOU ENTER THE DUNGEON...");

        Map map = new Map();

        int random;

        System.out.println("\nGood luck " + hero.getName() + "!");

        dungeonLoop:
        do {
            System.out.println("HP: " + (int) hero.getHealthPoints() + "/" + (int) hero.getMaxHealthPoints());
            
            // Method for the minimap
            map.startGame();

            // Check if the player is not stuck in non-traversable tiles
            if (!(map.isCheckStuck())) {
                // Check if the player is not on the boss tile
                if (!(map.isCheckBoss())) {
                    random = (int) Math.floor(Math.random() * 2);

                    // Random after visiting a tile, 50% chance of power-up or encountering an enemy
                    if (random == 0) {
                        c.randomPowerUp();
                        System.out.println("\nYou can advance to the next room");
                    } else if (random == 1) {
                        c.battle(hero, c.getRandomEnemy());
                        // c.PowerUpRandom();
                        System.out.println("\nYou can advance to the next room");
                    }
                } else {
                	System.out.println(hero.getName() + "...\nAh, it's you, venturing into my treacherous dungeon!");
                	System.out.println("For every adventurer who dared challenge my monstrous minions, a heavy toll is due!");
                	System.out.println("I'll show you the might of creatures that dwell in the depths of this code-infested realm!");
                    
                    c.battle(hero, boss);
                    
                    if (boss.getHealthPoints() <= 0 && hero.getHealthPoints() > 0) {
                    	System.out.println("...\n\n# Enchanted Realm - Glorious Triumph\n\n"
                    	        + "Hail, " + hero.getName() + "!\n"
                    	        + "Your unwavering spirit and magical prowess have resulted in the triumph over the mighty Boss " + boss.getName() + " of the Shadow Abyss!\n\n"
                    	        + "Your valor and expertise in facing mystical challenges have brought forth an extraordinary victory.\n"
                    	        + "Now, the Enchanted Realm gleams with the radiance of success. A symphony of accolades echoes through the enchanted lands.\n\n"
                    	        + "The mystical beings of the realm regard you with awe. Your triumph has been etched into the scrolls of eternity.\n\n"
                    	        + "Congratulations, " + hero.getName() + " Sorcerer Supreme!\r\n");
                        break dungeonLoop;
                    }
                }
            } else {
                // If you are stuck, you die
            	System.out.println("...\n\n# Enchanted Realm - Mystic Obstruction\n\n"
            	        + "Alas!\n"
            	        + "Your journey through the Enchanted Realm has encountered an unforeseen magical barrier.\n"
            	        + "It appears you are ensnared in a mystical tangle of enchantments and perplexing conjurations.\n"
            	        + "The arcane forces of fate are not aligning in your favor at this moment.\n\n"
            	        + "The way forward is obstructed by an inscrutable spell. Progress is hindered, and the path remains veiled.\n\n"
            	        + "May the next incantation be in your favor. Prepare your magical debug wand and invocation of refactoring.\n");
                break dungeonLoop;
            }

            if (hero.getHealthPoints() <= 0) {
            	System.out.println("...\n\n# Enchanted Realm - Approaching Conundrum\n\n"
            	        + "Alas!\n"
            	        + "Your mystical reserves have swiftly diminished.\n"
            	        + "It appears your magical defenses have succumbed to the onslaught of enchantments and anomalies.\n"
            	        + "Your valiant quest in the Enchanted Realm approaches an unforeseen culmination.\n\n"
            	        + "May your next incantation session be guided by sagacity and indomitable spirit.\n"
            	        + "Remember: triumph lies just a refactor spell away. Best of luck, Mystic Warrior!\n\n"
            	        + "(YOU HAVE MET YOUR MYSTICAL END)");

                break dungeonLoop;
            }
        } while (true);

        input.close();
    }
}