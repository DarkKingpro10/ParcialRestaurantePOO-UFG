package Model;

import Utils.ResultadoOperacion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jesus Esquivel
 */
public class Restaurante {

    //Atributos de clase
    private static List<Producto> inventario = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<Pedido>();
    private static List<Plato> platos = new ArrayList<>();
    private static List<Promocion> promociones = new ArrayList<>();

    //GETTERS Y SETTERS
    public static List<Producto> getInventario() {
        return inventario;
    }

    public static void setInventario(List<Producto> inventario) {
        Restaurante.inventario = inventario;
    }

    public static List<Pedido> getPedidos() {
        return pedidos;
    }

    public static void setPedidos(List<Pedido> pedidos) {
        Restaurante.pedidos = pedidos;
    }

    public static List<Plato> getPlatos() {
        return platos;
    }

    public static void setPlatos(List<Plato> platos) {
        Restaurante.platos = platos;
    }

    public static List<Promocion> getPromociones() {
        return promociones;
    }

    public static void setPromociones(List<Promocion> promociones) {
        Restaurante.promociones = promociones;
    }

    //Métodos de las clases
    //Métodos para productos
    /**
     * Método para añadir un producto al inventario
     *
     * @param nombreProducto representa el nombre del producto a añadir
     * @param cantidadProducto representa la cantidad del producto a añadir
     * @return objeto de tipo {ResultadoOperacion}
     */
    public static ResultadoOperacion agregarProducto(String nombreProducto, int cantidadProducto) {
        //Validamos que el nombre del producto no exista
        try {
            int id = inventario.size() + 1;
            nombreProducto = nombreProducto.trim();
            Producto productoNuevo = new Producto(id, nombreProducto, cantidadProducto);

            //Validamos que él producto no exista
            if (inventario.contains(productoNuevo)) {
                return new ResultadoOperacion(false, "¡El producto ya se encuentra registrado, si desea añadir más cantidad de él modifiqulo!");
            }

            //Ya que el producto no se ha registrado, entonces lo añadiremos al arreglo
            inventario.add(productoNuevo);
            return new ResultadoOperacion(true, "¡Se agrego el producto con exito!");
        } catch (Exception e) {
            return new ResultadoOperacion(false, "¡Error al agregar producto!");
        }
    }

    /**
     * Método para actualizar un producto
     *
     * @param id representa el id y la posición más 1 en la lista
     * @param nombre representa el nombre del producto a modificar
     * @param cantidad representa la cantidad del producto a añadir
     * @return objeto de tipo {ResultadoOperacion}
     */
    public static ResultadoOperacion modificarProducto(int id, String nombre, int cantidad) {
        try {
            Producto productoAModificar = inventario.get(id - 1);

            //Validando si no ha sido eliminado
            if (productoAModificar.getCantidadProducto() == -1) {
                return new ResultadoOperacion(false, "¡El producto a modificar no existe!");
            }

            //Como el producto existe entonces modificaremos el producto
            //Validamos que él nombre del producto no exista
            nombre = nombre.trim();
            Producto pruebaNombre = new Producto(id, nombre, cantidad);
            if (inventario.contains(pruebaNombre)) {
                return new ResultadoOperacion(false, "¡El producto ya se encuentra registrado, si desea añadir más cantidad de él modifiqulo!");
            }

            productoAModificar.setNombreProducto(nombre);
            int cantidadActual = productoAModificar.getCantidadProducto();
            productoAModificar.setCantidadProducto(cantidad + cantidadActual);

            return new ResultadoOperacion(true, "¡El producto ha sido modificado con exito!");
        } catch (IndexOutOfBoundsException e) {
            return new ResultadoOperacion(false, """
                                                  ¡Error al actualizar el producto!
                                                  El id del producto no ha sido encontrado
                                                  """);
        } catch (Exception e) {
            return new ResultadoOperacion(false, "¡Error al actualizar el producto!");
        }
    }

    /**
     * Producto a eliminar
     *
     * @param id representa el producto a eliminar
     * @return objeto de tipo {ResultadoOperacion}
     */
    public static ResultadoOperacion eliminarProducto(int id) {
        try {
            Producto productoAEliminar = inventario.get(id - 1);

            //Validando si no ha sido eliminado
            if (productoAEliminar.getCantidadProducto() == -1) {
                return new ResultadoOperacion(false, "¡El producto a modificar no existe!");
            }

            //Como el producto existe entonces eliminaremos el producto
            productoAEliminar.setCantidadProducto(-1);
            return new ResultadoOperacion(true, "¡El producto ha sido eliminado con éxito!");
        } catch (IndexOutOfBoundsException e) {
            return new ResultadoOperacion(false, """
                                                  ¡Error al eliminar!
                                                  El id del producto no ha sido encontrado
                                                  """);
        } catch (Exception e) {
            return new ResultadoOperacion(false, "¡Error al eliminar el producto!");
        }
    }

    /**
     * Método para buscar un producto
     *
     * @param nombre representa el nombre del producto a buscar
     * @return objeto de tipo Producto
     */
    public static List<Producto> buscarProducto(String nombre) {
        //Recorremos la lista para buscar
        nombre = nombre.trim().toLowerCase();
        List<Producto> coincidencias = new ArrayList<>();
        for (Producto producto : inventario) {
            if (producto.getNombreProducto().toLowerCase().contains(nombre)) {
                coincidencias.add(producto);
            }
        }
        return coincidencias;
    }

