import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class filtros {

    public static Producto filtrarPorProducto(String producto) {
        try {
            File archivo = new File("productos.txt");
            Scanner scanner = new Scanner(archivo);

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(", ");
                String nombreProducto = partes[0];
                String descripcion = partes[1];
                String categoria = partes[2];
                double precio = Double.parseDouble(partes[3]);

                if (nombreProducto.contains(producto)) {
                    System.out.println("Nombre del producto: " + nombreProducto);
                    System.out.println("Descripción: " + descripcion);
                    System.out.println("Categoría: " + categoria);
                    System.out.println("Precio: " + precio);
                    System.out.println();
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void verProductosGuardados(String categoriaFiltro) {
        try {
            File archivo = new File("productos.txt");
            Scanner scanner = new Scanner(archivo);

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(", ");
                String nombreProducto = partes[0];
                String descripcion = partes[1];
                String categoria = partes[2];
                double precio = Double.parseDouble(partes[3]);

                if (categoriaFiltro.isEmpty() || categoriaFiltro.equalsIgnoreCase(categoria)) {
                    System.out.println("Nombre del producto: " + nombreProducto);
                    System.out.println("Descripción: " + descripcion);
                    System.out.println("Categoría: " + categoria);
                    System.out.println("Precio: " + precio);
                    System.out.println();
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void verProductosGuardados() {
        try {
            File archivo = new File("productos.txt");
            Scanner scanner = new Scanner(archivo);

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(", ");
                String nombreProducto = partes[0];
                String descripcion = partes[1];
                String categoria = partes[2];
                double precio = Double.parseDouble(partes[3]);

                System.out.println("Nombre del producto: " + nombreProducto);
                System.out.println("Descripción: " + descripcion);
                System.out.println("Categoría: " + categoria);
                System.out.println("Precio: " + precio);
                System.out.println();
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Producto buscarProducto(String productoComprar) {
        List<Producto> productosEncontrados = new ArrayList<>();
        try {
            File archivo = new File("productos.txt");
            Scanner scanner = new Scanner(archivo);

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(", ");
                String nombreProducto = partes[0];
                String descripcion = partes[1];
                String categoria = partes[2];
                double precio = Double.parseDouble(partes[3]);

                if (nombreProducto.contains(productoComprar)) {
                    Producto productoEncontrado = new Producto(nombreProducto, descripcion, categoria, precio);
                    productosEncontrados.add(productoEncontrado);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (!productosEncontrados.isEmpty()) {
            return productosEncontrados.get(0);
        } else {
            return null;
        }
    }

}
