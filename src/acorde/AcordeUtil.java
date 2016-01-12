package acorde;

import net.sourceforge.jFuzzyLogic.FIS;

/**
 *
 * @author Lucas
 */
public class AcordeUtil {

    public AcordeUtil() {

        fis = FIS.load("acordes.fis");
    }

    public char codeToNote(float code) {

        fis.setVariable("codigo", code);
        fis.evaluate();

        return (char)fis.getVariable("nota").getValue();
    }

    public String toFormattedNote(float code) {
        return toFormattedNote(codeToNote(code));
    }

    public String toFormattedNote(char note) {
        switch (note) {
            case 'a':
                return "A";
            case 'A':
                return "A#";
            case 'b':
                return "B";
            case 'c':
                return "C";
            case 'C':
                return "C#";
            case 'd':
                return "D";
            case 'D':
                return "D#";
            case 'e':
                return "E";
            case 'f':
                return "F";
            case 'F':
                return "F#";
            case 'g':
                return "G";
            case 'G':
                return "G#";
            default:
                return "";
        }
    }

    public String toFormattedAcorde(char root, int type) {
        switch (type) {
            case 1:
                return toFormattedNote(root);
            case 2:
                return toFormattedNote(root) + "m";
            case 3:
                return toFormattedNote(root) + "sus4";
            default:
                return "";
        }
    }

    public String toFormattedAcorde(int iRoot, int type) {
        return toFormattedAcorde(codeToNote(iRoot), type);
    }

    public float noteToCode(char note) {
        switch (note) {
            case 'a':
                return 0.5F / 12;
            case 'A':
                return 1.5F / 12;
            case 'b':
                return 2.5F / 12;
            case 'c':
                return 3.5F / 12;
            case 'C':
                return 4.5F / 12;
            case 'd':
                return 5.5F / 12;
            case 'D':
                return 6.5F / 12;
            case 'e':
                return 7.5F / 12;
            case 'f':
                return 8.5F / 12;
            case 'F':
                return 9.5F / 12;
            case 'g':
                return 10.5F / 12;
            case 'G':
                return 11.5F / 12;
            default:
                return -1;
        }
    }

    public String getProperties(int i1, int i2, int i3) {
        return getProperties(new char[]{AcordeCLL.get(i1),
            AcordeCLL.get(i2),
            AcordeCLL.get(i3)});
    }

    public String getProperties(char n1, char n2, char n3) {
        return getProperties(new char[]{n1, n2, n3});
    }

    public String getProperties(char[] acorde) {
        int intervalo1 = AcordeCLL.distBtw(acorde[0], acorde[1]);
        int intervalo2 = AcordeCLL.distBtw(acorde[1], acorde[2]);

        fis.setVariable("intv1", intervalo1);
        fis.setVariable("intv2", intervalo2);
        fis.evaluate();

        int tipo = (int) fis.getVariable("tipo").getValue();

        if (tipo == 0) {
            return "";
        }

        char raiz = acorde[(int) fis.getVariable("raiz").getValue() - 1];
        int inversao = (int) fis.getVariable("inversao").getValue();

        // Uncomment para ver os acordes válidos enquanto são gerados
        //System.out.println(n1+"  "+n2+"  "+n3+"  "+raiz+"  "+tipo);//fis.getVariable("tipo").getValue());
        return (raiz + "" + tipo + "" + inversao);
    }

    FIS fis;
}
