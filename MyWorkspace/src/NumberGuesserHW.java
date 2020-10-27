import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class NumberGuesserHW {
	private int level = 1;
	private int strikes = 0;
	private int maxStrikes = 5;
	private int remainder = 0;
	private int number = 0;
	private boolean isRunning = false;
	private int compBetMoney = 0;
	final String saveLevelFile = "numberGuesserSaveL.txt";
	final String saveGuessFile = "numberGuesserSaveG.txt";
	final String saveStrikesFile = "numberGuesserSaveS.txt";
	final String saveBetFile = "numberGuesserSaveB.txt";

	/***
	 * Gets a random number between 1 and level.
	 * 
	 * @param level (level to use as upper bounds)
	 * @return number between bounds
	 */
	public static int getNumber(int level) {
		int range = 9 + ((level - 1) * 5);
		System.out.println("I picked a random number between 1-" + (range + 1) + ", let's see if you can guess.");
		return new Random().nextInt(range) + 1;
	}

	private void win() {
		System.out.println("That's right!");
		level++;// level up!
		strikes = 0;
		compBetMoney = compBetMoney * level;
		System.out.println("You won $" + compBetMoney);
		System.out.println("Welcome to level " + level + "\n");
		number = getNumber(level);
		saveLevel();
		saveGuess();
		saveStrikes();
		saveBet();
	}

	private void lose() {
		System.out.println("\nUh oh, looks like you need to get some more practice.");
		System.out.println("The correct number was " + number);
		level--;
		compBetMoney = 0;
		if (level <= 0) {
			level = 1;
		}
		System.out.println("\nYou went back to level " + level);
		if (remainder < 1) {
			strikes = 0;
		}
		if (compBetMoney == 0) {
			System.out.println("You lost your money \n");
		}

		strikes = remainder;
		number = getNumber(level);
		saveLevel();
		saveGuess();
		saveStrikes();
		saveBet();
	}

	private void processCommands(String message) throws IOException {
		if (message.equalsIgnoreCase("quit")) {
			System.out.println("Tired of playing? No problem, see you next time.");
			isRunning = false;
		}
		if (message.equalsIgnoreCase("bet")) {
			System.out.println("How much (in USD [$])");
			isRunning = true;
			try (Scanner scan = new Scanner(System.in)) {
				takeMoney(scan.nextLine());
				saveBet();

			}
		}
	}

	private int takeMoney(String bet) {
		int userMoney = 0;

		try {
			userMoney = Integer.parseInt(bet);
		} catch (NumberFormatException e) {
			System.out.println("You didn't enter a valid amount, please try again");
		}
		compBetMoney = userMoney;
		System.out.println("You bet $" + compBetMoney);
		return compBetMoney;
	}

	// private int processBet(int bet) {
	// if (bet > 0) {
	// return bet;
	// }
	// System.out.println("You bet $" + compBetMoney);
	// }

	private void processGuess(int guess) {
		if (guess < 0) {
			return;
		}
		System.out.println("You guessed " + guess);
		if (guess == number) {
			win();
		} else {
			System.out.println("That's wrong");
			strikes++;
			saveStrikes();
			if (strikes >= maxStrikes) {
				lose();
			} else {
				remainder = maxStrikes - strikes;
				System.out.println("You have " + remainder + "/" + maxStrikes + " attempts remaining\n");
				if (guess > number) {
					System.out.println("Lower");
				} else if (guess < number) {
					System.out.println("Higher");
				}
			}
		}
	}

	private int getGuess(String message) {
		int guess = -1;
		try {
			guess = Integer.parseInt(message);
		} catch (NumberFormatException e) {
			System.out.println("You didn't enter a number, please try again");

		}
		return guess;
	}

	private void saveBet() {
		try (FileWriter fw = new FileWriter(saveBetFile)) {
			fw.write("" + compBetMoney);// here we need to convert it to a String to record correctly
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void saveLevel() {
		try (FileWriter fw = new FileWriter(saveLevelFile)) {
			fw.write("" + level);// here we need to convert it to a String to record correctly
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void saveGuess() {
		try (FileWriter fw = new FileWriter(saveGuessFile)) {
			fw.write("" + number);// here we need to convert it to a String to record correctly
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void saveStrikes() {
		try (FileWriter fw = new FileWriter(saveStrikesFile)) {
			fw.write("" + strikes);// here we need to convert it to a String to record correctly
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private boolean loadBet() {
		File file = new File(saveBetFile);
		if (!file.exists()) {
			return false;
		}
		try (Scanner reader = new Scanner(file)) {
			while (reader.hasNextLine()) {
				int _bet = reader.nextInt();
				if (_bet > 0) {
					compBetMoney = _bet;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}
		return compBetMoney > 0;
	}

	private boolean loadLevel() {
		File file = new File(saveLevelFile);
		if (!file.exists()) {
			return false;
		}
		try (Scanner reader = new Scanner(file)) {
			while (reader.hasNextLine()) {
				int _level = reader.nextInt();
				if (_level > 1) {
					level = _level;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}
		return level > 1;
	}

	private boolean loadGuess() {
		File file = new File(saveGuessFile);
		if (!file.exists()) {
			return false;
		}
		try (Scanner reader = new Scanner(file)) {
			while (reader.hasNextLine()) {
				int _guess = reader.nextInt();
				if (_guess > 1) {
					number = _guess;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}
		return number > 0;
	}

	private boolean loadStrikes() {
		File file = new File(saveStrikesFile);
		if (!file.exists()) {
			return false;
		}
		try (Scanner reader = new Scanner(file)) {
			while (reader.hasNextLine()) {
				int _strikes = reader.nextInt();
				if (_strikes > 0) {
					strikes = _strikes;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}
		return strikes >= 1;
	}

	private boolean loadGame() {
		if ((loadLevel()) && (loadGuess()) && (loadStrikes())) {
			return true;
		}
		return false;
	}

	void run() {
		try (Scanner input = new Scanner(System.in);) {
			System.out.println("Welcome to Number Guesser 4.0!");
			System.out.println("I'll ask you to guess a number between a range, and you'll have " + maxStrikes
					+ " attempts to guess.\n");
			if (loadGame()) {
				System.out.println("Successfully loaded Game to level " + level + " let's continue then");
				System.out.println("You had " + strikes + " strike(s)\nGOOD LUCK!");
			}
			// number = getNumber(level);
			isRunning = true;
			while (input.hasNext()) {
				String message = input.nextLine();
				processCommands(message);
				if (!isRunning) {
					break;
				}
				if (!message.equalsIgnoreCase("bet")) {
					int guess = getGuess(message);
					processGuess(guess);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		NumberGuesserHW guesser = new NumberGuesserHW();
		guesser.run();
	}
}