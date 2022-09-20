
package DAOs;

import entidades.Estudiante;
import entidades.Tema;
import excepcionesPropias.ExcepcionPropia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jdbc.ConexionBD;
import listasPropias.ListaEstudiantes;

public class EstudianteDAO {
    
    Connection conn;
    
    public EstudianteDAO(){
        /*Se crea la conexión con la BD cuando se realiza una instancia de la clase EsudianteDAO*/
        conn = ConexionBD.conectarMySQL();
    }
    
    public void registrar(Estudiante objEstudiante) throws ExcepcionPropia, SQLException{
       
        //Validar la no repitencia de algún otro nombre y apellido de Estudiante
        ListaEstudiantes estudiantes;
        estudiantes = obtenerEstudiantes();
        
        for (int i = 0; i < estudiantes.tamanio(); i++) {
            if(estudiantes.obtenerEstudiante(i).getNombres().equalsIgnoreCase(objEstudiante.getNombres())){
                if(estudiantes.obtenerEstudiante(i).getApellidoPaterno().equalsIgnoreCase(objEstudiante.getApellidoPaterno())
                    && estudiantes.obtenerEstudiante(i).getApellidoMaterno().equalsIgnoreCase(objEstudiante.getApellidoMaterno())){
                    throw new ExcepcionPropia("Nombres y apellidos ya registrados en el sistema.");
                }
            }
        }
        
        //Estableciendo la sentencia de inserción SQL
        String sql = "INSERT INTO estudiante (nombres, apellidopaterno, apellidomaterno) VALUES (?, ?, ?);";
        PreparedStatement pstm_insert = conn.prepareStatement(sql);
        
        //Asignando parámetros de la sentencia
        pstm_insert.setString(1, objEstudiante.getNombres());
        pstm_insert.setString(2, objEstudiante.getApellidoPaterno());
        pstm_insert.setString(3, objEstudiante.getApellidoMaterno());
        
        //Manipulando, finalmente, la base de datos
        pstm_insert.executeUpdate();
    }

    public void actualizar(Estudiante objEstudiante) throws ExcepcionPropia, SQLException{
        
        /*Validar la no repitencia de los nombre y apellidos del estudiante a excepción 
        del mismo objeto*/
        ListaEstudiantes estudiantes = new ListaEstudiantes();
        estudiantes = obtenerEstudiantes();
        
        //Bucle para comparar con todos los datos de Materia
        for (int i = 0; i < estudiantes.tamanio(); i++) {
            if(estudiantes.obtenerEstudiante(i).getNombres().equalsIgnoreCase(objEstudiante.getNombres())
                    && estudiantes.obtenerEstudiante(i).getId() != objEstudiante.getId()){
                if(estudiantes.obtenerEstudiante(i).getApellidoPaterno().equalsIgnoreCase(objEstudiante.getApellidoPaterno())
                    && estudiantes.obtenerEstudiante(i).getApellidoMaterno().equalsIgnoreCase(objEstudiante.getApellidoMaterno())){
                    throw new ExcepcionPropia("Ya existen estos nombres y apellidos registrados en el sistema.");
                }
            }
        }
        
        //Estableciendo la sentencia SQL para la actualización y asignando parámetros
        String sql_update = "UPDATE estudiante SET nombres = ?, apellidopaterno = ?, apellidomaterno = ?, estado = ?  WHERE idestudiante = ?";
        PreparedStatement pstm_update = conn.prepareStatement(sql_update);
        
        pstm_update.setString(1, objEstudiante.getNombres());
        pstm_update.setString(2, objEstudiante.getApellidoPaterno());
        pstm_update.setString(3, objEstudiante.getApellidoMaterno());
        
        if(objEstudiante.getEstado().equals("Activo")){
            pstm_update.setInt(4, 1);
        } else{
            pstm_update.setInt(4, 0);
        }
        
        pstm_update.setInt(5, objEstudiante.getId());
        
        //Manipulación en la BD
        pstm_update.executeUpdate();
    }
    
