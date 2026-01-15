import { Model, DataTypes } from 'sequelize';
import sequelize from '../config/database';
import Pista from './pista';

class Reserva extends Model {
    public id!: number;
    public pista_id!: number;
    public fecha!: Date;
    public hora_inicio!: string;
    public hora_fin!: string;

    // Timestamps
    public readonly createdAt!: Date;
    public readonly updatedAt!: Date;
}

Reserva.init(
    {
        id: {
            type: DataTypes.INTEGER,
            autoIncrement: true,
            primaryKey: true,
        },
        pista_id: {
            type: DataTypes.INTEGER,
            allowNull: false,
            references: {
                model: 'pistas',
                key: 'id',
            },
        },
        fecha: {
            type: DataTypes.DATEONLY,
            allowNull: false,
        },
        hora_inicio: {
            type: DataTypes.TIME,
            allowNull: false,
        },
        hora_fin: {
            type: DataTypes.TIME,
            allowNull: false,
        },
    },
    {
        sequelize,
        tableName: 'reservas',
        timestamps: true,
        underscored: true,
        indexes: [
            {
                unique: true,
                fields: ['pista_id', 'fecha', 'hora_inicio', 'hora_fin'],
            },
        ],
    }
);

// Relación con Pista
Reserva.belongsTo(Pista, {
    foreignKey: 'pista_id',
    as: 'pista',
});

export default Reserva;