import { Router } from 'express';
import { ReservaController } from '../controllers/reservaController';

const router = Router();

// POST /reservas - Crear reserva
router.post('/', ReservaController.crear);

// GET /reservas - Listar reservas 
router.get('/', ReservaController.listar);

// GET /reservas/:id - Obtener una reserva
router.get('/:id', ReservaController.obtenerPorId);

// DELETE /reservas/:id - Cancelar/eliminar reserva
router.delete('/:id', ReservaController.eliminar);

export default router;