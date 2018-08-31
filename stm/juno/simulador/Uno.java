package stm.juno.simulador;

import stm.juno.busca.AlgoBusca;
import stm.juno.diversos.Mensageiro;
import stm.juno.simulador.acoes.*;
import stm.juno.simulador.cartas.Carta;
import stm.juno.simulador.cartas.CorCarta;
import stm.juno.simulador.cartas.TipoCarta;
import stm.juno.simulador.jogadores.Jogador;
import stm.juno.simulador.jogadores.Jogadores;
import stm.juno.simulador.pilhas.PilhaCompra;
import stm.juno.simulador.pilhas.PilhaDescarte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class Uno {

    private PilhaCompra pilhaCompra;
    private PilhaDescarte pilhaDescarte;
    private Jogadores jogadores;

    private double[] pontuacoes;
    private int[] totaisCartasAnteriores;
    private int totalTurnos;

    public int contagem;
    public double valores;
    private boolean nMax;

    public Uno(int numJogadores) {
        pilhaCompra = new PilhaCompra(Carta.getBaralho());
        jogadores = new Jogadores(numJogadores, 7, this);
        pilhaDescarte = new PilhaDescarte(this);

        pontuacoes = new double[numJogadores];
        totaisCartasAnteriores = new int[numJogadores];
    }

    public Uno(Uno cobaia, boolean nMax) {
        this.nMax = nMax;
        pilhaCompra = new PilhaCompra(cobaia.pilhaCompra);
        jogadores = new Jogadores(cobaia.jogadores);
        pilhaDescarte = new PilhaDescarte(cobaia.pilhaDescarte);

        totalTurnos = cobaia.totalTurnos;
        pontuacoes = new double[cobaia.jogadores.size()];
        System.arraycopy(cobaia.pontuacoes, 0, pontuacoes, 0, cobaia.pontuacoes.length);
        totaisCartasAnteriores = new int[cobaia.jogadores.size()];
        System.arraycopy(cobaia.totaisCartasAnteriores, 0, totaisCartasAnteriores, 0,
                cobaia.totaisCartasAnteriores.length);
    }

    public Uno(Uno cobaia) {
        pilhaCompra = new PilhaCompra(cobaia.pilhaCompra);
        jogadores = new Jogadores(cobaia.jogadores);
        pilhaDescarte = new PilhaDescarte(cobaia.pilhaDescarte);

        totalTurnos = cobaia.totalTurnos;
        pontuacoes = new double[cobaia.jogadores.size()];
        System.arraycopy(cobaia.pontuacoes, 0, pontuacoes, 0, cobaia.pontuacoes.length);
        totaisCartasAnteriores = new int[cobaia.jogadores.size()];
        System.arraycopy(cobaia.totaisCartasAnteriores, 0, totaisCartasAnteriores, 0,
                cobaia.totaisCartasAnteriores.length);

        valores = cobaia.valores;
        contagem = cobaia.contagem;
    }

    public int getIndiceGanhador() {
        for (int i = 0; i < jogadores.size(); i++) {
            if (jogadores.get(i).getCartas().isEmpty()) {
                return i;
            }
        }

        return -1;
    }

    private Integer[] getIndicesCartasCompativeis(Carta cartaTeste, CorCarta corTeste) {
        List<Integer> resultados = new ArrayList<>();
        boolean mesmaCorEncontrada = false;
        int indiceComprarQuatro = -1;

        for (int i = 0; i < jogadores.getJogadorAtual().getCartas().size(); i++) {
            Carta c = jogadores.getJogadorAtual().getCartas().get(i);

            if (indiceComprarQuatro == -1 && c.getTipo().equals(TipoCarta.CURINGA_COMPRAR_QUATRO)) {
                indiceComprarQuatro = i;
            } else if (!c.getTipo().equals(TipoCarta.CURINGA_COMPRAR_QUATRO)) {
                if (c.getCor() != null && c.getCor().equals(corTeste)) {
                    mesmaCorEncontrada = resultados.add(i);
                } else if (cartaTeste != null) {
                    boolean curinga = c.getTipo().equals(TipoCarta.CURINGA),
                            mesmoTipo = c.getTipo().equals(cartaTeste.getTipo()),
                            numero = c.getTipo().equals(TipoCarta.NUMERO),
                            mesmoValor = c.getValor() == cartaTeste.getValor();
                    if (curinga || ((mesmoTipo && !numero) || (numero && mesmoValor)))
                        resultados.add(i);
                }
            }
        }

        if (!mesmaCorEncontrada && indiceComprarQuatro > -1)
            resultados.add(0, indiceComprarQuatro);

        return resultados.toArray(new Integer[0]);
    }

    private boolean cartaValidaComoProximaJogada(Carta carta) {
        if (carta != null) {
            Jogador atual = jogadores.getJogadorAtual();
            Carta ultimaJogada = pilhaDescarte.getUltimaJogada();
            CorCarta ultimaCor = pilhaDescarte.getUltimaCor();

            atual.getCartas().add(carta);
            int indiceUltimaCompra = atual.getCartas().size() - 1;
            Integer[] indicesCartasCompativeis = getIndicesCartasCompativeis(ultimaJogada, ultimaCor);
            boolean cartaValida = indicesCartasCompativeis.length > 0
                    && indicesCartasCompativeis[indicesCartasCompativeis.length - 1] == indiceUltimaCompra;
            atual.getCartas().remove(indiceUltimaCompra);
            return cartaValida;
        } else {
            return false;
        }
    }

    public Jogada[] getJogadasPossiveis() {
        List<Jogada> jogadasPossiveis = new ArrayList<>();

        if (pilhaDescarte.temAcaoPendente()) {
            switch (pilhaDescarte.getUltimaJogada().getTipo()) {
                case CURINGA:
                    for (CorCarta cor : CorCarta.values()) {
                        Integer[] indicesCartasCompativeis = getIndicesCartasCompativeis(null, cor);
                        if (indicesCartasCompativeis.length > 0) {
                            for (int indiceCarta : indicesCartasCompativeis) {
                                Carta cartaPossivel = jogadores.getJogadorAtual().getCartas().get(indiceCarta);
                                if (cartaPossivel.getCor() == null) {
                                    for (CorCarta proximaCor : CorCarta.values()) {
                                        jogadasPossiveis.add(new Jogada(new EscolherCor(cor),
                                                new JogarCarta(indiceCarta, cartaPossivel),
                                                new EscolherCor(proximaCor)));
                                    }
                                } else {
                                    jogadasPossiveis.add(new Jogada(new EscolherCor(cor),
                                            new JogarCarta(indiceCarta, cartaPossivel)));
                                }
                            }
                        } else {
                            jogadasPossiveis.add(new Jogada(new EscolherCor(cor), new ComprarCarta(1)));
                        }

                        Carta proximaNaPilha = pilhaCompra.proximaNaPilha();
                        if (cartaValidaComoProximaJogada(proximaNaPilha)) {
                            if (proximaNaPilha.getCor() == null) {
                                for (CorCarta proximaCor : CorCarta.values()) {
                                    jogadasPossiveis.add(new Jogada(new EscolherCor(cor), new ComprarCarta(1),
                                            new JogarCarta(-jogadores.getJogadorAtual().getCartas().size(),
                                                    proximaNaPilha), new EscolherCor(proximaCor)));
                                }
                            } else if (proximaNaPilha.getCor().equals(cor)) {
                                jogadasPossiveis.add(new Jogada(new EscolherCor(cor), new ComprarCarta(1),
                                        new JogarCarta(-jogadores.getJogadorAtual().getCartas().size(),
                                                proximaNaPilha)));
                            }
                        }
                    }
                    break;
                case CURINGA_COMPRAR_QUATRO:
                    jogadasPossiveis.add(new Jogada(new ComprarCarta(4)));
                    break;
                case COMPRAR_DUAS:
                    jogadasPossiveis.add(new Jogada(new ComprarCarta(2)));
                    break;
                case INVERTER:
                    jogadasPossiveis.add(new Jogada(new InverterSentido()));
                    break;
                case PULAR:
                    jogadasPossiveis.add(new Jogada(new PularJogador()));
                    break;
            }
        } else {
            Carta proximaNaPilha = pilhaCompra.proximaNaPilha();
            if (cartaValidaComoProximaJogada(proximaNaPilha)) {
                if (proximaNaPilha.getCor() == null) {
                    for (CorCarta proximaCor : CorCarta.values()) {
                        jogadasPossiveis.add(new Jogada(new ComprarCarta(1),
                                new JogarCarta(-jogadores.getJogadorAtual().getCartas().size(), proximaNaPilha),
                                new EscolherCor(proximaCor)));
                    }
                } else {
                    jogadasPossiveis.add(new Jogada(new ComprarCarta(1),
                            new JogarCarta(-jogadores.getJogadorAtual().getCartas().size(), proximaNaPilha)));
                }
            }

            Integer[] indicesCartasCompativeis = getIndicesCartasCompativeis(pilhaDescarte.getUltimaJogada(),
                    pilhaDescarte.getUltimaCor());
            if (indicesCartasCompativeis.length > 0) {
                for (int indiceCartaCompativel : indicesCartasCompativeis) {
                    Carta cartaPossivel = jogadores.getJogadorAtual().getCartas().get(indiceCartaCompativel);
                    if (cartaPossivel.getCor() == null) {
                        for (CorCarta cor : CorCarta.values()) {
                            jogadasPossiveis.add(new Jogada(new JogarCarta(indiceCartaCompativel, cartaPossivel),
                                    new EscolherCor(cor)));
                        }
                    } else {
                        jogadasPossiveis.add(new Jogada(new JogarCarta(indiceCartaCompativel, cartaPossivel)));
                    }
                }
            } else {
                jogadasPossiveis.add(new Jogada(new ComprarCarta(1)));
            }
        }

        return jogadasPossiveis.toArray(new Jogada[0]);
    }

    private void imprimirStatus(Jogada[] jogadasPossiveis) {
        Mensageiro.imprimir("\n--------- JOGADOR " + jogadores.getIndiceJogadorAtual() + " turno #" + totalTurnos
                + " ----------");
        Mensageiro.imprimir("Última jogada: " + pilhaDescarte.getUltimaJogada().toString());
        CorCarta uc = pilhaDescarte.getUltimaCor();
        Mensageiro.imprimir("Última cor: " + (uc != null ? uc.toString() : " nenhuma"));
        Mensageiro.imprimir("Ação pendente: " + pilhaDescarte.temAcaoPendente());
        Mensageiro.imprimir("Número de cartas na pilha de compra: " + pilhaCompra.size());
        Mensageiro.imprimir("Número de cartas na pilha de descarte: " + pilhaDescarte.size());
        Mensageiro.imprimir("Cartas do jogador atual: " + jogadores.getJogadorAtual().toString());
        Mensageiro.imprimir("Quantidade de jogadas possíveis: " + jogadasPossiveis.length);
        //Carta pc = pilhaCompra.proximaNaPilha();
        //Mensageiro.imprimir("Próxima carta disponível para compra: " + (pc != null ? pc.toString() : "nenhuma"));
        for (Jogada j : jogadasPossiveis)
            Mensageiro.imprimir(j.toString());
    }

    private void atualizarPontuacoes() {
        double[] resultados = new double[pontuacoes.length];
        System.arraycopy(pontuacoes, 0, resultados, 0, pontuacoes.length);

        int[] diferencas = new int[jogadores.size()];
        int[] totaisCartasAtuais = jogadores.totaisCartasEmPosse();

        for (int i = 0; i < totaisCartasAnteriores.length; i++)
            diferencas[i] = totaisCartasAnteriores[i] - totaisCartasAtuais[i];

        for (int i = 0; i < diferencas.length; i++) {
            double diferenca = diferencas[i];
            for (int j = 0; j < totaisCartasAnteriores.length; j++) {
                if (diferenca != 0) {
                    if (i == j) {
                        resultados[j] = resultados[j] + diferenca;
                    } else {
                        resultados[j] = resultados[j] + ((diferenca / (jogadores.size() - 1)) * -1);
                    }
                }
            }
        }

        System.arraycopy(resultados, 0, pontuacoes, 0, resultados.length);
    }

    public void executarJogada(Jogada jogada, boolean verboso) {
        totaisCartasAnteriores = jogadores.totaisCartasEmPosse();
        jogada.executar(this, verboso);

        atualizarPontuacoes();
        totalTurnos++;
        jogadores.proximoJogador();

        if (verboso) {
            Mensageiro.imprimir("Pontuações atuais: " + Arrays.toString(pontuacoes));
            Mensageiro.imprimir("Soma das pontuações: " + DoubleStream.of(pontuacoes).sum());
        }
    }

    private void escolherJogada() {
        Jogada[] jogadasPossiveis = getJogadasPossiveis();
        imprimirStatus(jogadasPossiveis);

        Jogada escolha;

        if (jogadores.getIndiceJogadorAtual() == 0) {
            AlgoBusca.nosExplorados = 0;
            if (nMax) {
                escolha = AlgoBusca.NMax(this, 0).x;
            } else {
                escolha = AlgoBusca.hypermax(this, 0, AlgoBusca.getAlfa(jogadores.size())).x;
            }
            valores += AlgoBusca.nosExplorados;
            contagem++;
        } else {
            escolha = jogadasPossiveis[ThreadLocalRandom.current().nextInt(jogadasPossiveis.length)];
        }

        executarJogada(escolha, true);
    }

    public boolean proximaJogada() {
        int ganhador = getIndiceGanhador();
        if (ganhador == -1) {
            escolherJogada();
            return true;
        } else {
            Mensageiro.imprimir("Fim de jogo, jogador " + ganhador + " ganhou em " + totalTurnos + " turnos");
            return false;
        }
    }


    public PilhaCompra getPilhaCompra() {
        return pilhaCompra;
    }

    public PilhaDescarte getPilhaDescarte() {
        return pilhaDescarte;
    }

    public Jogadores getJogadores() {
        return jogadores;
    }

    public double[] getPontuacoes() {
        return pontuacoes;
    }
}
