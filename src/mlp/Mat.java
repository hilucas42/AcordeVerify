package mlp;

/**
 * Funções matemáticas para uso no algoritmo de retropropagação.
 * Estas funções levam em conta a inversão do índice matricial.
 * @author Lucas
 */
public interface Mat {

    /**
     * Produto de Kroneker entre duas matrizes
     * @param m1 um operando
     * @param m2 outro operando
     * @return a matriz resultante do produto
     */
    public static double[][] kProduto(double[][] m1, double[][] m2) {
        double[][] m = new double[m1.length * m2.length][m1[0].length * m2[0].length];

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                m[i][j]
                        = m1[i / m2.length][j / m2[0].length]
                        * m2[i - (m2.length * (i / m2.length))][j - (m2[0].length * (j / m2[0].length))];
            }
        }
        return m;
    }

    /**
     * Produto entre matrizes, considerando inversão de índice (a ordem do produto se mantém)
     * @param m1 a primeira matriz
     * @param m2 a segunda matriz
     * @return a matriz resultante
     */
    public static double[][] produto(double[][] m1, double[][] m2) {
        if (m1.length != m2[0].length) {
            System.out.println("Erro ao efetuar o produto: Matrizes incompatíveis!");
            return null;
        }

        double[][] m = new double[m2.length][m1[0].length];

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                m[i][j] = 0;
                for (int k = 0; k < m1.length; k++) {
                    m[i][j] += m1[k][j] * m2[i][k];
                }
            }
        }

        return m;
    }

    public static double[][] produto(double[][] m1, double[] v1) {
        double[][] mat = new double[1][0];
        mat[0] = v1;

        return produto(m1, mat);
    }

    public static double[][] produto(double[] v1, double[][] m1) {
        double[][] mat = new double[1][0];
        mat[0] = v1;

        return produto(mat, m1);
    }

    /**
     * Transpõe uma matriz nxn
     * @param matriz a matriz a ser transposta
     * @return a transposta de 'matriz'
     */
    public static double[][] transp(double[][] matriz) {
        double[][] tMatriz = new double[matriz[0].length][matriz.length];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < tMatriz.length; j++) {
                tMatriz[j][i] = matriz[i][j];
            }
        }
        return tMatriz;
    }

    /**
     * Transpõe um vetor de n posições
     * @param vetor o vetor a ser transposto
     * @return a matriz transposta do vetor
     */
    public static double[][] transp(double[] vetor) {
        double[][] matriz = new double[vetor.length][1];

        for (int i = 0; i < vetor.length; i++) {
            matriz[i][0] = vetor[i];
        }

        return matriz;
    }

    /**
     * Cria uma matriz diagonal a partir de um vetor de n posições
     * @param vetor o vetor a ser diagonalizado
     * @return a matriz diagonal
     */
    public static double[][] diag(double[] vetor) {
        double[][] matriz = new double[vetor.length][vetor.length];

        for (int i = 0; i < vetor.length; i++) {
            for (int j = 0; j < vetor.length; j++) {
                matriz[i][j] = 0;
            }
            matriz[i][i] = vetor[i];
        }

        return matriz;
    }

    /**
     * Opera a potência de uma matriz diagonal
     * @param matriz a matriz base
     * @param expoente o expoente
     * @return a matriz potência
     */
    public static double[][] powDiag(double[][] matriz, int expoente) {
        for (int i = 0; i < matriz.length; i++) {
            matriz[i][i] = Math.pow(matriz[i][i], expoente);
        }

        return matriz;
    }

    /**
     * A derivada da função de transferência da rede, a partir do valor da 
     * função. Este método deve se adequar ao método MLP.fSigmoid().
     * @param fSigmoide o valor da função de transferência
     * @return a derivada
     */
    public static double[] dfSigmoide(double[] fSigmoide) {
        double[] df = new double[fSigmoide.length];

        for (int i = 0; i < df.length; i++) {
            df[i] = (1 - Math.pow(2 * fSigmoide[i] - 1, 2));
        }
        return df;
    }
}
