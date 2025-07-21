package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase CarritoDAOArchivoBinario
 *
 * Implementación de la interfaz CarritoDAO para persistir los datos de carritos
 * en archivos binarios mediante serialización de objetos.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 16/07/2025
 */
public class CarritoDAOArchivoBinario implements CarritoDAO {

    private String rutaBase;

    /**
     * Constructor de CarritoDAOArchivoBinario.
     *
     * @param rutaBase La ruta a la carpeta donde se guardarán los archivos .dat de los carritos.
     */
    public CarritoDAOArchivoBinario(String rutaBase) {
        this.rutaBase = rutaBase;
        new File(this.rutaBase).mkdirs();
    }

    /**
     * Crea un archivo binario para un nuevo carrito.
     *
     * Asigna un código único si es necesario y serializa el objeto Carrito completo.
     * @param carrito El objeto Carrito a persistir.
     */
    @Override
    public void crear(Carrito carrito) {
        if (carrito.getCodigo() == 0) {
            carrito.setCodigo(obtenerSiguienteCodigo());
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaBase + File.separator + carrito.getCodigo() + ".dat"))) {
            oos.writeObject(carrito);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lee un carrito desde su archivo binario.
     * @param codigo El código del carrito a leer.
     * @return El objeto Carrito deserializado si se encuentra, de lo contrario null.
     */
    @Override
    public Carrito leer(int codigo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaBase + File.separator + codigo + ".dat"))) {
            return (Carrito) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Actualiza un carrito sobrescribiendo su archivo binario.
     * @param carrito El objeto Carrito con los datos actualizados.
     */
    @Override
    public void actualizar(Carrito carrito) {
        crear(carrito);
    }

    /**
     * Elimina el archivo binario de un carrito.
     * @param codigo El código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        new File(rutaBase + File.separator + codigo + ".dat").delete();
    }

    /**
     * Obtiene una lista de todos los carritos leyendo todos los archivos .dat.
     * @return una lista con todos los carritos.
     */
    @Override
    public List<Carrito> obtenerTodos() {
        List<Carrito> carritos = new ArrayList<>();
        File[] archivos = new File(rutaBase).listFiles((dir, name) -> name.endsWith(".dat"));
        if (archivos != null) {
            for (File archivo : archivos) {
                try {
                    int codigo = Integer.parseInt(archivo.getName().replace(".dat", ""));
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
     *
     * Examina los nombres de los archivos en el directorio para encontrar el
     * ID más alto y devuelve el siguiente número.
     *
     * @return el siguiente código secuencial.
     */
    private int obtenerSiguienteCodigo() {
        File dir = new File(rutaBase);
        File[] files = dir.listFiles();
        int maxId = 0;
        if (files != null) {
            for (File file : files) {
                try {
                    int id = Integer.parseInt(file.getName().replace(".dat", ""));
                    if (id > maxId) {
                        maxId = id;
                    }
                } catch (NumberFormatException e) { /* Ignorar */ }
            }
        }
        return maxId + 1;
    }
}
