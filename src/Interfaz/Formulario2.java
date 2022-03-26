package Interfaz;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Formulario2 extends JFrame {

  private JPanel contentPane;
  private JTextField tf1;
  private JTextField tf2;
  private JLabel labelResultado;
  private JButton btnConsultaPorCdigo;
  private JTextField tf3;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Formulario frame = new Formulario();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public Formulario2() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 606, 405);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);
    
    JLabel lblDescripcinDelArtculo = new JLabel("Descripcion del artículo:");
    lblDescripcinDelArtculo.setBounds(23, 38, 193, 14);
    contentPane.add(lblDescripcinDelArtculo);
    
    tf1 = new JTextField();
    tf1.setBounds(247, 35, 193, 20);
    contentPane.add(tf1);
    tf1.setColumns(10);
    
    JLabel lblPrecio = new JLabel("Precio:");
    lblPrecio.setBounds(23, 74, 95, 14);
    contentPane.add(lblPrecio);
    
    tf2 = new JTextField();
    tf2.setBounds(247, 71, 107, 20);
    contentPane.add(tf2);
    tf2.setColumns(10);
    
    labelResultado = new JLabel("resultado");
    labelResultado.setBounds(361, 122, 229, 14);
    contentPane.add(labelResultado);
    
    btnConsultaPorCdigo = new JButton("Consulta por código");
    btnConsultaPorCdigo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        labelResultado.setText("");
        tf1.setText("");
        tf2.setText("");        
        try {
          Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/bd1","root" ,"");
          Statement comando=conexion.createStatement();
          ResultSet registro = comando.executeQuery("select descripcion,precio from articulos where codigo="+tf3.getText());
          if (registro.next()==true) {
            tf1.setText(registro.getString("descripcion"));
            tf2.setText(registro.getString("precio"));
          } else {
            labelResultado.setText("No existe un artículo con dicho código");
          }
          conexion.close();
        } catch(SQLException ex){
          setTitle(ex.toString());
        }
      }
    });
    btnConsultaPorCdigo.setBounds(25, 122, 177, 23);
    contentPane.add(btnConsultaPorCdigo);
    
    tf3 = new JTextField();
    tf3.setBounds(247, 123, 86, 20);
    contentPane.add(tf3);
    tf3.setColumns(10);
    
    JButton btnNewButton = new JButton("Borrar");
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        labelResultado.setText("");
        try {
          Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/bd1","root" ,"");
          Statement comando=conexion.createStatement();
          int cantidad = comando.executeUpdate("delete from articulos where codigo="+tf3.getText());
          if (cantidad==1) {
            tf1.setText("");
            tf2.setText("");        
            labelResultado.setText("Se borro el artículo con dicho código");
          } else {
            labelResultado.setText("No existe un artículo con dicho código");
          }
          conexion.close();
        } catch(SQLException ex){
          setTitle(ex.toString());
        }        
      }
    });
    btnNewButton.setBounds(24, 156, 177, 23);
    contentPane.add(btnNewButton);
    
    JButton btnNewButton_1 = new JButton("Modificar");
    btnNewButton_1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        labelResultado.setText("");
        try {
          Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/bd1","root" ,"");
          Statement comando=conexion.createStatement();
          int cantidad = comando.executeUpdate("update articulos set descripcion='" + tf1.getText() + "'," +
                                           "precio=" + tf2.getText() + " where codigo="+tf3.getText());
          if (cantidad==1) {
            labelResultado.setText("Se modifico la descripcion y el precio del artículo con dicho código");
          } else {
            labelResultado.setText("No existe un artículo con dicho código");
          }
          conexion.close();
        } catch(SQLException ex){
          setTitle(ex.toString());
        }                
      }
    });
    btnNewButton_1.setBounds(21, 190, 179, 23);
    contentPane.add(btnNewButton_1);
    cargarDriver();
  }
  
  private void cargarDriver() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
    }catch(Exception ex) {
      setTitle(ex.toString());
    }
  }
}