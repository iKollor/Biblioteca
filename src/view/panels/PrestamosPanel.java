package view.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import controller.HomeController;
import model.Prestamo;
import view.utils.fonts.SFProFont;

public class PrestamosPanel extends JPanel {

  private JPanel topContainer, bottomContainer;
  private JTable table;
  private JButton btnDevolver, btnClearPrestamos;

  private DefaultTableModel tableModel;

  private List<Prestamo> prestamosList;

  private boolean isAdmin = false;

  public PrestamosPanel() {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    setName(HomeController.Panel.PRESTAMOS.toString());

    initComponents();
    addComponents();
  }

  private void initComponents() {
    // Inicialización de componentes
    topContainer = new JPanel(new BorderLayout());
    JLabel lblTitulo = new JLabel("Préstamos");
    lblTitulo.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 24));
    topContainer.add(lblTitulo, BorderLayout.WEST);

    // Modelo de tabla
    String[] columnNames = { "ID", "Prestado por", "Título del Libro", "Fecha de Préstamo", "Fecha de Devolución",
        "Devuelto" };
    tableModel = new DefaultTableModel(columnNames, 0);
    table = new JTable(tableModel);
    table.setFillsViewportHeight(true);
    table.getTableHeader().setReorderingAllowed(false);
    table.getColumnModel().getColumn(0).setMaxWidth(50);
    table.getColumnModel().getColumn(5).setMaxWidth(100);
    table.setDefaultEditor(Object.class, null);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // Botón de devolución
    btnDevolver = new JButton("Devolver Libro");
    btnDevolver.setFont(new Font(SFProFont.SF_PRO_MEDIUM, Font.PLAIN, 16));
    btnDevolver.setEnabled(false);

    // Botón de limpiar préstamos
    btnClearPrestamos = new JButton("Limpiar Préstamos");
    btnClearPrestamos.setFont(new Font(SFProFont.SF_PRO_MEDIUM, Font.PLAIN, 16));

    bottomContainer = new JPanel(new GridLayout(1, 2, 10, 0));
    bottomContainer.add(btnDevolver);
    bottomContainer.add(btnClearPrestamos);
  }

  private void addComponents() {
    // Añadir componentes al panel
    add(topContainer, BorderLayout.NORTH);
    add(new JScrollPane(table), BorderLayout.CENTER); // Añadir la tabla dentro de un JScrollPane
    if (!isAdmin)
      add(bottomContainer, BorderLayout.SOUTH);
  }

  public void setPrestamosData(List<Prestamo> prestamos) {
    this.prestamosList = prestamos;
    if (prestamosList != null && !prestamosList.isEmpty()) {
      updateTableData();
    }
  }

  private void updateTableData() {
    tableModel.setRowCount(0); // Limpiar la tabla
    for (Prestamo prestamo : prestamosList) {
      tableModel.addRow(new Object[] {
          prestamo.getId(),
          prestamo.getUsuario().getNombre() + " " + prestamo.getUsuario().getApellido(),
          prestamo.getLibro().getTitulo(),
          prestamo.getFechaPrestamo(),
          prestamo.getFechaDevolucion(),
          prestamo.isDevuelto() ? "Sí" : "No"
      });
    }
    tableModel.fireTableDataChanged();
    table.validate();
    table.repaint();
  }

  public void updateTableView() {
    updateTableData();
    validate();
    repaint();
  }

  public JButton getBtnDevolver() {
    return btnDevolver;
  }

  public void setBtnDevolver(JButton btnDevolver) {
    this.btnDevolver = btnDevolver;
  }

  public JTable getTable() {
    return table;
  }

  public void setTable(JTable table) {
    this.table = table;
  }

  public List<Prestamo> getPrestamosList() {
    return prestamosList;
  }

  public void setPrestamosList(List<Prestamo> prestamosList) {
    this.prestamosList = prestamosList;
  }

  public JButton getBtnClearPrestamos() {
    return btnClearPrestamos;
  }

  public void setBtnClearPrestamos(JButton btnClearPrestamos) {
    this.btnClearPrestamos = btnClearPrestamos;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }
}
