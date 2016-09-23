package home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class home {        //메인클래스
public static void main(String[] ar){
game_Frame fms = new game_Frame();
}
}

class game_Frame extends JFrame implements KeyListener, Runnable{ 
	//프레임창만들기 
	//키보드이벤트용 KeyListener상속
	//스레드 돌리기용 Runnable 상속
int f_width =800;
int f_height= 600 ;            //프레임크기
 
int x, y;               // 캐릭터의 좌표 변수
boolean KeyUp = false;        //키보드 입력 처리를 위한 변수
boolean KeyDown = false;
boolean KeyLeft = false;
boolean KeyRight = false;
boolean KeySpace = false;


int cnt; //각종 타이밍 조절용 무한 루프를 세는 변수

Thread th;                   //스ㅜ레드 생성
Toolkit tk = Toolkit.getDefaultToolkit();    //이미지를 불러오는 툴킷
Image me_img;
Image Bullet_img;
Image Enemy_img; // 적 이미지를 받아들일 이미지 변수
ArrayList Bullet_List = new ArrayList();
ArrayList Enemy_List = new ArrayList();
//다수의 적을 등장 시켜야 하므로 배열을 이용.

Image buffImage; Graphics buffg;

Bullet ms;
Enemy en; //에너미 클래스 접근 키

game_Frame(){
init();
start();
  
setTitle("Soilder's Adventure");          // 게임이름
setSize(f_width, f_height);       
Dimension screen = tk.getScreenSize();

int f_xpos = (int)(screen.getWidth() / 2 - f_width / 2);
int f_ypos = (int)(screen.getHeight() / 2 - f_height / 2);

setLocation(f_xpos, f_ypos);
setResizable(false);           //프레임크기고정
setVisible(true);          //프레임을 보이게
}
public void init(){ 
x = 100;       //캐릭터의 최초 좌표.
y = 500;
f_width = 800;
f_height = 600;

me_img = tk.getImage("76.png");         //솔져 이미지 구현

Bullet_img = tk.getImage("나선로켓.png");        //총알이미지구현
Enemy_img = tk.getImage("한조.png");       //적 이미지 구현
}
public void start(){
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     //x누르면 종료
addKeyListener(this);      // 키보드이벤트생성행

th = new Thread(this);     // 스레드 생성
th.start();              // 스레드 실행

}

public void run(){     //스레드 무한뤂프
try{             // 예외옵션 설정으로 에러 방지
while(true){              //무한 루프
KeyProcess();          // 키보드 입력처리를 하여 x,y 갱신
EnemyProcess();        //적 메소드 실행
BulletProcess(); 

repaint();           //갱신된 xy갑ㅇㄴ으로 이미지 옮기기

Thread.sleep(20);             //초당 20밀리 스레드로 돌리기 
cnt ++;//무한 루프 카운터
}
}catch (Exception e){}
}

public void BulletProcess(){        //총알메소드실행
if ( KeySpace ){
ms = new Bullet(x, y);
Bullet_List.add(ms); 
}
}

public void EnemyProcess(){//적 행동 처리 메소드

for (int i = 0 ; i < Enemy_List.size() ; ++i ){ 
en = (Enemy)(Enemy_List.get(i));  //배열에서 적을 판별
en.move();              //적을 이동시킨다.
if(en.x < -200){          //적의 좌표가 화면 밖으로 넘어가면
Enemy_List.remove(i);          // 적을 삭제
}
}

if ( cnt % 300 == 0 ){       //루프 카운트 300회 마다
/*en = new Enemy(f_width + 100, 100);
Enemy_List.add(en);    //적을 생성한 후 배열에 추가
en = new Enemy(f_width + 100, 200);
Enemy_List.add(en);
en = new Enemy(f_width + 100, 300);
Enemy_List.add(en);
en = new Enemy(f_width + 100, 400);
Enemy_List.add(en);*/
en = new Enemy(f_width + 100, 480);
Enemy_List.add(en);
}

}

public void paint(Graphics g){
buffImage = createImage(f_width, f_height); 
buffg = buffImage.getGraphics();

update(g);
}

public void update(Graphics g){
Draw_Char();

Draw_Enemy(); // 적 이미지를 가져온다.

Draw_Bullet();

g.drawImage(buffImage, 0, 0, this); 
}

public void Draw_Char(){ 
buffg.clearRect(0, 0, f_width, f_height);
buffg.drawImage(me_img, x, y, this);
}

public void Draw_Bullet(){
for (int i = 0 ; i < Bullet_List.size()  ; ++i){
ms = (Bullet) (Bullet_List.get(i)); 
buffg.drawImage(Bullet_img, ms.pos.x + 100, ms.pos.y+ 35, this); 
ms.move();
if ( ms.pos.x > f_width ){ 
Bullet_List.remove(i); 
}
}
}

public void Draw_Enemy(){        // 적 이미지를 그림
for (int i = 0 ; i < Enemy_List.size() ; ++i ){
en = (Enemy)(Enemy_List.get(i));
buffg.drawImage(Enemy_img, en.x, en.y, this); //배열에 생성된 각 적을 판별하여 이미지 그리기
}
}

public void keyPressed(KeyEvent e){
switch(e.getKeyCode()){
case KeyEvent.VK_UP :
KeyUp = true;
break;
case KeyEvent.VK_DOWN :
KeyDown = true;
break;
case KeyEvent.VK_LEFT :
KeyLeft = true;
break;
case KeyEvent.VK_RIGHT :
KeyRight = true;
break;

case KeyEvent.VK_SPACE :
KeySpace = true;
break;
}
}
public void keyReleased(KeyEvent e){
switch(e.getKeyCode()){
case KeyEvent.VK_UP :
KeyUp = false;
break;
case KeyEvent.VK_DOWN :
KeyDown = false;
break;
case KeyEvent.VK_LEFT :
KeyLeft = false;
break;
case KeyEvent.VK_RIGHT :
KeyRight = false;
break;

case KeyEvent.VK_SPACE :
KeySpace = false;
break;

}
}
public void keyTyped(KeyEvent e){}
public void KeyProcess(){

if(KeyUp == true) y -= 5;
if(KeyDown == true) y += 5;
if(KeyLeft == true) x -= 5;
if(KeyRight == true) x += 5;
}
}

class Bullet{ 

Point pos;
Bullet(int x, int y){
pos = new Point(x, y); 
}
public void move(){
pos.x = pos.x + 15;   //총알이동속도
}
}

class Enemy{  // 적 클래스
int x;
int y;

Enemy(int x, int y){ 
this.x = x;
this.y = y;
}
public void move(){       // x좌표 이동 
x -= 3;
}
}