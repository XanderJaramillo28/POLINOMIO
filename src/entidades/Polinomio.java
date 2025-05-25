package entidades;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;

public class Polinomio {
    private Nodo cabeza;

    public Polinomio() {
        cabeza = null;
    }

    public void agregar(Nodo nuevo) {
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            if (nuevo.getExponente() > cabeza.getExponente()) {
                nuevo.siguiente = cabeza;
                cabeza = nuevo;
            } else {
                Nodo actual = cabeza;
                Nodo anterior = null;
                while (actual != null && nuevo.getExponente() < actual.getExponente()) {
                    anterior = actual;
                    actual = actual.siguiente;
                }

                if (actual != null && actual.getExponente() == nuevo.getExponente()) {
                    double sumaCoef = actual.getCoeficiente() + nuevo.getCoeficiente();
                    if (sumaCoef == 0) {
                        if (anterior == null) {
                            cabeza = actual.siguiente;
                        } else {
                            anterior.siguiente = actual.siguiente;
                        }
                    } else {
                        actual.setCoeficiente(sumaCoef);
                    }
                } else {
                    if (anterior == null) {
                        nuevo.siguiente = cabeza;
                        cabeza = nuevo;
                    } else {
                        anterior.siguiente = nuevo;
                        nuevo.siguiente = actual;
                    }
                }
            }
        }
    }

    public void limpiar() {
        cabeza = null;
    }

    public Polinomio getDerivada() {
        Polinomio derivada = new Polinomio();
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.getExponente() > 0) {
                int nuevoExp = actual.getExponente() - 1;
                double nuevoCoef = actual.getCoeficiente() * actual.getExponente();
                derivada.agregar(new Nodo(nuevoExp, nuevoCoef));
            }
            actual = actual.siguiente;
        }
        return derivada;
    }

    public List<Monomio> toDTO() {
        List<Monomio> lista = new ArrayList<>();
        Nodo actual = cabeza;
        while (actual != null) {
            lista.add(new Monomio(actual.getExponente(), actual.getCoeficiente()));
            actual = actual.siguiente;
        }
        return lista;
    }

    public void fromDTO(List<Monomio> monomios) {
        this.limpiar();
        for (Monomio m : monomios) {
            this.agregar(new Nodo(m.getExponente(), m.getCoeficiente()));
        }
    }

    public void mostrar(JLabel label) {
        if (cabeza == null) {
            label.setText("0");
            return;
        }

        StringBuilder sb = new StringBuilder("<html><span style='font-size:20pt;'>");
        Nodo actual = cabeza;
        boolean primero = true;

        while (actual != null) {
            double coef = actual.getCoeficiente();
            int exp = actual.getExponente();

            if (coef != 0) {
                if (coef > 0 && !primero) {
                    sb.append(" + ");
                } else if (coef < 0) {
                    if (primero) {
                        sb.append("-");
                    } else {
                        sb.append(" - ");
                    }
                    coef = -coef;
                }

                if (!(coef == 1 && exp != 0)) {
                    if (coef % 1 == 0) {
                        sb.append((int) coef);
                    } else {
                        sb.append(coef);
                    }
                }

                if (exp > 0) {
                    sb.append("x");
                    if (exp > 1) {
                        sb.append("<sup>").append(exp).append("</sup>");
                    }
                }

                primero = false;
            }

            actual = actual.siguiente;
        }

        sb.append("</span></html>");
        label.setText(sb.toString());
    }

    public Nodo getCabeza() {
        return cabeza;
    }
}
