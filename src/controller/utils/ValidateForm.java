package controller.utils;

import controller.services.BibliotecaServicio.Estado;
import model.Usuario;

/**
 * Clase que proporciona métodos para validar diferentes campos de formularios.
 */
public class ValidateForm {

  // Variable que permite habilitar o deshabilitar la validación de formularios
  private static boolean enabled = true;

  public static boolean isEnabled() {
    return enabled;
  }

  public static void setEnabled(boolean enabled) {
    ValidateForm.enabled = enabled;
  }

  /**
   * Valida una dirección de correo electrónico.
   * 
   * @param email la dirección de correo electrónico a validar
   * @return true si la dirección de correo electrónico es válida, false de lo contrario
   */
  public static boolean validateEmail(String email) {
    if (!enabled)
      return true;
    return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
  }

  /**
   * Valida una contraseña basada en los siguientes criterios: - Contiene al menos un dígito [0-9] -
   * Contiene al menos una letra minúscula [a-z] - Contiene al menos una letra mayúscula [A-Z] -
   * Contiene al menos un carácter especial [@#$%^&+=] - Tiene una longitud mínima de 8 caracteres
   * 
   * @param password La contraseña a validar
   * @return true si la contraseña cumple con los criterios, false de lo contrario
   */
  public static boolean validatePassword(String password) {
    if (!enabled)
      return true;
    return password.length() >= 8
        && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$");
  }

  /**
   * Valida un número de DNI. - Tiene una longitud de 10 dígitos - Contiene solo dígitos
   * 
   * @param dni el número de DNI a validar
   * @return true si el número de DNI es válido, false de lo contrario
   */
  public static boolean validateDNI(String dni) {
    if (!enabled)
      return true;
    return dni.matches("^[0-9]{10}$");
  }

  /**
   * Valida si el nombre cumple con el formato requerido. - Tiene una longitud mínima de 2
   * caracteres - Tiene una longitud máxima de 30 caracteres - Contiene solo letras
   * 
   * @param name el nombre a validar
   * @return true si el nombre cumple con el formato requerido, false de lo contrario
   */
  public static boolean validateName(String name) {
    if (!enabled)
      return true;
    return name.matches("^[a-zA-ZáéíóúÁÉÍÓÚ ]{2,30}$");
  }

  /**
   * Valida el apellido ingresado. - Tiene una longitud mínima de 2 caracteres - Tiene una longitud
   * máxima de 30 caracteres - Contiene solo letras
   * 
   * @param lastName el apellido a validar
   * @return true si el apellido es válido, false de lo contrario
   */
  public static boolean validateLastName(String lastName) {
    if (!enabled)
      return true;
    return lastName.matches("^[a-zA-ZáéíóúÁÉÍÓÚ ]{2,30}$");
  }

  /**
   * Valida un número de ISBN. - Tiene una longitud de 13 dígitos - Contiene solo dígitos
   * 
   * @param ISBN el número de ISBN a validar
   * @return true si el número de ISBN es válido, false de lo contrario
   */
  public static boolean validateISBN(String ISBN) {
    if (!enabled)
      return true;
    return ISBN.matches("^[0-9]{10}$");
  }

  /**
   * Valida un título. - Tiene una longitud mínima de 2 caracteres - Tiene una longitud máxima de 30
   * caracteres - Contiene solo letras y números
   * 
   * @param title el título a validar
   * @return true si el título es válido, false de lo contrario
   */
  public static boolean validateTitle(String title) {
    if (!enabled)
      return true;
    return title.matches("^[a-zA-ZáéíóúÁÉÍÓÚ0-9 ]{2,30}$");
  }

  /**
   * Valida un autor. - Tiene una longitud mínima de 2 caracteres - Tiene una longitud máxima de 30
   * caracteres - Contiene solo letras
   * 
   * @param author el autor a validar
   * @return true si el autor es válido, false de lo contrario
   */
  public static boolean validateAuthor(String author) {
    if (!enabled)
      return true;
    return author.matches("^[a-zA-ZáéíóúÁÉÍÓÚ ]{2,30}$");
  }

  /**
   * Valida un año. - Tiene una longitud de 4 dígitos - Contiene solo dígitos
   * 
   * @param year el año a validar
   * @return true si el año es válido, false de lo contrario
   */
  public static boolean validateYear(String year) {
    if (!enabled)
      return true;
    return year.matches("^[0-9]{4}$");
  }

  /**
   * Valida una edición. - Tiene una longitud de 1 o 2 dígitos - Contiene solo dígitos
   * 
   * @param edition la edición a validar
   * @return true si la edición es válida, false de lo contrario
   */
  public static boolean validateEdition(String edition) {
    if (!enabled)
      return true;
    return edition.matches("^[0-9]{1,2}$");
  }

  /**
   * Valida el número de páginas. - Tiene una longitud de 1 a 4 dígitos - Contiene solo dígitos
   * 
   * @param pages el número de páginas a validar
   * @return true si el número de páginas es válido, false de lo contrario
   */
  public static boolean validatePages(String pages) {
    if (!enabled)
      return true;
    return pages.matches("^[0-9]{1,4}$");
  }

  /**
   * Valida el estado de un libro en base al ENUM. - Puede ser "DISPONIBLE" o "PRESTADO"
   * 
   * @param estado el estado a validar
   * @return true si el estado es válido, false de lo contrario
   */
  public static boolean validateEstado(String estado) {
    if (!enabled)
      return true;
    return Estado.valueOf(estado) != null;
  }

  /**
   * Valida los campos de un formulario de registro.
   * 
   * @param nombre el nombre a validar
   * @param apellido el apellido a validar
   * @param dni el número de DNI a validar
   * @param email la dirección de correo electrónico a validar
   * @param password la contraseña a validar
   * @return true si todos los campos son válidos, false de lo contrario
   */
  public boolean validateRegisterForm(Usuario user) {
    if (!enabled)
      return true;
    return validateName(user.getNombre()) && validateLastName(user.getApellido())
        && validateDNI(user.getDni()) && validateEmail(user.getEmail())
        && validatePassword(new String(user.getPassword()));
  }

  /**
   * Valida los campos de un formulario de inicio de sesión.
   * 
   * @param email la dirección de correo electrónico a validar
   * @param password la contraseña a validar
   * @return true si todos los campos son válidos, false de lo contrario
   */
  public boolean validateLoginForm(String email, String password) {
    if (!enabled)
      return true;
    return validateEmail(email) && validatePassword(password);
  }

  /**
   * Valida los campos de un formulario de libro.
   * 
   * @param ISBN el número de ISBN a validar
   * @param title el título a validar
   * @param author el autor a validar
   * @param year el año a validar
   * @param edition la edición a validar
   * @param pages el número de páginas a validar
   * @param estado el estado a validar
   * @return true si todos los campos son válidos, false de lo contrario
   */
  public boolean validateBookForm(String ISBN, String title, String author, String year,
      String edition, String pages, String estado) {
    if (!enabled)
      return true;
    return validateISBN(ISBN) && validateTitle(title) && validateAuthor(author)
        && validateYear(year) && validateEdition(edition) && validatePages(pages)
        && validateEstado(estado);
  }

}
