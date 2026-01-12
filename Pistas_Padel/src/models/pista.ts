import { Model, DataTypes } from 'sequelize';

import sequelize from '../config/database';

class Pista extends Model{
    public id!: number 
    public nombre!: string
    public tipo!: 'INDOOR' | 'OUTDOOR '
    public precio!: number
}

Pista.init(
    {
        id: {
            type: DataTypes.INTEGER,
            autoIncrement: true,
            primaryKey: true,
        },
        nombre:{
            type: DataTypes.STRING,
            allowNull: false,
        },
        tipo:{
            type: DataTypes.ENUM('INDOOR' , 'OUTDOOR '),
            allowNull: false,
            defaultValue: 'INDOOR'
        }
    },
    {
        sequelize,
        tableName: 'pistas',
    }
)
export default Pista

