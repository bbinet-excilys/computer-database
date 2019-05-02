package com.excilys;

import java.util.Scanner;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGenerator {

  public static void main(String[] args) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    Scanner               scanner = new Scanner(System.in);
    String                toHash  = scanner.nextLine();
    System.out.println(encoder.encode(toHash));
  }

}
