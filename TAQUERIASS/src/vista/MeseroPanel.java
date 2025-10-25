package vista;
import javax.swing.*;
import java.awt.*;

/**
 * Panel principal para el mesero.
 */
public class MeseroPanel extends JPanel {

    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    public MeseroPanel(String usuario) {
        setLayout(new BorderLayout());

        // Título
        JLabel lblTitulo = new JLabel("Panel del Mesero - Usuario: " + usuario, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        // Barra lateral
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnEntradas = new JButton("Ver Entradas");
        JButton btnPlatos = new JButton("Ver Platos");
        JButton btnBebidas = new JButton("Ver Bebidas");
        JButton btnPostres = new JButton("Ver Postres");
        JButton btnOrdenes = new JButton("Ver Órdenes");
        JButton btnMesas = new JButton("Ver Mesas");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        menuPanel.add(btnEntradas);
        menuPanel.add(btnPlatos);
        menuPanel.add(btnBebidas);
        menuPanel.add(btnPostres);
        menuPanel.add(btnOrdenes);
        menuPanel.add(btnMesas);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(btnCerrarSesion);

        add(menuPanel, BorderLayout.WEST);

        // Panel de contenido con CardLayout
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        contentPanel.add(new ProductosCategoriaPanel("entrada"), "Entradas");
        contentPanel.add(new ProductosCategoriaPanel("plato"), "Platos");
        contentPanel.add(new ProductosCategoriaPanel("bebida"), "Bebidas");
        contentPanel.add(new ProductosCategoriaPanel("postre"), "Postres");
        contentPanel.add(new OrdenesPanel(usuario), "Ordenes");
        contentPanel.add(new MesasPanel(usuario), "Mesas");

        add(contentPanel, BorderLayout.CENTER);

        // Acciones
        btnEntradas.addActionListener(e -> cardLayout.show(contentPanel, "Entradas"));
        btnPlatos.addActionListener(e -> cardLayout.show(contentPanel, "Platos"));
        btnBebidas.addActionListener(e -> cardLayout.show(contentPanel, "Bebidas"));
        btnPostres.addActionListener(e -> cardLayout.show(contentPanel, "Postres"));
        btnOrdenes.addActionListener(e -> cardLayout.show(contentPanel, "Ordenes"));
        btnMesas.addActionListener(e -> cardLayout.show(contentPanel, "Mesas"));

        btnCerrarSesion.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
            new LoginFrame().setVisible(true);
        });
    }
}

