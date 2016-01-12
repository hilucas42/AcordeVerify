package main;

import acorde.*;
import java.io.IOException;
import java.util.Scanner;
import mlp.Rede;

/**
 *
 * @author Lucas
 */
public class Main {

    void run() throws IOException, ClassNotFoundException {
        teclado = new Scanner(System.in);
        util = new AcordeUtil();
        acordeList = new AcordeGen(util);

        while (true) {
            switch (mainMenu()) {
                case '1':
                    System.out.println(acordeList.acordesValidos());
                    break;
                case '2':
                    acordeTest();
                    break;
                case '3':
                    acordeList.export();
                case '4':
                    new ControllerMLP(acordeList).runMLP();
                    break;
                case '0':
                    return;
                default:

            }
        }
    }

    void acordeTest() {

        acordeTest(false, null);
    }

    void acordeTest(Rede mlp) {

        acordeTest(true, mlp);
    }

    void acordeTest(boolean useMLP, Rede mlp) {

        String propriedades;

        System.out.println("LowerCase para naturais - UpperCase para sustenidos");
        System.out.print("Digite a primeira nota: ");
        char n1 = teclado.next().charAt(0);
        System.out.print("Digite a segunda nota: ");
        char n2 = teclado.next().charAt(0);
        System.out.print("Digite a terceira nota: ");
        char n3 = teclado.next().charAt(0);

        propriedades = util.getProperties(n1, n2, n3);

        if (propriedades.isEmpty()) {
            System.out.println("Desconhecido");
        } else {
            System.out.print("O acorde é "
                    + util.toFormattedAcorde(propriedades.charAt(0), propriedades.charAt(1) - 48));
            if (!useMLP) {
                switch (propriedades.charAt(2)) {
                    case 1:
                        System.out.println(" na forma fundamental");
                        break;
                    case 2:
                        System.out.println(" na primeira inversão");
                        break;
                    case 3:
                        System.out.println(" na segunda inversão");
                        break;
                    default:
                        System.out.println(" em forma aleatória");
                }
            } else {
                System.out.println();
            }
        }
        System.out.println();
    }

    char mainMenu() {
        
        System.out.println("Menu principal: \n"
                + "\t 1. Listar acordes válidos \n"
                + "\t 2. Analisar um acorde \n"
                + "\t 3. Resetar arquivo de acordes \n"
                + "\t 4. Usar a rede neural \n"
                + "\t 0. Sair");
        return teclado.next().charAt(0);
    }

    Scanner teclado;
    AcordeUtil util;
    AcordeGen acordeList;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        Main app = new Main();
        app.run();
    }
}
