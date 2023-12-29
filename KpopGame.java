/**
 * 20 Questions style game that asks yes or no questions, narrowing down to the right answer. Additionally learns from incorrect answers, allowing the user to add to the tree.
 * @author Max Endieveri
 * @version April 2023
 */

import java.io.*;
import java.util.Scanner;

public class KpopGame {
	
	GameTree tree;
	Node current;

	/**
	 * Constructor
	 * @param filename to build a tree
	 */
	public KpopGame(String filename){
		tree = new GameTree(filename);
	}

	/**
	 * separates answers (tree leaves) from questions
	 * @param node the tree's root
	 */
	public void printLeaf(Node node){
		if(node.isLeaf()){
			System.out.println(node.getData());
		}
		else{
			if(node.getLeftNode() != null){
				printLeaf(node.getLeftNode());
			}
			if (node.getRightNode() != null){
				printLeaf(node.getRightNode());
			}
		}
	}
	/**
	 * asks question and receives answer
	 * @param question
	 * @param scan takes user input; scanner is opened in main
	 * @return true if yes, false if no
	 */
	public static boolean askQuestion(String question, Scanner scan){
		String answer;
		System.out.println(question);
		answer = scan.nextLine();
		
		if (answer.equals("y")){
			return true;
		}
		else if(answer.equals("n")){
			return false;
		}
		else{
			System.out.println("\nEnter a valid input (y/n)\n");
			return (askQuestion(question, scan));
		}
	}

	/**
	 * Main game loop
	 * @param scan takes user input; scanner is opened in main
	 */
	public void playGame(Scanner scan){
		
		System.out.println("\nThink of a subject from the following list:\n");
		current = tree.getRoot();
		printLeaf(current);
		System.out.println("\n");

		// loops while the node is a question
		while (!(current).isLeaf()){
			// if question is TRUE, moves left
			if (askQuestion(current.getData(), scan)){
				current = current.getLeftNode();
			}
			// if question is FALSE, moves right
			else{
				current = current.getRightNode();
			}
		}
		// if we have ended up at a leaf, asks if we guessed correctly
		if (askQuestion("\nIs it " + current.getData() + "?\n", scan)){
			System.out.println("\nI got it, Hooray!\n");
		}

		// if we guessed incorrectly, then learns what the user was thinking of, to update the tree
		else{
			System.out.println("\nWhat thing were you thinking of?\n");
			String newThing = scan.nextLine();
			System.out.println("\nPlease give me a yes or no question that would have determined your thing.\n");
			Node newNode = new Node(scan.nextLine());
			String prompt = "\nIs the answer to your question \"(y)es\" or \"(n)o?\"\n";

			// gets the current's parent node to know position of current (L or R)
			Node parent = tree.getParent(current);

			// modifying the GameTree appropriately:
			// 1) set the new question node as a child of the current node
			if (parent == null) {
				tree.setRoot(newNode);
			} else if (parent.getLeftNode() == current) {
				parent.setLeftNode(newNode);
			} else {
				parent.setRightNode(newNode);
			}

			// 2) if the answer is "yes" to the user's question, updates tree
			if(askQuestion(prompt, scan)){
				// sets current to newQuestion's right child and newThing as newQuestion's left child
				newNode.setLeftNode(new Node(newThing));
				newNode.setRightNode(current);
			}
			// 2) if the answer is "no" to the user's question, updates tree
			else{
				newNode.setLeftNode(new Node(current.getData()));
				newNode.setRightNode(new Node(newThing));
			}

		}

	}

	/**
	 * Main function to loop the game
	 * @param args - command line
	 */
	public static void main(String[] args) throws FileNotFoundException{
		
		Scanner scan = new Scanner(System.in);
		// first, asks if the user would like to play
		System.out.println("Would you like to play a guessing game? (y/n)");
		String input = scan.nextLine();

		// txt file run as command line argument
		KpopGame game = new KpopGame(args[0]);
		
		// game loop
		while (!input.equals("n")){
			if (input.equals("y")){
				game.playGame(scan);
			}
			// in the case of invalid input (not "y" or "n")
			else{
				System.out.println("Please enter a valid input (y/n)\n");
			}
			// After the game loop completes, asks the user to play again
			System.out.println("Would you like to play again? (y/n)\n");
			input = scan.nextLine();
		}
		// scanner closes when game is over
		scan.close();
	}
}
