package snakegame;

import javax.swing.*; // extends jpanel iske andr aata hai.
import java.awt.*; // Color class iske andr rhti hai.
import java.awt.event.*;
public class Board extends JPanel implements ActionListener {    // JPanel = frame ka chotaa hissaaa.
    
    private Image apple;
    private Image dot;
    private Image head;
    
    private final int ALL_DOTS = 1000; // puri frame ka fixed size liye hai iss se bdaa nhi hogaa.
    private final int DOT_SIZE = 10; // ek dot ka widht.
    private final int RANDOM_POSITION= 29; // Apple ke liye koi bhi random location liye hai.
    
    private int apple_x;
    private int apple_y;
    
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    
    private boolean leftDirection = false;
    private boolean rightDirection = true; // default right move krna chahiye iss liye true liye isko.
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    private boolean inGame = true;// game chlte rhega agr collision hua aur rule tuta tho game rukk jayega.
    
    private int dots; // Golobally define dot isko bhut baar call krna padega.
    private Timer timer;
    
    Board() {
        
        addKeyListener(new TAdapter()); // key se handel krne ke liye.
        
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(500,500));
        setFocusable(true); // focus ke liye.
        
        loadImages();
        initGame(); //initialize game function.
    }
    
    public void loadImages() {
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Images/apple.png"));
        apple = i1.getImage();
        
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("Images/dot.png"));
        dot = i2.getImage();
        
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("Images/head.png"));
        head = i3.getImage();
    }
    
    public void initGame() {
        dots = 3;
        
        for(int i = 0; i < dots; i++){ //line se image chahiye snake bnane ke liye.
            y[i] = 50;
            x[i] = 50 - i * DOT_SIZE;// suppose i=0 so, 50-0=50,then i=1 so, 50-10=40, iss se ek ke peeche ek dot ayegi.
        }
        
        locateApple();
        
        timer = new Timer(140, this);
        timer.start();
    }
    
    public void locateApple() {
        int r = (int)(Math.random() * RANDOM_POSITION); //random position ke liye math random() use kiye aur float mai output deta tho int kiye usko aur r mai store krwa liye random postion.
        apple_x = r * DOT_SIZE;
        r = (int)(Math.random() * RANDOM_POSITION);
        apple_y = r * DOT_SIZE;
    }
    
    // Image ko frame mai laane ke liyee:-
    public void paintComponent(Graphics g) {  //methods jo frame ke upr image show krta hai.
        super.paintComponent(g);
        
        draw(g);
    }
    
    public void draw(Graphics g) {
        if (inGame){
        g.drawImage(apple, apple_x, apple_y, this);// apple ko frame mai laane ke liye.
        
        for(int i = 0; i < dots; i++) {
            if(i == 0) {
                g.drawImage(head,x[i], y[i], this);
            }else {
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        
        Toolkit.getDefaultToolkit().sync();//default se intialize hone ke liye.
        }else {
            gameOver(g);
        }
    }
    
    public void gameOver(Graphics g) {
        String msg = "Game Over!!!";
        Font font = new Font("SAN_SERIF", Font.BOLD, 20);
        FontMetrics metrices = getFontMetrics(font);
        
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (500 - metrices.stringWidth(msg)) / 2, 500/2);
    }
        
    
    public void move() {
        for(int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1]; // head ke peeche peeche dot move krega.
        }
        
        if(leftDirection) {
            x[0] = x[0] - DOT_SIZE; // x coordinate se minus krenge tho wo left jayega.
        }
        if(rightDirection) {
            x[0] = x[0] + DOT_SIZE; 
        }
        if(upDirection) {
            y[0] = y[0] - DOT_SIZE; 
        } 
        if(downDirection) {
            y[0] = y[0] + DOT_SIZE; 
        } 
    }
    
    public void checkApple() {
        if((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locateApple(); //dusra apple locate krne ke liye call kiye.
        }
    }
    
    public void checkCollision() {
        for(int i = dots; i > 0; i--) {
            if((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }
        if(y[0] >= 500) {   //boundry se tkranee se rukk jayega.
            inGame = false;
        }
        if(x[0] >= 500) {  
            inGame = false;
        }
        if(y[0] < 0) {
            inGame = false;
        }
        if(x[0] < 0) {
            inGame = false;
        }
        
        if(!inGame) {
            timer.stop();  // snake ko rokta hai time se move hoo rha iss liye timer ko rok diye.
        }
}
    
    public void actionPerformed(ActionEvent ae) {
        if (inGame){
        checkApple();// apple detect krne ke liye.
        move(); //snake ko move krane ke liye.
        checkCollision();
        }
            repaint();// refresh krta hai ye method.
    }
    
    public class TAdapter extends KeyAdapter { // class hoti hai arrow keyboard use krne ke liye 
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if(key == KeyEvent.VK_LEFT && (!rightDirection)) { // direct right dbane se kch nhi hoga.
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            
            if(key == KeyEvent.VK_DOWN && (!upDirection)) { // direct up dbane se kch nhi hoga.
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
            
            if(key == KeyEvent.VK_RIGHT && (!leftDirection)) { // direct right left se kch nhi hoga.
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            
            if(key == KeyEvent.VK_UP && (!downDirection)) { // direct right down se kch nhi hoga.
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }
}
