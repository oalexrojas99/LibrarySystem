package DAOs;

import entidades.Libro;
import entidades.Tema;
import excepcionesPropias.ExcepcionPropia;
import grafoNoDirigido.Grafo;
import grafoNoDirigido.Vertice;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jdbc.ConexionBD;
import listasPropias.ListaLibros;

public class LibroDAO {

    Connection conn;
    
    public LibroDAO(){
        /*Se crea la conexión con la BD cuando se realiza una instancia de la clase LibroDAO*/
        conn = ConexionBD.conectarMySQL();
    }
    
    public void registrar(Libro objLibro) throws ExcepcionPropia, SQLException{
        
        //Validar la no repitencia de algun otro título o ISBN de Libro
        ListaLibros libros = new ListaLibros();
        libros = obtenerLibros();
        
        for (int i = 0; i < libros.tamanio(); i++) {
            if(libros.obtenerLibro(i).getTitulo().equalsIgnoreCase(objLibro.getTitulo())){
                throw new ExcepcionPropia("Título del libro ya registrado en el sistema.");
            }
            
            if(libros.obtenerLibro(i).getIsbn().equalsIgnoreCase(objLibro.getIsbn())){
                throw new ExcepcionPropia("ISBN del libro ya registrado en el sistema.");
            }
        }
        
        //Estableciendo la sentencia de inserción SQL
        String sql = "INSERT INTO libro (isbn, titulo, autores, idmateria, anio, numejemplar) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement pstm_insert = conn.prepareStatement(sql);
        
        //Asignando parámetros de la sentencia
        pstm_insert.setString(1, objLibro.getIsbn());
        pstm_insert.setString(2, objLibro.getTitulo());
        pstm_insert.setString(3, objLibro.getAutores());
        pstm_insert.setInt(4, objLibro.getIdmateria());
        pstm_insert.setInt(5, objLibro.getAniopublicacion());
        pstm_insert.setInt(6, 1);
        
        //Manipulando, finalmente, la base de datos
        pstm_insert.executeUpdate();
    }

    public void actualizar(Libro objLibro) throws ExcepcionPropia, SQLException{
        
        //Validar la no repitencia del nombre delestudiante a excepción del mismo objeto
        ListaLibros libros = new ListaLibros();
        libros = obtenerLibros();
        
        //Bucle para comparar con todos los datos de Libro
        for (int i = 0; i < libros.tamanio(); i++) {
            if(libros.obtenerLibro(i).getId() != objLibro.getId()){
                if(libros.obtenerLibro(i).getTitulo().equalsIgnoreCase(objLibro.getTitulo())){
                    throw new ExcepcionPropia("Título del libro ya registrado en el sistema.");
                }

                if(libros.obtenerLibro(i).getIsbn().equalsIgnoreCase(objLibro.getIsbn())){
                    throw new ExcepcionPropia("ISBN del libro ya registrado en el sistema.");
                }
            }
        }
        
        //Estableciendo la sentencia SQL para la actualización y asignando parámetros
        String sql_update = "UPDATE libro SET isbn = ?, titulo = ?, autores = ?, idmateria = ?, anio = ?, numejemplar = ?, estado = ?  WHERE idlibro = ?";
        PreparedStatement pstm_update = conn.prepareStatement(sql_update);
        
        pstm_update.setString(1, objLibro.getIsbn());
        pstm_update.setString(2, objLibro.getTitulo());
        pstm_update.setString(3, objLibro.getAutores());
        pstm_update.setInt(4, objLibro.getIdmateria());
        pstm_update.setInt(5, objLibro.getAniopublicacion());
        pstm_update.setInt(6, objLibro.getNumejemplar());
        
        if(objLibro.getEstado().equals("Disponible")){
            pstm_update.setInt(7, 1);
        } else{
            pstm_update.setInt(7, 0);
        }
        
        pstm_update.setInt(8, objLibro.getId());
        
        //Manipulación en la BD
        pstm_update.executeUpdate();
    }
    
    public void eliminar(Libro objLibro) throws SQLException, Exception{
        
        //Estableciendo la sentencia SQL para consulta y asignando el parámetro necesario
        String sql_delete = "DELETE FROM libro WHERE idlibro = ?";
        PreparedStatement pstm = conn.prepareStatement(sql_delete);
        pstm.setInt(1, objLibro.getId());
        
        //Manipulando datos en la base de datos
        pstm.executeUpdate();
    }
    
