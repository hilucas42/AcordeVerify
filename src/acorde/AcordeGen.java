package acorde;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe responsável pela geração da lista de acordes,
 * base para o treino da rede mlp
 * @author Lucas
 */
public class AcordeGen {

    public AcordeGen(AcordeUtil aUtil) {
        util = aUtil;
        type = new int[1320];
        root = new float[1320];
        acordeList = new float[1320][3];
        listGen();
    }

    public void export() throws IOException {
        
        try (PrintWriter obj = new PrintWriter(new FileOutputStream("AcordeList.txt"))) {

            for (int i = 0; i < acordeList.length; i++) {
                obj.println(util.codeToNote(acordeList[i][0])+" "+
                        util.codeToNote(acordeList[i][1])+" "+
                        util.codeToNote(acordeList[i][2])+" "+
                        util.codeToNote(root[i])+" "+
                        type[i]);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            //Logger.getLogger(Agendinha.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toFile() throws IOException {

        listGen();
        export();
    }
    
    public void fromFile() throws IOException {

        int i = 0;
        int[] newTypeList;
        float[] newRootList;
        float[][] newAcordeList;
        
        try(FileReader obj = new FileReader("AcordeList.txt")) {
            
            while(obj.ready())
            {
                acordeList[i][0] = util.noteToCode((char)obj.read());
                obj.read();
                acordeList[i][1] = util.noteToCode((char)obj.read());
                obj.read();
                acordeList[i][2] = util.noteToCode((char)obj.read());
                obj.read();
                root[i] = util.noteToCode((char)obj.read());
                obj.read();
                type[i] = obj.read()-48;
                obj.read();
                obj.read();
                i++;
            }
            
            newTypeList = new int[i];
            newRootList = new float[i];
            newAcordeList = new float[i][3];
            
            System.arraycopy(type, 0, newTypeList, 0, i);
            System.arraycopy(root, 0, newRootList, 0, i);
            System.arraycopy(acordeList, 0, newAcordeList, 0, i);
            
            acordeList = newAcordeList;
            root = newRootList;
            type = newTypeList;
            
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            //Logger.getLogger(Agendinha.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public double[][] getBase()
    {
        double[][] base = new double[acordeList.length][5];
        
        for(int i = 0; i < base.length; i++)
        {
            base[i][0] = (double)acordeList[i][0];
            base[i][1] = (double)acordeList[i][1];
            base[i][2] = (double)acordeList[i][2];
            base[i][3] = (double)root[i];
            base[i][4] = ((double)type[i])/3;
        }
        
        return base;
    }
    
    private void listGen()
    {
        String prop;
        
        for (int i = 0, t = 0; i < 12; i++)
            for (int j = 0; j < 12; j++)
                for (int k = 0; k < 12; k++) {
                    if (i != j && j != k && k != i) {
                        acordeList[t][0] = (i+0.5F)/12;
                        acordeList[t][1] = (j+0.5F)/12;
                        acordeList[t][2] = (k+0.5F)/12;
                        prop = util.getProperties(i, j, k);
                        if(prop.isEmpty()) {
                            root[t] = 0.5F; // valor indiferente
                            type[t] = 0; // igual acordeList inválido
                        }
                        else {
                            root[t] = util.noteToCode(prop.charAt(0));
                            type[t] = prop.charAt(1)-48;
                        }
                        t++;
                    }
                }
    }

    @Override
    public String toString() {

        String str = new String();
        
        for(int i = 0; i < acordeList.length; i++)
            str = str.concat(util.codeToNote(acordeList[i][0])+" "+
                    util.codeToNote(acordeList[i][1])+" "+
                    util.codeToNote(acordeList[i][2])+"\t"+
                    util.codeToNote(root[i])+"\t"+
                    type[i]+"\n");
        return str;
    }
    
    public String acordesValidos() {
        String str = new String();
        
        for(int i = 0; i < acordeList.length; i++)
            if(type[i] != 0)
                str = str.concat(util.codeToNote(acordeList[i][0])+" "+
                        util.codeToNote(acordeList[i][1])+" "+
                        util.codeToNote(acordeList[i][2])+"\t"+
                        util.codeToNote(root[i])+"\t"+
                        type[i]+"\n");
        return str;
    }
    
    int[] type;
    float[] root;
    float[][] acordeList;
    AcordeUtil util;
}
