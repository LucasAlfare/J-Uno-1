package stm.juno.simulador.jogadores;

import stm.juno.simulador.Uno;

import java.util.ArrayList;
import java.util.Arrays;

public class Jogadores extends ArrayList<Jogador> {

    private int numJogadores;
    private int cartasPorJogador;
    private int indiceJogadorAtual;
    private boolean sentidoHorario;

    public Jogadores(int numJogadores, int cartasPorJogador, Uno jogo) {
        this.numJogadores = numJogadores;
        this.cartasPorJogador = cartasPorJogador;
        sentidoHorario = true;

        for (int i = 0; i < numJogadores; i++) {
            Jogador jogador = new Jogador();
            jogador.getCartas().addAll(Arrays.asList(jogo.getPilhaCompra().comprar(cartasPorJogador, jogo)));
            add(jogador);
        }
    }

    public Jogadores(Jogadores cobaia) {
        numJogadores = cobaia.numJogadores;
        cartasPorJogador = cobaia.cartasPorJogador;
        indiceJogadorAtual = cobaia.indiceJogadorAtual;
        sentidoHorario = cobaia.sentidoHorario;
        for (Jogador j : cobaia)
            add(new Jogador(j));
    }

    public int getIndiceJogadorAtual() {
        return indiceJogadorAtual;
    }

    public Jogador getJogadorAtual() {
        return get(indiceJogadorAtual);
    }

    public void inverterSentido() {
        sentidoHorario = !sentidoHorario;
    }

    public boolean sentidoHorario() {
        return sentidoHorario;
    }

    public void proximoJogador() {
        indiceJogadorAtual = (sentidoHorario ? indiceJogadorAtual + 1
                : indiceJogadorAtual + numJogadores - 1) % numJogadores;
    }

    public int[] totaisCartasEmPosse() {
        int[] totais = new int[numJogadores];
        for (int i = 0; i < numJogadores; i++) {
            totais[i] = get(i).getCartas().size();
        }

        return totais;
    }


}