    public void eliminar(Estudiante objEstudiante) throws SQLException, Exception{
        
        //Estableciendo la sentencia SQL para consulta y asignando el parámetro necesario
        String sql_delete = "DELETE FROM estudiante WHERE idestudiante = ?";
        PreparedStatement pstm = conn.prepareStatement(sql_delete);
        pstm.setInt(1, objEstudiante.getId());
        
        //Manipulando datos en la base de datos
        pstm.executeUpdate();
    }
    
    public ListaEstudiantes obtenerEstudiantes() throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT * FROM estudiante";
        
         /*Ejecutando la consulta y los datos seleccionados se almacenarán en ResultSet*/
        ResultSet rs = stm.executeQuery(sql);
        Estudiante objEstudiante;
        ListaEstudiantes listaEstudiantes = new ListaEstudiantes();
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Estudiante. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objEstudiante = new Estudiante();
            objEstudiante.setId(Integer.parseInt(rs.getString(1)));
            objEstudiante.setNombres(rs.getString(2));
            objEstudiante.setApellidoPaterno(rs.getString(3));
            objEstudiante.setApellidoMaterno(rs.getString(4));
            objEstudiante.setEstado(Integer.parseInt(rs.getString(5)));
            
            listaEstudiantes.agregarElementoSgte(objEstudiante);
        }
        
        return listaEstudiantes;
    }
    
    public ListaEstudiantes obtenerEstudiantesActivos() throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT * FROM estudiante where estado = 1";
        
         /*Ejecutando la consulta y los datos seleccionados se almacenarán en ResultSet*/
        ResultSet rs = stm.executeQuery(sql);
        Estudiante objEstudiante;
        ListaEstudiantes listaEstudiantes = new ListaEstudiantes();
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Estudiante. Posteriormente
        el objeto lo almacenamos en la estructuraList*/
        while(rs.next()){
            objEstudiante = new Estudiante();
            objEstudiante.setId(Integer.parseInt(rs.getString(1)));
            objEstudiante.setNombres(rs.getString(2));
            objEstudiante.setApellidoPaterno(rs.getString(3));
            objEstudiante.setApellidoMaterno(rs.getString(4));
            objEstudiante.setEstado(Integer.parseInt(rs.getString(5)));
            
            listaEstudiantes.agregarElementoSgte(objEstudiante);
        }
        
        return listaEstudiantes;
    }
    
    /*Método para listar todos los datos de estudiantes según los caracteres contenidos en la
        columna 'apellidopaterno' y 'apellidopaterno' de la tabla Estudiante de la base de datos*/
    public ListaEstudiantes obtenerEstudiantesXNombresApellidos(String cadena_contenido) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT * FROM estudiante WHERE nombres LIKE ? OR apellidopaterno LIKE ? OR apellidomaterno LIKE ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, "%" + cadena_contenido + "%");
        pstm.setString(2, "%" + cadena_contenido + "%");
        pstm.setString(3, "%" + cadena_contenido + "%");
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ListaEstudiantes estudiantes = new ListaEstudiantes();
        Estudiante objEstudiante;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Estudiante. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objEstudiante = new Estudiante();
            objEstudiante.setId(Integer.parseInt(rs.getString(1)));
            objEstudiante.setNombres(rs.getString(2));
            objEstudiante.setApellidoPaterno(rs.getString(3));
            objEstudiante.setApellidoMaterno(rs.getString(4));
            objEstudiante.setEstado(Integer.parseInt(rs.getString(5)));
            estudiantes.agregarElementoSgte(objEstudiante);
        }
        
        return estudiantes;
    }
    
    /*Ordenamiento de tipo QuickSort para ordenar los elementos de la lista según el nombre
    del estudiante de forma ascendente.*/
    public void ordenarEstudiantesXNombreASC(List<Estudiante> listaEstudiantes, int izq, int der){
        
        Estudiante pivote = listaEstudiantes.get(izq); // tomamos primer elemento como pivote
        int i = izq;         // i realiza la búsqueda de izquierda a derecha
        int j = der;         // j realiza la búsqueda de derecha a izquierda
        Estudiante aux;

        while(i < j){                          // mientras no se crucen las búsquedas                                   
           while(listaEstudiantes.get(i).getNombres().compareToIgnoreCase(pivote.getNombres()) <= 0 && i < j) i++; // busca elemento mayor que pivote
           while(listaEstudiantes.get(j).getNombres().compareToIgnoreCase(pivote.getNombres()) > 0) j--;           // busca elemento menor que pivote
           if (i < j) {                        // si no se han cruzado                      
               aux = listaEstudiantes.get(i);                      // los intercambia
               listaEstudiantes.set(i, listaEstudiantes.get(j));
               listaEstudiantes.set(j, aux);
           }
        }
   
        listaEstudiantes.set(izq, listaEstudiantes.get(j));      // se coloca el pivote en su lugar de forma que tendremos                                    
        listaEstudiantes.set(j, pivote);     // los menores a su izquierda y los mayores a su derecha

        if(izq < j - 1){
            ordenarEstudiantesXNombreASC(listaEstudiantes, izq, j - 1); // ordenamos subarray izquierdo
        }
                     
        if(j + 1 < der){
            ordenarEstudiantesXNombreASC(listaEstudiantes, j + 1, der); // ordenamos subarray derecho
        }
    }
    
    public void ordenarEstudiantesXNombreDESC(List<Estudiante> listaEstudiantes){
        ordenarEstudiantesXNombreASC(listaEstudiantes, 0, listaEstudiantes.size() - 1);
        Collections.reverse(listaEstudiantes);
    }
    
    /*Ordenamiento de tipo QuickSort para ordenar los elementos de la lista según los apellidos
    del estudiante de forma ascendente.*/
    public void ordenarEstudiantesXApellidosASC(List<Estudiante> listaEstudiantes, int izq, int der){
        
        Estudiante pivote = listaEstudiantes.get(izq); // tomamos primer elemento como pivote
        int i = izq;         // i realiza la búsqueda de izquierda a derecha
        int j = der;         // j realiza la búsqueda de derecha a izquierda
        Estudiante aux;
        
        while(i < j){                          // mientras no se crucen las búsquedas 
           
           while((listaEstudiantes.get(i).getApellidoPaterno() + " " + listaEstudiantes.get(i).getApellidoMaterno()).compareToIgnoreCase(pivote.getApellidoPaterno() + " " + pivote.getApellidoMaterno()) <= 0 && i < j) i++; // busca elemento mayor que pivote
           while((listaEstudiantes.get(j).getApellidoPaterno() + " " + listaEstudiantes.get(j).getApellidoMaterno()).compareToIgnoreCase(pivote.getApellidoPaterno() + " " + pivote.getApellidoMaterno()) > 0) j--;           // busca elemento menor que pivote
           if (i < j) {                        // si no se han cruzado                      
               aux = listaEstudiantes.get(i);                      // los intercambia
               listaEstudiantes.set(i, listaEstudiantes.get(j));
               listaEstudiantes.set(j, aux);
           }
        }
   
        listaEstudiantes.set(izq, listaEstudiantes.get(j));      // se coloca el pivote en su lugar de forma que tendremos                                    
        listaEstudiantes.set(j, pivote);     // los menores a su izquierda y los mayores a su derecha

        if(izq < j - 1){
            ordenarEstudiantesXNombreASC(listaEstudiantes, izq, j - 1); // ordenamos subarray izquierdo
        }
                     
        if(j + 1 < der){
            ordenarEstudiantesXNombreASC(listaEstudiantes, j + 1, der); // ordenamos subarray derecho
        }
    }
    
    public void ordenarEstudiantesXApellidosDESC(List<Estudiante> listaEstudiantes){
        ordenarEstudiantesXApellidosASC(listaEstudiantes, 0, listaEstudiantes.size() - 1);
        Collections.reverse(listaEstudiantes);
    }
    
    /*Método para mostrar cuál será el nuevo código que tendrá el estudiante
    cuando se desea registrarlo en el sistema.*/
    public int generarNuevoCodigo() throws SQLException{
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT MAX(idestudiante) FROM estudiante";
        
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
