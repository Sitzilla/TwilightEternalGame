package com.evansitzes.game.helpers;

import com.badlogic.gdx.Gdx;
import com.evansitzes.game.entity.team.Player;
import com.evansitzes.game.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by evan on 10/31/16.
 */
public class YamlParser {

    public static CharactersEnvelope loadData() {
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

    public static NpcConfiguration loadNpcConfiguration(final String tag) {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("resources/npc-configuration.yml")));
            final NpcConfigurationsEnvelope config = mapper.readValue(file, NpcConfigurationsEnvelope.class);
            return config.getConfiguration(tag);
        } catch (final IOException e) {
            System.out.println(e);
        }

        return null;
    }

    public static HashMap<String, Integer> loadPrices() {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("resources/price.yml")));
            final PricesEnvelope prices = mapper.readValue(file, PricesEnvelope.class);
            return prices.getMap();

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

    public static void savePlayer(final Player player) {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("resources/player.yml")));
            final CharactersEnvelope charactersEnvelope = mapper.readValue(file, CharactersEnvelope.class);

            charactersEnvelope.getCharacters().get(0).setEquipment(player.equipment);
            charactersEnvelope.getCharacters().get(0).setInventory(player.inventory);
            charactersEnvelope.getCharacters().get(0).setGold(player.gold);
            charactersEnvelope.getCharacters().get(0).setBaseAttributes(player.getBaseAttributes());
            charactersEnvelope.getCharacters().get(0).setExperience(player.experience);
            charactersEnvelope.getCharacters().get(0).setLevel(player.level);
            charactersEnvelope.getCharacters().get(0).setCurrentHitPoints(player.currentHitPoints);
            charactersEnvelope.getCharacters().get(0).setCurrentMagicPoints(player.currentMagicPoints);

            mapper.writeValue(new FileOutputStream(file), charactersEnvelope);
        } catch (final IOException e) {
            System.out.println(e);
        }
    }
}
