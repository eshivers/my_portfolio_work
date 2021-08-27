'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Destination extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      Destination.hasMany(models.Ride,{foreignKey: 'dest_id'})
      Destination.hasMany(models.UserFav,{foreignKey: 'dest_id'})
    }
  };
  Destination.init({
    name: {
      type: DataTypes.STRING,
      unique: false,
      allowNull: false 
    },
    long:{
        type: DataTypes.FLOAT,
        allowNull: false,
    },
    lat: {
        type: DataTypes.FLOAT,
        allowNull: false,
    },
    address:{
        type: DataTypes.STRING,
        allowNull: false
    },
  }, {
    sequelize,
    modelName: 'Destination',
  });
  return Destination;
};