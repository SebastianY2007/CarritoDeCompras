package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase ProductoDAOArchivoTexto
 *
 * Implementación de la interfaz ProductoDAO para persistir los datos de productos
 * en archivos de texto plano. Cada producto se guarda en un archivo individual,
 * nombrado con el código del producto.
 *
 * @author Sebastian Yupangui
 * @version 1.1
 * @since 16/07/2025
 */
public class ProductoDAOArchivoTexto implements ProductoDAO {

    private String rutaBase;

    /**
     * Constructor de ProductoDAOArchivoTexto.
     *
     * Inicializa el DAO, crea el directorio si no existe y, si el directorio
     * está vacío, crea un conjunto de productos por defecto.
     *
     * @param rutaBase La ruta a la carpeta donde se guardarán los archivos de productos.
     */
    public ProductoDAOArchivoTexto(String rutaBase) {
        this.rutaBase = rutaBase;
        new File(this.rutaBase).mkdirs(); // Crea el directorio si no existe

        File directorio = new File(rutaBase);
        if (directorio.list() == null || directorio.list().length == 0) {
            crearProductosPorDefecto();
        }
    }

    /**
     * Crea un conjunto de productos de ejemplo si no existen.
     *
     * Este método se invoca una única vez cuando se utiliza una carpeta de
     * almacenamiento vacía, para asegurar que la aplicación siempre tenga
     * datos iniciales con los que trabajar.
     */
    private void crearProductosPorDefecto() {
        crear(new Producto(1, "Laptop Gamer", 1250.99));
        crear(new Producto(2, "Mouse Inalámbrico RGB", 45.50));
        crear(new Producto(3, "Teclado Mecánico", 89.90));
        crear(new Producto(4, "Monitor Curvo 27 pulgadas", 320.00));
        crear(new Producto(5, "Webcam 4K", 150.00));
    }

    /**
     * Crea un archivo de texto para un nuevo producto.
     *
     * Escribe cada atributo del producto en una nueva línea dentro del archivo.
     * @param producto El objeto Producto a persistir.
     */
    @Override
    public void crear(Producto producto) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaBase + File.separator + producto.getCodigo() + ".txt"))) {
            writer.println(producto.getCodigo());
            writer.println(producto.getNombre());
            writer.println(producto.getPrecio());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca un producto leyendo su archivo de texto correspondiente.
     * @param codigo El código del producto, que corresponde al nombre del archivo.
     * @return El objeto Producto reconstruido si se encuentra, de lo contrario null.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaBase + File.separator + codigo + ".txt"))) {
            int cod = Integer.parseInt(reader.readLine());
            String nombre = reader.readLine();
            double precio = Double.parseDouble(reader.readLine());
            return new Producto(cod, nombre, precio);
        } catch (IOException | NumberFormatException e) {
            return null; // El archivo no existe o está dañado
        }
    }

    /**
     * Actualiza un producto sobrescribiendo su archivo de texto.
     * @param producto El objeto Producto con los datos actualizados.
     */
    @Override
    public void actualizar(Producto producto) {
        crear(producto);
    }

    /**
     * Elimina el archivo de texto de un producto.
     * @param codigo El código del producto a eliminar.
     * @return true si el archivo fue eliminado, false en caso contrario.
     */
    @Override
    public boolean eliminar(int codigo) {
        File archivo = new File(rutaBase + File.separator + codigo + ".txt");
        return archivo.exists() && archivo.delete();
    }

    /**
     * Lista todos los productos leyendo todos los archivos .txt del directorio.
     * @return Una lista con todos los productos.
     */
    @Override
    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        File directorio = new File(rutaBase);
        File[] archivos = directorio.listFiles((dir, name) -> name.endsWith(".txt"));

        if (archivos != null) {
            for (File archivo : archivos) {
                try {
                    int codigo = Integer.parseInt(archivo.getName().replace(".txt", ""));
                    Producto producto = buscarPorCodigo(codigo);
                    if (producto != null) {
                        productos.add(producto);
                    }
                } catch (NumberFormatException e) {
                    // Ignorar archivos que no tengan un número como nombre
                }
            }
        }
        return productos;
    }

    /**
     * Busca productos cuyo nombre contenga una cadena de texto.
     * @param nombre El texto a buscar en el nombre de los productos.
     * @return Una lista de productos que coinciden con el criterio de búsqueda.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        return listarTodos().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Actualiza únicamente el nombre de un producto.
     * @param productoId El ID del producto a modificar.
     * @param nuevoValorStr El nuevo nombre para el producto.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    @Override
    public boolean actualizarNombre(int productoId, String nuevoValorStr) {
        Producto p = buscarPorCodigo(productoId);
        if (p != null) {
            p.setNombre(nuevoValorStr);
            actualizar(p);
            return true;
        }
        return false;
    }

    /**
     * Actualiza únicamente el código de un producto.
     * @param codigoOriginal El código actual del producto.
     * @param nuevoCodigo El nuevo código para el producto.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    @Override
    public boolean actualizarCodigo(int codigoOriginal, int nuevoCodigo) {
        Producto p = buscarPorCodigo(codigoOriginal);
        if (p != null) {
            eliminar(codigoOriginal);
            p.setCodigo(nuevoCodigo);
            crear(p);
            return true;
        }
        return false;
    }

    /**
     * Actualiza únicamente el precio de un producto.
     * @param productoId El ID del producto a modificar.
     * @param nuevoPrecio El nuevo precio para el producto.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    @Override
    public boolean actualizarPrecio(int productoId, double nuevoPrecio) {
        Producto p = buscarPorCodigo(productoId);
        if (p != null) {
            p.setPrecio(nuevoPrecio);
            actualizar(p);
            return true;
        }
        return false;
    }
}
