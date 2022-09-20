
package DAOs;

import entidades.Tema;
import excepcionesPropias.ExcepcionPropia;
import java.io.Serializable;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.ConexionBD;
import listasPropias.ListaTemas;

public class TemaDAO {
    
    Connection conn;
    
    public TemaDAO(){
        
        /*Se crea la conexión con la BD cuando se realiza una instancia de la clase TemaDAO*/
        conn = ConexionBD.conectarMySQL();
    }
    
    public void registrar(Tema objTema) throws ExcepcionPropia, SQLException{
        
        //Validar la no repitencia de algun mismo nombre de Tema
        //List<Tema> materias = new ArrayList<>();
        ListaTemas temas;
        temas = obtenerTodosTemas();
        
        for (int i = 0; i < temas.tamanio(); i++) {
            if(temas.obtenerTema(i).getDescripcion().equalsIgnoreCase(objTema.getDescripcion())){
                throw new ExcepcionPropia("Ya existe esta descripción de tema en el sistema");
            }
        }
        
        //Estableciendo la sentencia de inserción SQL
        String sql = "INSERT INTO materia (descripcion) VALUES (?);";
        PreparedStatement pstm_insert = conn.prepareStatement(sql);
        
        //Asignando parámetros a la sentencia
        pstm_insert.setString(1, objTema.getDescripcion());
        
        //Manipulando, finalmente, la base de datos
        pstm_insert.executeUpdate();
        
        /*CREAR NUEVO VERTICE*/
    }

    public void actualizar(Tema objMateria) throws ExcepcionPropia, SQLException{
        
        //Validar la no repitencia de nombre de descripción de materia a excepción del mismo objeto
        //List<Tema> materias = new ArrayList<>();
        ListaTemas temas;
        temas = obtenerTodosTemas();
        
        //Bucle para comparar con todos los datos de Tema
        for (int i = 0; i < temas.tamanio(); i++) {
            if(temas.obtenerTema(i).getId() != objMateria.getId()
                    && temas.obtenerTema(i).getDescripcion().equalsIgnoreCase(objMateria.getDescripcion())){
                throw new ExcepcionPropia("Ya existe esta descripción de tema en el sistema");
            }
        }
        
        //Estableciendo la sentencia SQL para la actualización y asignando parámetros
        String sql_update = "UPDATE materia SET descripcion = ? WHERE idmateria = ?";
        PreparedStatement pstm_update = conn.prepareStatement(sql_update);
        
        pstm_update.setString(1, objMateria.getDescripcion());
        pstm_update.setInt(2, objMateria.getId());
        
        //Manipulación en la BD
        pstm_update.executeUpdate();
    }
    
    public void eliminar(Tema objMateria) throws SQLException, Exception{
        
        //Estableciendo la sentencia SQL para consulta y asignando el parámetro necesario (su ID)
        String sql_delete = "DELETE FROM materia WHERE idmateria = ?";
        PreparedStatement pstm = conn.prepareStatement(sql_delete);
        pstm.setInt(1, objMateria.getId());
        
        //Manipulando datos en la base de datos
        pstm.executeUpdate();
    }
    
    /*Método para recuperar los datos de la tabla Tema de la base de datos y almacenarlos
    en una estructura List que contenga objetos de tipo Tema.*/
    public ListaTemas obtenerTodosTemas() throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT * FROM materia ORDER BY descripcion ASC";
        
