package acorde;

/**
 * Simulação de lista encadeada circular, representando a continuidade
 * cíclica da sequência de notas musicais
 * @author Lucas
 */
public class AcordeCLL {

    public static char get(int i) {
        return lista[i];
    }

    public static int indexOf(char note) {
        for(int i = 0; i < 12; i++)
            if(lista[i] == note)
                return i;
        return -1;
    }

    public static int distBtw(char n1, char n2)
    {
        int i, j;
        for(i = 0; i < 12; i++)
            if(lista[i] == n1)
                break;
        for(j = 0; j < 12; j++){
            if(i+j == 12)
                i = -j;
            if(lista[i+j] == n2)
                return j;
        }
        return -1;
    }
    
    private static final char[] lista = {'a', 'A', 'b', 'c', 'C', 'd', 'D', 'e', 'f', 'F', 'g', 'G'};;
}
