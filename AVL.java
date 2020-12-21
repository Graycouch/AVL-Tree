package avl;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AVL 
{
    public static void main(String[] args) throws FileNotFoundException 
    {
            File inputFile = new File("AVL.txt");
            Scanner in = new Scanner(inputFile);
            
            int instructionCount = Integer.parseInt(in.nextLine());
            AVLTree AVL = new AVLTree();
            int counter = 1;
                
            while(in.hasNextLine() && counter < instructionCount + 1)
            {
                counter++;
                String[] instructions = in.nextLine().split(" ");
                    
                switch(instructions[0])
                {
                    case "IN":
                    {
                        AVL.insert(AVL.root , Integer.parseInt(instructions[1]) , Integer.parseInt(instructions[2]));
                        break;
                    }
                            
                    case "RM":
                    {
                        Node pointer = AVL.FCF(AVL.root , Integer.parseInt(instructions[1]) , Integer.parseInt(instructions[2]));
                        int rangeMin = Integer.MAX_VALUE;
                        System.out.println(AVL.RangeMinData(pointer, Integer.parseInt(instructions[1]) , Integer.parseInt(instructions[2]) , rangeMin));
                        break;
                    }
                        
                    default:
                    {
                        break;
                    }
                }
            }
            in.close();
        }
    }

class Node 
{
    Node parent;
    Node right;
    Node left;
    int key;
    int data;
    int height;
    
    public Node()
    {
        
    }
        
    public Node(int key , int data)
    {
        this.key = key;
        this.data = data;
        this.height = 1;
    }
}

class AVLTree
{       
    public Node root; 
    
    public AVLTree()
    {
        this.root = null;
    }
    
    int RangeMinData(Node pointer , int k1 , int k2 , int rangeMin)
    {
        if(pointer != null)
        {
            if(pointer.key < k1)
            {
                return RangeMinData(pointer.right , k1 , k2 , rangeMin);
            }
            
            else if(pointer.key > k2)
            {
                return RangeMinData(pointer.left , k1 , k2 , rangeMin);
            }
            
            else if(pointer.data < rangeMin)
            {
                rangeMin = pointer.data;
            }
            
            if(pointer.right != null)
            {
                return RangeMinData(pointer.right , k1 , k2 , rangeMin);
            }
            
            if(pointer.left != null)
            {
                return RangeMinData(pointer.left , k1 , k2 , rangeMin);
            }
        }
        
        return rangeMin;
    }
        
    Node insert(Node node , int key , int data)
    {          
        if (this.root == null) 
        {
            return this.root = new Node(key , data);
        }
        
        else if(node == null)
        {
            return new Node(key , data);
        }
  
        else if (key < node.key)
        {
            node.left = insert(node.left , key , data); 
        }
        
        else if (key > node.key) 
        {
            node.right = insert(node.right , key , data); 
        }
        
        if(node.left != null && node.right != null)
        {
            node.height = 1 + getMax(node.left.height , node.right.height);
        }
        
        int balance = getBalance(node);
        
        if (balance > 1 && key < node.left.key)
        {
            rightRotate(node);
        }
        
        else if (balance < -1 && key > node.right.key)
        {
            leftRotate(node);
        }
        
        else if (balance > 1 && key > node.left.key) 
        { 
            node.left = leftRotate(node.left);
            rightRotate(node); 
        }
        
        else if (balance < -1 && key < node.right.key) 
        { 
            node.right = rightRotate(node.right);
            leftRotate(node); 
        }
        
        return node;
    }
    
    int getMax(int a, int b) 
    { 
        return (a > b) ? a : b; 
    }
    
    int getBalance(Node node) 
    { 
        if (node == null) 
        {
            return 0;
        }
        
        if(node.left != null && node.right != null)
        {
            return node.left.height - node.right.height; 
        }
        return node.height;
    }
    
    Node FCF(Node node , int n1 , int n2)
    {
        if (node == null) 
        {
            return this.root;
        }
        
        while(node.key < n1 || node.key > n2)
        {
            if(node.key < n1)
            {
                node = node.right;
            }
            
            if(node.key > n2)
            {
                node = node.left;
            }
        }
        return node; 
    }
    
    Node rightRotate(Node k1) 
    { 
        Node k2 = k1.left; 
        Node k3 = k2.right;
        
        k2.right = k1; 
        k1.left = k3;
        
        k1.height = getMax(k1.left.height , k1.right.height) + 1; 
        k2.height = getMax(k2.left.height , k2.right.height) + 1; 
        
        return k2; 
    } 
    
    Node leftRotate(Node k1) 
    { 
        Node k2 = k1.right; 
        Node k3 = k2.left; 
        
        k2.left = k1; 
        k1.right = k3; 
        
        k1.height = getMax(k1.left.height , k1.right.height) + 1; 
        k2.height = getMax(k2.left.height , k2.right.height) + 1; 
        
        return k2; 
    } 
}