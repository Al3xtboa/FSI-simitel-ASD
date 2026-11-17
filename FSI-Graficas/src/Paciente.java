



public class Paciente {

    private String curp;
    private String nombre;

    public Paciente(String curp, String nombre) {
        this.curp = curp;
        this.nombre = nombre;
    }

    public String getCurp() {
        return curp;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}