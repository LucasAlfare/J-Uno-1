package stm.juno.busca;

import stm.juno.diversos.Tuple;
import stm.juno.simulador.Uno;
import stm.juno.simulador.acoes.Jogada;

import java.util.Arrays;

public class AlgoBusca {

    public static int nosExplorados;

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
        nosExplorados++;

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

    private static double somar(double[] valores) {
        double soma = 0;
        for (double p : valores)
            soma += p;

        return soma;
    }

    public static double[] getAlfa(int tamanho) {
        double[] a = new double[tamanho];
        for (int i = 0; i < tamanho; i++) {
            a[i] = -Double.MAX_VALUE;
        }
        return a;
    }

    public static Tuple<Jogada, double[]> hypermax(Uno jogo, int profundidade, double[] alfa) {
        profundidade++;
        nosExplorados++;

        if (jogo.getIndiceGanhador() != -1 || profundidade == 20) {
            return new Tuple<>(new Jogada(), subtrairMedia(jogo.getPontuacoes()));
        } else {
            Tuple<Jogada, double[]> melhorJogada = null;
            Jogada[] jogadasPossiveis = jogo.getJogadasPossiveis();
            for (int i = 0; i < jogadasPossiveis.length; i++) {
                Uno clone = new Uno(jogo);
                clone.executarJogada(jogadasPossiveis[i], false);
                int p = jogo.getJogadores().getIndiceJogadorAtual();

                Tuple<Jogada, double[]> psiEstrela = hypermax(clone, profundidade, Arrays.copyOf(alfa, alfa.length));

                if (i == 0)
                    melhorJogada = new Tuple<>(jogadasPossiveis[i], psiEstrela.y);

                if (alfa[p] < psiEstrela.y[p]) {
                    alfa[p] = psiEstrela.y[p];
                    melhorJogada = new Tuple<>(jogadasPossiveis[i], psiEstrela.y);
                }

                if (somar(alfa) >= 0)
                    break;
            }

            return melhorJogada;
        }
    }
}
