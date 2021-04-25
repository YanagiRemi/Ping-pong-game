/**
 * Pong.java
 * @author 18268059 yanagi remi
 * イベントリスナーの使用例
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

// イベントリスナーはinterfaceとして定義されている 
public class Pong extends JPanel implements Runnable,KeyListener{
    static final int WORLD_W = 450; // 画面のよこ幅
    static final int WORLD_H = 400; // 画面のたて幅
    static final int BALL_R = 20;  // ボールの半径
    static final double dT = 0.01; // 刻み時間
    private int x = WORLD_W/2, y = WORLD_H/2; // ボールの位置
    private double dx = 100.0; // ボールの速度
    private double dy = 100.0; // ボールの速度
    private boolean isRunning = true; // マウスクリックのたびに論理フラグが反転
	static final int paddleH = 100; //パドルの高さ
	static final int paddleW = 13;  //パドルの幅
	private int paddlex=370;  //パドルの位置
	private int paddley=200; //パドルの位置
    
    public Pong() { // コンストラクタ
	setOpaque(false); // 背景を透明に
	setPreferredSize(new Dimension(WORLD_W, WORLD_H)); // JPanelのサイズ指定
	addKeyListener(this); // <- KeyEventのイベントリスナーを追加
    }
    
    public void run() {
	while(true) {
	    if(isRunning) { // trueのときだけボールを動かす
	    	if(x>WORLD_W-BALL_R){   //右側の壁に当たった時，ボールの位置を初期化
	    		x=WORLD_W/2;
	    		y=WORLD_H/2;
	    	}
	    	if(x>=paddlex-BALL_R&&y>=paddley&&y<=paddley+paddleH){ //パドルにあたった時，速度反転
	    		dx *= -1.0;
	    	}
	    	if(x<BALL_R) dx *= -1.0; // 壁にぶつかったら速度反転（完全弾性衝突）
	    	if(y<BALL_R || y>WORLD_H-BALL_R) dy *= -1.0;// 壁にぶつかったら速度反転（完全弾性衝突）
	    	x += dx*dT;
	    	y += dy*dT;
	    }
	    repaint(); // java.awt.Componentクラス(Canvasのスーパークラス)のupdate()メソッドを呼び出し
		try { Thread.sleep((int)(dT*1000));}
	    catch (InterruptedException e) {}
	}
    }

    // paint()メソッドは，画面の再描画時やupdate()メソッドから呼び出される
    public void paintComponent(Graphics g) {
	super.paintComponent(g); // スーパークラス(JPanel)のpaintComponent()呼び出し
	g.setColor(Color.white);   
    g.fillOval(x-BALL_R, y-BALL_R, BALL_R*2, BALL_R*2);     // ボールの描画
    g.fillRect(paddlex,paddley,paddleW,paddleH);
    	this.requestFocusInWindow();
    }
	
	public void keyTyped(KeyEvent e){}
	
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
			case KeyEvent.VK_UP: if(paddley>=10)paddley-=10; break;    //カーソルキー上でパドルを10上にずらす
			case KeyEvent.VK_DOWN: if(paddley+paddleH<=WORLD_H-10)paddley+=10; break; //カーソルキー下でパドルを10下にずらす
		}
		repaint(); 
	}
    public void keyReleased(KeyEvent e){}
	
    public static void main(String[] args) {
	JFrame frame = new JFrame("Pong"); // JFrameによるウィンドウの生成
	frame.getContentPane().setBackground(Color.black);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ウインドウを閉じる処理
	Pong canvas = new Pong(); // 描画エリア(canvas)の生成
	frame.add(canvas); // Pong(=JPanel)インスタンスをJFrameに貼りつけ
	frame.pack(); // フレームサイズを包含物のサイズにより自動決定  
	frame.setVisible(true); // JFrameを可視化
	new Thread(canvas).start(); // Threadの開始
    }
}
