package io.ricardosteel.vendas.exceptions;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class ApplicationControllerAdvice {

	private static final String TIMESTAMP = "timestamp";
	private static final String MESSAGE = "message";

	@ExceptionHandler({ RegraNegocioException.class, MethodArgumentNotValidException.class,
			RegistroNaoEncontradoException.class, SenhaInvalidaException.class, UsernameNotFoundException.class })
	public final ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();

		if (ex instanceof RegraNegocioException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			RegraNegocioException unfe = (RegraNegocioException) ex;

			return handleRegraNegocioException(unfe, headers, status, request);
		} else if (ex instanceof RegistroNaoEncontradoException) {
			HttpStatus status = HttpStatus.NOT_FOUND;
			RegistroNaoEncontradoException rnee = (RegistroNaoEncontradoException) ex;

			return handleRegistroNaoEncontradoException(rnee, headers, status, request);
		} else if (ex instanceof MethodArgumentNotValidException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			MethodArgumentNotValidException manve = (MethodArgumentNotValidException) ex;

			return handleMethodArgumentNotValid(manve, headers, status, request);
		} else if (ex instanceof SenhaInvalidaException) {
			HttpStatus status = HttpStatus.UNAUTHORIZED;
			SenhaInvalidaException sie = (SenhaInvalidaException) ex;

			return handleSenhaInvalidaException(sie, headers, status, request);
		} else if (ex instanceof UsernameNotFoundException) {
			HttpStatus status = HttpStatus.UNAUTHORIZED;
			UsernameNotFoundException sie = (UsernameNotFoundException) ex;

			return handleUsernameNotFoundException(sie, headers, status, request);
		} else {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleExceptionInternal(ex, null, headers, status, request);
		}
	}

	private ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException sie, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put(TIMESTAMP, LocalDateTime.now());
		body.put(MESSAGE, sie.getMessage());

		return handleExceptionInternal(sie, body, headers, status, request);
	}

	/** Customize the response for SenhaInvalidaException. */
	private ResponseEntity<Object> handleSenhaInvalidaException(SenhaInvalidaException sie, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put(TIMESTAMP, LocalDateTime.now());
		body.put(MESSAGE, sie.getMessage());

		return handleExceptionInternal(sie, body, headers, status, request);
	}

	/** Customize the response for RegraNegocioException. */
	protected ResponseEntity<Object> handleRegistroNaoEncontradoException(RegistroNaoEncontradoException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put(TIMESTAMP, LocalDateTime.now());
		body.put(MESSAGE, ex.getMessage());

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	/** Customize the response for RegraNegocioException. */
	protected ResponseEntity<Object> handleRegraNegocioException(RegraNegocioException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put(TIMESTAMP, LocalDateTime.now());
		body.put(MESSAGE, ex.getMessage());

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	/** Customize the response for MethodArgumentNotValidException. */
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put(TIMESTAMP, LocalDateTime.now());
		body.put("status", status.value());
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	/** A single place to customize the response body of all Exception types. */
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}

		return new ResponseEntity<>(body, headers, status);
	}

}
