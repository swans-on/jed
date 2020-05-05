import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * <h1>AUTHOR:  Jason Swanson</h1>
 * <h1>TITLE:   Unit 11 Homework</h1>
 * <h1>DATE:    4/7/19</h1>
 * <h1>CLASS:   CS 3363 001R Data Structures</h1>
 * <p>
 *     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * <p>
 * 	   17.19: 	 Write a line-based text editor.
 * <p>
 * 	   OUTLINE: This program is a line-based text editor using a linked-linked list implementation.
 * 				It is similar to ed (as per the assignment description) in terms of its commands.
 * 				Hence, I have titled the program jed, which is short for Java ed. To see the list of commands
 * 				type "h" on the program. Any files that are written are saved to the /17.19/ folder.
 * <p>
 * 
 * @author Jason Swanson
 * @version 1.0
 * @since 2019-03-31
 */

public class Jedder<AnyType> {
	private Node<AnyType> insertionPoint = null;
	private int size = 0;
	boolean lineNums = false;

	/**
	 * isEmpty()
	 * Returns true if the list is empty.
	 * @return boolean
	 */
	public boolean isEmpty() {
		return insertionPoint == null;
	}

	/**
	 * atEnd()
	 * Returns true if the insertion point is at the end of the list
	 * @return boolean
	 */
	public boolean atEnd() {
		return insertionPoint.next == null;
	}

	/**
	 * atStart()
	 * Returns true if the insertion point is at the start of the list
	 * @return boolean
	 */
	public boolean atStart() {
		return insertionPoint.prev == null;
	}

	/**
	 * insert()
	 * Inserts new data into the list based on the location of the insertion point.
	 * @param data to be added
	 */
	public void insert(AnyType data) {
		if (insertionPoint == null) { // If list is empty
			insertionPoint = new Node<AnyType>(null, data, null);
		} else if (insertionPoint.next == null) { // If there is only one object in the list
			insertionPoint.next = new Node<AnyType>(insertionPoint, data, null); // Set the next object to the input
			insertionPoint = insertionPoint.next; // Move the insertion point
		} else {
			insertionPoint.next.prev = new Node<AnyType>(insertionPoint, data, insertionPoint.next); // Add data to the insertion point location
			insertionPoint.next = insertionPoint.next.prev;
			insertionPoint = insertionPoint.next; // Move the insertion point
		}

		size++;
		if (lineNums) System.out.print((getLineNum() + 1) + " "); // Include line numbers if toggled.
	}

	/**
	 * delete()
	 * Deletes an object in the list based on the location of the insertion point.
	 */
	public void delete() {
		if (insertionPoint.next == null) {
			insertionPoint = insertionPoint.prev;
			if (insertionPoint != null) insertionPoint.next = null;
		} else {
			insertionPoint.next.prev = insertionPoint.prev;
			if(insertionPoint.prev != null) insertionPoint.prev.next = insertionPoint.next;
			insertionPoint = insertionPoint.next;
		}
	}

	/**
	 * forward()
	 * Moves the insertion point forward (to the right) in the list.
	 */
	public void forward() {
		if (!atEnd()) insertionPoint = insertionPoint.next;
		if (lineNums) System.out.print(getLineNum() + " ");
	}

	/**
	 * backward()
	 * Moves the insertion point backward (to the left) in the list.
	 */
	public void backward() {
		if (!atStart()) insertionPoint = insertionPoint.prev;
		if (lineNums) System.out.print(getLineNum() + " ");
	}

	/**
	 * current();
	 * Returns the data of the current position of the insertion point.
	 */
	public AnyType current() {
		return insertionPoint.data;
	}

	/**
	 * getLine()
	 * Returns a line of the current insertion point location
	 * @return AnyType single line
	 */
	public AnyType getLine() {
		if (lineNums) System.out.print(getLineNum() + " ");
		return insertionPoint.data;
	}

