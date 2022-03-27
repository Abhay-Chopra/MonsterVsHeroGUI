package mvh.util;

import mvh.enums.WeaponType;
import mvh.world.Hero;
import mvh.world.Monster;
import mvh.world.World;

import java.io.*;

//TODO invalid files should cause an update status instead of overwriting files

/**
 * Class to assist reading in world file
 * @author Jonathan Hudson
 * @version 1.0
 */
public final class Reader {

    /**
     * Load the world from the given file
     * (Do not expect students to create anything near as robust as this file reading method!)
     * (A better design would also use sub-functions.)
     *
     * @param worldFile The world file to load
     * @return A World created from the world file
     */
    public static World loadWorld(File worldFile){
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
                            int row = 0;
                            int column = 0;
                            try{
                                //Initializing info common between Entities
                                row = Integer.parseInt(lineInfo[0]);
                                column = Integer.parseInt(lineInfo[1]);
                                if(row != currentRow || column != currentColumn)
                                {
                                    throw new Exception("Skipped row or column");
                                }
                            }catch (ArrayIndexOutOfBoundsException e){
                                System.err.println("Invalid entries in world file!");
                                System.exit(1);
                            } catch (Exception e) {
                                System.err.println("Invalid entries within world file!");
                                System.exit(1);
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
                                System.err.println("Too many values in line of provided world file!");
                                System.exit(1);
                            }
                            line = bufferedReader.readLine();
                        }
                    }
                }
            }catch (NumberFormatException e){
                System.err.println("Couldn't read from file! Missing world row or colum size!");
                System.exit(1);
            }
            catch (FileNotFoundException e) {
                System.err.println("Could not find file: " + worldFile.getAbsolutePath());
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Could not close file: " + worldFile.getAbsolutePath());
                System.exit(1);
            }
        }
        return world;
    }

    /**
     * Saves world information to desired file
     * @param file File to save to
     */
    public static void saveFile(File file, World world) {
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
                System.err.println("Error writing to file " + file);
                System.exit(1);
            }
        }
    }
}