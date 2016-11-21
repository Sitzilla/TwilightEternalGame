package com.evansitzes.game.conversation;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by evan on 9/22/16.
 */
public class ConversationChoice {
    ArrayList<String> choices = new ArrayList<String>();

    public ConversationChoice() {
        choices.add("What brings you to this land traveler?");
        choices.add("Everyone is speaking of monster sightings in the woods.");
        choices.add("I don't recognize your face but... it's far to the next town.");
        choices.add("I cannot remember the last time I saw the sun... or the moon.");
        choices.add("The King has holed himself up in the castle.");
        choices.add("Why isn't the King doing anything about the strangeness in the woods?");
        choices.add("The goblins have been more bold as of late");

    }

    public String getRandomConversation() {
        return choices.get(new Random().nextInt(choices.size()));
    }
}
