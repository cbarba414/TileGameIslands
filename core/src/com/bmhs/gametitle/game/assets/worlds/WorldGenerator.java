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

        seedColor = 38;
        lightGreen = 47;
        //call methods to build 2D array
        //randomize();
        //leftCoast ();

        setWater();
        seedIslands(10, 70);

        searchAndExpand(6, seedColor, lightGreen, 0.6);
        searchAndExpand(4, seedColor, 18, 0.55);
        searchAndExpand(2, seedColor, 44, 0.5);
        searchAndExpand(1, seedColor, 45, 0.45);
        searchAndExpand(1, seedColor, 46, 0.4);


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

//    private void seedIslands (int num) {
//        for (int i = 0; i < num; i++) {
//            int rSeed = MathUtils.random (worldIntMap.length-1);
//            int cSeed = MathUtils.random(worldIntMap[0].length-1);
//            worldIntMap[rSeed][cSeed] = seedColor;
//
//        }
//    }
// // the code above was the old method, this below is the new onw

    // below is the new seedislands im working on. for tomorrow, i need to make sure this method works and
    // that maybe i can increase the size of the vector
//private void seedIslands (int clusters, int islandsInCluster) {
//    for (int i = 0; i < clusters; i++) {
//        int rSeed = MathUtils.random(worldIntMap.length - 1);
//        int cSeed = MathUtils.random(worldIntMap[0].length - 1);
//
//        for (int island = 0; island < islandsInCluster; island++) {
//            int islandSize = MathUtils.random(worldMapColumns - 1);
//            int startX = MathUtils.random(rSeed - 1, rSeed + 1);
//            int startY = MathUtils.random(cSeed - 1, cSeed - 1);
//
//
//            for (int y = startY - islandSize; y <= startY + islandSize; y++) {
//                for (int x = startX - islandSize; y <= startX + islandSize; x++) {
//                    if (x >= 0 && x < cSeed && y >= 0 && y < rSeed) {
//                        if (MathUtils.random() < 0.001) {
//                            worldIntMap[y][x] = seedColor;
//                        }
//
//
//                    }
//                }
//            }
//        }
//    }
//}

    private void seedIslands(int numClusters, int numIslandsPerCluster) {
        for (int cluster = 0; cluster < numClusters; cluster++) {
            // Generate random cluster center
            int clusterCenterX = MathUtils.random(worldMapColumns - 1);
            int clusterCenterY = MathUtils.random(worldMapRows - 1);

        // Generate islands around the cluster center
            for (int island = 0; island < numIslandsPerCluster; island++) {
                int islandSize = MathUtils.random(1, 17); // Random island size
                int startX = MathUtils.random(clusterCenterX - 1, clusterCenterX + 1);
                int startY = MathUtils.random(clusterCenterY - 1, clusterCenterY + 1);

                // Generate island shape
                for (int y = startY - islandSize; y <= startY + islandSize; y++) {
                    for (int x = startX - islandSize; x <= startX + islandSize; x++) {
                        if (x >= 0 && x < worldMapColumns && y >= 0 && y < worldMapRows) {
                            if (MathUtils.random() < 0.001) { // Adjust randomness to control island shape
                                // Set the island color
                                worldIntMap[y][x] = seedColor;
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
