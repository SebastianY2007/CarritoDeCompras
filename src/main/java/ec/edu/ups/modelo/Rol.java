package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * Enumeración Rol
 *
 * Define los roles que un usuario puede tener dentro del sistema.
 * Esto permite un control de acceso basado en roles (RBAC) simple y efectivo.
 * Es serializable para poder guardarse junto con el objeto Usuario.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public enum Rol implements Serializable {
    /**
     * Rol de usuario estándar.
     * Típicamente tiene permisos para realizar compras y gestionar sus propios datos.
     */
    USUARIO,

    /**
     * Rol de administrador.
     * Típicamente tiene permisos para gestionar usuarios, productos y ver todos los carritos.
     */
    ADMINISTRADOR
}
