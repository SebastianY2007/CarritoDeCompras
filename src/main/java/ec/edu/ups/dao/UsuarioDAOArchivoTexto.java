package ec.edu.ups.dao;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class UsuarioDAOArchivoTexto implements UsuarioDAO{
    private String path = "/mi/ruta/usuario.txt";

    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        return null;
    }

    @Override
    public void crear(Usuario usuario) {

    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return null;
    }

    @Override
    public void eliminar(String username) {

    }

    @Override
    public void actualizar(Usuario usuario) {

    }

    @Override
    public List<Usuario> listarTodos() {
        return List.of();
    }

    @Override
    public List<Usuario> listarPorRol() {
        return List.of();
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        return List.of();
    }
}
