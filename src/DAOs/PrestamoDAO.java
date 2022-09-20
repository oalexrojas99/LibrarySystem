
package DAOs;

import entidades.Libro;
import entidades.Prestamo;
import excepcionesPropias.ExcepcionPropia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import jdbc.ConexionBD;
import listasPropias.ListaLibros;
import listasPropias.ListaPrestamos;

public class PrestamoDAO {
    
    Connection conn;
    
    public PrestamoDAO(){
        /*Se crea la conexión con la BD cuando se realiza una instancia de la clase PrestamoDAO*/
        conn = ConexionBD.conectarMySQL();
    }
    
    public void registrar(Prestamo objPrestamo) throws ExcepcionPropia, SQLException{
        
        //Estableciendo la sentencia de inserción SQL
        String sql = "INSERT INTO prestamo (idestudiante, idlibro, fechaprestamo, numdiasprestamo) VALUES (?, ?, CURDATE(), ?);";
        PreparedStatement pstm_insert = conn.prepareStatement(sql);
        
        //Asignando parámetros de la sentencia
        pstm_insert.setInt(1, objPrestamo.getIdestudiante());
        pstm_insert.setInt(2, objPrestamo.getIdlibro());
        pstm_insert.setInt(3, objPrestamo.getNum_dias_prestamo());
        
        //Manipulando, finalmente, la base de datos
        pstm_insert.executeUpdate();
    }
    
    public void modificarEstadoPrestamo(int idPrestamo, int estado) throws SQLException{
        //Estableciendo la sentencia de inserción SQL
        String sql = "UPDATE prestamo SET estado = ? WHERE idprestamo = ?;";
        PreparedStatement pstm_insert = conn.prepareStatement(sql);
        
        //Asignando parámetros de la sentencia
        pstm_insert.setInt(1, estado);
        pstm_insert.setInt(2, idPrestamo);
        
        //Manipulando, finalmente, la base de datos
        pstm_insert.executeUpdate();
    }
    
    public ListaPrestamos obtenerDetallePrestamos() throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        
        Statement stm = conn.createStatement();
        String sql = "SELECT p.idprestamo, CONCAT(e.nombres, ' ', e.apellidopaterno, ' ', e.apellidomaterno), l.titulo, l.numejemplar, " +
                        "p.fechaprestamo, DATE_ADD(p.fechaprestamo, INTERVAL p.numdiasprestamo DAY) FROM prestamo p " +
                        "INNER JOIN  estudiante e ON p.idestudiante = e.idestudiante " +
                        "INNER JOIN  libro l ON p.idlibro = l.idlibro;";
        
        //Recuperando clientes
        ResultSet rs = stm.executeQuery(sql);
        Prestamo objPrestamo;
        ListaPrestamos listaPrestamos = new ListaPrestamos();
        
        while(rs.next()){
            objPrestamo = new Prestamo();
            
            objPrestamo.setId(Integer.parseInt(rs.getString(1)));
            objPrestamo.setNombres_estudiante(rs.getString(2));
            objPrestamo.setTitulo_libro(rs.getString(3));
            objPrestamo.setNumejemplar_libro(rs.getInt(4));
            objPrestamo.setFechaPrestamo(formatearFecha(rs.getString(5)));
            objPrestamo.setFechaFinalizacionPrestamo(formatearFecha(rs.getString(6)));
            
            listaPrestamos.agregarElementoSgte(objPrestamo);
        }
        
