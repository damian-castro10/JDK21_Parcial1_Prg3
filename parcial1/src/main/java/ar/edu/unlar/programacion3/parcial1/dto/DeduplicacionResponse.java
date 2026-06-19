package ar.edu.unlar.programacion3.parcial1.dto;

import java.util.List;

/**
 * Representación pública e inmutable del resultado de un proceso de
 * deduplicación de alertas GPS.
 *
 * @param cantidadRecibida cantidad total de reportes recibidos, incluyendo duplicados.
 * @param cantidadUnica cantidad de reportes únicos resultantes tras la deduplicación.
 * @param alertasUnicas la lista de reportes únicos.
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public record DeduplicacionResponse(
    int cantidadRecibida,
    int cantidadUnica,
    List<AlertaGpsResponse> alertasUnicas
) {
}