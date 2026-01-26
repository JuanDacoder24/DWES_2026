
// reserva.routes.ts
import { Router } from 'express';
import { crearReserva, listarReservas, eliminarReserva } from '../controllers/reservaController';

const router = Router();

// POST /reservas - Crear reserva
router.post('/', crearReserva);

// GET /reservas - Listar reservas 
router.get('/', listarReservas);

// DELETE /reservas/:id - Cancelar/eliminar reserva
router.delete('/:id', eliminarReserva);

export default router;