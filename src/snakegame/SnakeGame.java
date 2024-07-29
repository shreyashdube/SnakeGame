package snakegame;
import javax.swing.*; //extend JFrame iske andr aataa hai.
public class SnakeGame extends JFrame {

    SnakeGame(){   // Constructer.
        super("Snake Game");//function hai parentclass ko call krta hai aur title bhi likh skte hai.
        add(new Board());// frame ke uppr kch add krna hai tho add() function use krte hai.
        pack(); //refresh krta hai khulee huee frame mai.
        
        setLocationRelativeTo(null);// Center mai krne ke liye frame ko.
        setResizable(false);
        setVisible(true);// frame dikhega.
    }
    public static void main(String[] args) {
       new SnakeGame(); //object.
    }
    
}
