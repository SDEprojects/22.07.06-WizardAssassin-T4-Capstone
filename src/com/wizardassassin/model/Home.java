package com.wizardassassin.model;

import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Home {

    public Home() {
    }

    public String gameObjective() throws IOException, URISyntaxException {
        Gson gson = new Gson();
        ClassLoader loader = getClass().getClassLoader();
        URI uri = loader.getResource("introduction.json").toURI();
        String reader = Files.readString(Path.of(uri));
        Introduction obj = gson.fromJson(reader, (Type) Introduction.class);
        String gameIntro = obj.getIntroduction();
        String gameObj = obj.getObjective();
        String gameWin = obj.getWin();
        return "\033[35m" + gameIntro + "\n" + gameObj + "\n" + gameWin + "\033[0m";
    }
}
