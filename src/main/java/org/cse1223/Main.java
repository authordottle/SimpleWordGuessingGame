import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		boolean play = true;
		System.out.print("Enter a filename containing your wordlist: ");
		Scanner in = new Scanner(System.in);
		// file --> string
		ArrayList<String> a = getList(in);
		while (play == true) {
			// get word from array list
			String getWord = getRandomWord(a);
			// change to stars
			String star = starWord(getWord);
			String change = star;
			String guesses = "";
			int commasign = 0;
			int count = 1;

			int n = 0;// prevent getting out of the loop
			while (n == 0) {
				// separate into two parts
				// part 1 compare characters
				if (count % 2 != 0) {
					System.out.println("The word to guess is:" + change);
					System.out.println("Previous characters guessed: " + guesses);
					System.out.print("Enter a character to guess: ");

					// get character
					char character = getCharacterGuess(in);

					// comma sign
					if (commasign != 0) {
						guesses = guesses + "," + character;
					} else {
						guesses = guesses + character;
						commasign++;
					}

					//
					ArrayList<Character> chars = new ArrayList();
					for (int i = 0; i < getWord.length(); i++) {
						chars.add(getWord.charAt(i));
					}
					boolean check = checkInList(character, chars);
					if (check == true) {
						String uppercase = "";
						uppercase = uppercase + character;
						System.out.println("The character " + uppercase.toUpperCase() + " occurs in "
								+ checkChar(character, getWord) + " positions");
						change = modifyGuess(character, getWord, star);
						star = change;
					} else {
						System.out.println("The character is not in the word.");
					}

				}

				// part 2 compare word to word
				else {
					System.out.println("The word to guess is now:" + change);
					System.out.print("Enter your guess: ");
					String guessit = in.nextLine();
					boolean checkword = checkWord(guessit, getWord);
					if (checkword == true) {
						System.out.println("Congratulations! " + guessit + " is the correct word.");
						System.out.println("You achieved the correct answer in " + count + " guesses!");
						// Loop again?
						System.out.print("Would you like a rematch [y/n]?");
						String playagain = in.nextLine();
						if (playagain.equals("n")) {
							play = false;
						}
						n = 1;// get new word to guess
					} else {
						System.out.println("That is not the correct word.");
						System.out.println("Please guess another letter and try again.");
					}

				}

				count++;
			}

		}
		System.out.print("Thanks for playing!  Goodbye!");
	}

	// Given a Scanner as input, returns a List<String> of strings read from the
	// Scanner.
	// The method should read words from the Scanner until there are no more words
	// in the file (i.e. inScanner.hasNext() is false).
	// The list of strings should be returned to the calling program.
	private static ArrayList<String> getList(Scanner inScanner) throws FileNotFoundException {

		ArrayList<String> list = new ArrayList<String>();
		String a = inScanner.nextLine();
		File inFile = new File(a);
		if (!inFile.exists()) {
			System.out.println("File named " + inFile + " does not exist");
		} else {
			Scanner words = new Scanner(inFile);
			while (words.hasNextLine()) {
				String s = words.nextLine();
				list.add(s);
			}
			words.close();
			System.out.println("Read " + list.size() + " words from the file");
		}

		return list;
	}

	// Given two strings as input, compares the first string (guess) to the second
	// (solution) character by character. If the two strings are not exactly the
	// same, return false. Otherwise return true.
	private static boolean checkWord(String guess, String solution) {
		// Fill in the body
		boolean a = true;
		guess = guess.toUpperCase();
		solution = solution.toUpperCase();
		if (guess.length() != solution.length()) {
			a = false;
		} else {
			for (int i = 0; i < guess.length(); i++) {
				char g = guess.charAt(i);
				char s = solution.charAt(i);
				if (g != s) {
					a = false;
				}
			}
		}
		return a;
	}

	// Given a ArrayList<String> list of strings as input, randomly selects one of
	// the strings in the list and returns it to the calling program.
	private static String getRandomWord(ArrayList<String> inList) {
		// (int) ((Math.random() * (max - min)) + min);
		int index = (int) ((Math.random() * (inList.size() - 0)) + 0);
		String word = inList.get(index);
		return word;
	}

	// Given a Scanner as input, prompt the user to enter a character.
	// If the character enters anything other than a single character provide an
	// error message and
	// ask the user to input a single character. Otherwise return the single
	// character to the calling program.
	private static char getCharacterGuess(Scanner inScanner) {
		String in = inScanner.nextLine();
		while (in.length() != 1) {
			System.out.println("That is not the single character.");
			System.out.print("Please enter an single character:");
			in = inScanner.nextLine();
		}

		char character = in.charAt(0);

		return character;

	}

	// Given a character inChar and a ArrayList<Character> list of characters,
	// check to see if the character inChar is in the list. If it is, return true.
	// Otherwise, return false.
	private static boolean checkInList(char inChar, ArrayList<Character> inList) {
		boolean check = true;
		for (int i = 0; i < inList.size(); i++) {
			String assign1 = "";
			assign1 = assign1 + inChar;
			String assign2 = "";
			assign2 = assign2 + inList.get(i);
			if ((assign1.toUpperCase()).equals(assign2) || (assign1).equals(assign2)
					|| (assign1.toLowerCase()).equals(assign2)) {
				check = true;
				i = inList.size();// get rid of loop
			} else {
				check = false;
			}
		}
		return check;
	}

	// Given a String, return a String that is the exact same length but consists of
	// nothing but '*' characters. For example, given the String DOG as input,
	// return the string ***
	private static String starWord(String inWord) {
		String starword = "";
		for (int i = 0; i < inWord.length(); i++) {
			starword = starword + "*";
		}
		return starword;

	}

	// Given a character and a String, return the count of the number of times the
	// character occurs in that String.
	private static int checkChar(char guessChar, String guessWord) {
		int count = 0;
		for (int i = 0; i < guessWord.length(); i++) {
			if (guessWord.charAt(i) == guessChar || (guessWord.toLowerCase()).charAt(i) == guessChar) {
				count++;
			}
		}
		return count;

	}

	// Given a character, a String containing a word, and a String containing a
	// 'guess' for that word, return a new String that is a modified version of the
	// 'guess'
	// string where characters equal to the character inChar are uncovered.
	// For example, given the following call:
	// modfiyGuess('G',"GEOLOGY", "**O*O*Y")
	// This functions should return the String "G*O*OGY".
	private static String modifyGuess(char inChar, String word, String currentGuess) {
		String assign = "";
		for (int i = 0; i < word.length(); i++) {
			if (inChar == word.charAt(i) || inChar == (word.toLowerCase()).charAt(i)) {
				assign = assign + inChar;

			} else {
				assign = assign + currentGuess.charAt(i);
			}
		}
		return assign;
	}

}
