package org.example;

import java.awt.event.WindowAdapter;

public class MVCAppWindowListener  extends WindowAdapter {

    void WindowClosing() {
        System.exit(0);
    }
}
