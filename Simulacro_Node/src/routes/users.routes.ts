import { Router } from "express";
import { createUser, deleteUser, getUsers, getUsersById, updateUser } from "../controllers/users.controller.js";

const router = Router()

router.get('/Users', getUsers)
router.get('/User/:id', getUsersById)
router.post('/User', createUser)
router.put('/User', updateUser)
router.delete('/User', deleteUser)

export default router