        return listaPrestamos;
    }
    
    public ListaPrestamos obtenerPrestamosXIdEstudiante(int idEstudiante) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT p.idprestamo, CONCAT(e.nombres, ' ', e.apellidopaterno, ' ', e.apellidomaterno), l.titulo, l.numejemplar, " +
                        "p.fechaprestamo, DATE_ADD(p.fechaprestamo, INTERVAL p.numdiasprestamo DAY) FROM prestamo p " +
                        "INNER JOIN  estudiante e ON p.idestudiante = e.idestudiante " +
                        "INNER JOIN  libro l ON p.idlibro = l.idlibro WHERE p.idestudiante = ?;";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, idEstudiante);
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ListaPrestamos prestamos = new ListaPrestamos();
        Prestamo objPrestamo;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objPrestamo = new Prestamo();
            objPrestamo.setId(Integer.parseInt(rs.getString(1)));
            objPrestamo.setNombres_estudiante(rs.getString(2));
            objPrestamo.setTitulo_libro(rs.getString(3));
            objPrestamo.setNumejemplar_libro(rs.getInt(4));
            objPrestamo.setFechaPrestamo(formatearFecha(rs.getString(5)));
            objPrestamo.setFechaFinalizacionPrestamo(formatearFecha(rs.getString(6)));
            
            prestamos.agregarElementoSgte(objPrestamo);
        }
        
        
        return prestamos;
    }
    
    public ListaPrestamos obtenerPrestamosXIdlibro(String titulo) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT p.idprestamo, CONCAT(e.nombres, ' ', e.apellidopaterno, ' ', e.apellidomaterno), l.titulo, l.numejemplar, " +
                        "p.fechaprestamo, DATE_ADD(p.fechaprestamo, INTERVAL p.numdiasprestamo DAY) FROM prestamo p " +
                        "INNER JOIN  estudiante e ON p.idestudiante = e.idestudiante " +
                        "INNER JOIN  libro l ON p.idlibro = l.idlibro WHERE l.titulo = ?;";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, titulo);
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ListaPrestamos prestamos = new ListaPrestamos();
        Prestamo objPrestamo;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objPrestamo = new Prestamo();
            objPrestamo.setId(Integer.parseInt(rs.getString(1)));
            objPrestamo.setNombres_estudiante(rs.getString(2));
            objPrestamo.setTitulo_libro(rs.getString(3));
            objPrestamo.setNumejemplar_libro(rs.getInt(4));
            objPrestamo.setFechaPrestamo(formatearFecha(rs.getString(5)));
            objPrestamo.setFechaFinalizacionPrestamo(formatearFecha(rs.getString(6)));
            
            prestamos.agregarElementoSgte(objPrestamo);
        }
        
        
        return prestamos;
    }
    
    public ListaPrestamos obtenerPrestamosXEstado(int estado) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT p.idprestamo, CONCAT(e.nombres, ' ', e.apellidopaterno, ' ', e.apellidomaterno), l.titulo, l.numejemplar, " +
                        "p.fechaprestamo, DATE_ADD(p.fechaprestamo, INTERVAL p.numdiasprestamo DAY) FROM prestamo p " +
                        "INNER JOIN  estudiante e ON p.idestudiante = e.idestudiante " +
                        "INNER JOIN  libro l ON p.idlibro = l.idlibro WHERE p.estado = ?;";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, estado);
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ListaPrestamos prestamos = new ListaPrestamos();
        Prestamo objPrestamo;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objPrestamo = new Prestamo();
            objPrestamo.setId(Integer.parseInt(rs.getString(1)));
            objPrestamo.setNombres_estudiante(rs.getString(2));
            objPrestamo.setTitulo_libro(rs.getString(3));
            objPrestamo.setNumejemplar_libro(rs.getInt(4));
            objPrestamo.setFechaPrestamo(formatearFecha(rs.getString(5)));
            objPrestamo.setFechaFinalizacionPrestamo(formatearFecha(rs.getString(6)));
            
            prestamos.agregarElementoSgte(objPrestamo);
        }
        
        
        return prestamos;
    }
    
    public ListaPrestamos obtenerPrestamosFueraDePlazo() throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT p.idprestamo, CONCAT(e.nombres, ' ', e.apellidopaterno, ' ', e.apellidomaterno), l.titulo, l.numejemplar, " +
                        "p.fechaprestamo, DATE_ADD(p.fechaprestamo, INTERVAL p.numdiasprestamo DAY) FROM prestamo p " +
                        "INNER JOIN  estudiante e ON p.idestudiante = e.idestudiante " +
                        "INNER JOIN  libro l ON p.idlibro = l.idlibro WHERE DATE_ADD(p.fechaprestamo, INTERVAL p.numdiasprestamo DAY) < CURDATE() AND p.estado = 1;";
        PreparedStatement pstm = conn.prepareStatement(sql);
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ListaPrestamos prestamos = new ListaPrestamos();
        Prestamo objPrestamo;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objPrestamo = new Prestamo();
            objPrestamo.setId(Integer.parseInt(rs.getString(1)));
            objPrestamo.setNombres_estudiante(rs.getString(2));
            objPrestamo.setTitulo_libro(rs.getString(3));
            objPrestamo.setNumejemplar_libro(rs.getInt(4));
            objPrestamo.setFechaPrestamo(formatearFecha(rs.getString(5)));
            objPrestamo.setFechaFinalizacionPrestamo(formatearFecha(rs.getString(6)));
            
            prestamos.agregarElementoSgte(objPrestamo);
        }
        
        return prestamos;
    }
    
    public ListaPrestamos obtenerPrestamosXEstadoYEstudiante(int estado, int idEstudiante) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT p.idprestamo, CONCAT(e.nombres, ' ', e.apellidopaterno, ' ', e.apellidomaterno), l.titulo, l.numejemplar, " +
                        "p.fechaprestamo, DATE_ADD(p.fechaprestamo, INTERVAL p.numdiasprestamo DAY) FROM prestamo p " +
                        "INNER JOIN  estudiante e ON p.idestudiante = e.idestudiante " +
                        "INNER JOIN  libro l ON p.idlibro = l.idlibro WHERE p.estado = ? AND p.idestudiante = ?;";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, estado);
        pstm.setInt(2, idEstudiante);
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ListaPrestamos prestamos = new ListaPrestamos();
        Prestamo objPrestamo;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objPrestamo = new Prestamo();
            objPrestamo.setId(Integer.parseInt(rs.getString(1)));
            objPrestamo.setNombres_estudiante(rs.getString(2));
            objPrestamo.setTitulo_libro(rs.getString(3));
            objPrestamo.setNumejemplar_libro(rs.getInt(4));
            objPrestamo.setFechaPrestamo(formatearFecha(rs.getString(5)));
            objPrestamo.setFechaFinalizacionPrestamo(formatearFecha(rs.getString(6)));
            
            prestamos.agregarElementoSgte(objPrestamo);
        }
        
        
        return prestamos;
    }
    
    public GregorianCalendar formatearFecha(String fecha){
        //Recibe AAAA-MM-DD
        
        GregorianCalendar formato_simplificado;
        
        String partes_fecha[] = fecha.split("-");
        
        int anio = Integer.parseInt(partes_fecha[0]);
        int mes = Integer.parseInt(partes_fecha[1]);
        int dia = Integer.parseInt(partes_fecha[2]);
        
        formato_simplificado = new GregorianCalendar(anio, mes, dia);
        
        return formato_simplificado;
    }
    
    public int generarNuevoCodigo() throws SQLException{
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT MAX(idprestamo) FROM prestamo";
        
        /*Ejecutando la consulta y los datos seleccionados se almacenan en ResultSet*/
        ResultSet rs = stm.executeQuery(sql);
        int codigoAutogenerado = 0;
        
        while(rs.next()){
            if(rs.getString(1) == null){
                codigoAutogenerado = 0;
            }else{
                codigoAutogenerado = Integer.parseInt(rs.getString(1));
            }
        }
        
        return codigoAutogenerado + 1;
    }

}
