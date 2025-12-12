package com.vladislav.onlinertest.core.driver;
import java.util.Scanner;
public class  PizzaSplit  {



        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);


            int pn = scanner.nextInt();
            int pieces = scanner.nextInt();


            int pizzas = 1;

            while ((pizzas * pieces) % pn!= 0) {
                pizzas++;
            }

            System.out.println(pizzas);

            scanner.close();
        }
    }

