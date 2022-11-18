import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{
        Scanner input = new Scanner(System.in);
        List<List<String>> users = new ArrayList<>();
        List<String> list = new ArrayList<>();
        int choice;
        System.out.println("Welcome to Banking Record System.");
        String user;
        String data = "";
        BufferedReader reader = new BufferedReader(new FileReader("UserData"));
        while ((user = reader.readLine()) != null) {
            for (int i = 0; i < user.length(); i++) {
                if (user.charAt(i) != ' ')
                    data += user.charAt(i);
                else {
                    list.add(data);
                    data = "";
                }
            }
        }
        reader.close();
        do {
            System.out.println();
            System.out.println("1) Create new account");
            System.out.println("2) Check balance");
            System.out.println("3) Add money");
            System.out.println("4) Transfer money");
            System.out.println("5) Sort accounts");
            System.out.println("6) Delete account");
            System.out.println("7) Exit from program" + "\n");
            choice = input.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter your name: ");
                    String name = input.next();
                    System.out.print("Enter your surname: ");
                    String surname = input.next();
                    System.out.print("Enter account number: ");
                    long accountNumber = input.nextLong();
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("UserData"));
                        list.add(name);
                        list.add(surname);
                        list.add(Long.toString(accountNumber));
                        list.add("0");
                        int index = 4;
                        for (int i = 0; i < list.size(); i++) {
                            if (i == index) {
                                index += 4;
                                writer.write("\n");
                            }
                            writer.write(list.get(i) + " ");
                        }
                        writer.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("\nAccount created!");
                }
                case 2 -> {
                    System.out.print("Enter account number: ");
                    long accountNumber = input.nextLong();
                    String balance = "";
                    boolean check = false;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).equals(Long.toString(accountNumber))) {
                            check = true;
                            balance = list.get(i + 1);
                        }
                    }
                    if (check)
                        System.out.println("Balance = " + balance);
                    else
                        System.out.println("Account number doesn't exists!");
                }
                case 3 -> {
                    System.out.print("Enter account number: ");
                    long accountNumber = input.nextLong();
                    boolean check = false;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).equals(Long.toString(accountNumber))) {
                            check = true;
                        }
                    }
                    if(check) {
                        System.out.print("Enter amount of money: ");
                        long amountOfMoney = input.nextLong();
                        long sum;
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).equals(Long.toString(accountNumber))) {
                                sum = Integer.parseInt(list.get(i+1)) + amountOfMoney;
                                list.set(i + 1, Long.toString(sum));
                            }
                        }
                        try {
                            BufferedWriter writer = new BufferedWriter(new FileWriter("UserData"));
                            int index = 4;
                            for (int i = 0; i < list.size(); i++) {
                                if (i == index) {
                                    index += 4;
                                    writer.write("\n");
                                }
                                writer.write(list.get(i) + " ");
                            }
                            System.out.println("The money has been added to the account!");
                            writer.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else
                        System.out.println("Account number doesn't exists!");
                }
                case 4 -> {
                    System.out.print("Enter transmitter account number: ");
                    long firstAccountNumber = input.nextLong();
                    System.out.print("Enter receiver account number: ");
                    long secondAccountNumber = input.nextLong();
                    boolean check = false;
                    for (int i = 0; i < list.size(); i++) {
                        if(list.contains(Long.toString(firstAccountNumber)) && list.contains(Long.toString(secondAccountNumber))){
                            check = true;
                        }
                    }
                    if(check){
                        System.out.print("Enter amount of money: ");
                        long amountOfMoney = input.nextLong();
                        boolean b = false;
                        for (int i = 0; i < list.size(); i++) {
                            if(list.get(i).equals(Long.toString(firstAccountNumber))){
                                long firstAccountBalance = Long.parseLong(list.get(i+1));
                                for (int j = 0; j < list.size(); j++) {
                                    if (list.get(j).equals(Long.toString(secondAccountNumber))) {
                                        long secondAccountBalance = Long.parseLong(list.get(j+1));
                                        if(firstAccountBalance - amountOfMoney < 0)
                                            System.out.println("Insufficient balance!");
                                        else{
                                            long result = firstAccountBalance - amountOfMoney;
                                            list.set(i+1, Long.toString(result));
                                            result = secondAccountBalance + amountOfMoney;
                                            list.set(j+1,Long.toString(result));
                                            b = true;
                                        }
                                    }
                                }
                            }
                        }
                        try {
                            BufferedWriter writer = new BufferedWriter(new FileWriter("UserData"));
                            int index = 4;
                            for (int i = 0; i < list.size(); i++) {
                                if (i == index) {
                                    index += 4;
                                    writer.write("\n");
                                }
                                writer.write(list.get(i) + " ");
                            }
                            writer.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else
                        System.out.println("Account number(s) doesn't exists.");
                }
                case 5 -> {
                    List<String> innerList = new ArrayList<>();
                    int nextLine = 4;
                    list.add(" ");
                    for (int i = 0; i < list.size(); i++) {
                        if(i == nextLine){
                            nextLine += 4;
                            users.add(new ArrayList<>(innerList));
                            innerList.clear();
                        }
                        innerList.add(list.get(i));
                    }
                    for (int i = 0; i < users.size(); i++) {
                       for (int j = 0; j < users.get(i).size(); j++) {
                            if (Long.parseLong(users.get(i).get(3)) < Long.parseLong(users.get(j).get(3))){
                                List<String> s = users.get(i);
                                users.set(i,users.get(j));
                                users.set(j,s);
                            }
                        }
                    }
                    for (int i = 0; i < users.size(); i++) {
                        for (int j = 0; j < users.get(i).size(); j++) {
                            System.out.print(users.get(i).get(j) + " ");
                        }
                        System.out.println();
                    }
                }
                case 6 -> {
                    System.out.print("Enter account number: ");
                    long accountNumber = input.nextLong();
                    boolean check = false;
                    for (int i = 0; i < list.size(); i++) {
                        if(list.contains(Long.toString(accountNumber))){
                            check = true;
                        }
                    }
                    if(check) {
                        List<List<String>> withoutDeleteUser = new ArrayList<>();
                        List<String> innerList = new ArrayList<>();
                        int nextLine = 4;
                        list.add(" ");
                        for (int i = 0; i < list.size(); i++) {
                            if (i == nextLine) {
                                nextLine += 4;
                                users.add(new ArrayList<>(innerList));
                                innerList.clear();
                            }
                            innerList.add(list.get(i));
                        }
                        for (int i = 0; i < users.size(); i++) {
                            if (!users.get(i).get(2).equals(Long.toString(accountNumber))) {
                                withoutDeleteUser.add(users.get(i));
                            }
                        }
                        users.clear();
                        users.addAll(withoutDeleteUser);
                        BufferedWriter writer = new BufferedWriter(new FileWriter("UserData"));
                        for (int i = 0; i < users.size(); i++) {
                            for (int j = 0; j < users.get(i).size(); j++) {
                                writer.write(users.get(i).get(j) + " ");
                            }
                            writer.write("\n");
                        }
                        System.out.println("Account deleted!");
                        writer.close();
                    }
                    else
                        System.out.println("Account number doesn't exists!");
                }
                case 7 -> System.out.println("Program Ended.");
                default -> System.out.println("Entered wrong choice!" + "\n");
            }
        } while (choice != 7);
    }
}