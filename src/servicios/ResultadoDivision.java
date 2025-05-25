package servicios;

import entidades.Polinomio;

public class ResultadoDivision {
    private Polinomio cociente;
    private Polinomio resto;

    public ResultadoDivision(Polinomio cociente, Polinomio resto) {
        this.cociente = cociente;
        this.resto = resto;
    }

    public Polinomio getCociente() {
        return cociente;
    }

    public Polinomio getResto() {
        return resto;
    }
}