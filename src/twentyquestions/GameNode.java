//import the main package
package twentyquestions;

//begin the class declaration
public class GameNode {
    //set up the public data
    //make a string for holding the object's identity (either a question or answer)
    public String identity;
    //make nodes for the left and right subtree for yes and no respectively
    public GameNode yesSide; //left subtree
    public GameNode noSide; //right subtree
    
    //make a leaf node (answer) constructor where yes and no are both null
    public GameNode(String answer){
        //set the identity to being an answer
        this.identity = answer;
        //make the yes and no (left/right substrees) both null because this is an answer
        this.yesSide = null;
        this.noSide = null;
    }
    
    //make a constructor that has non-null game node subrees for adding questions
    public GameNode(String question, GameNode yes, GameNode no)
    {
        //set the question as the identity
        this.identity = question;
        //set the yes and no subtrees
        this.yesSide = yes;
        this.noSide = no;
    }
    
}