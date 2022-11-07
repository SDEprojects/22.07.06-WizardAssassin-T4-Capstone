package com.wizardassassin.controller;

import com.apps.util.Console;
import com.google.gson.Gson;
import com.wizardassassin.model.Introduction;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Home {

    private Scanner inputScanner = new Scanner(System.in);
    Game newGame = new Game();

    public Home() throws IOException, URISyntaxException {
    }

    public void execute() throws IOException, URISyntaxException {
        title();
        gameObjective();
        beginGame();
    }

    private void title() throws IOException, URISyntaxException {
        System.out.println();
        ClassLoader loader = getClass().getClassLoader();
        URI uri = loader.getResource("welcome.txt").toURI();
        String welcome = Files.readString(Path.of(uri));
        System.out.println("\033[35m" + welcome + "\033[0m");

        // *** Avoid the below method ***
//        System.out.println("\033[35m" + Files.readString(Path.of("./resources/welcome.txt")) + "\033[0m");
        System.out.println();
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

//        System.out.println("\033[35m" + gameIntro + "\n" + gameObj + "\n" + gameWin + "\033[0m");
//        System.out.println();
        return "\033[35m" + gameIntro + "\n" + gameObj + "\n" + gameWin + "\033[0m";
    }

    private void beginGame() throws IOException, URISyntaxException {
        String start;

        System.out.println("\033[35m" + "Do you want to start the game? yes | no" + "\033[0m");
        start = inputScanner.nextLine().trim().toLowerCase();
        if (start.equals("yes") || start.equals("y")) {

            Console.clear();
            newGame.playGame(null, null,
                    null, null, null,
                    null, null, null,
                    null, null, null,
                    null, null, null,
                    null, null);
        } else if (start.equals("no") || start.equals("n")) {
            System.out.println("Thank you for playing");
            System.exit(0);
        } else {
            System.out.println("Please enter 'yes' to continue or 'no' to quit the game.");
            beginGame();
        }
    }
}
