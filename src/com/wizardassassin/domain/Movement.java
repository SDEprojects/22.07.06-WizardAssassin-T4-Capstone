package com.wizardassassin.domain;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Movement extends Game{

    public Movement() throws IOException {
    }

    public void handleMovement(String inputNoun) {
        String locationInput = getCurrentLocation().getDirections().get(inputNoun);
        if(locationInput.equals("Courtyard") && getCurrentLocation().getName().equals("Church")) {
            if(npcNames.isEmpty()) {
                setCurrentLocation(getObj().getPickedLocation(locationInput));
            }
            else {
                System.out.printf("The \033[31m%s\033[0m blocks your path. You must fight it.\nUse the stick%n", getNpcNames().get(0).toUpperCase());
            }
        }
        else if(locationInput.equals("Great Hall") && getCurrentLocation().getName().equals("Courtyard") && getCount() == 0) {
            if(getInventoryItems().contains("password")) {
                System.out.println("\033[31mGuard:\033[0m That's the right \033[92mPASSWORD\033[0m. Go ahead and pass.");
                System.out.println();
                while(true) {
                    System.out.println("Hit 'enter' to continue");
                    String progress = getInputScanner().nextLine();
                    if(progress.equals("")) {
                        break;
                    }
                }
                setCount(getCount()+1);
                setCurrentLocation(getObj().getPickedLocation(locationInput));
            }
            else {
                System.out.println("\033[31mGuard:\033[0m Wrong \033[92mPASSWORD\033[0m! Get outta here, ya scum!");
                System.out.println("\n\u001B[91m                         *********  You remain in the " + getCurrentLocation().getName() + ". *********\u001B[0m\n\n");
            }
        }
        else if(locationInput.equals("Royal Lounge") && getCurrentLocation().getName().equals("Great Hall") && getCount() == 1) {
            if(getInventoryItems().contains("tunic") && getInventoryItems().contains("sword")) {
                System.out.println("\033[31mGuard:\033[0m I don't know you... but you have the Kingdom's \033[92mTUNIC\033[0m... and that \033[92mSWORD\033[0m... You must be new... go ahead and pass.");
                System.out.println();
                while(true) {
                    System.out.println("Hit 'enter' to continue");
                    String progress = getInputScanner().nextLine();
                    if(progress.equals("")) {
                        break;
                    }
                }
                setCount(getCount()+1);
                setCurrentLocation(getObj().getPickedLocation(locationInput));
            }
            else {
                System.out.println("\033[31mGuard:\033[0m Where do you think you're going? Only knights can pass through here.\nAnd not just any bloak with a Kingdom's \033[92mTUNIC\033[0m.\nYou need a \033[92mSWORD\033[0m too.");
                System.out.println("\n\u001B[91m                         *********  You remain in the " + getCurrentLocation().getName() + ". *********\u001B[0m\n\n");
            }
        }
        else if(locationInput.equals("Wizard's Foyer") && getCurrentLocation().getName().equals("Great Hall") && getCount() <= 2) {
            if(getInventoryItems().contains("diamond key")) {
                System.out.println("Maybe I can \033[31mUSE\033[0m that \033[92mDIAMOND KEY\033[0m on this door.");
            }
            else {
                System.out.println("Hmm, it's locked. There's an emblem in the shape of a \033[92mDIAMOND\033[0m on the door");
                System.out.println("\n\u001B[91m                         *********  You remain in the " + getCurrentLocation().getName() + ". *********\u001B[0m\n\n");
            }
        }
        else {
            setCurrentLocation(getObj().getPickedLocation(locationInput));
        }
    }
}
