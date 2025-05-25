import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import entidades.Nodo;
import entidades.Operacion;
import entidades.Polinomio;
import servicios.Archivo;
import servicios.ResultadoDivision;
import servicios.ServicioPolinomio;

public class FrmPolinomios extends JFrame {

    private JComboBox<String> cmbOperacion;
    private JComboBox<String> cmbPolinomio;
    private JLabel lblCoeficiente, lblX, lblExponente;
    private JLabel lblPolinomio1, lblPolinomio2, lblPolinomioR, lblPolinomioRD;
    private JTextField txtCoeficiente, txtExponente;
    private String nombreArchivo = "";
    private List<Operacion> operaciones = new ArrayList<>();
    private final String[] tiposOperaciones = {
        "Suma", "Resta", "Multiplicacion", "Division", "Derivada", "Multiplicar Por Monomio"
    };

    private Polinomio p1 = new Polinomio();
    private Polinomio p2 = new Polinomio();

    public FrmPolinomios() {
        initComponents();
    }

    private void initComponents() {
        lblCoeficiente = new JLabel("Coeficiente:");
        txtCoeficiente = new JTextField();
        lblX = new JLabel("x");
        lblExponente = new JLabel("Exponente");
        txtExponente = new JTextField();
        cmbPolinomio = new JComboBox<>(new String[] { "Polinomio 1", "Polinomio 2" });
        JButton btnAgregar = new JButton("Agregar");
        JButton btnLimpiar = new JButton("Limpiar");
        lblPolinomio1 = new JLabel();
        lblPolinomio2 = new JLabel();
        cmbOperacion = new JComboBox<>(tiposOperaciones);
        lblPolinomioR = new JLabel();
        lblPolinomioRD = new JLabel();
        JButton btnCalcular = new JButton("Calcular");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCargar = new JButton("Cargar");

        setSize(600, 450);
        setTitle("Polinomios");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/IMAGEN/polinomio.png")).getImage());
        getContentPane().setLayout(null);

        lblCoeficiente.setBounds(10, 60, 80, 25);
        txtCoeficiente.setBounds(80, 60, 100, 25);
        lblX.setBounds(180, 40, 50, 40);
        lblX.setFont(new java.awt.Font("Times New Roman", 2, 48));
        lblExponente.setBounds(130, 20, 80, 25);
        txtExponente.setBounds(210, 20, 100, 25);
        cmbPolinomio.setBounds(300, 50, 100, 25);
        btnAgregar.setBounds(410, 50, 80, 25);
        btnLimpiar.setBounds(500, 50, 80, 25);

        lblPolinomio1.setBounds(0, 90, 600, 50);
        lblPolinomio1.setBackground(new java.awt.Color(0, 153, 204));
        lblPolinomio1.setOpaque(true);

        lblPolinomio2.setBounds(0, 150, 600, 50);
        lblPolinomio2.setBackground(new java.awt.Color(0, 153, 204));
        lblPolinomio2.setOpaque(true);

        btnCalcular.setBounds(10, 210, 100, 25);
        cmbOperacion.setBounds(120, 210, 100, 25);
        btnGuardar.setBounds(230, 210, 100, 25);
        btnCargar.setBounds(340, 210, 100, 25);

        lblPolinomioR.setBounds(0, 250, 600, 50);
        lblPolinomioR.setBackground(new java.awt.Color(255, 204, 153));
        lblPolinomioR.setOpaque(true);

        lblPolinomioRD.setBounds(0, 310, 600, 50);
        lblPolinomioRD.setBackground(new java.awt.Color(255, 153, 153));
        lblPolinomioRD.setOpaque(true);

        getContentPane().add(lblCoeficiente);
        getContentPane().add(txtCoeficiente);
        getContentPane().add(lblX);
        getContentPane().add(lblExponente);
        getContentPane().add(txtExponente);
        getContentPane().add(cmbPolinomio);
        getContentPane().add(btnAgregar);
        getContentPane().add(btnLimpiar);
        getContentPane().add(lblPolinomio1);
        getContentPane().add(lblPolinomio2);
        getContentPane().add(btnCalcular);
        getContentPane().add(cmbOperacion);
        getContentPane().add(btnGuardar);
        getContentPane().add(btnCargar);
        getContentPane().add(lblPolinomioR);
        getContentPane().add(lblPolinomioRD);

        btnAgregar.addActionListener(this::btnAgregarClick);
        btnLimpiar.addActionListener(this::btnLimpiarClick);
        btnCalcular.addActionListener(this::btnCalcularClick);
        btnGuardar.addActionListener(e -> btnGuardarClick());
        btnCargar.addActionListener(e -> btnCargarClick());
    }

