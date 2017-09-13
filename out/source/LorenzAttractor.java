import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import peasy.*; 
import peasy.org.apache.commons.math.*; 
import peasy.org.apache.commons.math.geometry.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class LorenzAttractor extends PApplet {





PeasyCam cam;

float defaultX = 2;
float defaultY = 0;
float defaultZ = 0;
float x = defaultX;
float y = defaultY;
float z = defaultZ;

float defaultA = 11;
float defaultB = 28;
float defaultC = 8.0f/3.0f;

float a = defaultA;
float b = defaultB;
//float b = 99.96;
//float b = 19;
float c =defaultC;
//float c = 0.8;

boolean tailPreset = true;
int defaultTailSize = 600;
boolean rainbowPreset = true;
float animationSpeed = 1;
boolean showHUD = true;

int tailSize = defaultTailSize;
ArrayList<PVector> points = new ArrayList<PVector>();

public void setup() {
   
  colorMode(HSB);    
  cam = new PeasyCam(this, 500);
}

public void draw() {
  background(0);
  if (showHUD) {
    showHUD();
  }

  for (int i = 0; i < animationSpeed; i++) {
    computeNewPoints();
  }

  translate(0, 0, -80);
  scale(2);
  stroke(255);
  noFill();
  if (!tailPreset) {
    tailSize = Integer.MAX_VALUE;
  }

  float hu;

  if (!rainbowPreset) {
    hu = 255 * 0.14f;
  } else {
    hu = 0;
  }

  beginShape();

  for (int i = points.size() - 1; i >= 0; i--) {
    float a = 126;
    if (tailPreset) {
      if (points.size() - tailSize > i) {
        a = 0;
      } else {
        a = map(0, tailSize, 0, 255, i - (points.size() - tailSize));
      }
    }

    stroke(hu, 255, 255, (int) Math.floor(a));

    PVector v = points.get(i);
    if (a != 0)
      vertex(v.x, v.y, v.z);
    else
      points.remove(i);

    if (rainbowPreset) {
      hu += 0.1f;
      if (hu > 255) {
        hu = 0;
      }
    }
  }

  endShape();
}

public void showHUD() {
  textSize(15);
  int textBase = 20;
  cam.beginHUD();
  text("Kurzorov\u00e9 \u0161ipky m\u011bn\u00ed parametry", 10, textBase);
  text("PageUP a PageDown m\u011bn\u00ed d\u00e9lku ocasu", 10, textBase+ 20);
  text("HOME a END m\u011bn\u00ed rychlost animace", 10, textBase+40);
  text("INSERT vyp\u00edn\u00e1 ocas", 10, textBase+60);
  text("DELETE vyp\u00edn\u00e1 duhov\u00e9 zbarven\u00ed", 10, textBase+80);
  text("R to reset animation", 10, textBase + 100);
  text("H to show/hide this help", 10, textBase + 120);
  cam.endHUD();
}

public void computeNewPoints() {
  float dt = 0.01f;
  float dx = (a * (y - x)) * dt;
  float dy = (x * (b - z) - y) * dt;
  float dz = (x * y - c * z) * dt;
  x = x + dx;
  y = y + dy;
  z = z + dz;
  points.add(new PVector(x, y, z));
}

public void keyPressed() {
  if (keyCode == UP) {
    c += 0.1f; 
    println("c = " + c);
  } else if (keyCode == DOWN) {
    c -= 0.1f;
    println("c = " + c);
  } else if (keyCode == LEFT) {
    b += 0.1f;
    println("b = " + b);
  } else if (keyCode == RIGHT) {
    b -= 0.1f;
    println("b = " + b);
  } else if (keyCode == 16) { // PGUP
    tailSize += 300;
    println("TailSize: " + tailSize);
  } else if (keyCode == 11) { //PGDWN
    tailSize -= 300;
    tailSize = Math.max(0, tailSize);
    println("TailSize: " + tailSize);
  } else if (keyCode == 2) { //HOME
    animationSpeed++;
    println("AnimationSpeed = " + animationSpeed);
  } else if (keyCode == 3) { // END
    animationSpeed--;
    animationSpeed = Math.max(0, animationSpeed);
    println("AnimationSpeed = " + animationSpeed);
  } else if (keyCode == 26) { //INS
    tailPreset = !tailPreset;
    if (tailPreset) tailSize = defaultTailSize;
    println("tailPreset = " + tailPreset);
  } else if (keyCode == 147) { //DEL
    rainbowPreset = !rainbowPreset;
    println("rainbowPreset = " + rainbowPreset);
  } else if (keyCode == 72) { //H
    showHUD = !showHUD;
  } else if (keyCode == 82) { //R
    resetToDefaults();
  }
  
}

public void resetToDefaults() {
    x = defaultX;
    y = defaultY;
    z = defaultZ;
    a = defaultA;
    b = defaultB;
    c = defaultC;
}
  public void settings() {  size(800, 600, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "LorenzAttractor" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
