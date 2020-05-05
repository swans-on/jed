import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

public class JED {
	/** Java ed (jed) Text Editor **/
	public static void main(String[] args) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		Jedder<String> jed = new Jedder<String>();
		String command, line;
		
		do {
			System.out.println(": ");
			command = input.readLine();
			
			switch(command) {
				case "1": // Move insertion point to top
					while (!jed.atStart()) jed.backward();
					if (jed.lineNumsToggled()) System.out.print(jed.getLineNum() + " ");
					if (!jed.isEmpty()) System.out.println(">" + jed.current()); // Print the current insertion point
					break;
					
				case "a": // Adds lines after the insertion point
					do {
						line = input.readLine();
						if (line.length() == 1 && line.charAt(0) == '.') // '.' on its own line ends insertion mode.
							break;
						jed.insert(line);
					} while (true);
					if (!jed.isEmpty()) System.out.println(">" + jed.current()); // Print the current insertion point
					break;
					
				case "d": // Deletes the current line
					if (jed.isEmpty()) {
						System.out.println("?"); // If nothing has been written.
					} else {
						jed.delete();
						if (!jed.isEmpty()) System.out.println(jed.current()); // Print the current insertion point
					}
					break;
				
				case "h": // Print help screen
					jed.help();
					break;
						
				case "i": // Insert lines before the insertion point
					int numOfMoves = 0;
					do {
		
						line = input.readLine();
						if (line.length() == 1 && line.charAt(0) == '.') // '.' on its own line ends insertion mode.
							break;
						jed.backward();
						jed.insert(line);
						numOfMoves++;
					} while (true);
					if (!jed.isEmpty()) System.out.println("> " + jed.current()); // Print the current insertion point
					
					// Reset insertion point position to original position
					for (int i = 0; i < numOfMoves; i++) jed.forward();
					break;
				
				case "n": // Toggle line numbers
					jed.toggleLineNums();
					break;
				
				case "p": // Print current line
					System.out.println("> " + jed.getLine());
					break;
				
				case "q!": // Quit without saving
					System.out.println("Exiting...");
					System.exit(0);
					
				case "w": // Write to file
					System.out.print("Enter file name: ");
					line = input.readLine();
					jed.write(line);
					break;
				
				case "x!": // Write and quit
					System.out.print("Enter file name: ");
					line = input.readLine();
					jed.write(line);
					System.out.println("Exiting...");
					System.exit(0);
				
				case "$": // Move insertion point to the bottom
					while (!jed.atEnd()) jed.forward();
					if (jed.lineNumsToggled()) System.out.print(jed.getLineNum() + " ");
					if (!jed.isEmpty()) System.out.println(">" + jed.current()); // Print the current insertion point
					break;
					
				case "-": // Move insertion point up one
					jed.backward();
					if (!jed.isEmpty()) System.out.println("> " + jed.current());
					break;
				
				case "+": // Move insertion point down one
					jed.forward();
					if (!jed.isEmpty()) System.out.println("> " + jed.current());
					break;
				
				case "=": // Print current line number
					System.out.println(jed.getLineNum());
					break;
				
				case "#": // Print number of lines and characters
					System.out.println("Lines: " + jed.size());
					System.out.println("Characters: " + jed.numOfChars());
					break;
					
				default:
					System.out.println("?");
				}
				
		} while (command != "q!");
	}
}
