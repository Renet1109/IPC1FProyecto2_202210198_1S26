package view;

import controller.LoginController;
import model.Usuario;
import util.Validador;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField txtCodigo;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private LoginController controller;

    public LoginView() {
        controller = new LoginController();

        setTitle("Sancarlista Academy - Login");
        setSize(420, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Sancarlista Academy");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBounds(95, 20, 250, 30);
        add(lblTitulo);

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(50, 80, 100, 25);
        add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(150, 80, 180, 25);
        add(txtCodigo);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(50, 120, 100, 25);
        add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 120, 180, 25);
        add(txtPassword);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(150, 165, 120, 30);
        add(btnIngresar);

        btnIngresar.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {
        String codigo = txtCodigo.getText();
        String password = new String(txtPassword.getPassword());

        if (Validador.textoVacio(codigo) || Validador.textoVacio(password)) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        Usuario usuario = controller.autenticar(codigo, password);

        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Bienvenido " + usuario.getNombre() + " (" + usuario.getTipo() + ")");

        dispose();

        if (usuario.getTipo().equals("ADMINISTRADOR")) {
            new AdminDashboard();
        } else if (usuario.getTipo().equals("INSTRUCTOR")) {
            new InstructorDashboard();
        } else if (usuario.getTipo().equals("ESTUDIANTE")) {
            new EstudianteDashboard();
        }
    }
}