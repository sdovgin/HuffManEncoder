//Sarah Dovgin (smd148)
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.*;

/**
 * HuffmanEncoder represents an encoding class that can take a file input, convert it into a
 * frequency tree for each character value, encode the characters based on frequency, write
 * an output file with the encoded input, and print statistics for the tree
 */
public class HuffmanEncoder{
   private static HuffmanNode root;            //represents the root of the HuffmanTree
   private static ArrayList<HuffmanNode> huffmanEncoding = new ArrayList<HuffmanNode>(); //an ArrayList containing the HuffmanNodes for each character along with its frequency and encoded value
    
   /**
    * HuffmanNode represents a single node in the Huffman encoding tree
    */
   public static class HuffmanNode{
       private Character inChar;       //character value associated with the node
       private int frequency;          //frequency of the character within the input file
       private HuffmanNode left;       //pointer to the left HuffmanNode of the current Node
       private HuffmanNode right;      //pointer to the right HuffmanNode of the current Node
       private String encodedString;   //encoded String value based on the character frequency
       
       /**
        * Constructor for the HuffmanNode
        * @param inChar is the character value associated with the node
        * @param frequency is the frequency that the character appears in the input file
        */
       public HuffmanNode(Character inChar, int frequency){
           this.inChar = inChar;     //character value associated with the node
           this.frequency = frequency;  //frequency of the character within the input file
       }
       /**
        * Get method that returns the inChar
        * @return inChar is the character value associated with the node
        */
       public Character getinChar(){
           return inChar;   //returns character associated with HuffmanNode
       }
       /**
        * Get method that returns the frequency
        * @return frequency is the frequency of the character within the input file
        */
       public int getFrequency(){
           return frequency; //returns frequency of the character within the input file
       }
       /**
        * Get method that returns the left child of the current node
        * @return left is the left child of the current node
        */
       public HuffmanNode getLeft(){
           return left;  //returns left child of the current node
       }
       /**
        * Get method that returns the right child of the current node
        * @return right is the right child of the current node
        */
       public HuffmanNode getRight(){
           return right;  //returns right child of the current node
       }
       /**
        * Get method that returns the encoded String based on the character frequency
        * @return encodedString is the encoded String based on the character frequency
        */
       public String getEncodedString(){
           return encodedString;   //returns the encoded String based on the character frequency
       }
       /**
        * Set method that sets the inChar of the HuffmanNode
        * @param inChar is the character associated with the node
        */
       public void setinChar(Character inChar){
           this.inChar = inChar;    //sets the local inChar to the param inChar
       }
       /**
        * Set method that sets the frequency of the HuffmanNode
        * @param frequency is the frequency of the character within the input file
        */
       public void setFrequency(int frequency){
           this.frequency = frequency;  //sets the local frequency to the param frequency
       }
       /**
        * Set method that sets the left child of the HuffmanNode
        * @param left is the left child of the HuffmanNode
        */
       public void setLeft(HuffmanNode left){
           this.left = left;       //sets the local left node to the param left node
       }
       /**
        * Set method that sets the right child of the HuffmanNode
        * @param right is the right child of the HuffmanNode
        */
       public void setRight(HuffmanNode right){
           this.right = right;       //sets the local right node to the param right node
       }
       /**
        * Set method that sets the encodedString of the HuffmanNode
        * @param encodedString is the encoded value based on the frequency of the character
        */
       public void setEncodedString(String encodedString){
           this.encodedString = encodedString;     //sets the local encodedString to the param encodedString
       }
   }

    /**
     * Method sets the root of the Huffman tree within the class
     * @param Inroot is the root of the Huffman tree within the class
     */
   public static void setRoot(HuffmanNode Inroot){
       root = Inroot;    //sets the node of the root
   }
   /**
    * Main method runs the Huffman Encoder with the given input and output file names as arguments
    */
   public static void main(String[] args) throws IOException{
        huffmanCoder(args[0],args[1]);  //takes the input argument Strings
        System.out.print("The number of leaf nodes is: " + getLeafNodes(root)+ "\n");  //returns the number of leaf nodes
        System.out.print("The depth of the node is: " + getDepth(root) + "\n");        //returns the depth of the node
        System.out.print("The tree is balanced: " + isBalanced(root));         //determines if the tree is balanced
   }
   
   /**
    * Method that uses each of the methods in order to create the character frequency hashmap, create the tree, encode each character based on frequency, and writes the output to a file
    * @param input is the input file containing characters
    * @param output is the output file containing the HuffmanEncoded input file
    * @return String indicates if the method was performed sucessfully
    */
   public static String huffmanCoder(String input, String output){
       try{
           Map<Character, Integer> charFreq = readInput(input);    //reads the input from the file into a hashmap
           HuffmanNode root = createTree(charFreq);     //creates a HuffmanNode tree based on the values of the hashmap
           setRoot(root);       //sets the root of the class
           encode(root,new StringBuilder());   //encodes the values into each of the nodes of the Huffman encoding tree
           writeOutput(root, input, output);  //writes the output to a text file
           printStats(charFreq,root);  //prints the Huffman Encoding table and statistics for the file
           return "OK";   //indicates method was successfully completed
       } catch (IOException e){  //catches exception
           return("Error in input file");  //indicates error in running input file
       }
    }
   