    private void btnAgregarClick(ActionEvent evt) {
        try {
            int exponente = Integer.parseInt(txtExponente.getText());
            int coeficiente = Integer.parseInt(txtCoeficiente.getText());
            Nodo nodo = new Nodo(exponente, coeficiente);

            if (cmbPolinomio.getSelectedIndex() == 0) {
                p1.agregar(nodo);
                p1.mostrar(lblPolinomio1);
            } else {
                p2.agregar(nodo);
                p2.mostrar(lblPolinomio2);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese números válidos para coeficiente y exponente.");
        }
    }

    private void btnLimpiarClick(ActionEvent evt) {
        if (cmbPolinomio.getSelectedIndex() == 0) {
            p1.limpiar();
            p1.mostrar(lblPolinomio1);
        } else {
            p2.limpiar();
            p2.mostrar(lblPolinomio2);
        }
    }

    private Object calcular() {
        switch (cmbOperacion.getSelectedIndex()) {
            case 0: return ServicioPolinomio.sumar(p1, p2);
            case 1: return ServicioPolinomio.restar(p1, p2);
            case 2: return ServicioPolinomio.multiplicar(p1, p2);
            case 3: return ServicioPolinomio.dividir(p1, p2);
            case 4:
                return (cmbPolinomio.getSelectedIndex() == 0 ? p1.getDerivada() : p2.getDerivada());
            case 5:
                if (txtCoeficiente.getText().isEmpty() || txtExponente.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debes ingresar coeficiente y exponente del monomio.");
                    return new Polinomio();
                }
                try {
                    int coef = Integer.parseInt(txtCoeficiente.getText());
                    int expo = Integer.parseInt(txtExponente.getText());
                    Nodo monomio = new Nodo(expo, coef);
                    return ServicioPolinomio.multiplicarPorMonomio(
                            cmbPolinomio.getSelectedIndex() == 0 ? p1 : p2, monomio);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Formato inválido del monomio.");
                    return new Polinomio();
                }
            default:
                return new Polinomio();
        }
    }

    private void btnCalcularClick(ActionEvent evt) {
    String operacion = (String) cmbOperacion.getSelectedItem();
    Polinomio resultado = new Polinomio();

    // Limpiar resultados anteriores
    lblPolinomioR.setText("");
    lblPolinomioRD.setText("");

    switch (operacion) {
        case "Suma":
            resultado = ServicioPolinomio.sumar(p1, p2);
            resultado.mostrar(lblPolinomioR);
            break;

        case "Resta":
            resultado = ServicioPolinomio.restar(p1, p2);
            resultado.mostrar(lblPolinomioR);
            break;

        case "Multiplicacion":
            resultado = ServicioPolinomio.multiplicar(p1, p2);
            resultado.mostrar(lblPolinomioR);
            break;

        case "Division":
            try {
                ResultadoDivision division = ServicioPolinomio.dividir(p1, p2);
                division.getCociente().mostrar(lblPolinomioR);
                division.getResto().mostrar(lblPolinomioRD);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Error en la división: " + ex.getMessage());
            }
            break;

        case "Derivada":
            // Derivar el polinomio seleccionado
            Polinomio derivada = ServicioPolinomio.derivar(
                    cmbPolinomio.getSelectedIndex() == 0 ? p1 : p2);
            derivada.mostrar(lblPolinomioRD);
            break;

        case "Multiplicar Por Monomio":
            try {
                // Leer los valores del monomio desde los campos de texto
                String coefStr = txtCoeficiente.getText().trim();
                String expStr = txtExponente.getText().trim();

                if (coefStr.isEmpty() || expStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ingrese coeficiente y exponente del monomio.");
                    return;
                }

                double coef = Double.parseDouble(coefStr);
                int exp = Integer.parseInt(expStr);

                Nodo monomio = new Nodo(exp, coef);

                // Obtener el polinomio seleccionado
                Polinomio polinomioOriginal = (cmbPolinomio.getSelectedIndex() == 0) ? p1 : p2;
                resultado = ServicioPolinomio.multiplicarPorMonomio(polinomioOriginal, monomio);
                resultado.mostrar(lblPolinomioR);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Coeficiente o exponente inválido. Deben ser números.");
            }
            break;

        default:
            JOptionPane.showMessageDialog(this, "Operación no reconocida.");
            break;
    }
}

    private void btnGuardarClick() {
        if (nombreArchivo.isEmpty()) {
            nombreArchivo = Archivo.elegirArchivo();
        }
        if (!nombreArchivo.isEmpty()) {
            Object resultado = calcular();
            Operacion operacion = new Operacion(
                    tiposOperaciones[cmbOperacion.getSelectedIndex()],
                    p1.toDTO(), p2.toDTO(), resultado instanceof Polinomio ? ((Polinomio) resultado).toDTO() : null
            );
            operaciones.add(operacion);
            Archivo.guardarJson(nombreArchivo, operaciones);
            JOptionPane.showMessageDialog(this, "Archivo guardado con éxito.");
        }
    }

    private void btnCargarClick() {
        nombreArchivo = Archivo.elegirArchivo();
        if (!nombreArchivo.isEmpty()) {
            operaciones = Archivo.leerJson(nombreArchivo, new TypeReference<List<Operacion>>() {});
            if (!operaciones.isEmpty()) {
                p1.fromDTO(operaciones.get(0).getPolinomio1());
                p2.fromDTO(operaciones.get(0).getPolinomio2());
                p1.mostrar(lblPolinomio1);
                p2.mostrar(lblPolinomio2);
            }
        }
    }
}