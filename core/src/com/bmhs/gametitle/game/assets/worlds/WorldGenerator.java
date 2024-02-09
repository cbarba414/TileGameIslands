package com.bmhs.gametitle.game.assets.worlds;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;
import com.bmhs.gametitle.gfx.utils.TileHandler;


public class WorldGenerator {

    private int worldMapRows, worldMapColumns;

    private int[][] worldIntMap;

    private int seedColor, lightGreen, Green;

    public WorldGenerator(int worldMapRows, int worldMapColumns) {
        this.worldMapRows = worldMapRows;
        this.worldMapColumns = worldMapColumns;

        worldIntMap = new int[worldMapRows][worldMapColumns];


        Vector2 mapSeed = new Vector2(MathUtils.random(worldIntMap[0].length), MathUtils.random(worldIntMap.length));
        //Vector2 mapSeed = new Vector2(10,20);

        System.out.println(mapSeed.y + " " + mapSeed.x);

        worldIntMap[(int) mapSeed.y][(int) mapSeed.x] = 4;

        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {
                Vector2 tempVector = new Vector2(c, r);
                if (tempVector.dst(mapSeed) < 10) {
                    worldIntMap[r][c] = 2;
                }
            }
        }

        seedColor = 46;
        //randomize();
        //leftCoast ();

        setWater();
        seedIslands(10, 70);
        //change colors
        searchAndExpand(8, seedColor, 30, 0.15);
        searchAndExpand(8, seedColor, 31, 0.25);
        searchAndExpand(5, seedColor, 40, 0.45);
        searchAndExpand(3, seedColor, 53, 0.55);
        searchAndExpand(2, seedColor, 73, 0.65);
        searchAndExpand(1, seedColor, 45, 0.70);


        Gdx.app.error("WorldGenerator", "WorldGenerator(WorldTile[][][])");

        generateWorldTextFile();
    }


    //worldIntMap[(int)mapSeed.y][(int)mapSeed.x] = seedColor;


    public void setWater() {
        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {
                if (c >= 0) {
                    worldIntMap[r][c] = 20;
                }
            }
        }
    }


    private void seedIslands(int numOfGroups, int numIslandsPerGroup) {

        for (int group = 0; group < numOfGroups; group++) {
            int cSeed = MathUtils.random(worldMapColumns - 1);
            int rSeed = MathUtils.random(worldMapRows - 1);

            for (int island = 0; island < numIslandsPerGroup; island++) {
                int islandSize = MathUtils.random(1, 17);
                int firstRSeed = MathUtils.random(rSeed - 1, rSeed + 1);
                int firstCSeed = MathUtils.random(cSeed - 1, cSeed + 1);


                for (int r = firstRSeed - islandSize; r <= firstRSeed + islandSize; r++) {
                    for (int c = firstCSeed - islandSize; c <= firstCSeed + islandSize; c++) {
                        if (c >= 0 && c < worldMapColumns && r >= 0 && r < worldMapRows) {
                            if (MathUtils.random() < 0.001) {
                                worldIntMap[r][c] = seedColor;
                            }
                        }
                    }
                }
            }
        }
    }



    private void searchAndExpand (int radius, int numToFind, int numToWrite, double probability) {
        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {

                if (worldIntMap[r][c] == numToFind) {

                    for (int subRow = r-radius; subRow <= r+radius; subRow++) {
                        for (int subCol = c-radius; subCol <=c+radius; subCol++) {

                            if (subRow >= 0 && subCol >= 0 && subRow <= worldIntMap.length -1 && subCol <= worldIntMap[0].length-1 && worldIntMap [subRow][subCol] != numToFind) {
                               if (Math.random () < probability) {
                                   worldIntMap[subRow][subCol] = numToWrite;

                               }

                            }

                        }
                    }

                }

            }
        }
    }

    private void searchAndExpand (int radius) {
        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {

                if (worldIntMap[r][c] == seedColor) {

                    for (int subRow = r-radius; subRow <= r+radius; subRow++) {
                        for (int subCol = c-radius; subCol <=c+radius; subCol++) {

                            if (subRow >= 0 && subCol >= 0 && subRow <= worldIntMap.length -1 && subCol <= worldIntMap[0].length-1 && worldIntMap [subRow][subCol] != seedColor) {
                                worldIntMap[subRow][subCol] = 32;

                            }

                        }
                    }

                }

            }
        }
    }



    public String getWorld3DArrayToString() {
        String returnString = "";

        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                returnString += worldIntMap[r][c] + " ";
            }
            returnString += "\n";
        }

        return returnString;
    }
   /*public void leftCoast () {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldIntMap[r][c] = MathUtils.random(TileHandler.getTileHandler().getWorldTileArray().size-1);
                if (c< 10) {
                    worldIntMap[r][c] = 5;
                }
            }
        }
    } */


    public void randomize() {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldIntMap[r][c] = MathUtils.random(TileHandler.getTileHandler().getWorldTileArray().size-1);
            }
        }
    }



    public WorldTile[][] generateWorld() {
        WorldTile[][] worldTileMap = new WorldTile[worldMapRows][worldMapColumns];
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldTileMap[r][c] = TileHandler.getTileHandler().getWorldTileArray().get(worldIntMap[r][c]);
            }
        }
        return worldTileMap;
    }
    private void generateWorldTextFile () {
        FileHandle file = Gdx.files.local ("assets/worlds/world.txt");
        file.writeString (getWorld3DArrayToString(), false);
    }
}
