package utility;

import java.util.Scanner;

public class Map {

	private String[][] mapData = {
			{" ", " ", " ", " ", " ", " ", " "},
			{" ", " O ", " O ", " O ", " O ", " \u001B[32mB\u001B[0m ", " ","         Legend\n"},
			{" ", " O ", " O ", " O ", " O ", " O ", " ","         \u001B[32mB\u001B[0m = Boss\n"},
			{" ", " O ", " O ", " O ", " O ", " O ", " ","         \u001B[34mP\u001B[0m = Character \n"},
			{" ", " O ", " O ", " O ", " O ", " O ", " ","         \u001B[31mX\u001B[0m = Visited Rooms\n"},
			{" ", " \u001B[34mP\u001B[0m ", " O ", " O ", " O ", " O ", " ","         O = Rooms to Visit\n"},
			{" ", " ", " ", " ", " ", " ", " "}
	};

	private boolean checkStuck = false;
	private boolean checkBoss = false;

	private int playerX = 1;
	private int playerY = 5;

	Scanner keyboard = new Scanner(System.in);

	public void startGame() {

		MapLoop: while (true) {
			drawMinimap();

			int newX = playerX;
			int newY = playerY;

			if (newX == 5 && newY == 1) {
				this.checkBoss = true;
				System.out.println("\nBOSS ROOM\n");
				break MapLoop;
			}

			if ((mapData[newY + 1][newX].equals(" \u001B[31mX\u001B[0m ") || mapData[newY + 1][newX].equals(" ")) &&
					(mapData[newY - 1][newX].equals(" \u001B[31mX\u001B[0m ") || mapData[newY - 1][newX].equals(" ")) &&
					(mapData[newY][newX + 1].equals(" \u001B[31mX\u001B[0m ") || mapData[newY][newX + 1].equals(" ")) &&
					(mapData[newY][newX - 1].equals(" \u001B[31mX\u001B[0m ") || mapData[newY][newX - 1].equals(" "))) {
				this.checkStuck = true;
				return;
			}

			System.out.println("Choose a direction (w up, s down, a left, d right): ");

			String direction = keyboard.nextLine();

			switch (direction) {
			case "w": // Up
				newY = playerY - 1;
				break;
			case "s": // Down
				newY = playerY + 1;
				break;
			case "a": // Left
				newX = playerX - 1;
				break;
			case "d": // Right
				newX = playerX + 1;
				break;
			default:
				System.out.println("\nInvalid choice. Try again.");
				continue;
			}

			if (newX == 5 && newY == 1) {
				this.checkBoss = true;
				System.out.println("\nBOSS ROOM\n");
				break MapLoop;
			}

			if (isValidMove(newX, newY)) {

				// Clear the current player position
				mapData[playerY][playerX] = " \u001B[31mX\u001B[0m ";
				// Update the player position
				playerX = newX;
				playerY = newY;
				// Draw the player in the new position
				mapData[playerY][playerX] = " \u001B[34mP\u001B[0m ";
				break MapLoop;
			} else {
				System.out.println("\nInvalid move. Try again.");
			}
		}
	}

	public void drawMinimap() {
		for (String[] row : mapData) {
			for (String cell : row) {
				System.out.print(cell + " ");
			}
			System.out.println();
		}
	}

	public boolean isValidMove(int x, int y) {
		// Check if the new position is within the map boundaries
		return x >= 0 && x < 6 && y >= 0 && y < 6 && !mapData[y][x].equals(" ") && !mapData[y][x].equals(" \u001B[31mX\u001B[0m ");
	}

	public boolean isCheckStuck() {
		return checkStuck;
	}

	public boolean isCheckBoss() {
		return checkBoss;
	}
}