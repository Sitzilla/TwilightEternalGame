package com.evansitzes.game.helpers;

import com.badlogic.gdx.Gdx;
import com.evansitzes.game.model.CharactersEnvelope;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.io.File;
import java.io.IOException;


/**
 * Created by evan on 10/31/16.
 */
public class MyDataYamlFile {
//    @JsonProperty
//    private List<MyData> skills;
//
//    public List<MyData> getSkills() {
//        return skills;
//    }

    public CharactersEnvelope loadData(){
//        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//        FileHandle yaml = Gdx.files.internal("resources/player.txt");
//        FileHandle file = Gdx.files.local("resources/player.txt");

//        FileHandle[] files = Gdx.files.local("resources/").list();


        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("resources/player.yml")));
            CharactersEnvelope envelope = mapper.readValue(file, CharactersEnvelope.class);

            System.out.println(envelope);
            return envelope;
        } catch (final IOException e) {
            System.out.println(e);
//            throw new UnprocessableEntityException("Unable to parse customers. Error: " + e.getMessage());
        }

        return null;
//        for(FileHandle file: files) {
//            System.out.println(file.readString());
//            file.writeString("My god, it's full of stars", true);
//        }
//        try {
////            MyDataYamlFile data = mapper.readValue(yaml.readString(), MyDataYamlFile.class);
////            return data.getSkills();
//        } catch (IOException e) {
//            Gdx.app.log("MyDataYamlFile", "loadData", e);
//        }
//        return null;
    }
}
