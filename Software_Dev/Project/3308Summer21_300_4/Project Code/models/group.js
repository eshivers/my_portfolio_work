'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Group extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      Group.hasMany(models.GroupLine,{foreignKey: 'group_id'});
      Group.belongsTo(models.Ride,{foreignKey: 'ride_id' });
    }
  };
  Group.init({
    name: {
      type: DataTypes.STRING,
      allowNull: false
    },
    ride_id:{
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: 'Rides',
        key: 'id'
      }
    }
  }, {
    sequelize,
    modelName: 'Group',
  });
  return Group;
};