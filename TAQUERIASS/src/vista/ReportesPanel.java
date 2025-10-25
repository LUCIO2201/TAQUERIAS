package vista;

import controlador.ReporteControlador;
import modelo.ReporteVenta;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * Panel para ver reportes de ventas y generar PDFs.
 */
public class ReportesPanel extends JPanel {
    private JTable tabla;
    private final DefaultTableModel modeloTabla;
    private final ReporteControlador controlador;

    public ReportesPanel() {
        controlador = new ReporteControlador();
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Reportes de Ventas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Fecha", "Total"}, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnSemanal = new JButton("Ver Semanal");
        JButton btnMensual = new JButton("Ver Mensual");
        JButton btnGenerarPDF = new JButton("Generar PDF");

        panelBotones.add(btnSemanal);
        panelBotones.add(btnMensual);
        panelBotones.add(btnGenerarPDF);

        add(panelBotones, BorderLayout.SOUTH);

        // Acciones
        btnSemanal.addActionListener(e -> cargarReporte("semanal"));
        btnMensual.addActionListener(e -> cargarReporte("mensual"));
        btnGenerarPDF.addActionListener(e -> generarPDF());
    }

    private void cargarReporte(String tipo) {
        modeloTabla.setRowCount(0);
        List<ReporteVenta> ventas = tipo.equals("semanal")
                ? controlador.obtenerVentasSemanales()
                : controlador.obtenerVentasMensuales();

        for (ReporteVenta r : ventas) {
            modeloTabla.addRow(new Object[]{r.getId(), r.getFecha(), r.getTotal()});
        }
    }

    private void generarPDF() {
        File archivo = controlador.generarReportePDF();

        if (archivo != null && archivo.exists()) {
            JOptionPane.showMessageDialog(this, "PDF generado: " + archivo.getName());
            try {
                Desktop.getDesktop().open(archivo);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No se pudo abrir el PDF.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se generó el PDF.");
        }
    }
}

