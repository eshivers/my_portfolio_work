'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class RideRate extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      RideRate.belongsTo(models.User,{foreignKey: 'rater_id' });
      RideRate.belongsTo(models.User,{foreignKey: 'ratee_id' });
      RideRate.belongsTo(models.Ride,{foreignKey: 'ride_id' });
    }
  };
  RideRate.init({
    ride_id: {
      type: DataTypes.INTEGER,
      references: {
          model: 'Rides',
          key: 'id'
      }
    },
    ratee_id:{
      type: DataTypes.INTEGER,
      references: {
          model: 'Users',
          key: 'id'
      }
    },
    rater_id:{
      type: DataTypes.INTEGER,
      references: {
          model: 'Users',
          key: 'id'
      }
    },
    rating:{
      type: DataTypes.INTEGER
    }
  }, {
    sequelize,
    modelName: 'RideRate',
  });
  return RideRate;
};