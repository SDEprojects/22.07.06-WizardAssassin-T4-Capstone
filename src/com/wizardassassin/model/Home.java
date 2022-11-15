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

        //noinspection ConstantConditions
        try (Reader reader = new InputStreamReader(loader.getResourceAsStream("introduction.json"))) {
            return gson.fromJson(reader, Introduction.class).getIntroduction();
        }
    }
}
