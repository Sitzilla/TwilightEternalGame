package com.evansitzes.game.helpers;

import com.badlogic.gdx.Gdx;
import com.evansitzes.game.model.Character;
import com.evansitzes.game.model.CharactersEnvelope;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by evan on 10/31/16.
 */
public class MyDataYamlFile {

    public static CharactersEnvelope loadData(){
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("resources/player.yml")));
            final CharactersEnvelope envelope = mapper.readValue(file, CharactersEnvelope.class);

            System.out.println(envelope);
            return envelope;
        } catch (final IOException e) {
            System.out.println(e);
        }

        return null;
    }

    //TODO combine these
    public static void saveEquipment(final ArrayList<String> equipment, final ArrayList<String> inventory) {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        final CharactersEnvelope charactersEnvelope = new CharactersEnvelope();
        final Character player = new Character();
        player.setName("Toshiro");
        player.setEquipment(equipment);
        player.setInventory(inventory);

        final ArrayList<Character> characters = new ArrayList<Character>();
        characters.add(player);

        charactersEnvelope.setCharacters(characters);
        try {
            final File file = new File(String.valueOf(Gdx.files.local("resources/player.yml")));
            mapper.writeValue(new FileOutputStream(file), charactersEnvelope);
        } catch (final IOException e) {
            System.out.println(e);
        }
    }
}
