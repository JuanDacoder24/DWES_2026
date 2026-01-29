import { Request, Response } from 'express';
import { BookService } from '../services/bookService';
import { validateBook } from '../utils/validators';

const bookService = new BookService();

export class BookController {
  
  // GET /api/books - Obtener todos los libros
  async getAll(req: Request, res: Response): Promise<void> {
    try {
      const books = await bookService.getAllBooks();
      res.status(200).json({
        success: true,
        count: books.length,
        data: books
      });
    } catch (error) {
      res.status(500).json({
        success: false,
        message: 'Error al obtener los libros',
        error: error instanceof Error ? error.message : 'Error desconocido'
      });
    }
  }

  // GET /api/books/:id - Obtener un libro por ID
  async getById(req: Request, res: Response): Promise<void> {
    try {
      const id = parseInt(req.params.id);
      
      if (isNaN(id)) {
        res.status(400).json({
          success: false,
          message: 'ID inválido'
        });
        return;
      }

      const book = await bookService.getBookById(id);
      
      if (!book) {
        res.status(404).json({
          success: false,
          message: 'Libro no encontrado'
        });
        return;
      }

      res.status(200).json({
        success: true,
        data: book
      });
    } catch (error) {
      res.status(500).json({
        success: false,
        message: 'Error al obtener el libro',
        error: error instanceof Error ? error.message : 'Error desconocido'
      });
    }
  }

  // GET /api/books/search?q=query - Buscar libros
  async search(req: Request, res: Response): Promise<void> {
    try {
      const query = req.query.q as string;
      
      if (!query || query.trim().length === 0) {
        res.status(400).json({
          success: false,
          message: 'Parámetro de búsqueda requerido'
        });
        return;
      }

      const books = await bookService.searchBooks(query);
      
      res.status(200).json({
        success: true,
        count: books.length,
        data: books
      });
    } catch (error) {
      res.status(500).json({
        success: false,
        message: 'Error al buscar libros',
        error: error instanceof Error ? error.message : 'Error desconocido'
      });
    }
  }

  // POST /api/books - Crear un nuevo libro
  async create(req: Request, res: Response): Promise<void> {
    try {
      const validation = validateBook(req.body);
      
      if (!validation.isValid) {
        res.status(400).json({
          success: false,
          message: 'Datos inválidos',
          errors: validation.errors
        });
        return;
      }

      // Verificar si el ISBN ya existe
      const isbnExists = await bookService.isbnExists(req.body.isbn);
      if (isbnExists) {
        res.status(409).json({
          success: false,
          message: 'Ya existe un libro con este ISBN'
        });
        return;
      }

      const newBook = await bookService.createBook(req.body);
      
      res.status(201).json({
        success: true,
        message: 'Libro creado exitosamente',
        data: newBook
      });
    } catch (error) {
      res.status(500).json({
        success: false,
        message: 'Error al crear el libro',
        error: error instanceof Error ? error.message : 'Error desconocido'
      });
    }
  }

  // PUT /api/books/:id - Actualizar un libro
  async update(req: Request, res: Response): Promise<void> {
    try {
      const id = parseInt(req.params.id);
      
      if (isNaN(id)) {
        res.status(400).json({
          success: false,
          message: 'ID inválido'
        });
        return;
      }

      // Verificar si el libro existe
      const existingBook = await bookService.getBookById(id);
      if (!existingBook) {
        res.status(404).json({
          success: false,
          message: 'Libro no encontrado'
        });
        return;
      }

      // Si se actualiza el ISBN, verificar que no exista en otro libro
      if (req.body.isbn && req.body.isbn !== existingBook.isbn) {
        const isbnExists = await bookService.isbnExists(req.body.isbn, id);
        if (isbnExists) {
          res.status(409).json({
            success: false,
            message: 'Ya existe otro libro con este ISBN'
          });
          return;
        }
      }

      const updated = await bookService.updateBook(id, req.body);
      
      if (!updated) {
        res.status(500).json({
          success: false,
          message: 'No se pudo actualizar el libro'
        });
        return;
      }

      const updatedBook = await bookService.getBookById(id);
      
      res.status(200).json({
        success: true,
        message: 'Libro actualizado exitosamente',
        data: updatedBook
      });
    } catch (error) {
      res.status(500).json({
        success: false,
        message: 'Error al actualizar el libro',
        error: error instanceof Error ? error.message : 'Error desconocido'
      });
    }
  }

  // DELETE /api/books/:id - Eliminar un libro
  async delete(req: Request, res: Response): Promise<void> {
    try {
      const id = parseInt(req.params.id);
      
      if (isNaN(id)) {
        res.status(400).json({
          success: false,
          message: 'ID inválido'
        });
        return;
      }

      const book = await bookService.getBookById(id);
      if (!book) {
        res.status(404).json({
          success: false,
          message: 'Libro no encontrado'
        });
        return;
      }

      const deleted = await bookService.deleteBook(id);
      
      if (!deleted) {
        res.status(500).json({
          success: false,
          message: 'No se pudo eliminar el libro'
        });
        return;
      }

      res.status(200).json({
        success: true,
        message: 'Libro eliminado exitosamente'
      });
    } catch (error) {
      res.status(500).json({
        success: false,
        message: 'Error al eliminar el libro',
        error: error instanceof Error ? error.message : 'Error desconocido'
      });
    }
  }
}
