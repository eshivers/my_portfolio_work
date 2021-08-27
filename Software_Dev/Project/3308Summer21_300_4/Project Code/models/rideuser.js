'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class RideUser extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      RideUser.belongsTo(models.User,{foreignKey: 'user_id' });
      RideUser.belongsTo(models.Ride,{foreignKey: 'ride_id' });
    }
  };
  RideUser.init({
    ride_id: {
      type: DataTypes.INTEGER,
      references: {
          model: 'Rides',
          key: 'id'
      }
    },
    user_id:{
          type: DataTypes.INTEGER,
          references: {
              model: 'Users',
              key: 'id'
          }
    }
  }, {
    sequelize,
    modelName: 'RideUser',
  });
  return RideUser;
};