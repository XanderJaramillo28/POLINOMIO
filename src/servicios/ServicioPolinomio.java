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
                double suma = actual1.getCoeficiente() + actual2.getCoeficiente();
                if (suma != 0) {
                    nR = new Nodo(actual1.getExponente(), suma);
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
                double resta = actual1.getCoeficiente() - actual2.getCoeficiente();
                if (resta != 0) {
                    nR = new Nodo(actual1.getExponente(), resta);
                }
                actual1 = actual1.siguiente;
                actual2 = actual2.siguiente;
            } else if (actual2 == null || (actual1 != null && actual1.getExponente() < actual2.getExponente())) {
                nR = new Nodo(actual1.getExponente(), actual1.getCoeficiente());
                actual1 = actual1.siguiente;
            } else {
                nR = new Nodo(actual2.getExponente(), -actual2.getCoeficiente());
                actual2 = actual2.siguiente;
            }

            if (nR != null) {
                pR.agregar(nR);
            }
        }

        return pR;
    }

    public static Polinomio multiplicarPorMonomio(Polinomio p, Nodo monomio) {
    if (p == null) {
        throw new IllegalArgumentException("El polinomio no puede ser nulo");
    }
    if (monomio == null) {
        throw new IllegalArgumentException("El monomio no puede ser nulo");
    }

    Polinomio resultado = new Polinomio();
    Nodo actual = p.getCabeza();

    while (actual != null) {
        int nuevoExponente = actual.getExponente() + monomio.getExponente();
        double nuevoCoeficiente = actual.getCoeficiente() * monomio.getCoeficiente();

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
            resultado = sumar(resultado, producto);
            actual = actual.siguiente;
        }

        return resultado;
    }

    public static Polinomio derivar(Polinomio p) {
        Polinomio derivada = new Polinomio();
        Nodo actual = p.getCabeza();

        while (actual != null) {
            int nuevoExponente = actual.getExponente() - 1;
            double nuevoCoeficiente = actual.getCoeficiente() * actual.getExponente();

            if (nuevoExponente >= 0 && nuevoCoeficiente != 0) {
                derivada.agregar(new Nodo(nuevoExponente, nuevoCoeficiente));
            }

            actual = actual.siguiente;
        }

        return derivada;
    }

    public static ResultadoDivision dividir(Polinomio dividendo, Polinomio divisor) {
        if (divisor == null || divisor.getCabeza() == null) {
            throw new IllegalArgumentException("El divisor no puede ser nulo o vacío");
        }

        Polinomio cociente = new Polinomio();
        Polinomio resto = copiarPolinomio(dividendo);

        while (resto.getCabeza() != null &&
               obtenerMayorTermino(resto).getExponente() >= obtenerMayorTermino(divisor).getExponente()) {

            Nodo cabezaResto = obtenerMayorTermino(resto);
            Nodo cabezaDivisor = obtenerMayorTermino(divisor);

            int nuevoExp = cabezaResto.getExponente() - cabezaDivisor.getExponente();
            double nuevoCoef = cabezaResto.getCoeficiente() / cabezaDivisor.getCoeficiente();

            Nodo termino = new Nodo(nuevoExp, nuevoCoef);
            cociente.agregar(termino);

            Polinomio producto = multiplicarPorMonomio(divisor, termino);
            resto = restar(resto, producto);
        }

        return new ResultadoDivision(cociente, resto);
    }

    private static Polinomio copiarPolinomio(Polinomio original) {
        Polinomio copia = new Polinomio();
        Nodo actual = original.getCabeza();

        while (actual != null) {
            copia.agregar(new Nodo(actual.getExponente(), actual.getCoeficiente()));
            actual = actual.siguiente;
        }

        return copia;
    }

    private static Nodo obtenerMayorTermino(Polinomio p) {
        Nodo actual = p.getCabeza();
        Nodo mayor = actual;

        while (actual != null) {
            if (mayor == null || actual.getExponente() > mayor.getExponente()) {
                mayor = actual;
            }
            actual = actual.siguiente;
        }

        return mayor;
    }
}