    /**
     * Method that reads the input from a file into a hashmap containing the character and associated frequency
     * @param input is the input file that the characters are read from
     * @return Map<Character,Integer> is the hashmap containing the characters and their frequencies
     */
   public static Map<Character, Integer> readInput(String input) throws IOException{
       BufferedReader read = new BufferedReader(new FileReader(input));   //reads the input from the file
       HashMap<Character,Integer> charFreq = new HashMap<Character, Integer>();  //initializes a hashmap for the characters and frequencies
       String line = null;  //initializes line representing a line of the text file
       while((line = read.readLine()) != null){  //while the line has values that can be read
           for(int i = 0; i < line.length(); i++){  //for loop iterates through the characters of each line
               Character ch = line.charAt(i);   //represents a single character in the line
               if (charFreq.containsKey(ch)){   //if the hashmap already has the character then the frequency value is incremented by 1
                   charFreq.put(ch,charFreq.get(ch)+1);
                }else{   // if the hashmap doesn't have the character then the character is added to the hashap with a frequency of 1
                   charFreq.put(ch,1);
                }
            }
        }
       return charFreq;   //the hashmap containing the characters and frequencies is returned
   }
  
   /**
    * Method that creates a tree using the hashmap containing characters and frequencies
    * The method uses a priority queue with the lowest frequencies at the front
    * The priority queue is then converted to a Huffman Encoding tree
    * @param charFreq is the hashmap containing all of the characters and their associated frequencies
    * @return HuffmanNode is the root the tree containing the HuffmanNodes
    */
   public static HuffmanNode createTree(Map<Character,Integer> charFreq){
        PriorityQueue<HuffmanNode> nodeList = new PriorityQueue<>(Comparator.comparing(HuffmanNode::getFrequency));  //initializes a priority queue that adds nodes based on their frequency values
        for(Map.Entry<Character, Integer> entry: charFreq.entrySet()){  //for loop iterates through all of the values of the hashmap
           Character charVal = entry.getKey();  //sets charVal as the value of the hashmap key
           Integer freq = entry.getValue();  //sets freq as the value of the hashmap value
           HuffmanNode entryVal = new HuffmanNode(charVal,freq);  //creates a new HuffmanNode based on the key and value from the hashmap
           nodeList.add(entryVal);  //adds the HuffmanNode to the priority queue where it is ordered based on the frequency
        }
        while(nodeList.size() > 1){   //while loop continutes to iterate through the priority queue until there is only one HuffmanNode left (represents the root of the tree)
            HuffmanNode node1 = nodeList.poll();   //takes the current first value from the priority queue (char of lowest frequency)
            HuffmanNode node2 = nodeList.poll();   //takes another value from the top of the priority queue (char of second lowest frequency)
            HuffmanNode node3 = merge(node1, node2);  //creates a parent node for the two nodes
            nodeList.add(node3);   //adds the new node to the priority queue
        }
        return nodeList.poll();   //returns the root of the Huffman Encoding tree
    }
    
   /**
    * Method that adds the encoded string value to each of the corresponding nodes
    * @param root is the root of the tree
    * @param sb is the encoded StringBuilder representing the String value
    */
   public static void encode(HuffmanNode root, StringBuilder sb) 
   {
       //if the char value of the node is not null is the base case
       if (root.getinChar() != null) { 
           root.setEncodedString(sb.toString());
       } else {
           //recursively calls encode by appending a 0 to the encodedString for every time the branch goes to the left
           if(root.getLeft() != null){
               encode(root.getLeft(),sb.append("0"));
               sb.setLength(sb.length() - 1);
            }
           //recursively calls encode by appending a 1 to the encodedString for every time the branch goes to the right
           if(root.getRight() != null){
               encode(root.getRight(),sb.append("1"));
               sb.setLength(sb.length() - 1);
            }
        }
   }

    
   /**
    * Method that writes the output of the encoded input chars to a textfile
    * @param root is the HuffmanNode root of the encoding tree
    * @param input is the input file name to be encoded
    * @param output is the name of the outputfile to tbe created
    */
   public static void writeOutput(HuffmanNode root, String input, String  output) throws IOException{
       BufferedReader read = new BufferedReader(new FileReader(input));   //reads the information of the input file
       BufferedWriter write = new BufferedWriter(new FileWriter(output));  //write the information to an output file

       String line = null; //initializes line representing a line of the text file
       while((line = read.readLine()) != null){ //while the line has values that can be read
           for(int i = 0; i < line.length(); i++){  //for loop iterates through the characters of each line
               Character ch = line.charAt(i); //represents a single character in the line
               HuffmanNode sample = search(ch,root); //creates a HuffmanNode to store the Node representing the char value
               if(sample != null){  //if a node exists for the char
                   write.write(sample.getEncodedString());  //writes the associated encoded String of the char
               }
            }
        }
       read.close();   //closes the BufferedReader
       write.close();  //closes the BufferedWriter

   }
   