    //Método para la clase Promociones
    //Métodos de las clases
    //Métodos para productos
    /**
     * Método para añadir una promocion
     *
     * @param nombre representa el nombre de la promoción a añadir
     * @param descripcion representa la descripcion de la promocion
     * @param plato representa el plato a añadir a la promocion
     * @param porcentajeDescuento representa el porcentaje de descuento de la
     * promocion
     * @param platosMinimos representa los platos minimos para que la promocion
     * sea valida
     * @return objeto de tipo {ResultadoOperacion}
     */
    public static ResultadoOperacion agregarPromocion(String nombre, String descripcion, Plato plato, int porcentajeDescuento, int platosMinimos) {
        //Validamos que el nombre del producto no exista
        try {
            int id = promociones.size() + 1;
            nombre = nombre.trim();
            descripcion = descripcion.trim();

            if (plato == null || !plato.isDisponibilidad()) {
                return new ResultadoOperacion(false, "Plato inexistente");
            }
            if (porcentajeDescuento <= 0) {
                return new ResultadoOperacion(false, "Porcentaje de descuento invalido debe ser mayor o igual a 1");
            }
            if (platosMinimos <= 0) {
                return new ResultadoOperacion(false, "Platos minimos invalido, debe ser mayor o igual a 1");
            }

            Promocion promocionNueva = new Promocion(id, nombre, descripcion, plato, porcentajeDescuento, platosMinimos);

            //Validamos que él producto no exista
            if (inventario.contains(promocionNueva)) {
                return new ResultadoOperacion(false, "¡La promoción ya se encuentra registrada!");
            }

            //Ya que el producto no se ha registrado, entonces lo añadiremos al arreglo
            promociones.add(promocionNueva);
            return new ResultadoOperacion(true, "¡Se agrego la promoción con exito!");
        } catch (Exception e) {
            return new ResultadoOperacion(false, "¡Error al agregar la promocióno!");
        }
    }

    /**
     * Método para actualizar una promocion
     *
     * @param id representa la posicion en la lista más 1
     * @param nombre representa el nombre de la promoción a modificar
     * @param descripcion representa la descripcion de la promocion
     * @param plato representa el plato a modificar a la promocion
     * @param porcentajeDescuento representa el porcentaje de descuento de la
     * promocion
     * @param platosMinimos representa los platos minimos para que la promocion
     * sea valida
     * @return objeto de tipo {ResultadoOperacion}
     */
    public static ResultadoOperacion modificarPromocion(int id, String nombre, String descripcion, Plato plato, int porcentajeDescuento, int platosMinimos) {
        try {
            Promocion promocionAModificar = promociones.get(id - 1);

            //Como la promocion existe entonces modificaremos
            //Validamos que la promocion no exista
            nombre = nombre.trim();
            descripcion = descripcion.trim();

            if (plato == null || !plato.isDisponibilidad()) {
                return new ResultadoOperacion(false, "Plato inexistente");
            }
            if (porcentajeDescuento <= 0) {
                return new ResultadoOperacion(false, "Porcentaje de descuento invalido debe ser mayor o igual a 1");
            }
            if (platosMinimos <= 0) {
                return new ResultadoOperacion(false, "Platos minimos invalido, debe ser mayor o igual a 1");
            }

            Promocion promocionPrueba = new Promocion(id, nombre, descripcion, plato, porcentajeDescuento, platosMinimos);

            //Validamos que él producto no exista
            if (inventario.contains(promocionPrueba)) {
                return new ResultadoOperacion(false, "¡La promoción ya se encuentra registrada!");
            }

            promocionAModificar.setNombrePromocion(nombre);
            promocionAModificar.setDescripcion(descripcion);
            promocionAModificar.setPlato(plato);
            promocionAModificar.setPorcentajeDescuento(porcentajeDescuento);
            promocionAModificar.setPlatosMinimos(platosMinimos);

            return new ResultadoOperacion(true, "¡La promocion ha sido modificada con exito!");
        } catch (IndexOutOfBoundsException e) {
            return new ResultadoOperacion(false, """
                                                  ¡Error al actualizar la promoción!
                                                  El id de la promoción no ha sido encontrado
                                                  """);
        } catch (Exception e) {
            return new ResultadoOperacion(false, "¡Error al actualizar la promoción!");
        }
    }

    /**
     * Promocion a eliminar
     *
     * @param id representa la promoción a eliminar
     * @return objeto de tipo {ResultadoOperacion}
     */
    public static ResultadoOperacion eliminarPromocion(int id) {
        try {
            promociones.remove(id);
            return new ResultadoOperacion(true, "¡La promoción ha sido eliminada con éxito!");
        } catch (IndexOutOfBoundsException e) {
            return new ResultadoOperacion(false, """
                                                  ¡Error al eliminar!
                                                  El id del producto no ha sido encontrado
                                                  """);
        } catch (Exception e) {
            return new ResultadoOperacion(false, "¡Error al eliminar la promocion!");
        }
    }

    /**
     * Método para buscar una promocion
     *
     * @param query representa la coincidencia a buscar
     * @return objeto de tipo Producto
     */
    public static List<Promocion> buscarPromociones(String query) {
        //Recorremos la lista para buscar
        query = query.trim().toLowerCase();
        List<Promocion> coincidencias = new ArrayList<>();
        
        for (Promocion promocion : promociones) {
            if (promocion.getNombrePromocion().toLowerCase().contains(query)) {
                coincidencias.add(promocion);
            }else if(promocion.getDescripcion().toLowerCase().contains(query)){
                coincidencias.add(promocion);
            } else if(promocion.getPlato().getNombrePlato().toLowerCase().contains(query)){
                coincidencias.add(promocion);
            }
        }
        
        return coincidencias;
    }
}