    public ListaLibros obtenerLibros() throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT l.*, m.descripcion FROM libro l INNER JOIN materia m "
                + "ON l.idmateria = m.idmateria;";
        
         /*Ejecutando la consulta y los datos seleccionados se almacenarán en ResultSet*/
        ResultSet rs = stm.executeQuery(sql);
        Libro objLibro;
        ListaLibros listaLibros = new ListaLibros();
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objLibro = new Libro();
            objLibro.setId(Integer.parseInt(rs.getString(1)));
            objLibro.setIsbn(rs.getString(2));
            objLibro.setTitulo(rs.getString(3));
            objLibro.setAutores(rs.getString(4));
            objLibro.setIdmateria(Integer.parseInt(rs.getString(5)));
            objLibro.setAniopublicacion(Integer.parseInt(rs.getString(6)));
            objLibro.setNumejemplar(Integer.parseInt(rs.getString(7)));
            objLibro.setEstado(Integer.parseInt(rs.getString(8)));
            objLibro.setDescripcion_materia(rs.getString(9));
            
            listaLibros.agregarElementoSgte(objLibro);
        }
        
        return listaLibros;
    }
    
    public ListaLibros obtenerLibrosSinEjemplares() throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT l.idlibro, l.isbn, l.titulo, l.autores, l.idmateria, l.anio, count(l.numejemplar), m.descripcion " +
                        "FROM libro l INNER JOIN materia m ON l.idmateria = m.idmateria GROUP BY l.titulo;";
        
         /*Ejecutando la consulta y los datos seleccionados se almacenarán en ResultSet*/
        ResultSet rs = stm.executeQuery(sql);
        Libro objLibro;
        ListaLibros listaLibros = new ListaLibros();
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objLibro = new Libro();
            objLibro.setId(Integer.parseInt(rs.getString(1)));
            objLibro.setIsbn(rs.getString(2));
            objLibro.setTitulo(rs.getString(3));
            objLibro.setAutores(rs.getString(4));
            objLibro.setIdmateria(Integer.parseInt(rs.getString(5)));
            objLibro.setAniopublicacion(Integer.parseInt(rs.getString(6)));
            objLibro.setDescripcion_materia(rs.getString(8));
            
            listaLibros.agregarElementoSgte(objLibro);
        }
        
        return listaLibros;
    }
    
    public ListaLibros obtenerLibrosDisponibles() throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT l.idlibro, l.isbn, l.titulo, l.autores, l.idmateria, l.anio, l.numejemplar, m.descripcion " +
                        "FROM libro l INNER JOIN materia m ON l.idmateria = m.idmateria WHERE l.estado = 1;";
        
         /*Ejecutando la consulta y los datos seleccionados se almacenarán en ResultSet*/
        ResultSet rs = stm.executeQuery(sql);
        Libro objLibro;
        ListaLibros listaLibros = new ListaLibros();
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objLibro = new Libro();
            objLibro.setId(Integer.parseInt(rs.getString(1)));
            objLibro.setIsbn(rs.getString(2));
            objLibro.setTitulo(rs.getString(3));
            objLibro.setAutores(rs.getString(4));
            objLibro.setIdmateria(Integer.parseInt(rs.getString(5)));
            objLibro.setAniopublicacion(Integer.parseInt(rs.getString(6)));
            objLibro.setNumejemplar(Integer.parseInt(rs.getString(7)));
            objLibro.setDescripcion_materia(rs.getString(8));
            
            listaLibros.agregarElementoSgte(objLibro);
        }
        
        return listaLibros;
    }
    
    public ListaLibros obtenerLibrosXTitulo(String cadena) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT l.*, m.descripcion FROM libro l INNER JOIN materia m "
                + "ON l.idmateria = m.idmateria WHERE l.titulo LIKE ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, "%" + cadena + "%");
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ListaLibros listaLibros = new ListaLibros();
        Libro objLibro;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objLibro = new Libro();
            objLibro.setId(Integer.parseInt(rs.getString(1)));
            objLibro.setIsbn(rs.getString(2));
            objLibro.setTitulo(rs.getString(3));
            objLibro.setAutores(rs.getString(4));
            objLibro.setIdmateria(Integer.parseInt(rs.getString(5)));
            objLibro.setAniopublicacion(Integer.parseInt(rs.getString(6)));
            objLibro.setNumejemplar(Integer.parseInt(rs.getString(7)));
            objLibro.setEstado(Integer.parseInt(rs.getString(8)));
            objLibro.setDescripcion_materia(rs.getString(9));
            
            listaLibros.agregarElementoSgte(objLibro);
        }
        
        return listaLibros;
    }
    
    public ArrayList<Libro> consultarLibros(int idTema, int anio1, int anio2, String palabraClave) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT l.idlibro, l.isbn, l.titulo, l.autores, l. idmateria, l.anio, m.descripcion FROM libro l INNER JOIN materia m " +
                        "ON l.idmateria = m.idmateria WHERE l.idmateria = ? AND l.anio >= ? AND l.anio <= ? AND l.titulo LIKE ? " +
                        "GROUP BY l.titulo;";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, idTema);
        pstm.setInt(2, anio1);
        pstm.setInt(3, anio2);
        pstm.setString(4, "%" + palabraClave + "%");
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ArrayList<Libro> listaLibros = new ArrayList<>();
        Libro objLibro;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objLibro = new Libro();
            objLibro.setId(Integer.parseInt(rs.getString(1)));
            objLibro.setIsbn(rs.getString(2));
            objLibro.setTitulo(rs.getString(3));
            objLibro.setAutores(rs.getString(4));
            objLibro.setIdmateria(Integer.parseInt(rs.getString(5)));
            objLibro.setAniopublicacion(Integer.parseInt(rs.getString(6)));
            objLibro.setDescripcion_materia(rs.getString(7));
            
            listaLibros.add(objLibro);
        }
        
        return listaLibros;
    }
    
    public ArrayList<Libro> obtenerLibrosRelacionados(ArrayList<String> temas) throws SQLException{
        
        String sql = "SELECT l.idlibro, l.isbn, l.titulo, l.autores, l.idmateria, l.anio, m.descripcion FROM libro l INNER JOIN materia m " +
                        "ON l.idmateria = m.idmateria WHERE m.descripcion = ?";
        
        for (int i = 1; i < temas.size(); i++) {
            sql = sql.concat(" OR m.descripcion = ?");
        }
        
        sql = sql.concat(" GROUP BY l.titulo");
        
        /*Estableciendo la sentencia SQL para consulta*/
        //String sql = "SELECT * FROM materia WHERE descripcion LIKE ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        
        pstm.setString(1, temas.get(0).toString());
        for (int i = 1; i < temas.size(); i++) {
            pstm.setString(i + 1, temas.get(i).toString());
        }
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ArrayList<Libro> librosRelacionados = new ArrayList<Libro>();
        Libro objLibro;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Materia. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objLibro = new Libro();
            objLibro.setId(Integer.parseInt(rs.getString(1)));
            objLibro.setIsbn(rs.getString(2));
            objLibro.setTitulo(rs.getString(3));
            objLibro.setAutores(rs.getString(4));
            objLibro.setIdmateria(Integer.parseInt(rs.getString(5)));
            objLibro.setAniopublicacion(Integer.parseInt(rs.getString(6)));
            objLibro.setDescripcion_materia(rs.getString(7));
            librosRelacionados.add(objLibro);
        }
        
        return librosRelacionados;
    }
    
    public Libro obtenerLibroXID(int idLibro) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT l.idlibro, l.isbn, l.titulo, l.autores, l. idmateria, l.anio, m.descripcion FROM libro l INNER JOIN materia m " +
                        "ON l.idmateria = m.idmateria WHERE l.idlibro = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, idLibro);
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        Libro objLibro = new Libro();
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objLibro = new Libro();
            objLibro.setId(Integer.parseInt(rs.getString(1)));
            objLibro.setIsbn(rs.getString(2));
            objLibro.setTitulo(rs.getString(3));
            objLibro.setAutores(rs.getString(4));
            objLibro.setIdmateria(Integer.parseInt(rs.getString(5)));
            objLibro.setAniopublicacion(Integer.parseInt(rs.getString(6)));
            objLibro.setDescripcion_materia(rs.getString(7));
        }
        
        return objLibro;
    }
    
    public int generarNuevoCodigo() throws SQLException{
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT MAX(idlibro) FROM libro";
        
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
    
    public int contarNumEjemplares(String titulo) throws SQLException{
        //Estableciendo la sentencia SQL para consulta y asignando el parámetro necesario
        String sql = "SELECT COUNT(*) FROM libro WHERE titulo = ?"; //CORREGIR Y CAMBIAR columna
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, titulo);
        
        //Recuperando último ejemplar
        int numEjemplares = 0;
        
        //Ejecución de la consulta a la BD
        ResultSet rs = pstm.executeQuery();
        
        while (rs.next()) {
            numEjemplares = rs.getInt(1);
        }
        
        return numEjemplares;
    }
    
    public int generarNumEjemplar(String titulo) throws SQLException{
        
        return contarNumEjemplares(titulo) + 1;
    }
    
    public void agregarEjemplar(Libro objLibro) throws SQLException{
        
        //Estableciendo la sentencia de inserción SQL
        String sql = "INSERT INTO libro (isbn, titulo, autores, idmateria, anio, numejemplar) "
                + "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement pstm_insert = conn.prepareStatement(sql);
        
        //Asignando parámetros de la sentencia
        pstm_insert.setString(1, objLibro.getIsbn());
        pstm_insert.setString(2, objLibro.getTitulo());
        pstm_insert.setString(3, objLibro.getAutores());
        pstm_insert.setInt(4, objLibro.getIdmateria());
        pstm_insert.setInt(5, objLibro.getAniopublicacion());
        pstm_insert.setInt(6, generarNumEjemplar(objLibro.getTitulo()));
        
        //Manipulando, finalmente, la base de datos
        pstm_insert.executeUpdate();
    }
    
    public void ordenarLibrosXAnioASC(List<Libro> listaLibros, int izq, int der){
        
        Libro pivote = listaLibros.get(izq); // tomamos primer elemento como pivote
        int i = izq;         // i realiza la búsqueda de izquierda a derecha
        int j = der;         // j realiza la búsqueda de derecha a izquierda
        Libro aux;

        while(i < j){                          // mientras no se crucen las búsquedas                                   
           while(listaLibros.get(i).getAniopublicacion() <=  pivote.getAniopublicacion() && i < j) i++; // busca elemento mayor que pivote
           while(listaLibros.get(j).getAniopublicacion() >  pivote.getAniopublicacion()) j--;           // busca elemento menor que pivote
           if (i < j) {                        // si no se han cruzado                      
               aux = listaLibros.get(i);                      // los intercambia
               listaLibros.set(i, listaLibros.get(j));
               listaLibros.set(j, aux);
           }
        }
   
        listaLibros.set(izq, listaLibros.get(j));      // se coloca el pivote en su lugar de forma que tendremos                                    
        listaLibros.set(j, pivote);     // los menores a su izquierda y los mayores a su derecha

        if(izq < j - 1){
            ordenarLibrosXAnioASC(listaLibros, izq, j - 1); // ordenamos subarray izquierdo
        }
                     
        if(j + 1 < der){
            ordenarLibrosXAnioASC(listaLibros, j + 1, der); // ordenamos subarray derecho
        }
    }
    
    public void ordenarLibrosXAnioDESC(List<Libro> listaLibros){
        ordenarLibrosXAnioASC(listaLibros, 0, listaLibros.size() - 1);
        Collections.reverse(listaLibros);
    }
    
    public void ordenarLibrosXTituloASC(List<Libro> listaLibros, int izq, int der){
        
        Libro pivote = listaLibros.get(izq); // tomamos primer elemento como pivote
        int i = izq;         // i realiza la búsqueda de izquierda a derecha
        int j = der;         // j realiza la búsqueda de derecha a izquierda
        Libro aux;

        while(i < j){                          // mientras no se crucen las búsquedas                                   
           while(listaLibros.get(i).getTitulo().compareToIgnoreCase(pivote.getTitulo()) <= 0 && i < j) i++; // busca elemento mayor que pivote
           while(listaLibros.get(j).getTitulo().compareToIgnoreCase(pivote.getTitulo()) > 0) j--;           // busca elemento menor que pivote
           if (i < j) {                        // si no se han cruzado                      
               aux = listaLibros.get(i);                      // los intercambia
               listaLibros.set(i, listaLibros.get(j));
               listaLibros.set(j, aux);
           }
        }
   
        listaLibros.set(izq, listaLibros.get(j));      // se coloca el pivote en su lugar de forma que tendremos                                    
        listaLibros.set(j, pivote);     // los menores a su izquierda y los mayores a su derecha

        if(izq < j - 1){
            ordenarLibrosXTituloASC(listaLibros, izq, j - 1); // ordenamos subarray izquierdo
        }
                     
        if(j + 1 < der){
            ordenarLibrosXTituloASC(listaLibros, j + 1, der); // ordenamos subarray derecho
        }
    }
    
    public void ordenarLibrosXTituloDESC(List<Libro> listaLibros){
        ordenarLibrosXTituloASC(listaLibros, 0, listaLibros.size() - 1);
        Collections.reverse(listaLibros);
    }
    
}
