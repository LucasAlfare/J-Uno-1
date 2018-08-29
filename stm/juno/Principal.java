package stm.juno;

import stm.juno.diversos.Mensageiro;
import stm.juno.simulador.Uno;

import java.util.Arrays;

public class Principal {

    private static void jogarPartidas(int numPartidas, int numJogadores) {
        int[] vitorias = new int[numJogadores];
        double media = 0;

        for (int i = 0; i < numPartidas; i++) {
            Uno jogo = new Uno(numJogadores);
            while (jogo.proximaJogada()) ;

            vitorias[jogo.getIndiceGanhador()] += 1;
            media += jogo.valores / jogo.contagem;

            System.out.println(jogo.valores / jogo.contagem + " média de nós explorados, "
                    + (((double) i + 1) / numPartidas) * 100 + "% concluído.");
        }

        System.out.println(media / numPartidas + " média geral de nós explorados");
        System.out.println(Arrays.toString(vitorias));
    }

    public static void main(String[] args) {
        Mensageiro.ativo = false;
        jogarPartidas(100, 2);
    }
}
