package main;

import acorde.AcordeGen;
import acorde.AcordeUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import mlp.Layer;
import mlp.Rede;
import mlp.Teacher;

/**
 *
 * @author Lucas
 */
public class ControllerMLP {
    ControllerMLP(AcordeGen acordeList) {
        this.acordeList = acordeList;
        teclado = new Scanner(System.in);
        util = new AcordeUtil();
    }
    
    void runMLP() throws IOException, ClassNotFoundException {
        Teacher teachmlp;
        Layer[][] camadas;
        Rede mlp = null;

        while (true) {
            switch (mlpMenu()) {
                case '1':
                    int[] cardRede = {5,8, 2};
                    teachmlp = new Teacher(cardRede, 3);
                    double[][] base = acordeList.getBase();
                    System.out.println("Base adquirida. Agora treinando...");
                    int epocas = teachmlp.treinar(base);
                    System.out.println("Treinada com "+epocas+" épocas");
                    mlp = teachmlp;
                    break;
                case '2':
                    acordeTest(mlp);
                    break;
                case '3':
                    acordeList.fromFile();
                    System.out.println("A lista de acordes foi atualizada");
                    break;
                case '4':
                    mlp = abrirRede();
                    break;
                case '5':
                    if(mlp == null)
                        System.out.println("Rede inexistente");
                    else
                        salvarRede(mlp);
                    break;
                case '0':
                    return;
                default:
            }
        }
    }
    
    void acordeTest(Rede mlp) {
        
        double[] acorde = new double[3];
        double[] saida;
        
        if (mlp == null) {
            System.out.println("Você precisa carregar ou treinar uma mlp");
            return;
        }
        
        System.out.println("\tUse LowerCase para notas naturais e UpperCase para sustenidos");
        System.out.print("Digite a primeira nota: ");
        acorde[0] = util.noteToCode(teclado.next().charAt(0));
        System.out.print("Digite a segunda nota: ");
        acorde[1] = util.noteToCode(teclado.next().charAt(0));
        System.out.print("Digite a terceira nota: ");
        acorde[2] = util.noteToCode(teclado.next().charAt(0));
        
        mlp.feedForward(acorde);
        saida = mlp.getSaida();
        System.out.println("O acorde é "+
                util.toFormattedAcorde(
                        util.codeToNote((float)saida[0]), 
                        (int)Math.round(saida[1]*3)));
    }
    
    void salvarRede(Rede mlp) throws IOException  {
        ObjectOutputStream out;
        
        try {
            out = new ObjectOutputStream(new FileOutputStream("rede.mlp"));
            
            out.writeObject(mlp.getCamadas());
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControllerMLP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    Rede abrirRede() throws IOException, ClassNotFoundException {
        ObjectInputStream in;
        
        try {
            in = new ObjectInputStream(new FileInputStream("rede.mlp"));
            
            return new Rede((Layer[])in.readObject());
            
        } catch (IOException|ClassNotFoundException ex) {
            Logger.getLogger(ControllerMLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    char mlpMenu() {
        
        System.out.println("Menu da rede neural: \n"
                + "\t 1. Treinar rede neural \n"
                + "\t 2. Analisar um acorde \n"
                + "\t 3. Atualizar base de treinamento a partir do arquivo \n"
                + "\t 4. Importar rede do disco \n"
                + "\t 5. Salvar rede no disco\n"
                + "\t 0. Voltar");
        return teclado.next().charAt(0);
    }
    
    AcordeUtil util;
    AcordeGen acordeList;
    Scanner teclado;
}