	/**
	 * getLineNum()
	 * Returns the line number of the current insertion point location
	 * @return
	 */
	public int getLineNum() {
		Node<AnyType> temp;

		if (insertionPoint != null) {
			for (temp = insertionPoint; temp.prev != null; temp = temp.prev); // Set temp to the front of the list.
			for (int i = 0; i < size; i++) {
				if (temp.equals(insertionPoint))
					return i + 1;
				temp = temp.next;
			}
		}
		return -1;
	}

	/**
	 * toggleLineNums()
	 * Enables line numbers to be displayed on insertion mode
	 * and line prints.
	 */
	public void toggleLineNums() {
		if (lineNums) {
			lineNums = false;
			System.out.println("Line numbers are now off.");
		} else {
			lineNums = true;
			System.out.println("Line numbers are now on.");
		}
	}

	/**
	 * lineNumsToggled()
	 * Returns true if line numbers are toggled.
	 * @return
	 */
	public boolean lineNumsToggled() {
		return lineNums;
	}

	/**
	 * write()
	 * Writes the list to a file. It is placed in the project folder.
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public void write(String filename) throws FileNotFoundException {
		PrintWriter printWriter = new PrintWriter(filename);
		Node<AnyType> temp;

		if (insertionPoint != null) {
			for (temp = insertionPoint; temp.prev != null; temp = temp.prev);
			for (; temp != null; temp = temp.next) {
				printWriter.println(temp.data);
			}
		}
		printWriter.close();
		System.out.println("Wrote file: " + filename + " to the current directory.");
	}
	
	/**
	 * size()
	 * Returns the size of the list.
	 * @return int size of lise
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns the number of characters in the list.
	 * @return int total chars
	 */
	public int numOfChars() {
		int charCount = 0;
		
		Node<AnyType> temp;

		if (insertionPoint != null) {
			for (temp = insertionPoint; temp.prev != null; temp = temp.prev);
			for (; temp != null; temp = temp.next) {
				charCount += temp.data.toString().length();
			}
		}
		
		return charCount;
	}

	/**
	 * help()
	 * Prints the help screen with a list of commands
	 */
	public void help() {
		System.out.println("Java ed commands");
		System.out.println("1 - Go to the top.");
		System.out.println("a - Add text after current line until . on its own line");
		System.out.println("d - Delete current line.");
		System.out.println("h - Get help.");
		System.out.println("i - Like append, but add lines before current line.");
		System.out.println("n - Toggle whether line numbers are displayed.");
		System.out.println("p - Print current line.");
		System.out.println("q! - Abort without write.");
		System.out.println("w - Write file to disk.");
		System.out.println("x! - Exit with write.");
		System.out.println("$ - Go to the last line.");
		System.out.println("- - Go up one line.");
		System.out.println("+ - Go down one line.");
		System.out.println("= - Print current line number.");
		System.out.println("# - Print number of lines and characters in file.");
	}

	/**
	 * toString()
	 * Returns a string containing each line in the list.
	 * @return String lines in list
	 */
	public String toString() {
		Node<AnyType> temp;
		String str = "";

		if (insertionPoint != null) {
			for (temp = insertionPoint; temp.prev != null; temp = temp.prev);
			for (; temp != null; temp = temp.next) {
				if (temp == insertionPoint)
					str += "> "; // This indicates the insertion point.
				else
					str += " ";
				str += temp.data + "\n";
			}
		}
		return str;
	}

	/**
	 * Utility class used to create node objects for the LinkedList.
	 */
	class Node<AnyType> {
		Node<AnyType> prev;
		Node<AnyType> next;
		AnyType data;

		/** CONSTRUCTOR **/
		public Node(Node<AnyType> prev, AnyType data, Node<AnyType> next) {
			this.prev = prev;
			this.next = next;
			this.data = data;
		}

		/**
		 * Returns true if the content of the two nodes are equal. 
		 * @param node to be compared
		 * @return true if equal
		 */
		public boolean equals(Node<AnyType> node) {
			if (node.data.equals(data))
				return true;
			return false;
		}
	}
}