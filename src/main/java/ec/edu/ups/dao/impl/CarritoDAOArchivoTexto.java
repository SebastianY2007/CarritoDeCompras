package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.FabricaDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase CarritoDAOArchivoTexto
 *
 * Implementación de la interfaz CarritoDAO para persistir los datos de carritos
 * en archivos de texto plano. Guarda los IDs de los objetos relacionados para
 * poder reconstruirlos durante la lectura.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 16/07/2025
 */
public class CarritoDAOArchivoTexto implements CarritoDAO {

    private String rutaBase;
    private UsuarioDAO usuarioDAO;
    private ProductoDAO productoDAO;

    /**
     * Constructor de CarritoDAOArchivoTexto.
     *
     * @param rutaBase La ruta a la carpeta donde se guardarán los archivos de carritos.
     * @param fabrica La fábrica de DAOs para obtener acceso a otros DAOs (UsuarioDAO, ProductoDAO).
     */
    public CarritoDAOArchivoTexto(String rutaBase, FabricaDAO fabrica) {
        this.rutaBase = rutaBase;
        // Se obtienen las instancias de otros DAOs a través de la fábrica
        // para poder reconstruir los objetos Carrito completos.
        this.usuarioDAO = fabrica.getUsuarioDAO();
        this.productoDAO = fabrica.getProductoDAO();
        new File(this.rutaBase).mkdirs();
    }

    /**
     * Crea un archivo de texto para un nuevo carrito.
     *
     * Guarda la cédula del usuario en la primera línea, y luego cada ítem
     * del carrito en una nueva línea con el formato "codigoProducto,cantidad".
     * @param carrito El objeto Carrito a persistir.
     */
    @Override
    public void crear(Carrito carrito) {
        if (carrito.getCodigo() == 0) {
            carrito.setCodigo(obtenerSiguienteCodigo());
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaBase + File.separator + carrito.getCodigo() + ".txt"))) {
            writer.println("Propietario: " + carrito.getUsuario().getCedula());
            for (ItemCarrito item : carrito.getItems()) {
                writer.println("Código del producto: " + item.getProducto().getCodigo() + ", Unidades: " + item.getCantidad());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lee un carrito desde su archivo de texto y reconstruye el objeto.
     *
     * Lee los IDs del archivo y utiliza los otros DAOs para obtener los
     * objetos Usuario y Producto completos.
     * @param codigo El código del carrito a leer.
     * @return El objeto Carrito reconstruido, o null si no se encuentra o hay un error.
     */
    @Override
    public Carrito leer(int codigo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaBase + File.separator + codigo + ".txt"))) {
            String cedulaUsuario = reader.readLine();
            Usuario usuario = usuarioDAO.buscarPorCedula(cedulaUsuario);
            if (usuario == null) {
                return null; // Si el usuario no existe, el carrito es inválido
            }

            Carrito carrito = new Carrito(codigo, usuario);
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                int codigoProducto = Integer.parseInt(partes[0]);
                int cantidad = Integer.parseInt(partes[1]);

                Producto producto = productoDAO.buscarPorCodigo(codigoProducto);
                if (producto != null) {
                    carrito.agregarItem(producto, cantidad);
                }
            }
            return carrito;
        } catch (IOException | NumberFormatException e) {
            return null;
        }
    }

    /**
     * Actualiza un carrito sobrescribiendo su archivo.
     * @param carrito El objeto Carrito con los datos actualizados.
     */
    @Override
    public void actualizar(Carrito carrito) {
        crear(carrito);
    }

    /**
     * Elimina el archivo de texto de un carrito.
     * @param codigo El código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        new File(rutaBase + File.separator + codigo + ".txt").delete();
    }

    /**
     * Obtiene una lista de todos los carritos leyendo todos los archivos .txt.
     * @return una lista con todos los carritos.
     */
    @Override
    public List<Carrito> obtenerTodos() {
        List<Carrito> carritos = new ArrayList<>();
        File[] archivos = new File(rutaBase).listFiles((dir, name) -> name.endsWith(".txt"));
        if (archivos != null) {
            for (File archivo : archivos) {
                try {
                    int codigo = Integer.parseInt(archivo.getName().replace(".txt", ""));
                    Carrito carrito = leer(codigo);
                    if (carrito != null) {
                        carritos.add(carrito);
                    }
                } catch (NumberFormatException e) {
                    // Ignorar archivos con nombres no numéricos
                }
            }
        }
        return carritos;
    }

    /**
     * Busca todos los carritos que pertenecen a un usuario específico.
     * @param usuario El usuario propietario de los carritos.
     * @return una lista de carritos del usuario.
     */
    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        return obtenerTodos().stream()
                .filter(c -> c.getUsuario().getCedula().equals(usuario.getCedula()))
                .collect(Collectors.toList());
    }

    /**
     * Calcula el siguiente código disponible para un nuevo carrito.
     * @return el siguiente código secuencial.
     */
    private int obtenerSiguienteCodigo() {
        File dir = new File(rutaBase);
        File[] files = dir.listFiles();
        int maxId = 0;
        if (files != null) {
            for (File file : files) {
                try {
                    int id = Integer.parseInt(file.getName().replace(".txt", ""));
                    if (id > maxId) {
                        maxId = id;
                    }
                } catch (NumberFormatException e) { /* Ignorar */ }
            }
        }
        return maxId + 1;
    }
}
