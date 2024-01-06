package view.components;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchPanel extends JPanel {

  private JTextField txtTituloSearch;
  private JTextField txtAutorSearch;
  private JComboBox<String> cmbEstadoSearch;

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
    cmbEstadoSearch = new JComboBox<String>();
    cmbEstadoSearch.addItem("Todos");
    cmbEstadoSearch.addItem("Disponible");
    cmbEstadoSearch.addItem("Agotado");

    add(lblSearch);
    add(txtTituloSearch);
    add(lblAutorSearch);
    add(txtAutorSearch);
    add(lblEstadoSearch);
    add(cmbEstadoSearch);
  }

  public String getSearchText() {
    return txtTituloSearch.getText();
  }

  public String getAutorSearchText() {
    return txtAutorSearch.getText();
  }

  public String getEstadoSearch() {
    return cmbEstadoSearch.getSelectedItem().toString();
  }

  // TODO: Métodos adicionales según sea necesario
}
