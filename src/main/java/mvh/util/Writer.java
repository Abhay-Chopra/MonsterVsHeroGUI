package mvh.util;

import mvh.world.World;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class to assist writing in world file
 * @author Jonathan Hudson, Abhay Chopra
 * @version 1.1
 */
public class Writer {
    /**
     * Saves world information to desired file
     *
     * @param file File (from FileChooser) to save world data to
     */
    public static void saveFile(File file, World world) {
        if(world == null){
            throw new RuntimeException("No current world!");
        }
        if(file.exists() && file.isFile() && file.canWrite()){
            //with-resources method of creating file and print writer
            try(FileWriter fileWriter = new FileWriter(file);
                //Writing row size, column size to file
                PrintWriter printWriter = new PrintWriter(fileWriter)) {
                printWriter.printf("%s%n%s%n",world.getRows(), world.getColumns());
                //Looping through World and accessing each location
                for (int rows = 0; rows < world.getRows(); rows++) {
                    for (int columns = 0; columns < world.getColumns(); columns++) {
                        //Handling entities
                        if(world.getEntity(rows, columns) != null && world.getEntity(rows, columns).isAlive()){
                            //Handling different types of entities
                            if(world.isHero(rows, columns)){
                                printWriter.printf("%s,%s,HERO,%s,%s,%s,%s\n",rows,columns,world.getEntity(rows,columns).getSymbol(),world.getEntity(rows,columns).getHealth(), world.getEntity(rows,columns).weaponStrength(), world.getEntity(rows,columns).armorStrength());
                            }else if(world.isMonster(rows, columns)){
                                //Handling Monster Weapon Types
                                char weaponType = 0;
                                if(world.getEntity(rows,columns).weaponStrength() == 2){
                                    weaponType = 'C';
                                }
                                else if(world.getEntity(rows,columns).weaponStrength() == 3){
                                    weaponType = 'A';
                                }
                                else if(world.getEntity(rows,columns).weaponStrength() == 4){
                                    weaponType = 'S';
                                }
                                //Writing line to File
                                printWriter.printf("%s,%s,MONSTER,%s,%s,%s\n",rows,columns,world.getEntity(rows,columns).getSymbol(),world.getEntity(rows,columns).getHealth(), weaponType);
                            }
                        }
                        //Handling floors
                        else if(world.getEntity(rows, columns) == null || world.getEntity(rows, columns).isDead()){
                            printWriter.printf("%s,%s\n",rows,columns);
                        }
                    }
                }
            }
            //Catching exceptions when file writing is interrupted
            catch (IOException e) {
                throw new RuntimeException("Invalid world file!");
            }
        }
    }
}
