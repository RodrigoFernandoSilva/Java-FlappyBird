/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static jplay.InputBase.DETECT_INITIAL_PRESS_ONLY;

import jplay.Window;
import jplay.Keyboard;
import jplay.Physics;
import jplay.GameImage;
import jplay.Sound;

import Bird.Bird;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Main {
    
    public static boolean[] birdsEnable;
    public static boolean running = true;
    public static boolean birdOk = false;
    public static boolean levelOk = false;
    public static boolean isElimination = true;
    public static boolean wasBeforePlay = false;
    public static boolean cheatEnable = false;
    
    public static Keyboard keyboard;
    public static Window window;
    public static Physics physics;
    public static Playing playing;
    
    public static GameImage sky;
    public static GameImage cheatEnableImage = new GameImage("Images/GUI/Menu/Cheat Enable.png");
    
    public static final String SELECT_FILE = "Sounds/Select.wav";
    public static Sound select = new Sound(SELECT_FILE);
    
    public static XML xml = new XML();
    
    public static int levelSpeed;
    public static int levelDistancePipesX;
    public static int levelDistancePipesY;
    public static int oldLevelSpeed;
    public static int oldLevelDistancePipesX;
    public static int oldLevelDistancePipesY;
    
    public Bird bird;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        
        DeltaTime deltaTime = new DeltaTime();
        deltaTime.start();
        
        Sound music = new Sound("Sounds/Music.wav");
        music.setRepeat(true);
        music.play();
        
        birdsEnable = new boolean[7];
        birdsEnable[0] = true;
            
        sky = new GameImage("Images/Scene/Sky.png");
        
        window = new Window(sky.width, sky.height);
        keyboard = window.getKeyboard();
        physics = new Physics();
        physics.createWorld(window.getWidth(), window.getHeight());
        
        //Add the keys that going to be used on menus
        keyboard.addKey(KeyEvent.VK_UP, DETECT_INITIAL_PRESS_ONLY);
        keyboard.addKey(KeyEvent.VK_DOWN, DETECT_INITIAL_PRESS_ONLY);
        keyboard.addKey(KeyEvent.VK_LEFT, DETECT_INITIAL_PRESS_ONLY);
        keyboard.addKey(KeyEvent.VK_RIGHT, DETECT_INITIAL_PRESS_ONLY);
        keyboard.addKey(KeyEvent.VK_ENTER, DETECT_INITIAL_PRESS_ONLY);
        keyboard.addKey(KeyEvent.VK_CONTROL, DETECT_INITIAL_PRESS_ONLY);
        
        ImageIcon icon = new ImageIcon("Images/Logo/logo.png");
        window.setTitle("FlappyBird");
        window.setIconImage(icon.getImage());
        
        Menu menu = new Menu();
        Setting setting = new Setting();
        BeforePlay beforePlay = new BeforePlay();
        playing = new Playing();
        
        @SuppressWarnings("UnusedAssignment")
        int[] readSetting = new int[3];
        
        readSetting = xml.GetXmlSetting();
        
        if ((readSetting[0] < 210 | readSetting[0] > 330) |
            (readSetting[1] < 130 | readSetting[1] > 170) |
            (readSetting[2] < 160 | readSetting[2] > 240)) {
            
            xml.ResetXmlSetting();
            
        }
        
        LoadSettingToGameVar();
        
        int index = 0;
        while (running) {
            
            if (!wasBeforePlay) {
                index = menu.Menu();
            }
            
            switch (index) {
                case 0:
                    wasBeforePlay = true;
                    if (beforePlay.BeforePlay())
                        playing.Playing();
                    break;
                case 1:
                    setting.Setting();
                    break;
                case 2:
                    window.exit();
                    break;
                default:
                    break;
            }
        }
    
    }
    
    public static void LoadSettingToGameVar () {
        @SuppressWarnings("UnusedAssignment")
        int[] readSetting = new int[3];
        
        readSetting = xml.GetXmlSetting();
        
        levelDistancePipesX = readSetting[0];
        levelDistancePipesY = readSetting[1];
        levelSpeed = readSetting[2];
    }
    
}
