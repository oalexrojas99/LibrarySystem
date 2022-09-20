
package entidades;

public class Libro {
    private int id;
    private String isbn;
    private String titulo;
    private String autores;
    private int idmateria;
    private int aniopublicacion; /*Reempleazarlo por numero de ejemplar*/
    private int numejemplar;
    private int estado;
    
    //Atributos secundarios
    private String descripcion_materia;

    public Libro() {
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAniopublicacion(int aniopublicacion) {
        this.aniopublicacion = aniopublicacion;
    }

    public int getAniopublicacion() {
        return aniopublicacion;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getAutores() {
        return autores;
    }

    public void setDescripcion_materia(String descripcion_materia) {
        this.descripcion_materia = descripcion_materia;
    }

    public String getDescripcion_materia() {
        return descripcion_materia;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIdmateria(int materia) {
        this.idmateria = materia;
    }

    public int getIdmateria() {
        return idmateria;
    }

    public void setNumejemplar(int numejemplar) {
        this.numejemplar = numejemplar;
    }

    public int getNumejemplar() {
        return numejemplar;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public String getEstado() {
        
        switch (estado) {
            case 0:
                return "No disponible";
            case 1:
                return "Disponible";
            default:
                return "";
        }
    }
    
}
