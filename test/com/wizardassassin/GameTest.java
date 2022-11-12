package com.wizardassassin;

import com.wizardassassin.view.model.*;
import com.wizardassassin.model.*;
import org.junit.*;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.*;

import static junit.framework.TestCase.*;

public class GameTest {


    @Test
    public void shouldReturnHomeFrameTitle() throws IOException, URISyntaxException {
        JFrame frame = new JFrame();
        HomeFrame homeFrame = new HomeFrame(frame);
        assertEquals("Wizard Assassin", frame.getTitle());
    }

    @Test
    public void shouldReturnHomePanelTitle() throws IOException, URISyntaxException {
        JFrame frame = new JFrame();
        HomeFrame homeFrame = new HomeFrame(frame);
        assertEquals("WIZARD ASSASSIN", homeFrame.getTitle().getText());
        }

    @Test
    public void shouldReturnHomePanelDescription() throws IOException, URISyntaxException {
        JFrame frame = new JFrame();
        HomeFrame homeFrame = new HomeFrame(frame);
        Home home = new Home();
        assertEquals(home.gameObjective(), homeFrame.getDescription().getText());
    }

    @Test
    public void shouldReturnHomePanelStartButton() throws IOException, URISyntaxException {
        JFrame frame = new JFrame();
        HomeFrame homeFrame = new HomeFrame(frame);
        Home home = new Home();
        assertEquals("Start", homeFrame.getStartButton().getText());
    }

    @Test
    public void shouldReturnHomePanelQuitButton() throws IOException, URISyntaxException {
        JFrame frame = new JFrame();
        HomeFrame homeFrame = new HomeFrame(frame);
        Home home = new Home();
        assertEquals("Quit", homeFrame.getQuitButton().getText());
    }
}
