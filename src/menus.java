import java.io.*;
import java.util.Scanner;
import java.util.UUID;

public class menus {
    static void menuUsuario(Usuario usuario) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nElige una opción:");
            System.out.println("1. Ver productos.");
            System.out.println("2. Ver historial.");
            System.out.println("3. Comprar producto");
            System.out.println("4. Salir");

            int opcion = scanner.nextInt();


            scanner.nextLine();

            switch (opcion) {

                case 1:
                    filtros.verProductosGuardados();
                    System.out.println("¿Desea filtrar por categoría o por producto?");
                    System.out.println("1. Filtrar por categoría");
                    System.out.println("2. Filtrar por producto");
                    int filtro = scanner.nextInt();
                    scanner.nextLine();

                    switch (filtro) {
                        case 1:
                            System.out.println("Ingrese la categoría que desea filtrar:");
                            String categoria = scanner.nextLine();
                            filtros.verProductosGuardados(categoria);
                            break;
                        case 2:
                            String producto;
                            System.out.println("Ingrese el nombre del producto que desea filtrar:");
                            producto = scanner.nextLine();
                            filtros.filtrarPorProducto(producto);
                            break;

                        default:
                            System.out.println("Opción inválida.");
                    }
                    break;


                case 2:
                    System.out.println("Ingrese su nombre de usuario:");
                    String nombreUsuario = scanner.nextLine();
                    verHistorial(nombreUsuario);
                    break;


                case 3:
                    System.out.println("Ingrese los productos que desea comprar, separados por comas:");
                    String[] productosComprar = scanner.nextLine().split(",");
                    double totalCompra = 0;
                    UUID compraId = UUID.randomUUID();
                    for (String nombreProducto : productosComprar) {
                        Producto productoEncontrado = filtros.buscarProducto(nombreProducto.trim());
                        if (productoEncontrado == null) {
                            System.out.println("No se encontró el producto: " + nombreProducto);
                            continue;
                        }
                        System.out.println("Ingrese la cantidad que desea comprar de " + nombreProducto + ":");
                        int cantidadComprar = scanner.nextInt();
                        scanner.nextLine();
                        double totalProducto = cantidadComprar * productoEncontrado.getPrecio();
                        System.out.println("Total de la compra de " + nombreProducto + ": " + totalProducto);
                        agregarCompraArchivo(compraId, Usuario.getNombreUsuario(), productoEncontrado.getNombreProducto(), cantidadComprar, totalProducto);
                        totalCompra += totalProducto;
                    }
                    System.out.println("Total de la compra: " + totalCompra);
                    System.out.println("¿Desea continuar con la compra? (Confirmar/Cancelar)");
                    String confirmacion = scanner.nextLine();
                    if (confirmacion.equalsIgnoreCase("Confirmar")) {
                        System.out.println("Compra realizada con éxito. ID de compra: " + compraId);
                    } else {
                        System.out.println("Compra cancelada. ID de compra: " + compraId);
                    }
                    break;

                case 4:
                    System.out.println("Muchas gracias por usar nuestro programa, vuelva pronto.");
                    System.exit(0);
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    static void menuAdmin(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        double totalCompras = 0;

        while (true) {
            System.out.println("\nElige una opción:");
            System.out.println("1. Agregar productos.");
            System.out.println("2. Eliminar productos.");
            System.out.println("3. Informe de ventas.");
            System.out.println("4. Salir.");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:

                    System.out.print("Ingrese el nombre del producto:");
                    String nombreProducto = scanner.nextLine();
                    System.out.print("Ingrese la descripción del producto:");
                    String descripcion = scanner.nextLine();
                    System.out.println();
                    System.out.print("Ingrese la categoria del producto:");
                    String categoria = scanner.nextLine();
                    System.out.print("Ingrese el precio del producto:");
                    double precio = scanner.nextDouble();
                    scanner.nextLine();

                    Producto nuevoProducto = new Producto(nombreProducto, descripcion, categoria, precio);
                    agregarProductos(nuevoProducto);
                    System.out.println("\nProducto agregado exitosamente.");
                    break;

                case 2:
                    System.out.print("Ingrese el nombre del producto a eliminar: ");
                    String productoAEliminar = scanner.nextLine();

                    File archivo = new File("compras.txt");
                    File archivoTemporal = new File("temp.txt");

                    try {
                        FileReader fr = new FileReader(archivo);
                        BufferedReader br = new BufferedReader(fr);

                        FileWriter fw = new FileWriter(archivoTemporal);
                        BufferedWriter bw = new BufferedWriter(fw);

                        String lineaActual;

                        while ((lineaActual = br.readLine()) != null) {


                            if (!lineaActual.contains(productoAEliminar)) {
                                bw.write(lineaActual);
                                bw.newLine();
                            }
                        }
                        bw.close();
                        br.close();
                        archivo.delete();
                        archivoTemporal.renameTo(archivo);

                        System.out.println("Producto eliminado exitosamente");

                    } catch (IOException e) {
                        System.out.println("Ocurrió un error al intentar eliminar el producto");
                        e.printStackTrace();
                    }
                    break;


                case 3:
                    verCompras();
                    System.out.println("¿Desea conocer el total definitivo de las compras?");
                    System.out.println("1. Si");
                    System.out.println("2. No");
                    int eleccionSuma = scanner.nextInt();
                    scanner.nextLine();

                    switch (eleccionSuma) {
                        case 1:
                            archivo = new File("compras.txt");


                            try (FileReader fr = new FileReader(archivo);
                                 BufferedReader br = new BufferedReader(fr)) {

                                String linea;

                                while ((linea = br.readLine()) != null) {
                                    String[] partes = linea.split(",");
                                    double precioTotal = Double.parseDouble(partes[4]);
                                    totalCompras += precioTotal;
                                }

                                System.out.println("El total de las compras es: $" + totalCompras);

                            } catch (IOException e) {
                                System.out.println("Error al leer el archivo " + archivo + ": " + e.getMessage());
                            }
                            break;

                        case 2:
                            System.out.println("Entendido.");
                            break;
                        default:
                            System.out.println("Opción inválida.");
                    }
                    break;


                case 4:
                    System.out.println("Muchas gracias por usar nuestro programa, vuelva pronto.");
                    System.exit(0);
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    public static void agregarProductos(Producto productos) {
        try {
            FileWriter archivo = new FileWriter("productos.txt", true);
            archivo.write(productos.getNombreProducto() + ", " + productos.getDescripcion() + ", "
                    + productos.getCategoria() + ", " + productos.getPrecio() + "\n");
            archivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void agregarCompraArchivo(UUID compraId, String nombreUsuario, String nombreProducto, int cantidad, double precioTotal) {
        try {
            FileWriter archivo = new FileWriter("compras.txt", true);
            archivo.write(compraId + "," + nombreUsuario + "," + nombreProducto + "," + cantidad + "," + precioTotal + "\n");
            archivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void verHistorial(String nombreUsuario) {
        File archivo = new File("compras.txt");

        try {
            Scanner scanner = new Scanner(archivo);

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();

                if (linea.contains(nombreUsuario)) {
                    String[] partes = linea.split(",");
                    String idCompra = partes[0];
                    String usuarioCompra = partes[1];
                    String nombreProducto = partes[2];
                    int cantidad = Integer.parseInt(partes[3]);
                    double precioTotal = Double.parseDouble(partes[4]);

                    System.out.println("ID de compra: " + idCompra + ", Usuario: " + usuarioCompra + ", Producto: " + nombreProducto +
                            ", Cantidad: " + cantidad + ", Precio total: " + precioTotal);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: No se encontró el archivo de compras.");
        }
    }

    static void verCompras() {
        try {
            FileReader fileReader = new FileReader("compras.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String linea;
            System.out.println("ID de Compra         \tUsuario        \tProducto        \tCantidad      \tTotal");
            while ((linea = bufferedReader.readLine()) != null) {
                System.out.println(linea);
            }

            bufferedReader.close();
            fileReader.close();

        } catch (IOException e) {
            System.out.println("No se puede leer el archivo.");
            e.printStackTrace();
        }
    }

}








