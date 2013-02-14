package org.successcorner.learning_utils;

import java.util.Scanner;
import java.io.*;          // Access System.out


public class GetDigitAtLocation {

  public static void main(String[] args) {
    System.out.print("Enter a number : ");
    Scanner in = new Scanner(System.in);
    int num = in.nextInt();
    
    System.out.println("The digit at location " +  num + " is " + getDigitAtLocation(num));

  }
  
  
  public static int getDigitRange(int index) {
    int round = 1;
    int min = 0;
    int max = 0;
    while (true) {
      min = max + 1;
      max = (int) (min + 9*round*(Math.pow(10.0, (round-1)))) - 1;
      if (index >= min && index <= max) {
        return round;
      }
      round++;
    }
  }
  
  public static int getOffsetIndex(int index) {
    int rounds = getDigitRange(index);
    int min = 0;
    int max = 0;
    int band = 1;
    int offset = 0;
    while (band < rounds) {
      min = max + 1;
      max = (int) (min + 9*band*(Math.pow(10.0, (band-1)))) - 1;
      offset += (max-min+1);
      band++;
    }
    return index - offset - 1;
  }
  
  public static int getDigitAtLocation(int index) {
    int numberOfDigits = getDigitRange(index);
    int offsetIndex = getOffsetIndex(index);
    int numbersCovered = 0;
    for (int i=0; i<numberOfDigits-1; i++) {
      numbersCovered += 9*(Math.pow(10.0,i));
    }
    numbersCovered += (offsetIndex/numberOfDigits);
    int digit = Integer.toString(numbersCovered+1).toCharArray()[offsetIndex%numberOfDigits];
    return digit-48;
  }
}
