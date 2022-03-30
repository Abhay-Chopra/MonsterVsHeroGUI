package mvh.util;

import mvh.enums.WeaponType;
import mvh.world.Hero;
import mvh.world.Monster;
import mvh.world.World;

import java.io.*;


/**
 * Class to assist reading in world file
 * @author Jonathan Hudson, Abhay Chopra
 * @version 1.1
 */
public final class Reader {

    /**
     * Load the world from the given file
     *
     * @param worldFile The world file to load
     * @return A World created from the world file
     */
    public static World loadWorld(File worldFile) throws RuntimeException{
        World world = null;
        //reconfirming that file can be accessed
        if(worldFile.isFile() && worldFile.canRead() && worldFile.exists()){
            //Try and Catch Block for reading from file (With-resources)
            try(FileReader fileReader = new FileReader(worldFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader))
            {
                //Reading from File
                int rowCount = Integer.parseInt(bufferedReader.readLine());
                int columnCount = Integer.parseInt(bufferedReader.readLine());
                //Creating a world object
                world = new World(rowCount, columnCount);
                String line = bufferedReader.readLine();
                //Looping for "following" lines
                for(int currentRow = 0; currentRow < rowCount; currentRow++){
                    for (int currentColumn = 0; currentColumn < columnCount; currentColumn++) {
                        //Conditional checks that there is content within the line
                        if(line != null){
                            //Reading and splitting a line
                            String[] lineInfo = line.split(",");
                            //Initializing vars for current line
                            int row;
                            int column;
                            try{
                                //Initializing info common between Entities
                                row = Integer.parseInt(lineInfo[0]);
                                column = Integer.parseInt(lineInfo[1]);
                                if(row != currentRow || column != currentColumn)
                                {
                                    throw new Exception("Skipped row or column");
                                }
                            } catch (Exception e){
                                throw new RuntimeException("Invalid entries within world file!");
                            }
                            //Conditional for handling non-floor entities in row, column
                            if(lineInfo.length > 2 && lineInfo.length <= 7)
                            {
                                //getting health and symbol from the passed line
                                int entityHealth = Integer.parseInt(lineInfo[4]);
                                char entitySymbol = lineInfo[3].charAt(0);
                                //Creating entity dependent on type
                                if(lineInfo[2].equals("MONSTER"))
                                {
                                    //Creating variables as current Entity would be of type Monster
                                    char weaponSymbol = lineInfo[5].charAt(0);
                                    Monster monster = new Monster(entityHealth, entitySymbol, WeaponType.getWeaponType(weaponSymbol));
                                    //Adding entity onto the current world
                                    world.addEntity(row, column, monster);
                                }
                                else if(lineInfo[2].equals("HERO"))
                                {
                                    //Creating variables as current Entity would be of type Hero
                                    int weaponStrength = Integer.parseInt(lineInfo[5]);
                                    int armorStrength = Integer.parseInt(lineInfo[6]);
                                    Hero hero = new Hero(entityHealth, entitySymbol, weaponStrength, armorStrength);
                                    //Adding entity onto the world
                                    world.addEntity(row, column, hero);
                                }
                            } else if (lineInfo.length > 7){
                                throw new RuntimeException("Too many values in line of provided world file!");
                            }
                            line = bufferedReader.readLine();
                        }
                    }
                }
            }catch (NumberFormatException e){
                throw new RuntimeException("Missing row or colum size in world file!");
            } catch (IOException e) {
                throw new RuntimeException("Could not find file!");
            }
        }
        return world;
    }
}