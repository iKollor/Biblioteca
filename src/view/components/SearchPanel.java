package view.components;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import controller.services.BibliotecaServicio.Estado;

public class SearchPanel extends JPanel {

  private JTextField txtTituloSearch;
  private JTextField txtAutorSearch;
  private JComboBox<Estado> cmbEstadoSearch;
  private JButton btnSearch;


  public SearchPanel() {
    setLayout(new FlowLayout(FlowLayout.LEFT));

    JLabel lblSearch = new JLabel("Título: ");
    txtTituloSearch = new JTextField();
    txtTituloSearch.setColumns(12);
    txtTituloSearch.setToolTipText("Buscar por título");

    JLabel lblAutorSearch = new JLabel("Autor: ");
    txtAutorSearch = new JTextField();
    txtAutorSearch.setColumns(12);
    txtAutorSearch.setToolTipText("Buscar por autor");

    JLabel lblEstadoSearch = new JLabel("Estado: ");
    cmbEstadoSearch = new JComboBox<Estado>(Estado.values());
    cmbEstadoSearch.setSelectedItem(Estado.TODOS);

    btnSearch = new JButton("Buscar");

    add(lblSearch);
    add(txtTituloSearch);
    add(lblAutorSearch);
    add(txtAutorSearch);
    add(lblEstadoSearch);
    add(cmbEstadoSearch);
    add(btnSearch);
  }

  public String getSearchText() {
    return txtTituloSearch.getText();
  }

  public String getAutorSearchText() {
    return txtAutorSearch.getText();
  }

  public Estado getEstadoSearch() {
    return (Estado) cmbEstadoSearch.getSelectedItem();
  }
  
  public JButton getBtnSearch() {
    return btnSearch;
  }

}
