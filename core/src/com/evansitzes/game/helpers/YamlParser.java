package com.evansitzes.game.helpers;

import com.badlogic.gdx.Gdx;
import com.evansitzes.game.model.ArticlesEnvelope;
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
public class YamlParser {

    public static CharactersEnvelope loadData(){
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("resources/player.yml")));
            return mapper.readValue(file, CharactersEnvelope.class);
        } catch (final IOException e) {
            System.out.println(e);
        }

        return null;
    }

    public static ArticlesEnvelope loadItemMap() {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("resources/article.yml")));
            return mapper.readValue(file, ArticlesEnvelope.class);
        } catch (final IOException e) {
            System.out.println(e);
        }

        return null;
    }

    //TODO combine these
    public static void saveEquipment(final ArrayList<String> equipment, final ArrayList<String> inventory) {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("resources/player.yml")));
            final CharactersEnvelope charactersEnvelope = mapper.readValue(file, CharactersEnvelope.class);

            charactersEnvelope.getCharacters().get(0).setEquipment(equipment);
            charactersEnvelope.getCharacters().get(0).setInventory(inventory);


            mapper.writeValue(new FileOutputStream(file), charactersEnvelope);
        } catch (final IOException e) {
            System.out.println(e);
        }
    }

    public static void saveGold(final int gold) {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("resources/player.yml")));
            final CharactersEnvelope charactersEnvelope = mapper.readValue(file, CharactersEnvelope.class);

            charactersEnvelope.getCharacters().get(0).setGold(gold);
            mapper.writeValue(new FileOutputStream(file), charactersEnvelope);
        } catch (final IOException e) {
            System.out.println(e);
        }
    }
}
