package com.botscrew.test;

import com.botscrew.test.ui.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MyRunner implements CommandLineRunner {

    private final Menu menu;

    public MyRunner(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String exit;
        do {
            menu.printMenu();
            System.out.println("Enter exit to exit or whatever to continue");
            exit = scanner.next();
        } while (!exit.equals("exit"));
    }
}
