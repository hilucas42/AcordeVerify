package mlp;

/**
 * Define a rede MLP, em seu caráter funcional (não inclui treinamento)
 * @author Lucas
 */
public interface MLP {

    /**
     * Define o valor para um bias, obtido em treinamento prévio
     * @param iCamada a camada a definir o valor
     * @param iNeuronio o neurônio a definir o valor
     * @param valor o novo valor
     */
    public void setBias(int iCamada, int iNeuronio, double valor);

    /**
     * Define o valor para um peso, obtido em treinamento prévio
     * @param iCamada a camada a definir o valor
     * @param iNeuronio o neurônio a definir o valor
     * @param iSinapse a sinapse à qual o peso em apreço corresponde
     * @param valor o novo valor
     */
    public void setW(int iCamada, int iNeuronio, int iSinapse, double valor);

    /**
     * O algoritmo de alimentação da rede
     * @param entrada os valores de entrada para alimentar a rede
     */
    public void feedForward(double[] entrada);

    /**
     * @return o vetor de saída da rede
     */
    public double[] getSaida();

    /**
     * A função de transferência da rede
     * @param soma a soma obtida no neurônio
     * @return a nova saída (sinal) do neurônio
     */
    public static double fSigmoide(double soma) {
        return (1 + Math.tanh(soma)) / 2;
    };
    
    public static final double MI = 0.001;

    public static final double ETA = 0.001;
}
