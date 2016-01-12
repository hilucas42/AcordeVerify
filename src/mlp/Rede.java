package mlp;

/**
 *
 * @author Lucas
 */
public class Rede implements MLP{
    public Rede(){}
    
    public Rede(Layer[] novasCamadas)
    {
        camada = novasCamadas;
    }

    @Override
    public void setBias(int iCamada, int iNeuronio, double valor) {
        camada[iCamada].b[iNeuronio] = valor;
    }
    
    @Override
    public void setW(int iCamada, int iNeuronio, int iSinapse, double valor) {
        camada[iCamada].w[iNeuronio][iSinapse] = valor;
    }

    @Override
    public void feedForward(double[] entrada) {
        for (Layer umaCamada : camada) 
            entrada = umaCamada.transferencia(entrada);
    }

    @Override
    public double[] getSaida() {
        return camada[camada.length-1].getSaida();
    }
    
    public Layer[] getCamadas() {
        return camada;
    }
    
    protected Layer[] camada;
}
