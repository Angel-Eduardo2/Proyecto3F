package dao;

import exception.DatabaseException;
import java.util.List;

public interface DAO<T, K> {

    void insertar(T entidad) throws DatabaseException;

    void actualizar(T entidad) throws DatabaseException;

    void eliminar(K id) throws DatabaseException;

    T buscarPorId(K id) throws DatabaseException;

    List<T> listarTodos() throws DatabaseException;
}
