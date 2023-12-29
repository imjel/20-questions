/**
 * Binary tree for guessing game
 * @author Max Endieveri, with guidance from professors A.Burns and Tayloe at MHC 2023
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class GameTree {

	
	
	// root node
	private Node root;

	// LL that holds file values
	private LinkedList<String> values;

	/**
	 * Constructor creates an empty tree
	 */
	public GameTree(){
		root = null;
	}

	/**
	 * Constructor creates a tree based on the file passed by the user 
	 * @param filename
	 */
	public GameTree(String filename){
		root = buildTree(filename);
	}

	/**
	 * set root node
	 */
	public void setRoot(Node root){
		this.root = root;
	}

	/**
	 * @return root node
	 */
	public Node getRoot(){
		return root;
	}

	/**
	 * @return true if tree is empty
	 */
	public boolean isEmpty(){
		return root == null;
	}

	/**
	 * Build the tree based on the filename
	 * @param filename of .txt file holding data for the tree
	 */
	public Node buildTree(String filename){
		// all files put into list
		values = new LinkedList<String>();
		try {
			File infile = new File(filename);
			Scanner inData = new Scanner(infile);

			// while there is still more data, loop runs
			while (inData.hasNext()){
				// trim new lines after adding
				values.addLast(inData.nextLine().stripTrailing());
			}
			// close scanner
			inData.close();

			// recursion on new list
			return recursiveParse();
		} catch (FileNotFoundException exception){
			System.out.println("Can't find " + filename);
			exception.printStackTrace();
			return null;
		}
	}

	public Node recursiveParse(){
		// start with first line 
		String line = values.pop();
		// count tabs
		int tabCount = countTabs(line);

		Node node = new Node(line.substring(tabCount));

		// check if there are enough tabs to add two children
		if (values.size() > 1){
			line = values.peek();
			if(countTabs(line) > tabCount){
				node.setLeftNode(recursiveParse());
				node.setRightNode(recursiveParse());
			}
		}
		return node;
	}

	/**
	 * @param string with tabs
	 * @return amount of tabs in string
	 */
	public int countTabs(String string){
		char tab = '\t';
		int counter = 0;

		for(int i = 0; i < string.length(); i++){
			if(string.charAt(i) == tab){
				counter++;
			}
		}
		return counter;
	}

	/**
	 * @return string with in order traversal of tree
	 */
	public String toString(){
		return inOrderTraversal(root);
	}

	public String inOrderTraversal(Node node){
		String res = "";
        if (!node.isLeaf()){
            res = res + inOrderTraversal(node.getLeftNode());
        }

        res = res + node.getData()+"\n";

        if (!node.isLeaf()){
            res = res + inOrderTraversal(node.getRightNode());
        }

        return res;
	}


	/**
	 * get parent of a ggiven node
	 * @param node whose parent we are looking for
	 * @return parent node
	 */
	public Node getParent(Node node){
		if(node == root){
			return null;
		}
		return getNodeParent(root, node);
	}

	/**
	 * recursive helper for getParent
	 * @param current
	 * @param node whose parent we are looking for
	 * @return parent node
	 */
	public Node getNodeParent(Node current, Node targetNode){
		if(current == null){
			return null;
		}
		// base case: if the current node's children = the node we pass through, then it is the parent of that node
		else if (current.getLeftNode() == targetNode || current.getRightNode() == targetNode){
			return current;
		}
		// recursive step: check the other subtrees if we do not find the parent in the current one
		Node parent = getNodeParent(current.getLeftNode(), targetNode);
		if(parent == null){
			parent = getNodeParent(current.getRightNode(), targetNode);
		}
		return parent;
	}

	/**
	 * @param root node
	 * @return preorder traversal representation
	 */
	public String preOrderString(Node root){
		return preOrderString(root);
	}

	/**
	 * helper function for preorder traversal
	 * @param current node
	 */
	public String preOrderTraversal(Node current){
		String tree = "";

		tree = tree + current.getData() + "\n";
		
		if(current.getLeftNode() != null){
			tree = tree + preOrderTraversal(current.getLeftNode());
		}
		
		if(current.getRightNode() != null){
			tree = tree + preOrderTraversal(current.getRightNode());
		}

		return tree;
	}
}
