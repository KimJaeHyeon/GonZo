package home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*; // 파일 클래스 사용을 위한 임포트 추가
import javax.imageio.*;
import java.awt.image.*;// 버퍼이미지 클래스 사용을 위한 임포트 추가

public class game {
public static void main(String[] ar){
game_Frame fms = new game_Frame();
}
}

class game_Frame extends JFrame implements KeyListener, Runnable{ 
int f_width ;
int f_height ;
 
int x, y; 
boolean KeyUp = false;
boolean KeyDown = false;
boolean KeyLeft = false;
boolean KeyRight = false;
boolean KeySpace = false;

int cnt;

int e_w, e_h; 
int m_w, m_h; //미사일 이미지의 크기값을 받을 변수

Thread th;
Toolkit tk = Toolkit.getDefaultToolkit();
Image me_img;
Image Missile_img;
Image Enemy_img; 
ArrayList Missile_List = new ArrayList();
ArrayList Enemy_List = new ArrayList();

Image buffImage; Graphics buffg;

Missile ms;
Enemy en;

game_Frame(){
init();
start();
  
setTitle("슈팅 게임 만들기");
setSize(f_width, f_height);
Dimension screen = tk.getScreenSize();

int f_xpos = (int)(screen.getWidth() / 2 - f_width / 2);
int f_ypos = (int)(screen.getHeight() / 2 - f_height / 2);

setLocation(f_xpos, f_ypos);
setResizable(false);
setVisible(true);
}
public void init(){ 
x = 100;
y = 100;
f_width = 800;
f_height = 600;

me_img = tk.getImage("76.png"); 
Missile_img = tk.getImage("나선로켓.png");
Enemy_img = tk.getImage("한조.png");

e_w = ImageWidthValue("한조.png");
e_h = ImageHeightValue("한조.png");
//적 이미지의 w(넓이)값, h(높이) 값을 계산하여 받습니다.
//해당 메소드는 아래에 이미지 크기값 계산용으로
//추가된 메소드 입니다.

m_w = ImageWidthValue("나선로켓.png");
m_h = ImageHeightValue("나선로켓.png");
//미사일 이미지의 w(넓이)값, h(높이) 값

}
public void start(){
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
addKeyListener(this);

th = new Thread(this); 
th.start(); 

}

public void run(){ 
try{ 
while(true){
KeyProcess(); 
EnemyProcess();
MissileProcess(); 

repaint(); 

Thread.sleep(20);
cnt ++;
}
}catch (Exception e){}
}

public void MissileProcess(){ 
if ( KeySpace ){
ms = new Missile(x+150, y+30);
//미사일 발사 위치를 제대로 하기 위한 좌표조정
Missile_List.add(ms); 
}

for ( int i = 0 ; i < Missile_List.size() ; ++i){
ms = (Missile) Missile_List.get(i);
ms.move();
if ( ms.x > f_width - 20 ){
Missile_List.remove(i);
}
//편의상 그림그리기 부분에 있던 미사일 이동과
//미사일이 화면에서 벗어났을시 명령 처리를
//이쪽으로 옮겼습니다.
for (int j = 0 ; j < Enemy_List.size(); ++ j){
en = (Enemy) Enemy_List.get(j);
if (Crash(ms.x,ms.y,en.x,en.y, m_w, m_h, e_w, e_h)){
//미사일과 적 객체를 하나하나 판별하여
//접촉했을시 미사일과 적을 화면에서 지웁니다.
//판별엔 Crash 메소드에서 계산하는 방식을 씁니다.
Missile_List.remove(i);
Enemy_List.remove(j);
}
}
}
}

public void EnemyProcess(){

for (int i = 0 ; i < Enemy_List.size() ; ++i ){ 
en = (Enemy)(Enemy_List.get(i)); 
en.move(); 
if(en.x < -200){ 
Enemy_List.remove(i); 
}
}

if ( cnt % 300 == 0 ){ 
en = new Enemy(f_width + 100, 100);
Enemy_List.add(en); 
en = new Enemy(f_width + 100, 200);
Enemy_List.add(en);
en = new Enemy(f_width + 100, 300);
Enemy_List.add(en);
en = new Enemy(f_width + 100, 400);
Enemy_List.add(en);
en = new Enemy(f_width + 100, 500);
Enemy_List.add(en);
}

}

public boolean Crash(int x1, int y1, int x2, int y2, int w1, int h1, int w2, int h2){
//﻿충돌 판정을 위한 새로운 Crash 메소드를 만들었습니다.
//판정을 위해 충돌할 두 사각 이미지의 좌표 및 
//넓이와 높이값을 받아들입니다.
//여기서 이미지의 넓이, 높이값을 계산하기 위해 밑에 보면
//이미지 크기 계산용 메소드를 또 추가했습니다.
boolean check = false;

if ( Math.abs( ( x1 + w1 / 2 )  - ( x2 + w2 / 2 ))  <  ( w2 / 2 + w1 / 2 )  
&& Math.abs( ( y1 + h1 / 2 )  - ( y2 + h2 / 2 ))  <  ( h2 / 2 + h1/ 2 ) ){
//충돌 계산식입니다. 사각형 두개의 거리및 겹치는 여부를 확인
//하는 방식이라고 알고 있는데, 만들다보니 생각보다 식이 
//복잡해진것 같습니다.
//이보다 더 간편한 방식이 있을 것도 같은데 
//일단 이렇게 해봤습니다.
check = true;//위 값이 true면 check에 true를 전달합니다.
}else{ check = false;}

return check; //check의 값을 메소드에 리턴 시킵니다.

}


public void paint(Graphics g){
buffImage = createImage(f_width, f_height); 
buffg = buffImage.getGraphics();

update(g);
}

public void update(Graphics g){
Draw_Char();
Draw_Enemy(); 
Draw_Missile();

g.drawImage(buffImage, 0, 0, this); 
}

public void Draw_Char(){ 
buffg.clearRect(0, 0, f_width, f_height);
buffg.drawImage(me_img, x, y, this);
}

public void Draw_Missile(){
for (int i = 0 ; i < Missile_List.size()  ; ++i){
ms = (Missile) (Missile_List.get(i)); 
buffg.drawImage(Missile_img, ms.x, ms.y, this); 
}
}

public void Draw_Enemy(){ 
for (int i = 0 ; i < Enemy_List.size() ; ++i ){
en = (Enemy)(Enemy_List.get(i));
buffg.drawImage(Enemy_img, en.x, en.y, this);
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

public int ImageWidthValue(String file){ 
// 이미지 넓이 크기 값 계산용 메소드 입니다.
// 파일을 받아들여 그 파일 값을 계산 하도록 하는 것입니다.
int x = 0;
try{
File f = new File(file); // 파일을 받습니다.
BufferedImage bi = ImageIO.read(f);
//받을 파일을 이미지로 읽어들입니다.
x = bi.getWidth(); //이미지의 넓이 값을 받습니다.
}catch(Exception e){}
return x; //받은 넓이 값을 리턴 시킵니다.
}

public int ImageHeightValue(String file){ // 이미지 높이 크기 값 계산
int y = 0;
try{
File f = new File(file);
BufferedImage bi = ImageIO.read(f);
y = bi.getHeight();
}catch(Exception e){}
return y;
}
}

class Missile{
int x;
int y; //편의상 변수 명 변경

Missile(int x, int y){
this.x = x; 
this.y = y;//편의상 변수명 변경
}
public void move(){
x += 10;
}
}

class Enemy{ 
int x;
int y;

Enemy(int x, int y){ 
this.x = x;
this.y = y;
}
public void move(){ 
x -= 3;
}
}