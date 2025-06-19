package ec.edu.ups.poo.vista;

import ec.edu.ups.poo.controller.ProductoController;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.poo.modelo.Producto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PrincipalView principalView = new PrincipalView();
                ProductoDAO productoDAO = new ProductoDAOMemoria() {
                    @Override
                    public Producto buscarPorCodigo(int codigo) {
                        return null;
                    }

                    @Override
                    public void eliminar(int codigo) {

                    }
                };
                ProductoController productoController = new ProductoController(productoDAO);

                principalView.getMenuItemCrearProducto().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                        productoController.setProductoAnadirView(productoAnadirView);
                        principalView.getjDesktopPane().add(productoAnadirView);
                    }
                });

                principalView.getMenuItemEliminarProducto().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });

            }
        });
    }
}