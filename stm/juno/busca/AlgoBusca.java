package stm.juno.busca;

import stm.juno.diversos.Tuple;
import stm.juno.simulador.Uno;
import stm.juno.simulador.acoes.Jogada;

public class AlgoBusca {

    private static double[] subtrairMedia(double[] pontos) {
        double media = 0;
        for (double p : pontos)
            media += p;
        media /= pontos.length;

        double[] novosPontos = new double[pontos.length];
        for (int i = 0; i < pontos.length; i++)
            novosPontos[i] = pontos[i] - media;
        return novosPontos;
    }
    
    public static Tuple<Jogada, double[]> NMax(Uno jogo, int profundidade) {
        profundidade++;

        if (jogo.getIndiceGanhador() != -1 || profundidade == 15) {
            return new Tuple<>(new Jogada(), subtrairMedia(jogo.getPontuacoes()));
        } else {
            Tuple<Jogada, double[]> melhorJogada = null;
            double alfa = -Double.MAX_VALUE;
            Jogada[] jogadasPossiveis = jogo.getJogadasPossiveis();
            for (Jogada jogada : jogadasPossiveis) {
                Uno clone = new Uno(jogo);
                clone.executarJogada(jogada, false);

                int p = jogo.getJogadores().getIndiceJogadorAtual();
                Tuple<Jogada, double[]> psiEstrela = NMax(clone, profundidade);

                if (alfa < psiEstrela.y[p]) {
                    alfa = psiEstrela.y[p];
                    melhorJogada = new Tuple<>(jogada, psiEstrela.y);
                }
            }

            return melhorJogada;
        }
    }
}