   /**
    * Method that searches for the HuffmanNode that corresponds to the given Character value
    * @param ch is the Character value to be searched for
    * @param root is the root of the tree
    * @HuffmanNode is the node that corresponds to the given Character
    */
   public static HuffmanNode search(Character ch, HuffmanNode root){
       if(root != null){  //if the root is not null
           if(ch.equals(root.getinChar())){ //if the specified char is the same as the char associated with the node
               return root; //return the associated node
           } else {  //continue to search through the tree until the node associated with the char is found
               HuffmanNode foundNode = search(ch, root.getLeft());
               if(foundNode == null) {  //if the node cannot be cound in the left branch then search in the right branch
                   foundNode = search(ch, root.getRight());
               }
               return foundNode; //returns the node associated with the char
           }
       } else {
           return null;
       }
   }

   /**
    * Method that retrieves the number of leaf nodes in the HuffmanNode tree
    * @param root is the root of the tree
    * @return int is the number of leaf nodes in the tree
    */
   public static int getLeafNodes(HuffmanNode root){
       //if the root is null then there are no leaf nodes in the tree
       if (root == null){
           return 0;
       }
       //recognizes if a leaf node has been reached (represented by node with no children)
       if (root.getLeft() == null && root.getRight() == null){
           return 1;    
       } else { //if a non-leaf node then get the number of leaf nodes associated with it
           return getLeafNodes(root.getLeft()) + getLeafNodes(root.getRight());
       }
   }
    
   /**
    * Method that returns the depth of a specified HuffmanNode in the tree
    * @param root is the HuffmanNode that's depth will be returned
    * @int is the depth of the specified HuffmanNode
    */
   public static int getDepth(HuffmanNode root){
       //if the root value has no children then the depth is 0
       if (root.getLeft() == null && root.getRight() == null){
           return 0;
       }
       int leftDepth = 0;     //initializes the left depth value
       int rightDepth = 0;    //initializes the rigth depth value
       //if the root has a left child then the depth of the left branch will be returned
       if (root.getLeft() != null){
           leftDepth = getDepth(root.getLeft());  //uses a recursive function to get the depth of the left branch
       }
       //if the root has a right child then the depth of the right branch will be returned
       if (root.getRight() != null){ 
           rightDepth = getDepth(root.getRight());  //uses a recursive function to get the depth of the right branch
       }
       //returns the depth of the longer branch plus 1
       if(rightDepth >= leftDepth){
           return rightDepth + 1;
       } else {
           return leftDepth + 1;
       }
   }
    
   /**
    * Method that merges the values of two HuffmanNodes into a new node
    * @param node1 is one of the HuffmanNodes to be merged
    * @param node2 is one of the HuffmanNodes to be merged
    * @return HuffmanNode is the new HuffmanNode
    */
   public static HuffmanNode merge(HuffmanNode node1, HuffmanNode node2){
       HuffmanNode node3 = new HuffmanNode(null, node1.getFrequency()+node2.getFrequency()); //creates a new HuffmanNode
       //sets the right and left child of the new HuffmanNode based on the frequency values
           node3.setRight(node2);
           node3.setLeft(node1);
       return node3;    //returns the new HuffmanNode
   }
   
   /**
    * Method that determines if the tree is balanced
    * @param root is the root of the tree
    * @return boolean indicates if the tree is balanced or not
    */
   public static boolean isBalanced(HuffmanNode root){
       int balance = Math.abs(getDepth(root.getLeft()) - getDepth(root.getRight()));   //balance value of the node
       if(root == null){  //if the root is null
           return true;  //the tree is balanced
        } else if(balance <= 1 && isBalanced(root.getLeft()) && isBalanced(root.getRight())){  //if the balance value is less than or equal to one and the left and right sides are balanced
            return true;  //the tree is balanced
        }else {
            return true; //the tree is not balanced
        }
   }

    /**
     * This method prints the statistics of the Huffman encoding tree
     * @param charFreq is the hashmap containing the character and the frequency
     * @param root is the root of the Huffman encoding tree
     */
    private static void printStats(Map<Character, Integer> charFreq, HuffmanNode root){
        int bitsSaved = 0; //initializes the number of bits saved
        for(Map.Entry<Character, Integer> entry: charFreq.entrySet()){  //for loop iterates through all of the values of the hashmap
            Character charVal = entry.getKey();  //sets charVal as the value of the hashmap key
            Integer freq = entry.getValue();  //sets freq as the value of the hashmap value
            HuffmanNode encodeRoot = search(charVal,root);  //finds the node associated with the char
            String encoded = encodeRoot.getEncodedString();  //returns the encoded String within the node
            bitsSaved = bitsSaved + (8*freq); //represents the bits saved per char
            System.out.printf("Char:%c     frequency: %d      encoded:   %s", charVal, freq, encoded); //prints the statistics
            System.out.print("\n");
        }
        System.out.printf("The number of bits saved was: %d",bitsSaved); //prints the bits saved
        System.out.print("\n");
    }


}
