import { Book } from '../models/bookModel';

interface ValidationResult {
  isValid: boolean;
  errors: string[];
}

export const validateBook = (book: Partial<Book>): ValidationResult => {
  const errors: string[] = [];

  if (!book.titulo || book.titulo.trim().length === 0) {
    errors.push('El título es obligatorio');
  }

  if (!book.autor || book.autor.trim().length === 0) {
    errors.push('El autor es obligatorio');
  }

  if (!book.isbn || book.isbn.trim().length === 0) {
    errors.push('El ISBN es obligatorio');
  } else if (!/^[0-9-]{10,17}$/.test(book.isbn.replace(/\s/g, ''))) {
    errors.push('El ISBN no tiene un formato válido');
  }

  if (!book.editorial || book.editorial.trim().length === 0) {
    errors.push('La editorial es obligatoria');
  }

  if (!book.anio_publicacion) {
    errors.push('El año de publicación es obligatorio');
  } else if (book.anio_publicacion < 1000 || book.anio_publicacion > new Date().getFullYear()) {
    errors.push('El año de publicación no es válido');
  }

  if (!book.genero || book.genero.trim().length === 0) {
    errors.push('El género es obligatorio');
  }

  return {
    isValid: errors.length === 0,
    errors
  };
};
