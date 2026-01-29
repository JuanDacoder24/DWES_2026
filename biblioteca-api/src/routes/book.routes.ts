import { Router } from 'express';
import { BookController } from '../controller/bookController';

const router = Router();
const bookController = new BookController();

// Rutas para libros
router.get('/books/search', (req, res) => bookController.search(req, res));
router.get('/books', (req, res) => bookController.getAll(req, res));
router.get('/books/:id', (req, res) => bookController.getById(req, res));
router.post('/books', (req, res) => bookController.create(req, res));
router.put('/books/:id', (req, res) => bookController.update(req, res));
router.delete('/books/:id', (req, res) => bookController.delete(req, res));

export default router;
