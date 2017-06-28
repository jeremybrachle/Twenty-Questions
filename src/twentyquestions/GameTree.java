//import the main package
package twentyquestions;

//import util for Scanner capability for user inputs
import java.util.*;
//import io for file processing
import java.io.*;

//begin the class declaration
public class GameTree {
    //root node (make it private so only this class can access it)
    private GameNode treeRoot;
    //scanner for getting user input
    private Scanner gameInput = new Scanner(System.in);
    //string for recording answers
    private String playerResponse = "";
    //make a counter for the number of questions asked
    private int counter = 0;
    
    //make a constructor that creates a default root node
    public GameTree()
    {
        //make a default root that will be overridden
        treeRoot = new GameNode("Blank Root");
    }
    
    //make a public create tree method:
    public void generateTree(Scanner file){
        //set the root equal to the tree built by the recursive call
        treeRoot = recursiveGenerate(file);
    }
    
    //make a private recusrive create tree method
    public GameNode recursiveGenerate(Scanner file)
    {
        //get the line:
        String line = file.nextLine();
        
        //check if question or answer
        if (getType(line) == "Question")
        {
            //get the question (strip the question text)
            String question = line.replaceAll("Question\\|", "");
            //make GameNode with two children (for yes/no answer):
            return new GameNode(question, recursiveGenerate(file), recursiveGenerate(file));
        }
        else
        {
            //get the answer (strip the answer text)
            String answer = line.replaceAll("Answer\\|", "");
            //create GameNode leaf with no children:
            return new GameNode(answer);
        }
        
    }
    
    //get the type (question or answer)
    private String getType(String line)
    {
        //return String
        String type = "";
        
        //System.out.println(line.charAt(0));
        //check the first character:
        if (line.charAt(0) == 'Q')
        {
            type = "Question";
        }
        else
        {
            type = "Answer";
        }
        
        return type;
    }
    
    //public method for playing the game
    public void playGame()
    {
        //send the root to the private recursive method
        recursivePlay(treeRoot);
    }
        
    private GameNode recursivePlay(GameNode currentNode)
    {
        //check if at a leaf node (answer)
        if (currentNode.yesSide == null && currentNode.noSide == null)
        {
            //increment the counter:
            counter++;
            //ask if the user is thinking of this answer:
            System.out.println(counter + ": Are you thinking of " + currentNode.identity + "?");
            //get the user input
            playerResponse = gameInput.nextLine();
            
            //if the computer guessed right
            if (playerResponse.equals("y"))
            {
                //tell the user that the computer is right
                System.out.println("The computer guessed right");
                //check if less than 20 guesses
                if (counter <= 20)
                {
                    System.out.println("The computer wins!");
                }
                else
                {
                    System.out.println("Took more than 20 guesses, therefore you win!");
                }
                //reset the counter
                counter = 0;
            }
            else
            {
                //Ask what the user was thinking of:
                System.out.println("The computer could not guess your object");
                System.out.println("You win!");
                //reset the counter
                counter = 0;
                System.out.println("What object were you thinking of?");
                String newObject = "";
                newObject = gameInput.nextLine();
                //ask what type of question would distinguish this object from the previous object
                System.out.println("Please provide a yes or no question to distinguish this object from the one the computer guessed");
                String newQuestionText = "";
                newQuestionText = gameInput.nextLine();
                System.out.println("Provide 'y' or 'n' for the answer to the question for your object");
                String newAnswer = "";
                newAnswer = gameInput.nextLine();
                
                //add to the tree depending on whether the answer was yes or no
                if (newAnswer.equals("y"))
                {
                    //if yes subtree
                    //create a new leaf node for this new leaf node object for yes answer
                    GameNode newLeaf = new GameNode(newObject);
                    //make a copy of the previous leaf node (for no answer)
                    GameNode previousLeaf = new GameNode(currentNode.identity);
                    //make a new node for the new question to replace the old leaf node on the tree
                    //note: put the newLeaf as the second argument since it is "yes" to the question
                    GameNode newQuestionNode = new GameNode(newQuestionText, newLeaf, previousLeaf);
                    //update the current node to be the new question node
                    currentNode = newQuestionNode;
                }
                else if (newAnswer.equals("n"))
                {
                    //if no subtree
                    //create a new leaf node for this new leaf node object for no answer
                    GameNode newLeaf = new GameNode(newObject);
                    //make a copy of the previous leaf node (for yes answer)
                    GameNode previousLeaf = new GameNode(currentNode.identity);
                    //make a new node for the new question to replace the old leaf node on the tree
                    //note: put the newLeaf as the third argument since it is "no" to the question
                    GameNode newQuestionNode = new GameNode(newQuestionText, previousLeaf, newLeaf);
                    //update the current node to be the new question node
                    currentNode = newQuestionNode;
                }
                
            }
        }
        //if a question node:
        else 
        {
            //increment the question counter:
            counter++;
            //get the question:
            System.out.println(counter + ": " + currentNode.identity);
            //get the user's answer:
            playerResponse = gameInput.nextLine();
            //if yes
            if (playerResponse.equals("y"))
            {
                //go down the yes subtree recursively by assigning the current yes node back
                currentNode.yesSide = recursivePlay(currentNode.yesSide);
            }
            //if no
            else if (playerResponse.equals("n"))
            {
                //go down the no subtree recursively by assigning the current no node back
                currentNode.noSide = recursivePlay(currentNode.noSide);
            }
            
        }
        
        return currentNode;
    }
    
    //public method for overwriting
    public void overWriteFile(PrintStream f)
    {
        //send to the private recursive method
        recursiveOverWrite(treeRoot, f);
    }
    
    private void recursiveOverWrite(GameNode currentNode, PrintStream output)
    {
        //make sure there is still a node to traverse
        if (currentNode != null)
        {
            //print out the questions
            if (currentNode.yesSide != null || currentNode.noSide != null)
            {
                //make a question string
                String question = ("Question|" + currentNode.identity);
                //output to the file
                output.println(question);
            }
            //print out the answers
            else
            {
                //make an answer string
                String answer = ("Answer|" + currentNode.identity);
                //output to the file
                output.println(answer);
            }
            //recursively descend down the yes subtree
            recursiveOverWrite(currentNode.yesSide, output);
            //recursively descend down the no subtree
            recursiveOverWrite(currentNode.noSide, output);
        }
    }
    
}
