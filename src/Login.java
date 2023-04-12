import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Login {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nElige una opción:");
            System.out.println("1. Ingresar");
            System.out.println("2. Crear usuario");
            System.out.println("3. Salir");

            int opcion;
            while (!scanner.hasNextInt()) {
                System.out.println("Opción inválida. Ingrese un número válido.");
                scanner.nextLine();
            }
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:

                    System.out.print("\nIngrese su nombre de usuario:");
                    String usuario = scanner.nextLine();
                    System.out.print("Ingrese su contraseña:");
                    String contrasenia = scanner.nextLine();

                    Usuario usuarioActual = obtenerUsuario(usuario);

                    if (usuarioActual != null && usuarioActual.getContrasenia().equals(contrasenia)) {
                        System.out.println("Inicio de sesión exitoso.");

                        if (usuarioActual.getTipoUsuario().equals("usuario")) {
                            menus.menuUsuario(usuarioActual);
                        } else if (usuarioActual.getTipoUsuario().equals("admin")) {
                            menus.menuAdmin(usuarioActual);
                        }
                    } else {
                        System.out.println("\nInicio de sesión fallido.");
                    }

                    break;

                case 2:
                    System.out.print("Ingrese el nombre de usuario:");
                    String nombreUsuario = scanner.nextLine();
                    System.out.print("Ingrese la contraseña:");
                    String clave = scanner.nextLine();
                    System.out.println("Elija el tipo de usuario:");
                    System.out.println("1. Admin");
                    System.out.println("2. Usuario");
                    int tipoUsuarioOpcion = scanner.nextInt();
                    scanner.nextLine();
                    String tipoUsuario = null;

                    switch (tipoUsuarioOpcion) {

                        case 1:
                            tipoUsuario = "admin";
                            System.out.print("Ingrese la contraseña de autorización:");
                            String autorizacion = scanner.next();
                            if (!autorizacion.equals("2002")) {
                                System.out.println("Contraseña de autorización incorrecta. No se puede crear el usuario.");
                                break;
                            }
                            break;

                        case 2:
                            tipoUsuario = "usuario";
                            break;
                        default:
                            System.out.println("Opción inválida.");
                            break;
                    }

                    if (tipoUsuario != null && verificarCredencialesregistro(nombreUsuario)) {
                        Usuario nuevoUsuario = new Usuario(nombreUsuario, clave, tipoUsuario);
                        agregarUsuario(nuevoUsuario);
                        System.out.println("\nUsuario creado exitosamente.");
                    } else {
                        System.out.println("\nEl usuario ya existe.");
                    }
                    break;

                case 3:
                    System.out.println("Hasta luego.");
                    System.exit(0);
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    public static boolean verificarCredenciales(String usuario, String contrasenia) {
        if (usuario == null || contrasenia == null) {
            return false;
        }

        try {
            File archivo = new File("usuarios.txt");
            Scanner scanner = new Scanner(archivo);

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] componentes = linea.split(",");

                if (componentes.length >= 2) {
                    String nombreUsuario = componentes[0];
                    String clave = componentes[1];

                    if (usuario.equals(nombreUsuario) && contrasenia.equals(clave)) {
                        scanner.close();
                        return true;
                    }
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void agregarUsuario(Usuario usuario) {
        try {
            FileWriter archivo = new FileWriter("usuarios.txt", true);
            archivo.write(usuario.getNombreUsuario() + "," + usuario.getContrasenia() + "," + usuario.getTipoUsuario() + "\n");
            archivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean verificarCredencialesregistro(String nombreUsuariore) {
        try {
            File archivo = new File("usuarios.txt");
            Scanner scanner = new Scanner(archivo);

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] componentes = linea.split(",");
                String nombreUsuario = componentes[0];

                if (nombreUsuariore.equals(nombreUsuario)) {
                    scanner.close();
                    return false;
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return true;
    }


    public static Usuario obtenerUsuario(String nombreUsuario) {
        try {
            File archivo = new File("usuarios.txt");
            Scanner scanner = new Scanner(archivo);
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] componentes = linea.split(",");

                if (componentes.length >= 3) {
                    String usuario = componentes[0];
                    String contrasenia = componentes[1];
                    String tipoUsuario = componentes[2];

                    if (usuario.equals(nombreUsuario)) {
                        scanner.close();
                        return new Usuario(usuario, contrasenia, tipoUsuario);
                    }
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


}