        /*Ejecutando la consulta y los datos seleccionados se almacenan en ResultSet*/
        ResultSet rs = stm.executeQuery(sql);
        Tema objTema;
        ListaTemas temas = new ListaTemas();
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Materia. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objTema = new Tema();
            objTema.setId(Integer.parseInt(rs.getString(1)));
            objTema.setDescripcion(rs.getString(2));
            temas.agregarElementoSgte(objTema);
        }
        
        return temas;
    }
    
    /*Método para recuperar todos los datos de tema según los caracteres contenidos en la
        columna 'descripcion' de la tabla Tema de la base de datos. Dichos datos se alojarán
        en una estructura de Lista.*/
    public ListaTemas obtenerTemasXDescripcion(String cadena_contenido) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT * FROM materia WHERE descripcion LIKE ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, "%" + cadena_contenido + "%");
        
        //Recuperando datos de materia
        ResultSet rs = pstm.executeQuery();
        ListaTemas temas = new ListaTemas();
        Tema objTema;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Materia. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objTema = new Tema();
            objTema.setId(Integer.parseInt(rs.getString(1)));
            objTema.setDescripcion(rs.getString(2));
            temas.agregarElementoSgte(objTema);
        }
        
        return temas;
    }
    
    public ArrayList<Tema> obtenerTemas(ArrayList<String> descripciones) throws SQLException{
        
        String sql = "SELECT * FROM materia WHERE descripcion = ?";
        
        for (int i = 1; i < descripciones.size(); i++) {
            sql = sql.concat(" OR descripcion = ?");
        }
        
        /*Estableciendo la sentencia SQL para consulta*/
        //String sql = "SELECT * FROM materia WHERE descripcion LIKE ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        
        pstm.setString(1, descripciones.get(0).toString());
        for (int i = 1; i < descripciones.size(); i++) {
            pstm.setString(i + 1, descripciones.get(i).toString());
        }
        
        //Recuperando datos de materia
        ResultSet rs = pstm.executeQuery();
        ArrayList<Tema> temas = new ArrayList<Tema>();
        Tema objTema;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Materia. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objTema = new Tema();
            objTema.setId(Integer.parseInt(rs.getString(1)));
            objTema.setDescripcion(rs.getString(2));
            temas.add(objTema);
        }
        
        return temas;
    }
    
    /*Método de ordenamiento de tipo Selección simple que ordena alfabéticamente la descripción
    de los temas de los objetos almacenados en la lista*/
    public ListaTemas ordenarTemasXDescripcionASC(ListaTemas listaTemas){
        
        Tema temaActual, temaSiguiente;
         
        for (int i = 0; i < listaTemas.tamanio() - 1; i++) {
            for (int j = 0; j < listaTemas.tamanio() - 1; j++) {
                //Comparación de 2 nombres consecutivos en el array
                temaActual = listaTemas.obtenerTema(j);
                temaSiguiente = listaTemas.obtenerTema(j + 1);
                
                if(temaActual.getDescripcion().compareToIgnoreCase(temaSiguiente.getDescripcion()) > 0){ //Tipo de comparación 
                    
                    //Intercambiando de lugar para ordenarlos
                    listaTemas.establecerElemento(j, temaSiguiente);
                    listaTemas.establecerElemento(j + 1 , temaActual);
                }
                
            }
        }
        
        return listaTemas;
    }
    
    
    /*Método que invierte el orden de los elementos de la lista.*/
    public ListaTemas ordenarTemasXDescripcionDESC(ListaTemas listaTemas){
        Tema temaActual, temaSiguiente;
         
        for (int i = 0; i < listaTemas.tamanio() - 1; i++) {
            for (int j = 0; j < listaTemas.tamanio() - 1; j++) {
                //Comparación de 2 nombres consecutivos en el array
                temaActual = listaTemas.obtenerTema(j);
                temaSiguiente = listaTemas.obtenerTema(j + 1);
                
                if(temaActual.getDescripcion().compareToIgnoreCase(temaSiguiente.getDescripcion()) < 0){ //Tipo de comparación 
                    
                    //Intercambiando de lugar para ordenarlos
                    listaTemas.establecerElemento(j, temaSiguiente);
                    listaTemas.establecerElemento(j + 1 , temaActual);
                }
                
            }
        }
        
        return listaTemas;
    }
    
    /*Método que retornará un valor entero que será el código asignado en el nuevo
    registro de Tema.*/
    public int generarNuevoCodigo() throws SQLException{
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT MAX(idmateria) FROM materia";
        
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
    
    public int contarTemas(){
        int total = 0;
        
        try {
            /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
            ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
            Statement stm = conn.createStatement();
            String sql = "SELECT COUNT(*) FROM materia";
            
            /*Ejecutando la consulta y los datos seleccionados se almacenan en ResultSet*/
            ResultSet rs = stm.executeQuery(sql);
            
            while(rs.next()){
                if(rs.getString(1) == null){
                    total = 0;
                }else{
                    total = Integer.parseInt(rs.getString(1));
                }
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(TemaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return total;
    }
}
