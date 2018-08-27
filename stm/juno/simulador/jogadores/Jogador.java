package stm.juno.simulador.jogadores;

import stm.juno.simulador.cartas.Carta;

import java.util.ArrayList;
import java.util.List;

public class Jogador {

    private List<Carta> cartas;

    public Jogador() {
        cartas = new ArrayList<>();
    }

    public Jogador(Jogador cobaia) {
        cartas = new ArrayList<>();
        cartas.addAll(cobaia.getCartas());
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    @Override
    public String toString() {
        return cartas.toString();
    }
}
