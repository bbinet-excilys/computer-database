package ui;

import java.util.List;

import model.Computer;

public class ComputerUI {
    
    public static void print(Computer computer) {
        System.out.println(computer.toString());
    }
    
    public static void printList(List<Computer> computerList) {
        for(Computer tComputer : computerList) {
            print(tComputer);
        }
    }

}
