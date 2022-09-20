
package entidades;

import java.util.GregorianCalendar;

public class Prestamo {
    
    private int id;
    private int idestudiante;
    private int idlibro;
    private GregorianCalendar fechaPrestamo;
    private int num_dias_prestamo;
    private int estado; /**/
    
    //Atributos secundarios
    private String nombres_estudiante;
    private String titulo_libro;
    private int numejemplar_libro;
    private GregorianCalendar fechaFinalizacionPrestamo;
    

    public Prestamo() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdestudiante(int idestudiante) {
        this.idestudiante = idestudiante;
    }

    public int getIdestudiante() {
        return idestudiante;
    }

    public void setIdlibro(int idlibro) {
        this.idlibro = idlibro;
    }

    public int getIdlibro() {
        return idlibro;
    }

    public void setNombres_estudiante(String nombres_estudiante) {
        this.nombres_estudiante = nombres_estudiante;
    }

    public String getNombres_estudiante() {
        return nombres_estudiante;
    }

    public void setNum_dias_prestamo(int num_dias_prestamo) {
        this.num_dias_prestamo = num_dias_prestamo;
    }

    public int getNum_dias_prestamo() {
        return num_dias_prestamo;
    }

    public void setNumejemplar_libro(int numejemplar_libro) {
        this.numejemplar_libro = numejemplar_libro;
    }

    public int getNumejemplar_libro() {
        return numejemplar_libro;
    }

    public void setTitulo_libro(String titulo_libro) {
        this.titulo_libro = titulo_libro;
    }

    public String getTitulo_libro() {
        return titulo_libro;
    }

    public void setFechaPrestamo(GregorianCalendar fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
    
    public String getFechaPrestamo() {
        
        String anio = fechaPrestamo.get(fechaPrestamo.YEAR) + "";
        
        String mes;
        
        if(fechaPrestamo.get(fechaPrestamo.MONTH) < 10){
            mes = "0" + fechaPrestamo.get(fechaPrestamo.MONTH);
        } else{
            mes = fechaPrestamo.get(fechaPrestamo.MONTH) + "";
        }
        
        String dia = fechaPrestamo.get(fechaPrestamo.DAY_OF_MONTH) + "";
        
        return dia + "-" + mes + "-" + anio;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public String getEstado() {
        
        switch (estado) {
            case 0:
                return "Finalizado";
            case 1:
                return "En vigencia";
            default:
                return "";
        }
    }

    public void setFechaFinalizacionPrestamo(GregorianCalendar fechaFinalizacionPrestamo) {
        this.fechaFinalizacionPrestamo = fechaFinalizacionPrestamo;
    }

    public String getFechaFinalizacionPrestamo() {
        String anio = fechaFinalizacionPrestamo.get(fechaFinalizacionPrestamo.YEAR) + "";
        
        String mes;
        
        if(fechaFinalizacionPrestamo.get(fechaFinalizacionPrestamo.MONTH) < 10){
            mes = "0" + fechaFinalizacionPrestamo.get(fechaFinalizacionPrestamo.MONTH);
        } else{
            mes = fechaFinalizacionPrestamo.get(fechaFinalizacionPrestamo.MONTH) + "";
        }
        
        String dia = fechaFinalizacionPrestamo.get(fechaFinalizacionPrestamo.DAY_OF_MONTH) + "";
        
        return dia + "-" + mes + "-" + anio;
    }
}
