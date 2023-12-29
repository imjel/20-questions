/**
 * Node for binary tree
 * @author Max Endieveri, with guidance from professors A.Burns and Tayloe at MHC 2023
 */
public class Node {
		String data;
		Node left;
		Node right;
		
		// constructor creates a node with data
		public Node(String data){
			this.data = data;
		}
	
		/**
		 * @return data stored at this node
		 */
		public String getData(){
			return data;
		}
	
		/**
		 * Set data stored at this node
		 */
		public void setData(String data){
			this.data = data;
		}
	
		/**
		 * @return left child node
		 */
		public Node getLeftNode(){
			return left;
		}
	
		/**
		 * @return right child node
		 */
		public Node getRightNode(){
			return right;
		}
	
		/**
		 * set left child node
		 */
		public void setLeftNode(Node setLeft){
			left = setLeft;
		}
	
		/**
		 * set right child node
		 */
		public void setRightNode(Node setRight){
			right = setRight;
		}
	
		/**
		 * check if the node is a leaf (has no children)
		 * @return true if it is a leaf node
		 */
		public boolean isLeaf(){
			return (left == null) && (right == null);
		}
	
	}
