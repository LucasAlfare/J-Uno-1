package stm.juno;

import stm.juno.diversos.Mensageiro;
import stm.juno.simulador.Uno;

import java.util.Arrays;

public class Principal {

    private static void jogarPartidas(int numPartidas, int numJogadores) {
        int[] vitorias = new int[numJogadores];

        for (int i = 0; i < numPartidas; i++) {
            Uno jogo = new Uno(numJogadores);
            while (jogo.proximaJogada()) ;
            vitorias[jogo.getIndiceGanhador()] += 1;
        }

        System.out.println(Arrays.toString(vitorias));
    }

    public static void main(String[] args) {
        Mensageiro.ativo = false;
        jogarPartidas(10, 2);
    }
}
