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

    private static void comparativoHyperN(int numJogadores){

        Uno jogoBase = new Uno(numJogadores);

        var n = new Uno(jogoBase, true);
        var h = new Uno(jogoBase, false);

        while(n.proximaJogada());

        while(h.proximaJogada());

        System.out.println("NMax: Jogador ganhador: "+n.getIndiceGanhador()+", Média de nós explorados: "+n.valores/n.contagem+".");
        System.out.println("HyperMax: Jogador ganhador: "+h.getIndiceGanhador()+", Média de nós explorados: "+h.valores/h.contagem+".");

    }

    public static void main(String[] args) {
        Mensageiro.ativo = false;
        //jogarPartidas(10, 2);

        comparativoHyperN(2);
    }
}
