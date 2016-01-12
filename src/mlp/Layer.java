package mlp;

import java.io.Serializable;

/**
 *
 * @author Lucas
 */
public class Layer implements Serializable{
    public Layer(int card, int entradas){
        w = new double[card][entradas];
        b = new double[card];
        s = new double[card];
        o = new double[card];
        
        for(int i = 0; i < card; i++)
        {
            for(int j = 0; j < entradas; j++)
                w[i][j] = Math.random();
            b[i] = Math.random();
        }
    }
    
    public double[] transferencia(double[] entrada)
    {
        soma(entrada);
        
        for(int i = 0; i < s.length; i++)
            o[i] = MLP.fSigmoide(s[i]);
        return o;
    }
    
    private void soma(double[] entrada)
    {
        for(int i = 0; i < w.length; i++)
        {
            s[i] = b[i];
            for(int j = 0; j < w[i].length; j++)
                s[i] += w[i][j] * entrada[j];
        }
    }
    
    public double[] getSaida() { return o; }
    
    public double[][] w;
    public double[] b;
    private double[] s;
    private double[] o;
}
