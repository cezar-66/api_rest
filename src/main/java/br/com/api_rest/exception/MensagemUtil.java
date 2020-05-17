package br.com.api_rest.exception;

public class MensagemUtil {

    private String mensagemUsuario;
    private String mensagemDesenvolvedor;

    public MensagemUtil(String mensagemUsuario, String MensagemDesenvolvedor) {
        this.mensagemUsuario = mensagemUsuario;
        this.mensagemDesenvolvedor = MensagemDesenvolvedor;
    }

    public String getMensagemUsuario() {
        return mensagemUsuario;
    }

    public String getMensagemDesenvolvedor() {
        return mensagemDesenvolvedor;
    }
}
