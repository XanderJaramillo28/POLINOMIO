package servicios;

import entidades.Nodo;
import entidades.Polinomio;

public class ServicioPolinomio {

    public static Polinomio sumar(Polinomio p1, Polinomio p2) {
        Polinomio pR = new Polinomio();

        Nodo actual1 = p1.getCabeza();
        Nodo actual2 = p2.getCabeza();

        while (actual1 != null || actual2 != null) {
            Nodo nR = null;
            if (actual1 != null && actual2 != null && actual1.getExponente() == actual2.getExponente()) {
                if (actual1.getCoeficiente() + actual2.getCoeficiente() != 0) {
                    nR = new Nodo(actual1.getExponente(), actual1.getCoeficiente() + actual2.getCoeficiente());
                }
                actual1 = actual1.siguiente;
                actual2 = actual2.siguiente;
            } else if (actual2 == null || (actual1 != null && actual1.getExponente() < actual2.getExponente())) {
                nR = new Nodo(actual1.getExponente(), actual1.getCoeficiente());
                actual1 = actual1.siguiente;
            } else {
                nR = new Nodo(actual2.getExponente(), actual2.getCoeficiente());
                actual2 = actual2.siguiente;
            }
            if (nR != null) {
                pR.agregar(nR);
            }
        }
        return pR;
    }

    public static Polinomio restar(Polinomio p1, Polinomio p2) {
        Polinomio pR = new Polinomio();

        Nodo actual1 = p1.getCabeza();
        Nodo actual2 = p2.getCabeza();

        while (actual1 != null || actual2 != null) {
            Nodo nR = null;
            if (actual1 != null && actual2 != null && actual1.getExponente() == actual2.getExponente()) {
                if (actual1.getCoeficiente() - actual2.getCoeficiente() != 0) {
                    nR = new Nodo(actual1.getExponente(), actual1.getCoeficiente() - actual2.getCoeficiente());
                }
                actual1 = actual1.siguiente;
                actual2 = actual2.siguiente;
            } else if (actual2 == null || (actual1 != null && actual1.getExponente() < actual2.getExponente())) {
                nR = new Nodo(actual1.getExponente(), actual1.getCoeficiente());
                actual1 = actual1.siguiente;
            } else {
                nR = new Nodo(actual2.getExponente(), -1 * actual2.getCoeficiente());
                actual2 = actual2.siguiente;
            }
            if (nR != null) {
                pR.agregar(nR);
            }
        }
        return pR;
    }

    public static Polinomio multiplicarPorMonomio(Polinomio p, Nodo monomio) {
    // Validaciones previas para evitar errores
    if (p == null) {
        throw new IllegalArgumentException("El polinomio no puede ser nulo");
    }
    if (monomio == null) {
        throw new IllegalArgumentException("El monomio no puede ser nulo");
    }

    Polinomio resultado = new Polinomio();
    Nodo actual = p.getCabeza();

    // Recorrer el polinomio y multiplicar cada término por el monomio
    while (actual != null) {
        int nuevoExponente = actual.getExponente() + monomio.getExponente();
        double nuevoCoeficiente = actual.getCoeficiente() * monomio.getCoeficiente();

        // Agregar el nuevo término al resultado si el coeficiente no es 0
        if (nuevoCoeficiente != 0) {
            resultado.agregar(new Nodo(nuevoExponente, nuevoCoeficiente));
        }

        actual = actual.siguiente;
    }

        return resultado;   
    }

    public static Polinomio multiplicar(Polinomio p1, Polinomio p2) {
    Polinomio resultado = new Polinomio();
    Nodo actual = p2.getCabeza();

    while (actual != null) {
        Polinomio producto = multiplicarPorMonomio(p1, actual);
        resultado = sumar(resultado, producto); // ya tienes sumar implementado
        actual = actual.siguiente;
    }

        return resultado;
    }
}


    