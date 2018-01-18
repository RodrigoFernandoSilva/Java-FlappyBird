/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static Main.Main.window;
import static Main.Main.keyboard;
import static Main.Main.sky;
import static Main.Main.SELECT_FILE;
import static Main.Main.select;
import static Main.Main.xml;
import static Main.Main.cheatEnable;
import static Main.Main.cheatEnableImage;
import static Main.Main.levelDistancePipesX;
import static Main.Main.levelDistancePipesY;
import static Main.Main.levelSpeed;
import static Main.Main.oldLevelDistancePipesX;
import static Main.Main.oldLevelDistancePipesY;
import static Main.Main.oldLevelSpeed;

import jplay.GameImage;
import jplay.Sound;

import com.sun.glass.events.KeyEvent;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Menu {
    
    public GameImage righArrow = new  GameImage("Images/GUI/RighArrow.png");
    public GameImage leftArrow = new  GameImage("Images/GUI/LeftArrow.png");
    
    @SuppressWarnings("static-access")
    public int Menu () {
        
        int index = 0;
        
        GameImage floor = new GameImage("Images/Scene/Floor.png");
        floor.y = window.getHeight() - floor.height;
        
        //Load the button and put they on their correct posiont
        GameImage play = new GameImage("Images/GUI/Menu/Play.png");
        play.x = (window.getWidth() - play.width) / 2;
        play.y = 150;
        GameImage setting = new GameImage("Images/GUI/Menu/Setting.png");
        setting.x = (window.getWidth() - setting.width) / 2;
        setting.y = play.y + (play.height * 1.7);
        GameImage exit = new GameImage("Images/GUI/Menu/Exit.png");
        exit.x = (window.getWidth() - exit.width) / 2;
        exit.y = setting.y + (setting.height * 2);        
        
        while (true) {
            //Put the arrow over the tight button, using index how reference
            switch (index) {
                case 0:
                    PutArrowsOnIndex(play);
                    break;
                case 1:
                    PutArrowsOnIndex(setting);
                    break;
                case 2:
                    PutArrowsOnIndex(exit);
                    break;
                default:
                    index = 0;
                    break;
            }
            
            sky.draw();
            floor.draw();
            
            play.draw();
            setting.draw();
            exit.draw();
            
            righArrow.draw();
            leftArrow.draw();
            
            if (cheatEnable)
                cheatEnableImage.draw();
            
            window.update();
            
            //Move the arrow by the menu
            if (keyboard.keyDown(keyboard.UP_KEY)) {
                select = new Sound(SELECT_FILE);
                select.play();
                
                index --;
                if (index < 0) {
                    index = 2;
                }
            } else if (keyboard.keyDown(keyboard.DOWN_KEY)) {
                select = new Sound(SELECT_FILE);
                select.play();
                
                index ++;
                if (index > 3) {
                    index = 3;
                }
            } else if (keyboard.keyDown(keyboard.SPACE_KEY) && keyboard.keyDown(KeyEvent.VK_CONTROL)) {
                select = new Sound(SELECT_FILE);
                select.play();
                
                cheatEnable = !cheatEnable;
                
                if (cheatEnable) {
                    oldLevelDistancePipesX = levelDistancePipesX;
                    oldLevelDistancePipesY = levelDistancePipesY;
                    oldLevelSpeed = levelSpeed;
                } else {
                    levelDistancePipesX = oldLevelDistancePipesX;
                    levelDistancePipesY = oldLevelDistancePipesY;
                    levelSpeed = oldLevelSpeed;
                    xml.WtriteInXml(levelDistancePipesX, levelDistancePipesY, levelSpeed);
                }
                
            } else if (keyboard.keyDown(keyboard.ENTER_KEY)) {
                select = new Sound(SELECT_FILE);
                select.play();
                
                return index;
            } else if (keyboard.keyDown(keyboard.ESCAPE_KEY)) {
                select = new Sound(SELECT_FILE);
                select.play();
                
                if (cheatEnable) {
                    xml.WtriteInXml(oldLevelDistancePipesX, oldLevelDistancePipesY, oldLevelSpeed);
                }
                
                window.exit();
            }
        }
        
    }
    
    private void PutArrowsOnIndex (GameImage image) {
        righArrow.x = (image.x + image.width) + 8;
        righArrow.y = image.y;
        righArrow.height = image.height;
        righArrow.width = (int) (image.height * 0.7);
        
        leftArrow.x = image.x - leftArrow.width - 8;
        leftArrow.y = righArrow.y;
        leftArrow.height = righArrow.height;
        leftArrow.width = righArrow.width;
    }
    
}
