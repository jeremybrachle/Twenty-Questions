# Twenty-Questions
This program is a game of Twenty Questions implemented in Java. The program reads in a text file with yes or no questions, their answers, and the objects that can be guessed and then stores the data into a binary tree. The questions will be stored as internal nodes with the objects being the leaf nodes. Depending on whether the answer to the question is yes or no, the tree will store the subsequent question or object is the left or right subtree respectfully. The program will update the tree if the user guesses an object that is not currently known to the program. The user will have the choice to overwrite the text file for future games after the program has terminated. The program reads the text files from command line arguments.  
Here is a basic demonstration for how the text files are set up: (note: comments are denoted by // but cannot be used in the actual text files)

---

Question|Is the computer portable? //yes or no question

Answer|Laptop //answer if the user says yes

Answer|Desktop //answer if the user says no

---

Using the above format, textfiles of a particular theme can be easily set up and then expanded upon by playing the game and adding more questions and answers.
