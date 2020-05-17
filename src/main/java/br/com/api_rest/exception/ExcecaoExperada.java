package br.com.api_rest.exception;

import org.apache.tomcat.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* Autor Cezar Marçal - 05/2020
*/


@ControllerAdvice // Observa toda a aplicação em quanto está sendo executada
public class ExcecaoExperada extends ResponseEntityExceptionHandler  {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        String msgUser = messageSource.getMessage("apresenta.mensagem.usuario",
                null, LocaleContextHolder.getLocale());

        String msgDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();

        List<MensagemUtil> listErros = Arrays.asList(new MensagemUtil(msgUser, msgDev));

        return handleExceptionInternal(ex, listErros, headers, HttpStatus.BAD_REQUEST, request);
    }


    //Valida se os parâmetros que estão sendo passado na chamada do metódo é valido
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        List<MensagemUtil> listaErros = listErros(ex.getBindingResult());
        return handleExceptionInternal(ex, listaErros, headers, HttpStatus.BAD_REQUEST, request);
    }


    private List<MensagemUtil> listErros(BindingResult result){
        List<MensagemUtil> listaDeErros = new ArrayList<>();

        for (FieldError error: result.getFieldErrors()){
            String msgUser = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            String msgDev = error.toString();
            listaDeErros.add(new MensagemUtil(msgUser, msgDev));
        }
        return listaDeErros;
    }


    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
                                                                       WebRequest request){

        String msgUser = messageSource.getMessage("recurso.nao.encontrado",
                null, LocaleContextHolder.getLocale());

        String msgDev = ex.toString();

        List<MensagemUtil> listaErros = Arrays.asList(new MensagemUtil(msgUser, msgDev));


        return handleExceptionInternal(ex, listaErros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    //Ao cadastrar um lançamento e passar no JSON parametros invalidos
    // para categoria ou usuario entao sera lancado uma excecao
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                        WebRequest request){

        String msgUser = messageSource.getMessage("operacao.nao.permitida",
                null, LocaleContextHolder.getLocale());

        String msgDev = ex.getCause().toString();

        List<MensagemUtil> listaErros = Arrays.asList(new MensagemUtil(msgUser, msgDev));

        return handleExceptionInternal(ex, listaErros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
