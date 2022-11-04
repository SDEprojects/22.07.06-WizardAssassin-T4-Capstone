package com.wizardassassin.client;


import com.wizardassassin.controller.Home;

import java.io.IOException;
import java.net.URISyntaxException;

class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {

        Home app = new Home();
        app.execute();
    }
}