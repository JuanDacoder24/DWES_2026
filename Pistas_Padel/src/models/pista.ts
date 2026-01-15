import { Model, DataTypes } from 'sequelize';
import sequelize from '../config/database';

class Pista extends Model {
    public id!: number;
    public nombre!: string;
    public tipo!: 'INDOOR' | 'OUTDOOR';
    public precio_hora!: number; 
}

Pista.init(
    {
        id: {
            type: DataTypes.INTEGER,
            autoIncrement: true,
            primaryKey: true,
        },
        nombre: {
            type: DataTypes.STRING,
            allowNull: false,
            unique: true, 
        },
        tipo: {
            type: DataTypes.ENUM('INDOOR', 'OUTDOOR'), // ← CORREGIDO: sin espacio
            allowNull: false,
            defaultValue: 'INDOOR'
        },
        precio_hora: { 
            type: DataTypes.DECIMAL(7, 2),
            allowNull: false,
            defaultValue: 0.00
        }
    },
    {
        sequelize,
        tableName: 'pistas',
        timestamps: true, // ← AGREGADO: para created_at y updated_at
        underscored: true, // ← AGREGADO: convierte camelCase a snake_case
    }
);

export default Pista;