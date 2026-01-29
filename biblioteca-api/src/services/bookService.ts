import pool from '../config/database';
import { Book, BookRow, BookResult } from '../models/bookModel';
import { ResultSetHeader } from 'mysql2';

export class BookService {
  
  // Obtener todos los libros
  async getAllBooks(): Promise<Book[]> {
    const [rows] = await pool.query<BookRow[]>(
      'SELECT * FROM libros ORDER BY created_at DESC'
    );
    return rows;
  }

  // Obtener un libro por ID
  async getBookById(id: number): Promise<Book | null> {
    const [rows] = await pool.query<BookRow[]>(
      'SELECT * FROM libros WHERE id = ?',
      [id]
    );
    return rows.length > 0 ? rows[0] : null;
  }

  // Buscar libros por título o autor
  async searchBooks(query: string): Promise<Book[]> {
    const searchTerm = `%${query}%`;
    const [rows] = await pool.query<BookRow[]>(
      'SELECT * FROM libros WHERE titulo LIKE ? OR autor LIKE ?',
      [searchTerm, searchTerm]
    );
    return rows;
  }

  // Crear un nuevo libro
  async createBook(book: Book): Promise<Book> {
    const [result] = await pool.query<BookResult>(
      `INSERT INTO libros (titulo, autor, isbn, editorial, anio_publicacion, genero, disponible) 
       VALUES (?, ?, ?, ?, ?, ?, ?)`,
      [
        book.titulo,
        book.autor,
        book.isbn,
        book.editorial,
        book.anio_publicacion,
        book.genero,
        book.disponible ?? true
      ]
    );
    
    return {
      id: result.insertId,
      ...book
    };
  }

  // Actualizar un libro
  async updateBook(id: number, book: Partial<Book>): Promise<boolean> {
    const [result] = await pool.query<ResultSetHeader>(
      `UPDATE libros 
       SET titulo = COALESCE(?, titulo),
           autor = COALESCE(?, autor),
           isbn = COALESCE(?, isbn),
           editorial = COALESCE(?, editorial),
           anio_publicacion = COALESCE(?, anio_publicacion),
           genero = COALESCE(?, genero),
           disponible = COALESCE(?, disponible),
           updated_at = NOW()
       WHERE id = ?`,
      [
        book.titulo,
        book.autor,
        book.isbn,
        book.editorial,
        book.anio_publicacion,
        book.genero,
        book.disponible,
        id
      ]
    );
    
    return result.affectedRows > 0;
  }

  // Eliminar un libro
  async deleteBook(id: number): Promise<boolean> {
    const [result] = await pool.query<ResultSetHeader>(
      'DELETE FROM libros WHERE id = ?',
      [id]
    );
    return result.affectedRows > 0;
  }

  // Verificar si un ISBN ya existe
  async isbnExists(isbn: string, excludeId?: number): Promise<boolean> {
    const query = excludeId 
      ? 'SELECT id FROM libros WHERE isbn = ? AND id != ?'
      : 'SELECT id FROM libros WHERE isbn = ?';
    
    const params = excludeId ? [isbn, excludeId] : [isbn];
    const [rows] = await pool.query<BookRow[]>(query, params);
    
    return rows.length > 0;
  }
}
