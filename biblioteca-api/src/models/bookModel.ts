import { RowDataPacket, ResultSetHeader } from 'mysql2';

export interface Book {
  id?: number;
  titulo: string;
  autor: string;
  isbn: string;
  editorial: string;
  anio_publicacion: number;
  genero: string;
  disponible: boolean;
  created_at?: Date;
  updated_at?: Date;
}

export interface BookRow extends RowDataPacket, Book {}

export interface BookResult extends ResultSetHeader {
  insertId: number;
}
