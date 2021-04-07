package Server.Auth;

import Client.Client;
import Server.DB.ConnectionService;

import java.util.Scanner;


public class AutentificationDB {
    private String name;

    public AutentificationDB() {
    }

    public void regist(Scanner scanner){
        System.out.println("WELCOME IN CHAT");
        System.out.println();
        String login;
        String password;
        boolean subscribe = false;
        do{
            System.out.println("PLEASE ENTER YOUR LOGIN...");
            login = scanner.nextLine();
            if(!ConnectionService.isLoginFree(login)){
                do {
                    System.out.println("PLEASE ENTER YOUR PASSWORD...");
                    password = scanner.nextLine();
                    if (ConnectionService.checkPasswordbyLogin(login, password)) {
                        name = ConnectionService.getName(login);
                        System.out.println("HI " + name);
                        subscribe = true;
                        break;
                    } else {
                        System.out.println("PASSWOR WRONG!!!");
                    }

                }while (!ConnectionService.checkPasswordbyLogin(login, password));
            }else{
                System.out.println("LOGIN WRONG!!!");
                continue;
            }
        }while (!subscribe);

    }

    public String getName() {
        return name;
    }
}
