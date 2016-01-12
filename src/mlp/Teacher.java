package mlp;

/**
 * Gera um objeto capaz de criar uma rede MLP e treiná-la a partir de uma base.
 * As características funcionais de rede são herdadas da classe mlp.Rede.
 *
 * @author Lucas
 */
public class Teacher extends Rede {

    /**
     * Cria novas camadas para a rede a ser ensinada, a partir dos parâmetros
     *
     * @param lyrCard o vetor com a cardinalidade de cada camada da rede
     * @param inputCard a cardinalidade da entrada da rede
     */
    public Teacher(int[] lyrCard, int inputCard) {
        camada = new Layer[lyrCard.length];

        camada[0] = new Layer(lyrCard[0], inputCard);
        for (int i = 1; i < lyrCard.length; i++) {
            camada[i] = new Layer(lyrCard[i], lyrCard[i - 1]);
        }
    }

    /**
     * Treina a rede com para a base recebida como parâmetro
     *
     * @param base a base de dados para o treinamento, contendo entradas e
     * saídas esperadas
     * @return o número de épocas de treino
     */
    public int treinar(double[][] base) {
        int epocas = 0;
        int cardSaida = camada[camada.length - 1].b.length;
        int cardEntrada = base[0].length - cardSaida;

        double custo;
        double[] erro;
        double[] entrada = new double[cardEntrada];
        double[] esperado = new double[cardSaida];

        do {
            custo = 0.0;

            for (double[] linhaBase : base) {
                System.arraycopy(linhaBase, 0, entrada, 0, cardEntrada);
                System.arraycopy(linhaBase, cardEntrada, esperado, 0, cardSaida);
                
                feedForward(entrada);
                erro = erro(camada[camada.length - 1].getSaida(), esperado);
                backward(entrada, erro);
                
                for (double umErro : erro) {
                    custo += Math.pow(umErro, 2) / 2;
                }
            }
            custo /= base.length;
            epocas++;
        } while (custo > MI);

        return epocas;
    }

    /**
     * O algoritmo de retropropagação matricial (Ver a interface Mat)
     *
     * @param entradas as entradas da rede
     * @param e o erro obtido durante o feedForward da rede com 'entradas'
     */
    private void backward(double[] entradas, double[] e) {
        double[][] m = new double[1][0];
        double[][] deltaW;
        double[] deltaB;

        m[0] = e;

        for (int i = camada.length - 1; i > 0; i--) {
            m = Mat.produto(Mat.diag(Mat.dfSigmoide(camada[i].getSaida())), m);
            deltaB = m[0];
            deltaW = Mat.transp(Mat.kProduto(Mat.transp(camada[i - 1].getSaida()), m));
            m = Mat.produto(camada[i].w, m);
            atualizaCamada(i, deltaW, deltaB);
        }

        m = Mat.produto(Mat.diag(Mat.dfSigmoide(camada[0].getSaida())), m);
        deltaB = m[0];
        deltaW = Mat.transp(Mat.kProduto(Mat.transp(entradas), m));

        atualizaCamada(0, deltaW, deltaB);
    }

    /**
     * Execução dos termos do gradiente descendente correspondentes à camada
     * nCamada
     *
     * @param nCamada o índice da camada a ser atualizada
     * @param deltaW A matriz de variações dos pesos
     * @param deltaB O vetor de variações dos bias
     */
    private void atualizaCamada(int nCamada, double[][] deltaW, double[] deltaB) {
        for (int i = 0; i < deltaW.length; i++) {
            camada[nCamada].b[i] += ETA * deltaB[i];
            for (int j = 0; j < deltaW[i].length; j++) {
                camada[nCamada].w[i][j] += ETA * deltaW[i][j];
            }
        }
    }

    /**
     * Calcula o o vetor de erros da rede, a partir dos vetores de saída e saída
     * almejada
     *
     * @param saida o vetor de saídas da rede
     * @param alvo o vetor das saídas almejadas, fornecido pela base de dados
     * @return o vetor de erros
     */
    private double[] erro(double[] saida, double[] alvo) {
        double[] erro = new double[saida.length];
        for (int i = 0; i < saida.length; i++) {
            erro[i] = alvo[i] - saida[i];
        }
        return erro;
    }
}
