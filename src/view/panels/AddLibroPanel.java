package view.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.HomeController;
import model.Autor;
import model.Libro;
import model.utils.AutorItem;
import view.utils.PHTextField;
import view.utils.fonts.SFProFont;

public class AddLibroPanel extends JPanel {
  private PHTextField txtTitulo, txtYear, txtISBN, txtPaginas, txtEdicion;
  private JButton btnAddLibro;

  private JPanel formContainer, northContainer;

  private JComboBox<AutorItem> comboAutores;

  public AddLibroPanel() {

    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    setName(HomeController.Panel.LIBRO_INFO.toString());

    initComponents();
    addComponents();
  }

  private void initComponents() {

    // NORTH
    northContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
    northContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    JLabel lblTitle = new JLabel("Agregar libro", JLabel.LEFT);
    lblTitle.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 32));
    northContainer.add(lblTitle);

    // CENTER
    formContainer = new JPanel();
    formContainer.setLayout(new GridLayout(0, 1, 5, 5));

    txtTitulo = new PHTextField(20);
    txtTitulo.setPlaceholder("Título");
    txtTitulo.setFont(new Font(SFProFont.SF_PRO_REGULAR, Font.PLAIN, 22));

    comboAutores = new JComboBox<>();
    comboAutores.setFont(new Font(SFProFont.SF_PRO_REGULAR, Font.PLAIN, 22));

    txtYear = new PHTextField(20);
    txtYear.setPlaceholder("Año");
    txtYear.setFont(new Font(SFProFont.SF_PRO_REGULAR, Font.PLAIN, 22));

    txtISBN = new PHTextField(20);
    txtISBN.setPlaceholder("ISBN (Estándar Internacional de Libros)");
    txtISBN.setFont(new Font(SFProFont.SF_PRO_REGULAR, Font.PLAIN, 22));

    txtPaginas = new PHTextField(20);
    txtPaginas.setPlaceholder("Páginas");
    txtPaginas.setFont(new Font(SFProFont.SF_PRO_REGULAR, Font.PLAIN, 22));

    txtEdicion = new PHTextField(20);
    txtEdicion.setPlaceholder("Edición");
    txtEdicion.setFont(new Font(SFProFont.SF_PRO_REGULAR, Font.PLAIN, 22));

    btnAddLibro = new JButton("Añadir Libro");
    btnAddLibro.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 22));
    btnAddLibro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    formContainer.add(txtISBN);
    formContainer.add(txtTitulo);
    formContainer.add(comboAutores);
    formContainer.add(txtYear);
    formContainer.add(txtPaginas);
    formContainer.add(txtEdicion);
    formContainer.add(btnAddLibro);
  }

  private void addComponents() {
    add(northContainer, BorderLayout.NORTH);
    add(formContainer, BorderLayout.CENTER);
  }

  public void setAutores(List<Autor> autores) {
    comboAutores.removeAllItems();
    comboAutores.addItem(new AutorItem("Seleccione un autor"));
    for (Autor autor : autores) {
      comboAutores.addItem(new AutorItem(autor));
    }
  }

  public Autor getSelectedAutor() {
    return ((AutorItem) comboAutores.getSelectedItem()).getAutor();
  }

  public void clearForm() {
    txtTitulo.setEditable(true);
    txtYear.setEditable(true);
    txtPaginas.setEditable(true);
    txtEdicion.setEditable(true);
    comboAutores.setEnabled(true);

    txtTitulo.setText("");
    txtYear.setText("");
    txtPaginas.setText("");
    txtEdicion.setText("");
    comboAutores.setSelectedIndex(0);
  }

  public void setFormEditable(boolean editable) {
    txtTitulo.setEditable(editable);
    txtYear.setEditable(editable);
    txtPaginas.setEditable(editable);
    txtEdicion.setEditable(editable);
    comboAutores.setEnabled(editable);
  }

  public void autoCompleteForm(Libro libro) {
    txtTitulo.setText(libro.getTitulo());
    txtYear.setText(String.valueOf(libro.getAnioPublicacion()));
    txtPaginas.setText(String.valueOf(libro.getPaginas()));
    txtEdicion.setText(String.valueOf(libro.getEdicion()));
    selectAutorComboBox(libro.getAutor());
  }

  private void selectAutorComboBox(Autor autorBuscado) {
    for (int i = 0; i < getComboAutores().getItemCount(); i++) {
      AutorItem autorItem = getComboAutores().getItemAt(i);
      if (autorItem != null && autorItem.getAutor() != null
          && autorItem.getAutor().equals(autorBuscado)) {
        getComboAutores().setSelectedIndex(i);
        break;
      }
    }
  }

  public PHTextField getTxtTitulo() {
    return txtTitulo;
  }

  public void setTxtTitulo(PHTextField txtTitulo) {
    this.txtTitulo = txtTitulo;
  }

  public PHTextField getTxtYear() {
    return txtYear;
  }

  public void setTxtYear(PHTextField txtYear) {
    this.txtYear = txtYear;
  }

  public PHTextField getTxtISBN() {
    return txtISBN;
  }

  public void setTxtISBN(PHTextField txtISBN) {
    this.txtISBN = txtISBN;
  }

  public PHTextField getTxtPaginas() {
    return txtPaginas;
  }

  public void setTxtPaginas(PHTextField txtPaginas) {
    this.txtPaginas = txtPaginas;
  }

  public PHTextField getTxtEdicion() {
    return txtEdicion;
  }

  public void setTxtEdicion(PHTextField txtEdicion) {
    this.txtEdicion = txtEdicion;
  }

  public JButton getBtnAddLibro() {
    return btnAddLibro;
  }

  public void setBtnAddLibro(JButton btnAddLibro) {
    this.btnAddLibro = btnAddLibro;
  }

  public JComboBox<AutorItem> getComboAutores() {
    return comboAutores;
  }

  public void setComboAutores(JComboBox<AutorItem> comboAutores) {
    this.comboAutores = comboAutores;
  }

}
