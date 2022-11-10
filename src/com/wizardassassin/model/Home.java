package com.wizardassassin.model;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class Home {


    public Home() {
    }

    public String gameObjective() throws IOException, URISyntaxException {
        Gson gson = new Gson();
        ClassLoader loader = getClass().getClassLoader();
        final URI uri = loader.getResource("introduction.json").toURI();
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
//        FileSystem zipfs = FileSystems.newFileSystem(uri, env);
//        Path myFolderPath = Paths.get(uri);
//        String reader = Files.readString(Path.of(uri));
//        Introduction obj = gson.fromJson(reader, (Type) Introduction.class);
//        String gameIntro = obj.getIntroduction();
//        return gameIntro;

        //noinspection ConstantConditions
        try (Reader reader = new InputStreamReader(loader.getResourceAsStream("introduction.json"))) {
            return gson.fromJson(reader, Introduction.class).getIntroduction();
        }
    }
}
