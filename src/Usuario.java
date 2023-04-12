public class Usuario {
    private static String nombreUsuario;
    private String contrasenia;
    private String tipoUsuario;

    public Usuario(String nombreUsuario, String contrasenia, String tipoUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.tipoUsuario = tipoUsuario;
    }

    public static String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }



}

