import usersRouter from './routes/users.routes.js'
import express from 'express'

const app = express()

app.use(usersRouter)