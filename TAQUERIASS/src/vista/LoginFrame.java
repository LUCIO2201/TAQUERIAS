package vista;
import controlador.UsuarioControlador;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana de inicio de sesión.
 */
public class LoginFrame extends JFrame {
    private final JTextField txtUsuario;
    private final JPasswordField txtContrasena;
    private final JButton btnIngresar;
    private final UsuarioControlador usuarioControlador;

    public LoginFrame() {
        setTitle("Login - Taquería");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        usuarioControlador = new UsuarioControlador();

        // Panel del logo
        JPanel panelLogo = new JPanel();
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setIcon(new ImageIcon("src/recursos/logo.png")); // Cambia la ruta del logo según tu proyecto
        panelLogo.add(lblLogo);
        add(panelLogo, BorderLayout.NORTH);

        // Panel de login
        JPanel panelLogin = new JPanel(new GridLayout(5, 1, 10, 10));
        panelLogin.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        txtUsuario = new JTextField();
        txtContrasena = new JPasswordField();
        btnIngresar = new JButton("Ingresar");

        panelLogin.add(new JLabel("Usuario:"));
        panelLogin.add(txtUsuario);
        panelLogin.add(new JLabel("Contraseña:"));
        panelLogin.add(txtContrasena);
        panelLogin.add(btnIngresar);

        add(panelLogin, BorderLayout.CENTER);

        // Evento del botón
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText().trim();
                String pass = new String(txtContrasena.getPassword());

                Usuario u = usuarioControlador.login(usuario, pass);
                if (u != null) {
                    abrirPanelPorRol(u);
                    dispose(); // Cierra el login
                } else {
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Abre el panel correspondiente según el rol del usuario autenticado.
     */
    private void abrirPanelPorRol(Usuario usuario) {
        switch (usuario.getRol().toLowerCase()) {
            case "admin":
                new AdminPanel(usuario).setVisible(true);
                break;
            case "mesero":
                new MeseroPanel(usuario).setVisible(true);
                break;
            case "cocinero":
                new CocineroPanel(usuario).setVisible(true);
                break;
            case "cajero":
                new CajeroPanel(usuario).setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Rol no reconocido: " + usuario.getRol());
        }
    }
}

