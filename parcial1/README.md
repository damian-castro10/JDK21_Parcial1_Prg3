# Anexo Técnico de Rendimiento — EcoRide PRO

**Parcial 2:** EcoRide PRO — Evolución de Plataforma

*Integrantes:*
Perez Mercado Gaston Ezequiel
Castro Damian Emiliano
Corzo Ezequiel Nicolas
Bonanno Franco Fernando

## Por qué la nueva estructura de búsqueda es más rápida

| Operación | Complejidad anterior | Complejidad actual | Estructura/técnica utilizada |
|---|:---:|:---:|---|
| Búsqueda de vehículo por patente | O(n) | O(1) amortizado | `HashMap<String, Vehiculo>` | -> Pasamos de utilizar una busqueda lineal a una basada en hash


| Deduplicación de alertas GPS | O(n²) | O(n) | `LinkedHashSet` + `equals`/`hashCode` | -> En vez de utilizar una complejidad O(n^2) (For anidado) a una complejidad lineal O(n)

| Ordenamiento por batería (natural) | — | O(n log n) | `Comparable` + `Collections.sort()` (TimSort) | -> Se ordena las listas en forma ascendente o descendente de acuerdo a que endpoint se consume, y esto lo hacemos implementando el algoritmo de ordenamiento TimSort de la utilidad Collections

| Ordenamiento por tarifa (alternativo) | — | O(n log n) | `Comparator` externo + `Collections.sort()` (TimSort) | -> Implemntamos un endpoint de ordenamiento en donde se ordena la lista segun la tarifa, utilizando tambien TimSort de la misma utilidad Collections

Nosotros utilizamos el TimSort porque es el algoritmo de ordenamiento que mejor rendimiento tiene en base a la demora de ordenamiento. Ademas de ser un tipo de busqueda ya implementada en Java en sus utilidades (utils). Ademas, no requiere de gran logica para implementar, ya que solo hace falta conocer el parametro boolean para determinar si el mismo se va a hacer de forma ascendete o descendente

Implementamos tambien una tabla para esquematizar los cambios que hicimos