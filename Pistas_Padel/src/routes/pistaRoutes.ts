import { Router } from "express";
import { obtenerPistas, crearPista } from "../controllers/pistaController";

const router = Router()
router.get('/pistas', obtenerPistas)
router.post('pistas', crearPista)