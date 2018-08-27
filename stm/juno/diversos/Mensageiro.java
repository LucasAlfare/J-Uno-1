package stm.juno.diversos;

public class Mensageiro {

    public static boolean ativo;

    public static void imprimir(String mensagem, boolean urgente) {
        if (ativo || urgente)
            System.out.println(mensagem);
    }

    public static void imprimir(String mensagem) {
        imprimir(mensagem, false);
    }
}
