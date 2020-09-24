/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockdemo;
import java.io.IOException;
import static java.lang.Thread.sleep;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author brett
 */


public class StockDemo {
    public static double getPrice()throws IOException {
        Document doc = Jsoup.connect("https://www.google.com/search?q=azn&oq=azn&aqs=chrome..69i57j0j69i59j46l3j0j46.570j0j4&sourceid=chrome&ie=UTF-8").get();
        String s = doc.getElementsByTag("span").text();
        //Test Connection
        //System.out.println(s);
        //System.out.println(doc.getElementsByTag("span class=\"knowledge-finance-wholepage-chart__hover-card-value\"").text());
        //Elements temp = doc.select("span.knowledge-finance-wholepage-chart__hover-card-value");
        //System.out.println(temp);

        char[] arr = s.toCharArray();
        //char[] numArr;
        if (arr[16] != 'O') {
            char[] numArr = Arrays.copyOfRange(arr, 16, 21);
            //numArr = Arrays.copyOfRange(arr, 16, 21);
            StringBuilder sb = new StringBuilder();
            sb.append(numArr);
            Double currentPrice = Double.parseDouble(sb.toString());
            return currentPrice;
        }
        else{
            char[] numArr = Arrays.copyOfRange(arr, 53, 58);
            //numArr = Arrays.copyOfRange(arr, 16, 21);
            StringBuilder sb = new StringBuilder();
            sb.append(numArr);
            Double currentPrice = Double.parseDouble(sb.toString());
            return currentPrice;
        }
    }

    public static double buy(double account, double currentPrice, boolean flag){
        if (flag == false){
            account -= currentPrice;
            System.out.print("I bought a share at $" + currentPrice + ".            ");
            flag = true;
        }
        return account;
    }

    public static double sell(double account, double currentPrice, boolean flag){
        if (flag == true){
            account += currentPrice;
            System.out.print("I sold a share at $" + currentPrice + ".              ");
            flag = false;
        }
        return account;
    }

    public static void check(double account, double prevPrice, double currentPrice, boolean flag){
        if (currentPrice < prevPrice){
            sell(account, currentPrice, flag);
        }
        else if (currentPrice > prevPrice){
            buy(account, currentPrice, flag);
        }
        else{
            System.out.print("Waiting.. Current price is $" + currentPrice + ". ");
        }
        System.out.println("Account Balance: $" + account);
        prevPrice = currentPrice;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        double account = 5000.00;
        double count = -1;
        boolean flag = false;
        double prevPrice = getPrice();
        int way = 0;
        int up = 0;
        int down = 0;
        System.out.print("Current price is $" + prevPrice + ".               ");
        System.out.print("Account Balance: $" + account + "     Time: ");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        sleep(60_000);

        while (true){
            double currentPrice = getPrice();// + count;
            if (currentPrice < prevPrice){
                if (flag == true){
                    if (down == 1) {
                        account += currentPrice;
                        System.out.print("I sold a share at $" + currentPrice + ".              ");
                        flag = false;
                        down = 0;
                        up = 0;
                    }
                    else{
                        System.out.print("Decreased to " +currentPrice +". I am waiting for another decrease.   ");
                        down = 1;
                        up = 0;
                    }
                }
                else{
                    System.out.print("Price is still decreasing from $" + currentPrice + ". ");
                }
            }
            else if (currentPrice > prevPrice){
                if (flag == false){
                    if (up == 1) {
                        account -= currentPrice;
                        System.out.print("I bought a share at $" + currentPrice + ".            ");
                        flag = true;
                        up = 0;
                        down = 0;
                    }
                    else{
                        System.out.print("Increased to " +currentPrice +". I am waiting for another increase.   ");
                        up = 1;
                        down = 0;
                    }
                }
                else{
                    System.out.print("Price is still increasing from $" + currentPrice + ". ");
                }

            }
            else{
                System.out.print("Current price is still $" + currentPrice + ".         ");
            }
            System.out.print("Account Balance: $" + account + "     Time: ");
            DateTimeFormatter date = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime current = LocalDateTime.now();
            System.out.println(dtf.format(current));
            prevPrice = currentPrice;

            //Test Algorithm
            /*if (way == 0){
                count--;
                if (count == -6){
                    way = 1;
                }

            }
            else{
                count++;
                if (count == 6){
                    way = 0;
                }
            }*/

            sleep(60_000);
        }
    }
}
