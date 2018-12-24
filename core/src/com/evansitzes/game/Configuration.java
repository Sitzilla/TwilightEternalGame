package com.evansitzes.game;

/**
 * Created by evan on 6/9/16.
 */
public class Configuration {
//    public static final int STARTING_POSITION_X = 416; // Test
//    public static final int STARTING_POSITION_Y = 224; // Test
    public static final int STARTING_POSITION_X = 600; // Woods
    public static final int STARTING_POSITION_Y = 300; // Woods
//    public static final int STARTING_POSITION_X = 500; // Town
//    public static final int STARTING_POSITION_Y = 300; // Town

    public static final int WIDTH = 1296;
    public static final int HEIGHT = 864;
    public static final float CAMERA_WIDTH = (float) (WIDTH / 1.5); // 1.5 width
    public static final float CAMERA_HEIGHT = (float) (HEIGHT / 1.5); // 1.5 of height

    public static final String TITLE = "Twilight Eternal";
    public static final String STARTING_LEVEL = "woods";
//    public static final String STARTING_LEVEL = "town";

    // NPC configs
    public static final int NPC_STARTING_OFFSET_X = 10;
    public static final int NPC_STARTING_OFFSET_Y = 10;
    public static final int NPC_RECTANGLE_OFFSET_X = 5;
    public static final int NPC_RECTANGLE_OFFSET_Y = 22;
    public static final int NPC_RECTANGLE_WIDTH = 15;
    public static final int NPC_RECTANGLE_HEIGHT = 27;
    public static final int NPC_CONVERSATION_RECTANGLE_OFFSET_X = 25;
    public static final int NPC_CONVERSATION_RECTANGLE_OFFSET_Y = 35;
    public static final int NPC_CONVERSATION_RECTANGLE_WIDTH = 50;
    public static final int NPC_CONVERSATION_RECTANGLE_HEIGHT = 70;

    // Item configs
    public static final int ITEM_CAMPAIGN_SIZE = 30;
    public static final int ITEM_CONVERSATION_RECTANGLE_OFFSET_X = 10;
    public static final int ITEM_CONVERSATION_RECTANGLE_OFFSET_Y = 30;
    public static final int ITEM_CONVERSATION_RECTANGLE_WIDTH = 50;
    public static final int ITEM_CONVERSATION_RECTANGLE_HEIGHT = 70;

    // Debug
    public static final boolean DEBUG = true;

}
